package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createOffer;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.ProvideActivity;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;

public class UserSendsMessageOnOfferTests extends IntegrationBaseTests {

	public UserSendsMessageOnOfferTests(String name) {
		super(name);
	}

	public UserSendsMessageOnOfferTests() {
		this("UserSendsMessageOnOfferTests");
	}
	
	public final void testUserModifyPrice() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		createOffer(listing.getString("id"));
		
		clickOnMyActivityMainTab();
		
		solo.clickOnText("我要卖");
		solo.clickOnText(listing.getString("title"));
		assertTrue(solo.searchText("37.37"));
		solo.assertCurrentActivity("Should be on offer page", ProvideActivity.class);
		
		// Click on edit price button 
		View editPriceButton = solo.getView(R.id.provide_edit_price_btn);
		solo.clickOnView(editPriceButton);
		
		solo.clearEditText(0);
		solo.enterText(0, "50");
		
		clickOnDoneButton();
		
		solo.assertCurrentActivity("After editing price", ProvideActivity.class);
		assertTrue(solo.searchText("50"));
	}
	
	public final void testUserSendsMessage() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		createOffer(listing.getString("id"));
		
		clickOnMyActivityMainTab();
		
		solo.clickOnText("我要卖");
		solo.clickOnText(listing.getString("title"));
		assertTrue(solo.searchText("37.37"));
		solo.assertCurrentActivity("Should be on offer page", ProvideActivity.class);
		
	    solo.enterText(0, "Reply the offer");
	    clickOnSendButton();
		
		solo.assertCurrentActivity("After adding message", ProvideActivity.class);
		assertTrue(solo.searchText("我"));
		assertTrue(solo.searchText("Reply the offer"));
	}
}
