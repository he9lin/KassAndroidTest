package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

import static jieqoo.android.KASS.test.Factory.*;

public class UserSignsInTests extends IntegrationBaseTests {

	public UserSignsInTests(String name) {
		super(name);
	}

	public UserSignsInTests() {
		this("SignInTests");
	}

	public final void testSignin() throws JSONException {
		JSONObject userJSON = createUser();
		signoutUser();

		String email = userJSON.getString("email");
		String password = userJSON.getString("password");

		solo.clickOnScreen(Fixtures.ACCOUNT_X, Fixtures.MENU_Y);
		solo.waitForActivity("SignIn");
		solo.enterText(0, email);
		solo.enterText(1, password);
		solo.clickOnButton("登录");
		solo.waitForText("我要买");
		
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
