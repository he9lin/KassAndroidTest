package jieqoo.android.KASS.collections.test;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import jieqoo.android.KASS.models.Listing;
import jieqoo.android.KASS.models.Offer;
import junit.framework.TestCase;

public class OffersTests extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testFindOfferByUserId() throws JSONException, ParseException {
		String listingJSONStr = "{\"id\":\"4f92912ccf602103e2000023\",\"title\":\"Bakk\",\"description\":\"\",\"price\":500.0,\"time\":\"2012-04-24T10:04:24+0000\",\"duration\":259200,\"user_id\":\"4f187d88cf60210899000001\",\"accepted_price\":null,\"state\":\"idle\",\"user\":{\"id\":\"4f187d88cf60210899000001\",\"image\":\"http://tp2.sinaimg.cn/1975401453/50/5609752561/1\",\"name\":\"he9lin\"},\"latlng\":[30.27408833333333,120.15506833333332],\"helper_messages\":[],\"expires_in\":249872,\"accepted_offer_id\":null,\"offers\":[{\"id\":\"4f92915ecf602103e2000024\",\"message\":\"walla\",\"price\":500.0,\"created_at\":\"2012-04-21T18:52:14+08:00\",\"user_id\":\"4f5c2a09cf60210b74000001\",\"state\":\"idle\",\"unread\":true,\"receiver_unread_messages_count\":0,\"owner_unread_messages_count\":0,\"user\":{\"id\":\"4f5c2a09cf60210b74000001\",\"image\":\"http://jieqoo.com/assets/headbg.gif\",\"name\":\"sasa\"},\"listing\":{\"id\":\"4f92912ccf602103e2000023\",\"title\":\"Bakk\",\"price\":500.0,\"duration\":259200,\"state\":\"idle\",\"user_id\":\"4f187d88cf60210899000001\"}},{\"id\":\"4f929347cf6021098d000002\",\"message\":\"walalak\",\"price\":500.0,\"created_at\":\"2012-04-21T19:00:23+08:00\",\"user_id\":\"4f2f3024cf60211f38000001\",\"state\":\"idle\",\"unread\":true,\"receiver_unread_messages_count\":0,\"owner_unread_messages_count\":0,\"user\":{\"id\":\"4f2f3024cf60211f38000001\",\"image\":\"http://jieqoo.com/assets/headbg.gif\",\"name\":\"kaka\"},\"listing\":{\"id\":\"4f92912ccf602103e2000023\",\"title\":\"Bakk\",\"price\":500.0,\"duration\":259200,\"state\":\"idle\",\"user_id\":\"4f187d88cf60210899000001\"}}]}";
		Listing listing = new Listing();
		listing.set(new JSONObject(listingJSONStr), true); // A deep set so that listing has offers collection
		Offer offer = listing.getOffers().findOfferByUserId("4f5c2a09cf60210b74000001");
		assertNotNull(offer);
		assertEquals("walla", offer.getMessage());
	}

}
