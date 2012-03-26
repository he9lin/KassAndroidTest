package jieqoo.android.KASS.test;

import java.text.ParseException;

import jieqoo.android.util.TimeParser;
import junit.framework.TestCase;
import android.util.Log;


public class TimeParserTests extends TestCase {

	public final void testParseString() throws ParseException {
		String timeString = "2012-03-18T15:59:53+08:00";
		String words = TimeParser.timeago(timeString);
		Log.d("Time difference: ", words);
		assertNotNull(words);
	}
}

