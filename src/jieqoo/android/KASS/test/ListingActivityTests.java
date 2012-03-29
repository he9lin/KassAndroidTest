/**
 * 
 */
package jieqoo.android.KASS.test;

import java.text.ParseException;

import jieqoo.android.KASS.ListingActivity;
import jieqoo.android.KASS.models.Listing;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;

import static android.test.MoreAsserts.assertContainsRegex;

/**
 * @author linhe
 *
 */

public class ListingActivityTests extends ActivityInstrumentationTestCase2<ListingActivity> {
	
	private ListingActivity mActivity;
	
	private TextView titleText, descriptionText, priceText, timeText;

	public ListingActivityTests(String name) {
		super(ListingActivity.class);
		setName(name);
	}
	
	public ListingActivityTests() {
		this("ListingActivityTests");
	}

	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		// Setup testing intent
		Intent listingIntent = new Intent().putExtra("id", Fixtures.LISTING_ID);
		setActivityIntent(listingIntent);
		
		mActivity = getActivity();
		
		titleText = mActivity.getTitleText();
		descriptionText = mActivity.getDescriptionText();
		priceText = mActivity.getPriceText();
		timeText = mActivity.getTimeText();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testSetListingId() {
		assertEquals(Fixtures.LISTING_ID, mActivity.getListing().getId());
	}

	@UiThreadTest
	public final void testFillInListingContent() throws JSONException, ParseException {
		Listing listing = new Listing();
		listing.set(new JSONObject(Fixtures.LISTING));
		mActivity.fillInListingContent(listing);
		assertEquals("Bartender", titleText.getText()); 
		assertEquals("A hansome one", descriptionText.getText()); 
		assertContainsRegex("3000", priceText.getText().toString());
		assertNotNull(timeText.getText());
	}
}
