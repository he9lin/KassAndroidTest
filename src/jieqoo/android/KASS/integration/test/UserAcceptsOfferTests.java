package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;
import jieqoo.android.KASS.OfferActivity;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

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
		
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("1个出价"));
		
		solo.clickOnText(listing.getString("title"));
		solo.clickOnText(offer.getString("message"));
		
		solo.waitForView(solo.getView(R.id.offer_accept_btn));
		
		solo.assertCurrentActivity("OfferActivity", OfferActivity.class);
		((FinishingTouchListener)solo.getCurrentActivity()).onFinishingTouch();
		
		solo.waitForView(solo.getView(R.id.offer_pay_btn));

		// Heavily depend on the flow. Bad!
		solo.goBack();
		solo.goBack();
		
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
		
		solo.clickOnText("我要买");
		assertTrue(solo.searchText("支付"));
		
		//
		signoutUser();
		signinUser(offerer.getString("email"), offerer.getString("password"));
		
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
		
		solo.clickOnText("我要卖");
		assertTrue(solo.searchText("等待付款"));
	}
}
