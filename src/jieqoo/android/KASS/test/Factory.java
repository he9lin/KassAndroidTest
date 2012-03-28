package jieqoo.android.KASS.test;

import java.io.IOException;

import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.models.RESTResponse;
import jieqoo.android.KASS.tasks.AccountCreateTask;
import jieqoo.android.KASS.util.Configuration;
import jieqoo.android.KASS.util.REST;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Factory {
	private static final String TAG = "Factory";
	
	public static RESTListener dummyRestListener() {
		return new RESTListener() {
			@Override
			public void onSuccess(Object response) { }

			@Override
			public void onError(Object response) { }
		};
	}
	
	public static String generateName() {
		return "user" + System.currentTimeMillis(); 
	}
	
	public static String generateEmail() {
		return "user" + System.currentTimeMillis() + "@example.com";
	}
	
	public static JSONObject createUser() {
		// Build params. TODO: Move to a factory.
		final JSONObject params = new JSONObject();
		try {
			params.put("name", generateName());
			params.put("password", "secret");
			params.put("phone_number", "12345678901");
			params.put("email", generateEmail());
		} catch (JSONException e) {
			Log.d(TAG, "Error building JSONObject");
		}

		new AccountCreateTask(params.toString(),
			new RESTListener() {
				public void onSuccess(Object response) { }
				public void onError(Object response) { }
			}
		).run();
		
		return params;
	}
	
	public static boolean acquireAuth() {
		boolean authenticated = false;
		
		try {
			RESTResponse response = REST.getJSON(Configuration.PREFIX + Configuration.HOST + "/v1/auth", "");
			int statusCode = response.getResponse().getStatusLine().getStatusCode();
			Log.d(TAG, "Received status code: " + statusCode);
			if (statusCode == 200) {
				Log.d(TAG, "Account authenticated");
				authenticated = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return authenticated;
	}

	public static void signoutUser() {
		Account.getInstance().signout(dummyRestListener());
	}
}
