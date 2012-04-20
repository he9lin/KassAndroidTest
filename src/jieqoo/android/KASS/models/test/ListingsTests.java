package jieqoo.android.KASS.models.test;

import java.text.ParseException;

import jieqoo.android.KASS.collections.Listings;
import jieqoo.android.KASS.test.Fixtures;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class ListingsTests extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * The expected JSON format is {listing:[{id:123,...},{id:456,...}]}
	 * 
	 * @throws JSONException 
	 * @throws ParseException 
	 */
	public final void testCreateFromJSONObject() throws JSONException, ParseException {
		JSONObject json = new JSONObject(Fixtures.LISTINGS);
		Listings listing = new Listings(json);
		assertEquals(listing.length(), 2);
	}
}
