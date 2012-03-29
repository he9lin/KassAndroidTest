package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.createListing;
import static jieqoo.android.KASS.test.Factory.createUser;
import static jieqoo.android.KASS.test.Factory.signoutUser;
import jieqoo.android.KASS.Main;
import jieqoo.android.KASS.MyOfferIdleActivity;
import jieqoo.android.KASS.widgets.SlideButton.FinishingTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.jayway.android.robotium.solo.Solo;

public class UserMakesOfferTests extends ActivityInstrumentationTestCase2<Main> {
	
	private Solo solo;

	public UserMakesOfferTests(String name) {
		super(Main.class);
		setName(name);
	}
	
	public UserMakesOfferTests() {
		this("UserEditsOfferTests");
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
	
	public final void testUserMakesOffer() throws JSONException {
		createUser();
		JSONObject listing = createListing();
		signoutUser();
		createUser();
		solo.clickOnButton("浏览");
		solo.waitForActivity("Browse");
		solo.clickOnText(listing.getString("title"));
		solo.clickOnButton("我想出价");
		solo.enterText(0, "I am a bartender");
		solo.clickOnButton("下一步");
		solo.waitForText("提交报价");
		ActivityMonitor am = solo.getActivityMonitor();
		Activity lastActivity = am.getLastActivity();
		Log.d("UserMakesOfferActivity", lastActivity.toString());
		((FinishingTouchListener)lastActivity).onFinishingTouch();
		solo.assertCurrentActivity("Should go to my offer page", MyOfferIdleActivity.class);
		assertTrue(solo.searchText(listing.getString("title")));
	}
}
