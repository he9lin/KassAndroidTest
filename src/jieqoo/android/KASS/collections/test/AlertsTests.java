package jieqoo.android.KASS.collections.test;

import java.text.ParseException;

import jieqoo.android.KASS.collections.Alerts;
import jieqoo.android.KASS.test.Fixtures;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class AlertsTests extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * The expected JSON format is {alerts:[{id:123,...},{id:456,...}]}
	 * 
	 * @throws JSONException 
	 * @throws ParseException 
	 */
	public final void testCreateFromJSONObject() throws JSONException, ParseException {
		JSONObject json = new JSONObject(Fixtures.ALERTS);
		Alerts alerts = new Alerts(json);
		assertEquals(alerts.length(), 2);
	}

}
