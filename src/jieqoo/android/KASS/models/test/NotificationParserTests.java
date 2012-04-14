package jieqoo.android.KASS.models.test;

import jieqoo.android.KASS.OfferActivity;
import jieqoo.android.KASS.models.NotificationParser;
import jieqoo.android.KASS.models.NotificationParser.NotificationMeta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.TestCase;

public class NotificationParserTests extends TestCase {
	private NotificationParser np;

	public NotificationParserTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		np = NotificationParser.getInstance();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// Actually 
	public final void testParseNotificationMessage() throws JSONException {
		JSONArray messageArray = new JSONArray("[{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"event\":\"new_message\",\"message\":\"新的回复 ￥333.0 哇卡卡卡\",\"body\":{\"id\":\"4f891b13cf60211018000001\",\"body\":\"哇卡卡卡\",\"user_id\":\"4f5c2a09cf60210b74000001\",\"offer_id\":\"4f883cb7cf602109dc000002\",\"created_at\":\"2012-04-14T14:37:07+08:00\",\"receiver_unread_messages_count\":71,\"offer\":{\"receiver_unread_messages_count\":9,\"owner_unread_messages_count\":0,\"listing_id\":\"4f883ca7cf602109dc000001\"}}}},\"ext\":{\"private_pub_token\":null}}]");
		JSONObject messageJSON = messageArray.getJSONObject(0);
		NotificationMeta nm = np.parseNotificationMessage(messageJSON);
		assertEquals(OfferActivity.class, nm.getActivityClass());
		assertEquals("4f883cb7cf602109dc000002", nm.getBundle().getString("id"));
		assertEquals("新的回复 ￥333.0 哇卡卡卡", nm.getMessage());
	}
}
