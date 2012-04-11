/**
 * 
 */
package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.R;
import jieqoo.android.KASS.models.Account;
import jieqoo.android.KASS.test.Fixtures;
import static jieqoo.android.KASS.test.Factory.*;

/**
 * @author linhe
 *
 */
public class UserSignsUpTests extends IntegrationBaseTests {
	
	public UserSignsUpTests() {
		this("SignUpTests");
	}
	
	public UserSignsUpTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		signoutUser();
		clickOnProfileMainTab();
		clickOnSignupButton();
	}
	
	public final void testSignup() {
		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.clearEditText(2);
		solo.clearEditText(3);
		solo.enterText(0, "kass" + System.currentTimeMillis());
		solo.enterText(1, "secret");
		solo.enterText(2, "kass" + System.currentTimeMillis() + "@example.com");
		solo.enterText(3, "12345678910");
		clickOnSignupButton();
		solo.waitForActivity("Main");
		assertTrue(Account.getInstance().isAuthenticated());
	}
	
	public final void testSignupWithInvalidPassword() {
		solo.enterText(0, "kass" + System.currentTimeMillis());
		solo.enterText(1, "s"); // invalid
		solo.enterText(2, "kass" + System.currentTimeMillis() + "@example.com");
		solo.enterText(3, "12345678910");
		clickOnSignupButton();
		assertTrue(solo.searchText("过短"));
	}
	
	public final void testSignupWithInvalidEmail() {
		solo.enterText(0, "kass" + System.currentTimeMillis());
		solo.enterText(1, "secret"); 
		solo.enterText(2, "kass" + System.currentTimeMillis()); // invalid
		solo.enterText(3, "12345678910");
		clickOnSignupButton();
		assertTrue(solo.searchText("无效"));
	}
	
	public final void testSignupWithInvalidPhoneNumber() {
		solo.enterText(0, "kass" + System.currentTimeMillis());
		solo.enterText(1, "secret"); 
		solo.enterText(2, "kass" + System.currentTimeMillis() + "@example.com");
		solo.enterText(3, "1234567891");
		clickOnSignupButton();
		assertTrue(solo.searchText("过短"));
		assertTrue(solo.searchText("11"));
	}
}
