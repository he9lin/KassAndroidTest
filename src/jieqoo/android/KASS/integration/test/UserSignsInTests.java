package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.test.Factory;
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

		clickOnProfileMainTab();
		clickOnSigninButton();
		
		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.enterText(0, email);
		solo.enterText(1, password);
		
		clickOnSigninButton();
		solo.waitForText("我要买");
		
		assertTrue(Account.getInstance().isAuthenticated());
	}
	
	public final void testSigninWithInvalidPassword() throws JSONException {
		JSONObject userJSON = createUser();
		signoutUser();

		String email = userJSON.getString("email");

		clickOnProfileMainTab();
		clickOnSigninButton();

		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.enterText(0, email);
		solo.enterText(1, "invalid");
		clickOnSigninButton();
		
		assertTrue(solo.searchText("邮箱或者密码不对"));
	}
}
