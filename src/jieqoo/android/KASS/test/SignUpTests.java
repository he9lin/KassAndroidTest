/**
 * 
 */
package jieqoo.android.KASS.test;

import jieqoo.android.KASS.SignUp;
import jieqoo.android.model.Account;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author linhe
 *
 */
public class SignUpTests extends
	ActivityInstrumentationTestCase2<SignUp> {
	
	private SignUp mActivity;
	private EditText nameInput, passwordInput, emailInput, phoneNumberInput;
	private Button createButton;
	
	public SignUpTests() {
		this("SignUpTests");
	}
	
	public SignUpTests(String name) {
		super(SignUp.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		mActivity = getActivity();
		
		nameInput = mActivity.getNameInput();
		passwordInput = mActivity.getPasswordInput();
		emailInput = mActivity.getEmailInput();
		phoneNumberInput = mActivity.getPhoneNumberInput();
		createButton = mActivity.getCreateButton();
	}

	@UiThreadTest
	public final void testSignup() {
		nameInput.setText("kass" + System.currentTimeMillis());
		passwordInput.setText("secret");
		emailInput.setText("kass" + System.currentTimeMillis() + "@example.com");
		phoneNumberInput.setText("12345678910");
		createButton.performClick();
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
