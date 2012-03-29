/**
 * 
 */
package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.SignUp;
import jieqoo.android.KASS.models.Account;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

/**
 * @author linhe
 *
 */
public class SignUpTests extends
	ActivityInstrumentationTestCase2<SignUp> {
	
	private Solo solo;
	
	public SignUpTests() {
		this("SignUpTests");
	}
	
	public SignUpTests(String name) {
		super(SignUp.class);
		setName(name);
	}
	
	@Override
	public void tearDown() throws Exception {
	    solo.finishOpenedActivities();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public final void testSignup() {
		solo.enterText(0, "kass" + System.currentTimeMillis());
		solo.enterText(1, "secret");
		solo.enterText(2, "kass" + System.currentTimeMillis() + "@example.com");
		solo.enterText(3, "12345678910");
		solo.clickOnButton("注册");
		solo.waitForText("发布");
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
