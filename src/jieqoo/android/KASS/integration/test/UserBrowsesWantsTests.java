package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.test.Fixtures;

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
	
	private void refreshMyActivity() {
		clickOnBrowseMainTab();
		clickOnMyActivityMainTab();
	}

	public final void testBrowsesListings() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		refreshMyActivity();
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
		
		refreshMyActivity();
		
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
		
		refreshMyActivity();
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText("1个出价"));
		
		solo.clickOnText(listing.getString("title"));
		assertTrue(solo.searchText(offer.getString("message")));
	}
}
