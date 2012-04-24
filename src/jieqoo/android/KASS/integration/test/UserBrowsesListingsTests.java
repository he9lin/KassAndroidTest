package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;

import java.util.Random;

import jieqoo.android.KASS.ListingActivity;
import jieqoo.android.KASS.OfferActivity;
import jieqoo.android.KASS.ProvideActivity;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.WantActivity;

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
	
	public final void testClickListingBelongsToSomeoneElse() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		
		// Signin as a different user
		createUser();
		goToBrowseListings();
		solo.clickOnText(listing.getString("title"));
		solo.assertCurrentActivity("Should go to listing activity", ListingActivity.class);
	}
	
	public final void testClickListingThatIHaveOffer() throws JSONException {
		// Someone else's listing
		createUser();
		JSONObject listing = createListing();
		signoutUser();

		// Signin as a different user
		createUser();
		
		// I make an offer
		JSONObject offer = createOffer(listing.getString("id"));
		
		goToBrowseListings();

		solo.clickOnText(listing.getString("title"));
		solo.assertCurrentActivity("Should go to my offer activity (ProvideActivity)", ProvideActivity.class);
	}
	
	public final void testClickListingBelongsToMe() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		goToBrowseListings();
		solo.clickOnText(listing.getString("title"));
		solo.assertCurrentActivity("WantActivity", WantActivity.class);
	}
	
//	public final void testClickListingBelongsToMeAndIHaveAcceptedAnOffer() throws JSONException {
//		JSONObject user = createUser();
//		JSONObject listing = createListing();
//		
//		signoutUser();
//		createUser();
//		JSONObject offer = createOffer(listing.getString("id"));
//		
//		signoutUser();
//		signinUser(user.getString("email"), user.getString("password"));	
//		
//		goToBrowseListings();
//		
//		solo.waitForActivity("BrowseActivity");
//		
//		// Must happen after user is on the browse page. Otherwise, the
//		// listing would be considered 'ended' and won't show up on the 
//		// database request.
//		acceptOffer(offer.getString("id"));
//
//		solo.clickOnText(listing.getString("title"));
//		solo.assertCurrentActivity("Should go to the offer activity", OfferActivity.class);
//	}
	
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
