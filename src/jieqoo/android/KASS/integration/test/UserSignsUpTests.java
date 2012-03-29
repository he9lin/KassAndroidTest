/**
 * 
 */
package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.models.Account;
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
	
	public final void testSignup() {
		signoutUser();
		
		solo.clickOnButton("登录");
		solo.clickOnButton("注册");
		solo.enterText(0, "kass" + System.currentTimeMillis());
		solo.enterText(1, "secret");
		solo.enterText(2, "kass" + System.currentTimeMillis() + "@example.com");
		solo.enterText(3, "12345678910");
		solo.clickOnButton("注册");
		solo.waitForText("发布");
		assertTrue(Account.getInstance().isAuthenticated());
	}
}
