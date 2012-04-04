package jieqoo.android.KASS.models.test;

import java.text.ParseException;

import jieqoo.android.KASS.models.Alert;
import jieqoo.android.KASS.test.Fixtures;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class AlertTests extends TestCase {

	public final void testSetWithJSONObject() throws JSONException, ParseException {
		JSONObject rawJSON = new JSONObject(Fixtures.ALERT);
		rawJSON = rawJSON.getJSONObject("alert");
		Alert alert = new Alert(true);
		alert.set(rawJSON);
		assertEquals("Soccer", alert.getQuery());
		assertEquals(45.0, alert.getRadius());
	}
	
}
