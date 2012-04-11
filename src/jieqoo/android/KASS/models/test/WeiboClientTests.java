package jieqoo.android.KASS.models.test;

import jieqoo.android.KASS.models.WeiboClient;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboClientTests extends TestCase {
	private WeiboClient wc;
	private static final String WEIBO_USER_HASH = "{\"id\":1975401453,\"idstr\":\"1975401453\",\"screen_name\":\"很久以前很瘦的kaka\",\"name\":\"很久以前很瘦的kaka\",\"province\":\"400\",\"city\":\"1\",\"location\":\"海外 美国\",\"description\":\"如果要编程，就要疯狂的去编程！\",\"url\":\"\",\"profile_image_url\":\"http://tp2.sinaimg.cn/1975401453/50/5609752561/1\",\"profile_url\":\"he9lin\",\"domain\":\"he9lin\",\"weihao\":\"\",\"gender\":\"m\",\"followers_count\":123,\"friends_count\":96,\"statuses_count\":113,\"favourites_count\":4,\"created_at\":\"Fri Feb 18 00:00:00 +0800 2011\",\"following\":false,\"allow_all_act_msg\":false,\"geo_enabled\":true,\"verified\":false,\"verified_type\":-1,\"status\":{\"created_at\":\"Tue Apr 10 12:41:10 +0800 2012\",\"id\":3433245054082550,\"mid\":\"3433245054082550\",\"idstr\":\"3433245054082550\",\"text\":\"[话筒] 我 在 #街区jieqoo# 要: 按摩保健 - 价钱: ￥100.0 - 期限:1 小时 --    -  http://t.cn/zOCJGBx\",\"favorited\":false,\"truncated\":false,\"in_reply_to_status_id\":\"\",\"in_reply_to_user_id\":\"\",\"in_reply_to_screen_name\":\"\",\"thumbnail_pic\":\"http://ww4.sinaimg.cn/thumbnail/75be3bedjw1drufux5mrbj.jpg\",\"bmiddle_pic\":\"http://ww4.sinaimg.cn/bmiddle/75be3bedjw1drufux5mrbj.jpg\",\"original_pic\":\"http://ww4.sinaimg.cn/large/75be3bedjw1drufux5mrbj.jpg\",\"geo\":null,\"reposts_count\":0,\"comments_count\":0,\"mlevel\":0,\"visible\":{\"type\":0,\"list_id\":0}},\"allow_all_comment\":true,\"avatar_large\":\"http://tp2.sinaimg.cn/1975401453/180/5609752561/1\",\"verified_reason\":\"\",\"follow_me\":false,\"online_status\":1,\"bi_followers_count\":84,\"lang\":\"zh-cn\"}";

	public WeiboClientTests(String name) {
		super(name);
		wc = new WeiboClient();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testBuildUserHash() throws JSONException {
		JSONObject userHash = wc.buildUserHash(WEIBO_USER_HASH);
		assertEquals("1975401453", userHash.getString("id"));
		assertEquals("很久以前很瘦的kaka", userHash.getString("screen_name"));
		assertEquals("很久以前很瘦的kaka", userHash.getString("name"));
		assertEquals("m", userHash.getString("gender"));
		assertEquals("http://tp2.sinaimg.cn/1975401453/50/5609752561/1", userHash.getString("profile_image_url"));
		assertEquals("如果要编程，就要疯狂的去编程！", userHash.getString("description"));
	}
	
	public final void testIsSet() throws JSONException {
		assertFalse(wc.isSet());
		wc.buildUserHash(WEIBO_USER_HASH);
		assertTrue(wc.isSet());
	}
}
