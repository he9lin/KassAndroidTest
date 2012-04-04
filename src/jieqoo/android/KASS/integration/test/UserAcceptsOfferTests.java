package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;
import jieqoo.android.KASS.OfferActivity;
import jieqoo.android.KASS.test.Factory;
import jieqoo.android.KASS.test.Fixtures;
import jieqoo.android.KASS.util.Configuration;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.util.Log;

public class UserAcceptsOfferTests extends IntegrationBaseTests {

	public UserAcceptsOfferTests(String name) {
		super(name);
	}
	
	public UserAcceptsOfferTests() {
		this("UserAcceptsOfferTests");
	}
	
	public final void testAcceptsAnOfferSuccessfully() throws JSONException {
		JSONObject user = createUser();
		JSONObject listing = createListing();
		signoutUser();
		JSONObject offerer = createUser();
		JSONObject offer = createOffer(listing.getString("id"));
		signoutUser();
		
		signinUser(user.getString("email"), user.getString("password"));
		
		solo.clickOnScreen(Fixtures.BROWSE_X, Fixtures.MENU_Y); // browse
		solo.clickOnScreen(Fixtures.ACTIVITY_X, Fixtures.MENU_Y); // my activity
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("1个出价"));
		
		solo.clickOnText(listing.getString("title"));
		solo.clickOnText(offer.getString("message"));
		
		assertTrue(solo.searchButton("接受出价", true));
		
		solo.assertCurrentActivity("OfferActivity", OfferActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		assertTrue(solo.searchButton("支付", true));
		
		// Heavily depend on the flow. Bad!
		solo.goBack();
		solo.goBack();
		
		solo.clickOnScreen(Fixtures.BROWSE_X, Fixtures.MENU_Y);
		solo.clickOnScreen(Fixtures.ACTIVITY_X, Fixtures.MENU_Y);
		solo.clickOnText("我要买");
		assertTrue(solo.searchText("支付"));
		
		//
		signoutUser();
		signinUser(offerer.getString("email"), offerer.getString("password"));
		solo.clickOnScreen(Fixtures.ACTIVITY_X, Fixtures.MENU_Y);
		solo.clickOnText("我要卖");
		assertTrue(solo.searchText("等待付款"));
	}
}
