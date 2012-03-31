package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSignsOutTests extends IntegrationBaseTests {

	public UserSignsOutTests(String name) {
		super(name);
	}
	
	public UserSignsOutTests() {
		this("UserSignsOutTests");
	}
	
	public final void testSignsOut() throws JSONException {
		JSONObject userJSON = createUser();
		signoutUser(); // This is factory signout. Not what we are testing.

		String email = userJSON.getString("email");
		String password = userJSON.getString("password");

		solo.clickOnScreen(Fixtures.ACCOUNT_X, Fixtures.MENU_Y);
		solo.waitForActivity("SignIn");
		solo.enterText(0, email);
		solo.enterText(1, password);
		solo.clickOnButton("登录");
		solo.waitForText("我要买");
		
		assertTrue(Account.getInstance().isAuthenticated());

		solo.clickOnScreen(Fixtures.ACCOUNT_X, Fixtures.MENU_Y);
		solo.waitForActivity("MyProfile");
		
		solo.clickOnButton("注销");
		solo.waitForText("登录");
		
		assertFalse(Account.getInstance().isAuthenticated());
	}
}
