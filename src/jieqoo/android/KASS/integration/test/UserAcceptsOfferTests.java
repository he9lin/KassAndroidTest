package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;
import jieqoo.android.KASS.OfferActivity;
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
		createUser();
		JSONObject offer = createOffer(listing.getString("id"));
		signoutUser();
		
		signinUser(user.getString("email"), user.getString("password"));
		
		solo.clickOnScreen(250, 730); // browse
		solo.clickOnScreen(80, 730); // my activity
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("1个出价"));
		
		solo.clickOnText(listing.getString("title"));
		solo.clickOnText(offer.getString("message"));
		
		assertTrue(solo.searchButton("接受出价", true));
		
		solo.assertCurrentActivity("OfferActivity", OfferActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		assertTrue(solo.searchButton("支付", true));
	}
}
