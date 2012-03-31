package jieqoo.android.KASS.util.test;

import jieqoo.android.KASS.util.Util;
import junit.framework.TestCase;

public class UtilTests extends TestCase {

	public UtilTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testNumberToCurrency() {
		String output = Util.numberToCurrency(1000.0);
		assertEquals("1000", output);

		output = Util.numberToCurrency(1000.15);
		assertEquals("1000.15", output);
	}
}
