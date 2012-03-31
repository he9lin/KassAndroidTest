package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.ProvideActivity;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.util.Log;

public class UserMakesOfferTests extends IntegrationBaseTests {
	
	public UserMakesOfferTests(String name) {
		super(name);
	}
	
	public UserMakesOfferTests() {
		this("UserEditsOfferTests");
	}
	
	public final void testUserMakesOffer() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		
		// click on browse
		solo.clickOnScreen(250, 730);
		solo.waitForActivity("Browse");
		solo.clickOnText(listing.getString("title"));
		solo.clickOnButton("我想出价");
		solo.enterText(0, "I am a bartender");
		solo.clickOnButton("下一步");
		solo.waitForText("提交报价");
		ActivityMonitor am = solo.getActivityMonitor();
		Activity lastActivity = am.getLastActivity();
		Log.d("UserMakesOfferActivity", lastActivity.toString());
		((FinishingTouchListener)lastActivity).onFinishingTouch();
		solo.assertCurrentActivity("Should go to my offer page", ProvideActivity.class);
		assertTrue(solo.searchText(listing.getString("title")));
	}
}
