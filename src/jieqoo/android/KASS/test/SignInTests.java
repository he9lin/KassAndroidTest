package jieqoo.android.KASS.test;

import jieqoo.android.KASS.SignIn;
import jieqoo.android.KASS.models.Account;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

public class SignInTests extends ActivityInstrumentationTestCase2<SignIn> {

	private SignIn mActivity;
	private EditText emailInput;
	private EditText passwordInput;
	private Button signinButton;

	public SignInTests(String name) {
		super(SignIn.class);
		setName(name);
	}

	public SignInTests() {
		this("SignInTests");
	}

	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		mActivity = getActivity();
		
		emailInput = mActivity.getEmailInput();
		passwordInput = mActivity.getPasswordInput();
		signinButton = mActivity.getSigninButton();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@UiThreadTest
	public final void testSignin() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		Factory.signoutUser();
		
		String email = userJSON.getString("email");
		String password = userJSON.getString("password");
		
		emailInput.setText(email);
		passwordInput.setText(password);
		signinButton.performClick();
		
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
