package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.Main;
import jieqoo.android.KASS.R;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public abstract class IntegrationBaseTests extends ActivityInstrumentationTestCase2<Main> {
	
	protected Solo solo;

	public IntegrationBaseTests(String name) {
		super(Main.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		clickOnCloseButton(); // close the initial popup
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		try {
			solo.finishOpenedActivities();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	protected void clickOnSigninMainTab() {
		solo.clickOnView(solo.getView(R.id.main_rbtn_SignIn));
	}
	
	protected void clickOnBrowseMainTab() {
		solo.clickOnView(solo.getView(R.id.main_rbtn_Browse));
	}
	
	protected void clickOnMyActivityMainTab() {
		solo.clickOnView(solo.getView(R.id.main_rbtn_MyActivity));
	}
	
	protected void clickOnPostMainTab() {
		solo.clickOnView(solo.getView(R.id.main_rbtn_Post));
	}
	
	protected void clickOnCancelButton() {
		solo.clickOnView(solo.getView(R.id.cancel_btn));		
	}
	
	protected void clickOnDoneButton() {
		solo.clickOnView(solo.getView(R.id.done_btn));		
	}
	
	protected void clickOnNextButton() {
		solo.clickOnView(solo.getView(R.id.next_btn));		
	}
	
	protected void clickOnSignupButton() {
		solo.clickOnView(solo.getView(R.id.signup_btn));
	}
	
	protected void clickOnCloseButton() {
		solo.clickOnView(solo.getView(R.id.close_btn));
	}
	
	protected void clickOnBackButton() {
		solo.clickOnView(solo.getView(R.id.back_btn));
	}
	
	protected void clickOnSendButton() {
		solo.clickOnView(solo.getView(R.id.send_btn));
	}
	
	protected void clickOnSaveButton() {
		solo.clickOnView(solo.getView(R.id.save_btn));
	}
	
	protected void clickOnSigninButton() {
		solo.clickOnView(solo.getView(R.id.signin_btn));
	}
	
	protected void clickOnSignoutButton() {
		solo.clickOnView(solo.getView(R.id.logout_btn));
	}
}
