package jieqoo.android.KASS.test.tasks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jieqoo.android.models.RESTListener;
import jieqoo.android.models.RESTResponse;
import jieqoo.android.tasks.FetchRESTResponseTask;
import jieqoo.android.util.Configuration;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class FetchRESTResponseTaskTests extends InstrumentationTestCase {
	private static final String TAG = "FetchRESTResponseTaskTests";
	private static final String BOUNDS = "30.371737637334984,120.33188021826174,30.23568452886941,119.97825778173831";
	private static final String URL = "http://" + Configuration.HOST + "/v1/listings?box=" + BOUNDS;
	private static boolean called;

	protected void setUp() throws Exception {
		super.setUp();
		called = false;
	}
	
	public final void testSuccessfulFetch() throws Throwable {
		// create  a signal to let us know when our task is done.
	    final CountDownLatch signal = new CountDownLatch(1);

		// Execute the async task on the UI thread! THIS IS KEY!
	    runTestOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	new FetchRESTResponseTask(URL, "", new RESTListener() {
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
