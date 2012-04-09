package jieqoo.android.KASS.integration.test;

import jieqoo.android.KASS.AlertFormActivity;
import jieqoo.android.KASS.AlertsActivity;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.test.Factory;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;

public class UserCreatesAlertTests extends IntegrationBaseTests {

	public UserCreatesAlertTests(String name) {
		super(name);
	}
	
	public UserCreatesAlertTests() {
		this("UserCreatesAlertTests");
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		clickOnBrowseMainTab();
		
		View alerts = solo.getView(R.id.market_alerts);
		solo.clickOnView(alerts);
		
		View newAlert = solo.getView(R.id.alerts_new_alert);
		solo.clickOnView(newAlert);	
		
		// Views built successfully
		assertTrue(solo.searchText("20")); // default 20 km
	}

	private void assertOnActivityFormActivity() {
		solo.assertCurrentActivity("AlertFormActivity", AlertFormActivity.class);
	}
	
	private void clickIWantToSellLabel() {
		solo.clickOnView(solo.getView(R.id.alert_form_query));
	}
	
	private void clickRadiusLabel() {
		solo.clickOnView(solo.getView(R.id.alert_form_radius));
	}
	
	private void clickMinPriceLabel() {
		solo.clickOnView(solo.getView(R.id.alert_form_min_price));
	}
	
	public final void testUserEntersQuery() {
		clickIWantToSellLabel();
		assertTrue(solo.searchText("关注的商品"));
		solo.enterText(0, "Basketball");
		clickOnDoneButton();
		assertOnActivityFormActivity();
		assertTrue(solo.searchText("Basketball"));
	}
	
	public final void testUserCancelsQueryEnter() {
		clickIWantToSellLabel();
		assertTrue(solo.searchText("关注的商品"));
		clickOnCancelButton();
		assertOnActivityFormActivity();
	}
	
	public final void testUserEnterMinPrice() {
		clickMinPriceLabel();
		assertTrue(solo.searchText("提醒价位"));
		solo.enterText(0, "37");
		clickOnDoneButton();
		assertOnActivityFormActivity();
		assertTrue(solo.searchText("37"));
	}
	
	public final void testUserEntersEmptyPrice() {
		clickMinPriceLabel();
		assertTrue(solo.searchText("提醒价位"));
		clickOnDoneButton();
		assertOnActivityFormActivity();
	}
	
	public final void testUserCancelMinPriceEnter() {
		clickMinPriceLabel();
		assertTrue(solo.searchText("提醒价位"));
		clickOnCancelButton();
		assertOnActivityFormActivity();
	}
	
	public final void testUserEntersEmptyQuery() {
		clickIWantToSellLabel();
		assertTrue(solo.searchText("关注的商品"));
		clickOnDoneButton();
		assertOnActivityFormActivity();
	}
	
	public final void testUserEditRadius() {
		clickRadiusLabel();
		assertTrue(solo.searchText("提醒位置"));
		assertTrue(solo.searchText("20")); // default 20
		solo.setProgressBar(0, 50);
		clickOnDoneButton();
		assertOnActivityFormActivity();
		assertTrue(solo.searchText("50"));
	}
	
	public final void testUserCancelRadiusEdit() {
		clickRadiusLabel();
		assertTrue(solo.searchText("提醒位置"));
		clickOnCancelButton();
		assertOnActivityFormActivity();
	}
	
	public final void testUserCancelsCreateAlert() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		Factory.signinUser(userJSON.getString("email"), userJSON.getString("password"));
		
		clickIWantToSellLabel();
		assertTrue(solo.searchText("关注的商品"));
		solo.enterText(0, "Basketball");
		clickOnDoneButton();
		
		clickRadiusLabel();
		assertTrue(solo.searchText("提醒位置"));
		assertTrue(solo.searchText("20")); // default 20
		solo.setProgressBar(0, 50);
		clickOnDoneButton();
		
		clickMinPriceLabel();
		assertTrue(solo.searchText("提醒价位"));
		solo.enterText(0, "37");
		clickOnDoneButton();
		
		assertOnActivityFormActivity();
		clickOnCancelButton();
		
		solo.assertCurrentActivity("Back to Alerts list", AlertsActivity.class);
	}
	
	public final void testCreateAlert() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		Factory.signinUser(userJSON.getString("email"), userJSON.getString("password"));
		
		clickIWantToSellLabel();
		assertTrue(solo.searchText("关注的商品"));
		solo.enterText(0, "Basketball");
		clickOnDoneButton();
		
		clickRadiusLabel();
		assertTrue(solo.searchText("提醒位置"));
		assertTrue(solo.searchText("20")); // default 20
		solo.setProgressBar(0, 50);
		clickOnDoneButton();
		
		clickMinPriceLabel();
		assertTrue(solo.searchText("提醒价位"));
		solo.enterText(0, "37");
		clickOnDoneButton();
		
		assertOnActivityFormActivity();
		solo.clickOnButton(1); 
		
		solo.waitForActivity("AlertsActivity");
		assertTrue(solo.searchText("Basketball"));
	}
}
