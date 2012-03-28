package jieqoo.android.KASS.tasks.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jieqoo.android.KASS.mocks.MockHttpJSONGetTask;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.models.RESTResponse;
import jieqoo.android.KASS.test.Fixtures;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class MockHttpJSONGetTaskTests extends InstrumentationTestCase {
	private static final String TAG = "MockRESTRequestTaskTests";
	private static boolean called;

	protected void setUp() throws Exception {
		super.setUp();
		called = false;
	}
	
	public final void testFailureFetch() throws Throwable {
		// create  a signal to let us know when our task is done.
	    final CountDownLatch signal = new CountDownLatch(1);

		// Execute the async task on the UI thread! THIS IS KEY!
	    runTestOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	new MockHttpJSONGetTask(401, Fixtures.LISTING, new RESTListener() {
	    			public void onSuccess(Object response) { 
	    			}
	    			public void onError(Object response) { 
	    				Log.d(TAG, "RESTListener: on error callback");
	    				called = true;
	    			}			
	    		}) {
	    			@Override
	    	        protected void onPostExecute(RESTResponse response) {
	    				Log.d(TAG, "running onPostExecute");
	    	            super.onPostExecute(response);

	    	            /* This is the key, normally you would use some type of listener
	    	             * to notify your activity that the async call was finished.
	    	             * 
	    	             * In your test method you would subscribe to that and signal
	    	             * from there instead.
	    	             */
	    	            signal.countDown();
	    	        }
	    		}.execute();                
	        }
	    });       

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 10 seconds passes and it times out.
	     */        
	    signal.await(10, TimeUnit.SECONDS);
		assertTrue(called);
	}
	
	public final void testSuccessfulFetch() throws Throwable {
		// create  a signal to let us know when our task is done.
	    final CountDownLatch signal = new CountDownLatch(1);

		// Execute the async task on the UI thread! THIS IS KEY!
	    runTestOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	new MockHttpJSONGetTask(200, Fixtures.LISTING, new RESTListener() {
	    			public void onSuccess(Object response) { 
	    				Log.d(TAG, "RESTListener: on success callback");
	    				called = true;
	    			}
	    			public void onError(Object response) { }			
	    		}) {
	    			@Override
	    	        protected void onPostExecute(RESTResponse response) {
	    				Log.d(TAG, "running onPostExecute");
	    	            super.onPostExecute(response);

	    	            /* This is the key, normally you would use some type of listener
	    	             * to notify your activity that the async call was finished.
	    	             * 
	    	             * In your test method you would subscribe to that and signal
	    	             * from there instead.
	    	             */
	    	            signal.countDown();
	    	        }
	    		}.execute();                
	        }
	    });       

	    /* The testing thread will wait here until the UI thread releases it
	     * above with the countDown() or 10 seconds passes and it times out.
	     */        
	    signal.await(10, TimeUnit.SECONDS);
		assertTrue(called);
	}

}
