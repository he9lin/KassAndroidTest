package jieqoo.android.KASS.integration.test;

import static jieqoo.android.KASS.test.Factory.*;
import jieqoo.android.KASS.R;
import jieqoo.android.KASS.test.Fixtures;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;

public class UserBrowsesAlertListingsTests extends IntegrationBaseTests {

	public UserBrowsesAlertListingsTests(String name) {
		super(name);
	}
	
	public UserBrowsesAlertListingsTests() {
		this("UserBrowsesAlertListingsTests");
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

	public final void testBrowsesListings() throws JSONException {
		createUser();
		createListing("Nike Basketball");
		signoutUser();
		
		JSONObject userJSON = createUser();
		signinUser(userJSON.getString("email"), userJSON.getString("password"));
		
		solo.clickOnScreen(Fixtures.BROWSE_X, Fixtures.MENU_Y);
		
		View alerts = solo.getView(R.id.market_alerts);
		solo.clickOnView(alerts);
		
		View newAlert = solo.getView(R.id.alerts_new_alert);
		solo.clickOnView(newAlert);	
		
		// Views built successfully
		assertTrue(solo.searchText("添加提醒")); // title
		assertTrue(solo.searchText("20")); // default 20 km
		
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
		
		assertTrue(solo.searchText("添加提醒"));
		solo.clickOnButton(1); // create alert button 
		
		solo.waitForActivity("AlertsActivity");
		assertTrue(solo.searchText("Basketball"));
		
		solo.clickOnText("Basketball");
		assertTrue(solo.searchText("Nike Basketball"));
	}
}
