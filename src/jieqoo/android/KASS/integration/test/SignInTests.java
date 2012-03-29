package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.SignIn;
import jieqoo.android.KASS.models.Account;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

import static jieqoo.android.KASS.test.Factory.*;

public class SignInTests extends ActivityInstrumentationTestCase2<SignIn> {

	private Solo solo;

	public SignInTests(String name) {
		super(SignIn.class);
		setName(name);
	}

	public SignInTests() {
		this("SignInTests");
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public final void testSignin() throws JSONException {
		JSONObject userJSON = createUser();
		signoutUser();

		String email = userJSON.getString("email");
		String password = userJSON.getString("password");
		
		solo.enterText(0, email);
		solo.enterText(1, password);
		solo.clickOnButton("登录");
		solo.waitForText("我要买");
		
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
