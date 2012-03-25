package jieqoo.android.KASS.test;

import jieqoo.android.KASS.Browse;
import android.test.ActivityInstrumentationTestCase2;

public class BrowseTests extends ActivityInstrumentationTestCase2<Browse> {

	private Browse mActivity;

	public BrowseTests(String name) {
		super(Browse.class);
		setName(name);
	}

	public BrowseTests() {
		this("BrowseTests");
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
