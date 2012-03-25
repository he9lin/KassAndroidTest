package jieqoo.android.KASS.test;

import jieqoo.android.model.Account;
import jieqoo.android.model.AccountCreateTask;
import jieqoo.android.util.RESTListener;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountCreateTaskTests extends TestCase {
	
	public final void testRun() {
		// Build params
		final JSONObject params = new JSONObject();
		try {
			params.put("name", "kass" + System.currentTimeMillis());
			params.put("password", "secret");
			params.put("phone_number", "12345678901");
			params.put("email", "kass" + System.currentTimeMillis() + "@example.com");
		} catch(JSONException e) {
			
		}

		AccountCreateTask task = new AccountCreateTask(params.toString(), new RESTListener() {
			public void onSuccess(Object response) {}
			public void onError(Object response) {}
		});
		
		task.run();
        
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
