package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;

import org.json.JSONException;
import org.json.JSONObject;

import jieqoo.android.KASS.ListingFormReviewActivity;
import jieqoo.android.KASS.Main;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.SignIn;
import jieqoo.android.KASS.test.Factory;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

public class UserPostsListingTests extends IntegrationBaseTests {

	public UserPostsListingTests(String name) {
		super(name);
	}
	
	public UserPostsListingTests() {
		this("UserPostsListingTests");
	}

	public final void testUserPostsAListing() throws InterruptedException {
		JSONObject userJSON = Factory.createUser();
		try {
			Factory.signinUser(userJSON.getString("email"), userJSON.getString("password"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		clickOnPostMainTab();
		solo.clickOnView(solo.getView(R.id.post_preview_new_btn));
		
		String title = "Bartender" + System.currentTimeMillis();
		
		solo.enterText(0, title);
		clickOnNextButton();
		solo.enterText(0, "37");
		clickOnNextButton();
		solo.clickInList(0);
		clickOnNextButton();
		
		solo.assertCurrentActivity("ListingFormReviewActivity", ListingFormReviewActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		// TODO:
	}

	public final void testUserPostsAListingWithoutLoginFirst() throws InterruptedException, JSONException {
		JSONObject userJSON = createUser();
		signoutUser();
		String email = userJSON.getString("email");
		String password = userJSON.getString("password");
		
		clickOnPostMainTab();
		solo.clickOnView(solo.getView(R.id.post_preview_new_btn));
		
		String title = "Bartender" + System.currentTimeMillis();
		
		solo.enterText(0, title);
		clickOnNextButton();
		solo.enterText(0, "37");
		clickOnNextButton();
		solo.clickInList(0);
		clickOnNextButton();
		
		solo.assertCurrentActivity("ListingFormReviewActivity", ListingFormReviewActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		solo.assertCurrentActivity("SignIn", SignIn.class);
		solo.waitForActivity("SignIn");
		solo.enterText(0, email);
		solo.enterText(1, password);
		
		clickOnSigninButton();
		solo.assertCurrentActivity("ListingFormReviewActivity", ListingFormReviewActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
	}
}
