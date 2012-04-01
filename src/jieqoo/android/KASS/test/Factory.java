package jieqoo.android.KASS.test;

import java.io.IOException;

import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.models.Listing;
import jieqoo.android.KASS.models.Offer;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.models.RESTResponse;
import jieqoo.android.KASS.tasks.AccountCreateTask;
import jieqoo.android.KASS.util.Configuration;
import jieqoo.android.KASS.util.REST;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
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
	
	public static JSONObject createOffer(String listingId) {
		final JSONObject params = new JSONObject();
		JSONObject offerJSON = new JSONObject();
		
		try {
			params.put("price", 37.37);
			params.put("message", "This is an offer");
			params.put("listing_id", listingId);
		} catch (JSONException e) {
			Log.d(TAG, "createOffer: Error building JSONObject");
		}
		
		String url = new Offer().getUrl();
		try {
			offerJSON = (JSONObject)REST.postJSON(url, params.toString()).getResponseBody();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			return offerJSON.getJSONObject("offer");
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		
		return offerJSON;
	}
	
	public static JSONObject createListing(double price) {
		final JSONObject params = new JSONObject();
		JSONObject listingJSON = new JSONObject();
		
		try {
			params.put("title", "Bartender" + System.currentTimeMillis());
			params.put("latlng", new JSONArray("[" + Fixtures.LAT_LNG.HZ + "]"));
			params.put("price", price);
			params.put("time", "2d");
		} catch (JSONException e) {
			Log.d(TAG, "Error building JSONObject");
		}
		
		String url = new Listing().getUrl();
		try {
			listingJSON = (JSONObject)REST.postJSON(url, params.toString()).getResponseBody();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return listingJSON;
	}
	
	// Needs user to be authenticated first!
	public static JSONObject createListing() {
		return createListing(3000);
	}
	
	public static void acceptOffer(String id) {
		try {
			REST.postJSON(Configuration.PREFIX + Configuration.HOST + "/v1/offers/" + id + "/accept.json", "");
		} catch (IOException e) {
			Log.d(TAG, "Error accepting offer");
			e.printStackTrace();
		} catch (JSONException e) {
			Log.d(TAG, "Error accepting offer");
			e.printStackTrace();
		}
	}
	
	public static JSONObject signinUser(String email, String password) {
		final JSONObject params = new JSONObject();
		try {
			params.put("email", email);
			params.put("password", password);
		} catch (JSONException e) {
			Log.d(TAG, "Error building JSONObject");
		}
		try {
			RESTResponse response = REST.postJSON(Configuration.PREFIX + Configuration.HOST + "/v1/auth", params.toString());
			Account.getInstance().set((JSONObject)response.getResponseBody()); // Setting the account!
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public static JSONObject createUser() {
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
				public void onSuccess(Object response) { Account.getInstance().set((JSONObject)response); }
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
