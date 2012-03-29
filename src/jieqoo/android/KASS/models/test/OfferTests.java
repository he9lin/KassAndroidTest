package jieqoo.android.KASS.models.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;

import java.text.ParseException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jieqoo.android.KASS.models.Offer;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.InstrumentationTestCase;
import android.util.Log;

public class OfferTests extends InstrumentationTestCase {
	private static final String TAG = "OfferTests";
	
	private Offer offer;
	private boolean called;

	protected void setUp() throws Exception {
		super.setUp();
		offer = new Offer();
		called = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testDefaultPrice() {
		assertEquals(Offer.INVALID_PRICE, offer.getPrice());
		assertFalse(offer.isPriceSet());
	}
	
	public final void testSetListingId() {
		offer.setListingId("123");
		assertEquals("123", offer.getListingId());
	}
	
	public final void testSetListingPrice() {
		double price = 37.37;
		offer.setListingPrice(price);
		assertEquals(price, offer.getListingPrice());
	}
	
	public final void testSetPrice() {
		double price = 37.37;
		offer.setPrice(price);
		assertEquals(price, offer.getPrice());
	}
	
	public final void testSetMessage() {
		String message = "Raise your price";
		offer.setMessage(message);
		assertEquals(message, offer.getMessage());
	}
	
	public final void testClear() {
		double price = 37.37;
		String message = "Raise your price";
		offer.setPrice(price);
		offer.setMessage(message);
		offer.reset();
		assertEquals(Offer.INVALID_PRICE, offer.getPrice());
		assertEquals(null, offer.getMessage());
		assertEquals(null, offer.getId());
	}
	
	public final void testIsNew() {
		assertTrue(offer.isNew());
		offer.setId("123");
		assertFalse(offer.isNew());
	}
	
	public final void testSet() throws JSONException, ParseException {
		offer.set(new JSONObject(Fixtures.OFFER));
		assertEquals("4f7430f2cf60210f10000033", offer.getId());
	}
	
	public final void testSaveNewRecord() throws JSONException, Throwable {
		 createUser();

		 JSONObject listingJSON = createListing();
		 final String listingId = listingJSON.getString("id");
		 
		 // Now we need a different user to make the offer.
		 signoutUser();
		 createUser();
		 
		// create  a signal to let us know when our task is done.
		    final CountDownLatch signal = new CountDownLatch(1);

			// Execute the Async task on the UI thread! THIS IS KEY!
		    runTestOnUiThread(new Runnable() {
		        @Override
		        public void run() {
		    		Offer offer = new Offer();
		   		 	offer.setListingId(listingId);
		   		 	offer.setMessage("This is an offer!");
		    		offer.save(new RESTListener() {
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
	
	public final void testSaveExistingRecord() {
		
	}
}
