package jieqoo.android.KASS.models.test;

import java.text.ParseException;

import jieqoo.android.KASS.models.Listing;
import jieqoo.android.KASS.test.Fixtures;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class ListingTests extends TestCase {

	public final void testCreateFromJSONObject() throws JSONException, ParseException {
		JSONObject object = new JSONObject(Fixtures.LISTING);
		Listing listing = new Listing(object);
		assertEquals(listing.getId(), "4f6053a8cf60210e50000002");
	}
	
}
