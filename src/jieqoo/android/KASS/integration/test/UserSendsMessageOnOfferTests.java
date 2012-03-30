package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createOffer;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.MyOfferIdleActivity;
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
		
		solo.clickOnScreen(Fixtures.ACTIVITY_X, Fixtures.MENU_Y); // my activity
		// Click on sellings
		solo.clickOnScreen(300, 50);
		solo.clickOnText(listing.getString("title"));
		assertTrue(solo.searchText("37.37"));
		solo.assertCurrentActivity("Should be on offer page", MyOfferIdleActivity.class);
		
		// Click on edit price button 
		View editPriceButton = solo.getView(R.id.my_offer_idle_edit_price_btn);
		solo.clickOnView(editPriceButton);
		
		solo.clearEditText(0);
		solo.enterText(0, "50");
		solo.clickOnText("确定");
		
		solo.assertCurrentActivity("After editing price", MyOfferIdleActivity.class);
		assertTrue(solo.searchText("50"));
	}
	
	public final void testUserSendsMessage() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		createOffer(listing.getString("id"));
		
		solo.clickOnScreen(Fixtures.ACTIVITY_X, Fixtures.MENU_Y); // my activity
		
		// Click on sellings
		solo.clickOnScreen(300, 50);
		solo.clickOnText(listing.getString("title"));
		assertTrue(solo.searchText("37.37"));
		solo.assertCurrentActivity("Should be on offer page", MyOfferIdleActivity.class);
		
		assertFalse(solo.searchButton("回复", true));
	    solo.enterText(0, "Reply the offer");
	    assertTrue(solo.searchButton("回复", true));
	    solo.clickOnButton("回复");
		
		solo.assertCurrentActivity("After adding message", MyOfferIdleActivity.class);
		assertTrue(solo.searchText("我"));
		assertTrue(solo.searchText("Reply the offer"));
	}
}
