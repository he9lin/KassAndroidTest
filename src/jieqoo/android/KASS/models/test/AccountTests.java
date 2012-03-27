/**
 * 
 */
package jieqoo.android.KASS.models.test;

import org.json.JSONException;
import org.json.JSONObject;

import jieqoo.android.KASS.models.Account;
import junit.framework.TestCase;

/**
 * @author linhe
 * 
 */
public class AccountTests extends TestCase {
	private Account account;

	/**
	 * 
	 */
	public AccountTests() {
	}

	protected void setUp() throws Exception {
		super.setUp();
		account = Account.getInstance();
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
			params.put("id", "123	");
			params.put("username", "kass");
		} catch(JSONException e) {
			
		}
		account.set(params);
		assertTrue(account.isAuthenticated());
	}
}
