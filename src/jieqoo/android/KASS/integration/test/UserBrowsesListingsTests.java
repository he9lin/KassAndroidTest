package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createUser;

import java.util.Random;

import jieqoo.android.KASS.R;
import jieqoo.android.KASS.test.Fixtures;
import jieqoo.android.KASS.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;

public class UserBrowsesListingsTests extends IntegrationBaseTests {

	public UserBrowsesListingsTests(String name) {
		super(name);
	}
	
	public UserBrowsesListingsTests() {
		this("UserBrowsesListingsTests");
	}
	
	private void goToBrowseListings() {
		clickOnBrowseMainTab();
		View browse = solo.getView(R.id.market_browse);
		solo.clickOnView(browse);
	}
	
	public final void testBrowseListings() throws JSONException {
		Random generator = new Random();
		createUser();
		
		int randPrice = generator.nextInt(2000);
		
		JSONObject listing = createListing(randPrice);
		
		goToBrowseListings();
		
		assertTrue(solo.searchText(listing.getString("title")));
		assertTrue(solo.searchText(randPrice + "元"));
	}
	
	public final void testSortByDistance() {
		
	}
	
	public final void testSortByPrice() {
		
	}
	
	public final void testSortByTime() {
		
	}
}
