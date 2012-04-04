package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.Main;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public abstract class IntegrationBaseTests extends ActivityInstrumentationTestCase2<Main> {
	
	protected Solo solo;

	public IntegrationBaseTests(String name) {
		super(Main.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		try {
			solo.finishOpenedActivities();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
