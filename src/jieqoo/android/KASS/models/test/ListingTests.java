package jieqoo.android.KASS.models.test;

import java.text.ParseException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jieqoo.android.KASS.mocks.MockHttpJSONGetTask;
import jieqoo.android.KASS.models.Listing;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.test.Factory;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.InstrumentationTestCase;
import android.util.Log;

import static android.test.MoreAsserts.assertContainsRegex;

public class ListingTests extends InstrumentationTestCase {
	private static final String TAG = "ListingTests";
	private static boolean called;

	public final void testCreateFromJSONObject() throws JSONException, ParseException {
		JSONObject object = new JSONObject(Fixtures.LISTING);
		Listing listing = new Listing(object);
		assertEquals(listing.getId(), "4f6053a8cf60210e50000002");
		assertEquals(3000.0, listing.getPrice());
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		called = false;
	}
	
	public final void testUrl() {
		Listing listing = new Listing("123");
		assertContainsRegex("v1/listings/123.json", listing.getUrl());
	}
	
	public final void testSaveUrl() {
		Listing listing = new Listing();
		assertContainsRegex("v1/listings.json", listing.getUrl());
	}

	public final void testSaveListing() throws Throwable {
		
		Factory.createUser();
		Factory.acquireAuth();

		// create  a signal to let us know when our task is done.
	    final CountDownLatch signal = new CountDownLatch(1);

		// Execute the Async task on the UI thread! THIS IS KEY!
	    runTestOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	    		Listing listing = new Listing();
	    		listing.setTitle("Bartender");
	    		listing.setTimeWords("2d");
	    		listing.setPrice(37.37);
	    		try {
					listing.setLatlng(new JSONArray("[" + Fixtures.LAT_LNG.HZ + "]"));
				} catch (JSONException e) {
					Log.d(TAG, "latlng not turned to json!");
					e.printStackTrace();
				}
	        	listing.save(new RESTListener() {
	    			public void onSuccess(Object response) { 
	    				Log.d(TAG, "RESTListener: success response: " + response);
	    				called = true;
	    				signal.countDown();
	    			}
	    			public void onError(Object response) {
	    				Log.d(TAG, "RESTListener: error response: " + response);
	    				signal.countDown();
	    			}			
	    		});             
	        }
	    });       

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 10 seconds passes and it times out.
	     */        
	    signal.await(10, TimeUnit.SECONDS);
		assertTrue(called);
	}
	
	public final void testFetchListing() throws Throwable {
		// create  a signal to let us know when our task is done.
	    final CountDownLatch signal = new CountDownLatch(1);

		// Execute the async task on the UI thread! THIS IS KEY!
	    runTestOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	Listing listing = new Listing() {
	    			@Override
	    			public void fetch(RESTListener listener) {
	    				new MockHttpJSONGetTask(200, Fixtures.LISTING, listener).execute();
	    			}
	    		};
		
	        	listing.fetch(new RESTListener() {
	        		@Override
	        		public void onSuccess(Object response) {
	        			called = true;
	        			signal.countDown();
	        		}

	        		@Override
	        		public void onError(Object response) {
	        			signal.countDown();
	        		}
	        	});
	        }
	    });
	    
	    signal.await(10, TimeUnit.SECONDS);
		assertTrue(called);
	}
}
