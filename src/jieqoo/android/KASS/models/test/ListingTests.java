package jieqoo.android.KASS.models.test;

import java.text.ParseException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jieqoo.android.KASS.mocks.MockRESTRequestTask;
import jieqoo.android.KASS.models.Listing;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.InstrumentationTestCase;

import static android.test.MoreAsserts.assertContainsRegex;

public class ListingTests extends InstrumentationTestCase {
	private static boolean called;

	public final void testCreateFromJSONObject() throws JSONException, ParseException {
		JSONObject object = new JSONObject(Fixtures.LISTING);
		Listing listing = new Listing(object);
		assertEquals(listing.getId(), "4f6053a8cf60210e50000002");
		assertEquals(3000.0, listing.getPrice());
	}
	
	public final void testUrl() {
		Listing listing = new Listing("123");
		assertContainsRegex("v1/listings/123.json", listing.getUrl());
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
	    				new MockRESTRequestTask(200, Fixtures.LISTING, listener).execute();
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
