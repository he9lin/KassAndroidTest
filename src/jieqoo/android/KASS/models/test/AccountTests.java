/**
 * 
 */
package jieqoo.android.KASS.models.test;

import jieqoo.android.KASS.Main;
import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.models.IntentMeta;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.test.Factory;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.util.Log;

/**
 * @author linhe
 * 
 */
public class AccountTests extends TestCase {
	private static final String TAG = "AccountTests";
	private Account account;
	private boolean called;

	/**
	 * 
	 */
	public AccountTests() {
	}

	protected void setUp() throws Exception {
		super.setUp();
		account = Account.getInstance();
		called = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		// TODO: clear account
	}

	/**
	 * @param name
	 */
	public AccountTests(String name) {
		super(name);
	}

	public final void testSingleton() {
		assertEquals(this.account, Account.getInstance());
	}

	public final void testCreate() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", "kass");
			obj.put("password", "secret");
			obj.put("phone_number", "12345678901");
			obj.put("email", "kass@example.com");
		} catch (JSONException e) {

		}
		// account.create(obj.toString());
		// Should just test if AccounCreateTask is run
	}

	public final void testAuthenticated() {
		JSONObject params = new JSONObject();
		try {
			params.put("id", "123");
			params.put("username", "kass");
		} catch(JSONException e) {
			
		}
		account.set(params);
		assertTrue(account.isAuthenticated());
	}
	
	public final void testSignin() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		String email = userJSON.getString("email");
		String password = userJSON.getString("password");
		
		account.signout(Factory.dummyRestListener());
		
		account.signin(email, password, new RESTListener() {
			@Override
			public void onSuccess(Object response) {
				called = true;
			}

			@Override
			public void onError(Object response) {
			}
		});
		assertTrue(Factory.acquireAuth());
		assertTrue(called);
	}
	
	public final void testSignout() throws JSONException {
		Factory.createUser();
		account.signout(new RESTListener() {
			@Override
			public void onSuccess(Object response) {
				called = true;
			}

			@Override
			public void onError(Object response) {
			}
		});
		assertFalse(Factory.acquireAuth());
		assertTrue(called);
	}
	
	public final void testRegisterAndConsumeAfterAuthenticationIntention() {
		IntentMeta im = new IntentMeta(Main.class);
		account.registerAfterAuthenticationIntention(im);
		IntentMeta im2 = account.consumeAfterAuthenticationIntention();
		assertNotNull(im2);
		assertEquals(Main.class, im2.getActivityClass());
		assertNull(account.consumeAfterAuthenticationIntention());
	}
	
	public final void testShowVerifications() throws JSONException, ParseException {
		String raw = "{\"id\":\"4f8cdf4acf60210172000003\",\"name\":\"user1334632264078\",\"email\":\"user1334632264079@example.com\",\"phone_number\":\"12345678901\",\"default_city_id\":null,\"unread_messages_count\":0,\"timg_url\":\"http://jieqoo.com/assets/headbg.gif\",\"verification\":{\"sources\":[{\"type\":\"email\",\"type_name\":\"\u90ae\u7bb1\",\"verified\":false},{\"type\":\"tsina\",\"type_name\":\"\u65b0\u6d6a\u5fae\u535a\",\"verified\":false},{\"type\":\"phone\",\"type_name\":\"\u7535\u8bdd\u53f7\u7801\",\"verified\":false,\"phone\":\"12345678901\"}],\"member_since\":\"2012-04-17T11:04:07+0800\"}}";
		account.set(new JSONObject(raw));
		Log.d(TAG, account.getVerifications().toString());
	}
}
