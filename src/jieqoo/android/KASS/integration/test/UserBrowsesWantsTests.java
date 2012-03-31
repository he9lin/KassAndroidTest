package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

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
		
		solo.clickOnScreen(250, 730); // browse
		solo.clickOnScreen(80, 730); // my activity
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("0个出价"));
	}

	public final void testBrowsesListingsWithOffers() throws JSONException {
		JSONObject user = createUser();
		JSONObject listing = createListing();
		
		signoutUser();
		createUser();
		createOffer(listing.getString("id"));
		
		signoutUser();
		signinUser(user.getString("email"), user.getString("password"));
		
		solo.clickOnScreen(250, 730); // browse
		solo.clickOnScreen(80, 730); // my activity
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("1个出价"));
	}
	
	public final void testBrowsesSingleListing() throws JSONException {
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
		assertTrue(solo.searchText(offer.getString("message")));
	}
}
