package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createUser;

import org.json.JSONException;
import org.json.JSONObject;

public class UserBrowsesWantsTests extends IntegrationBaseTests {

	public UserBrowsesWantsTests(String name) {
		super(name);
	}
	
	public UserBrowsesWantsTests() {
		this("UserBrowsesWantsTests");
	}

	public final void testBrowsesListings() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		
		solo.clickOnButton("浏览");
		solo.clickOnButton("我的活动");
		assertTrue(solo.searchText(listing.getString("title")));
	}
}
