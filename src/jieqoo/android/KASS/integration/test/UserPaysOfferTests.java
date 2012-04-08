package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;
import jieqoo.android.KASS.OfferActivity;
import jieqoo.android.KASS.test.Fixtures;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

public class UserPaysOfferTests extends IntegrationBaseTests {

	public UserPaysOfferTests(String name) {
		super(name);
	}
	
	public UserPaysOfferTests() {
		this("UserPaysOfferTests");
	}
	
	public final void testUserPaysAnOfferSuccessfully() throws JSONException {
		JSONObject user = createUser();
		JSONObject listing = createListing();
		signoutUser();
		
		JSONObject offerer = createUser();
		JSONObject offer = createOffer(listing.getString("id"));
		signoutUser();
		
		signinUser(user.getString("email"), user.getString("password"));
		acceptOffer(offer.getString("id"));
		
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("支付"));
		
		solo.clickOnText(listing.getString("title"));
		
		solo.assertCurrentActivity("OfferActivity", OfferActivity.class);

		// Pay!
		assertTrue(solo.searchButton("支付", true));
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		assertTrue(solo.searchText("交易已支付", true));
	}
}
