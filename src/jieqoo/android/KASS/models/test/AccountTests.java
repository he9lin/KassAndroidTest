/**
 * 
 */
package jieqoo.android.KASS.models.test;

import org.json.JSONException;
import org.json.JSONObject;

import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.test.Factory;
import junit.framework.TestCase;

/**
 * @author linhe
 * 
 */
public class AccountTests extends TestCase {
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
}
