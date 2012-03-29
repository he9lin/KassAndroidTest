package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;

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
		createOffer(listing.getString("id"));
		solo.clickOnButton("我的活动");
		solo.clickOnText("我要卖");
		assertTrue(solo.searchText(listing.getString("title")));
	}
}
