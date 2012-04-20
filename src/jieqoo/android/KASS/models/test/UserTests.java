package jieqoo.android.KASS.models.test;

import jieqoo.android.KASS.models.User;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class UserTests extends TestCase {
	
	private User user;

	public UserTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testVerifications() throws JSONException {
		String raw = "{\"id\":\"4f8cdf4acf60210172000003\",\"name\":\"user1334632264078\",\"email\":\"user1334632264079@example.com\",\"phone_number\":\"12345678901\",\"default_city_id\":null,\"unread_messages_count\":0,\"timg_url\":\"http://jieqoo.com/assets/headbg.gif\",\"verification\":{\"sources\":[{\"type\":\"email\",\"type_name\":\"\u90ae\u7bb1\",\"verified\":true},{\"type\":\"tsina\",\"type_name\":\"\u65b0\u6d6a\u5fae\u535a\",\"verified\":false},{\"type\":\"phone\",\"type_name\":\"\u7535\u8bdd\u53f7\u7801\",\"verified\":false,\"phone\":\"12345678901\"}],\"member_since\":\"2012-04-17T11:04:07+0800\"}}";
		user = new User(new JSONObject(raw));
		assertTrue(user.isEmailVerified());
		assertFalse(user.isWeiboVerified());
		assertFalse(user.isPhoneVerified());
		assertEquals("user1334632264078", user.getUsername());
		assertEquals("2012-04-17T11:04:07+0800", user.getMemberSince());
	}

}
