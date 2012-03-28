package jieqoo.android.KASS.tasks.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.tasks.HttpJSONPostTask;
import jieqoo.android.KASS.test.Factory;
import jieqoo.android.KASS.test.Fixtures;
import jieqoo.android.KASS.util.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.InstrumentationTestCase;
import android.util.Log;

public class HttpJSONPostTaskTests extends InstrumentationTestCase {
	private static final String TAG = "HttpJSONPostTaskTests";
	private static final String URL = "http://" + Configuration.HOST + "/v1/listings";
	private static boolean called;
	
	protected void setUp() throws Exception {
		super.setUp();
		called = false;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testSuccess() throws Throwable {
		
		Factory.createUser();
		Factory.acquireAuth();

		// create  a signal to let us know when our task is done.
	    final CountDownLatch signal = new CountDownLatch(1);

		// Execute the async task on the UI thread! THIS IS KEY!
	    runTestOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	    		
	    		JSONObject params = new JSONObject();
	    		try {
					params.put("title", "Bartender" + System.currentTimeMillis());
		    		params.put("price", 37.37);
		    		params.put("time", "2d");
		    		params.put("latlng", Fixtures.LNG_LAT.HZ);
				} catch (JSONException e) {
					Log.d(TAG, "error building params");
					e.printStackTrace();
				}
	        	
	        	new HttpJSONPostTask(URL, params.toString(), new RESTListener() {
	    			public void onSuccess(Object response) { 
	    				Log.d(TAG, "RESTListener: success response: " + response);
	    				called = true;
	    				signal.countDown();
	    			}
	    			public void onError(Object response) {
	    				Log.d(TAG, "RESTListener: error response: " + response);
	    				signal.countDown();
	    			}			
	    		}).execute();                
	        }
	    });       

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 10 seconds passes and it times out.
	     */        
	    signal.await(10, TimeUnit.SECONDS);
		assertTrue(called);
	}
}
