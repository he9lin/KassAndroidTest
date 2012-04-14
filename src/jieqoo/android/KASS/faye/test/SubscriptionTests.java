package jieqoo.android.KASS.faye.test;

import jieqoo.android.KASS.privatepub.Subscription;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;


public class SubscriptionTests extends TestCase {
	static final String SUBSCRIPTION_JSON = "{\"server\":\"http://localhost:9292/faye\",\"timestamp\":1334279432148,\"channel\":\"/user/4f2f3024cf60211f38000001\",\"signature\":\"90090859a8e39394ce082ddd6f62de3360b6664a\"}";

	public SubscriptionTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//server - timestamp - signature - channel
	public final void testSetByString() throws JSONException {
		Subscription s = new Subscription(SUBSCRIPTION_JSON);
		assertEquals("/user/4f2f3024cf60211f38000001", s.getChannel());
		assertEquals("1334279432148", s.getTimestamp());
		assertEquals("90090859a8e39394ce082ddd6f62de3360b6664a", s.getSignature());
	}
	
	public final void testSetByJSONObject() throws JSONException {
		JSONObject subsJSON = new JSONObject(SUBSCRIPTION_JSON);
		Subscription s = new Subscription(subsJSON);
		assertEquals("/user/4f2f3024cf60211f38000001", s.getChannel());
		assertEquals("1334279432148", s.getTimestamp());
		assertEquals("90090859a8e39394ce082ddd6f62de3360b6664a", s.getSignature());
	}
}
