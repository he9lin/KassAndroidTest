package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.ListingActivity;
import jieqoo.android.KASS.ListingFormReviewActivity;
import jieqoo.android.KASS.ProvideActivity;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.SignIn;
import jieqoo.android.KASS.test.Fixtures;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UserMakesOfferTests extends IntegrationBaseTests {
	
	public UserMakesOfferTests(String name) {
		super(name);
	}
	
	public UserMakesOfferTests() {
		this("UserEditsOfferTests");
	}
	
	public final void testUserMakesOfferWithoutLogin() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		
		// click on browse
		clickOnBrowseMainTab();
		View browse = solo.getView(R.id.market_browse);
		solo.clickOnView(browse);
		solo.clickOnText(listing.getString("title"));
		solo.clickOnButton("我想出价");
		
		solo.assertCurrentActivity("SignIn", SignIn.class);
		solo.waitForActivity("SignIn");
		
		JSONObject userJSON = createUser();
		
		solo.enterText(0, userJSON.getString("email"));
		solo.enterText(1, userJSON.getString("password"));
		
		clickOnSigninButton();
		solo.assertCurrentActivity("ListingActivity", ListingActivity.class);
	}
	
	public final void testUserMakesOffer() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		
		// click on browse
		clickOnBrowseMainTab();
		View browse = solo.getView(R.id.market_browse);
		solo.clickOnView(browse);
		solo.clickOnText(listing.getString("title"));
		solo.clickOnButton("我想出价");
		solo.enterText(0, "I am a bartender");
		
		clickOnNextButton();

		solo.waitForText("提交报价");

		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		solo.assertCurrentActivity("Should go to my offer page", ProvideActivity.class);
		assertTrue(solo.searchText(listing.getString("title")));
	}
}
