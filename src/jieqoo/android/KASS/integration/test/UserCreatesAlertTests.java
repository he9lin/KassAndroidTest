package jieqoo.android.KASS.integration.test;

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
		solo.clickOnScreen(Fixtures.BROWSE_X, Fixtures.MENU_Y);
		
		View alerts = solo.getView(R.id.market_alerts);
		solo.clickOnView(alerts);
		
		View newAlert = solo.getView(R.id.alerts_new_alert);
		solo.clickOnView(newAlert);	
		
		// Views built successfully
		assertTrue(solo.searchText("添加提醒")); // title
		assertTrue(solo.searchText("20")); // default 20 km
	}
	
	public final void testUserEntersQuery() {
		solo.clickOnText("我要出售");
		assertTrue(solo.searchText("关注的商品"));
		solo.enterText(0, "Basketball");
		solo.clickOnText("确定");
		assertTrue(solo.searchText("添加提醒"));
		assertTrue(solo.searchText("Basketball"));
	}
	
	public final void testUserCancelsQueryEnter() {
		solo.clickOnText("我要出售");
		assertTrue(solo.searchText("关注的商品"));
		solo.clickOnText("取消");
		assertTrue(solo.searchText("添加提醒"));
	}
	
	public final void testUserEnterMinPrice() {
		solo.clickOnText("最低价位");
		assertTrue(solo.searchText("提醒价位"));
		solo.enterText(0, "37");
		solo.clickOnText("确定");
		assertTrue(solo.searchText("添加提醒"));
		assertTrue(solo.searchText("37"));
	}
	
	public final void testUserCancelMinPriceEnter() {
		solo.clickOnText("最低价位");
		assertTrue(solo.searchText("提醒价位"));
		solo.clickOnText("取消");
		assertTrue(solo.searchText("添加提醒"));
	}
	
	public final void testUserEditRadius() {
		solo.clickOnText("位置");
		assertTrue(solo.searchText("提醒位置"));
		assertTrue(solo.searchText("20")); // default 20
		solo.setProgressBar(0, 50);
		solo.clickOnText("确定");
		assertTrue(solo.searchText("添加提醒"));
		assertTrue(solo.searchText("50"));
	}
	
	public final void testUserCancelRadiusEdit() {
		solo.clickOnText("位置");
		assertTrue(solo.searchText("提醒位置"));
		solo.clickOnText("取消");
		assertTrue(solo.searchText("添加提醒"));
	}
	
	public final void testCreateAlert() throws JSONException {
		JSONObject userJSON = Factory.createUser();
		Factory.signinUser(userJSON.getString("email"), userJSON.getString("password"));
		
		solo.clickOnText("我要出售");
		assertTrue(solo.searchText("关注的商品"));
		solo.enterText(0, "Basketball");
		solo.clickOnText("确定");
		
		solo.clickOnText("位置");
		assertTrue(solo.searchText("提醒位置"));
		assertTrue(solo.searchText("20")); // default 20
		solo.setProgressBar(0, 50);
		solo.clickOnText("确定");
		
		solo.clickOnText("最低价位");
		assertTrue(solo.searchText("提醒价位"));
		solo.enterText(0, "37");
		solo.clickOnText("确定");
		
		assertTrue(solo.searchText("添加提醒"));
		solo.clickOnButton(0); 
		
		solo.waitForActivity("AlertsActivity");
		assertTrue(solo.searchText("Basketball"));
	}
}
