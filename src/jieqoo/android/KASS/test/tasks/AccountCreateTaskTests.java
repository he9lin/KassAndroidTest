package jieqoo.android.KASS.test.tasks;

import jieqoo.android.models.Account;
import jieqoo.android.models.RESTListener;
import jieqoo.android.tasks.AccountCreateTask;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AccountCreateTaskTests extends TestCase {
	private static final String TAG = "AccountCreateTaskTests";
	private static boolean called;
	
	protected void setUp() throws Exception {
		super.setUp();
		called = false;
	}
	
	public final void testSuccessfulAccountCreation() {
		
		// Build params. TODO: Move to a factory.
		final JSONObject params = new JSONObject();
		try {
			params.put("name", "kass" + System.currentTimeMillis());
			params.put("password", "secret");
			params.put("phone_number", "12345678901");
			params.put("email", "kass" + System.currentTimeMillis() + "@example.com");
		} catch(JSONException e) {
			Log.d(TAG, "Error building JSONObject");
		}

		AccountCreateTask task = new AccountCreateTask(params.toString(), new RESTListener() {
			public void onSuccess(Object response) { called = true; }
			public void onError(Object response) {}
		});
		
		task.run();
        
		assertTrue(called);
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
