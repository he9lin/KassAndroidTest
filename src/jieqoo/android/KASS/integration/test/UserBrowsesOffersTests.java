package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createOffer;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

public class UserBrowsesOffersTests extends IntegrationBaseTests {
	
	public UserBrowsesOffersTests(String name) {
		super(name);
	}
	
	public UserBrowsesOffersTests() {
		this("UserBrowsesOffersTests");
	}
	
	public final void testBrowsesOffers() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		JSONObject offer = createOffer(listing.getString("id"));
		solo.clickOnScreen(Fixtures.ACTIVITY_X, Fixtures.MENU_Y);
		// Click on sellings
		solo.clickOnScreen(300, 50);
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText(offer.getString("price")));
		assertTrue(solo.searchText("等待确认"));
	}
}
