package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.ListingFormReviewActivity;
import jieqoo.android.KASS.ListingFormTitleDescActivity;
import jieqoo.android.KASS.Main;
import jieqoo.android.KASS.MyActivityWantActivity;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.SiginByWeiboActivity;
import jieqoo.android.KASS.SignIn;
import jieqoo.android.KASS.test.Factory;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

public class UserPostsListingTests extends IntegrationBaseTests {

	public UserPostsListingTests(String name) {
		super(name);
	}
	
	public UserPostsListingTests() {
		this("UserPostsListingTests");
	}
	
	public final void testUserDeleteListing() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		Factory.signinUser(userJSON.getString("email"), userJSON.getString("password"));
		JSONObject listingJSON = Factory.createListing();
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
		solo.clickOnText(listingJSON.getString("title"));
		solo.clickOnView(solo.getView(R.id.want_edit_btn));
		
		Activity activity = solo.getCurrentActivity();
		assertTrue(solo.searchText(activity.getString(R.string.delete)));
		solo.clickOnText(activity.getString(R.string.delete));
		solo.assertCurrentActivity("Back to wants", Main.class);
		assertFalse(solo.searchText(listingJSON.getString("title")));
	}
	
	public final void testUserEditsListing() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		Factory.signinUser(userJSON.getString("email"), userJSON.getString("password"));
		JSONObject listingJSON = Factory.createListing();
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
		solo.clickOnText(listingJSON.getString("title"));
		solo.clickOnView(solo.getView(R.id.want_edit_btn));
		
		Activity activity = solo.getCurrentActivity();
		assertTrue(solo.searchText(activity.getString(R.string.edit)));
		solo.clickOnText(activity.getString(R.string.edit));
		solo.assertCurrentActivity("ListingFormTitleDescActivity", ListingFormTitleDescActivity.class);
	
		String title = "New Bartender";
		solo.clearEditText(0);
		solo.enterText(0, title);
		clickOnNextButton(); 
		
		solo.clearEditText(0);
		solo.enterText(0, "50");
		clickOnNextButton();
		
		solo.clickInList(0);
		clickOnNextButton();
		
		solo.waitForActivity("ListingFormReviewActivity");
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
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
		
		solo.assertCurrentActivity("SiginByWeiboActivity", SiginByWeiboActivity.class);
		clickOnSigninButton();
		solo.clearEditText(0); solo.clearEditText(1);
		solo.enterText(0, email);
		solo.enterText(1, password);
		
		clickOnSigninButton();
		solo.assertCurrentActivity("ListingFormReviewActivity", ListingFormReviewActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
	}
}
