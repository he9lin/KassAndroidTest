package jieqoo.android.KASS.models.test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import jieqoo.android.KASS.Constants;
import jieqoo.android.KASS.models.WeiboEncrypt;
import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class WeiboEncryptTests extends TestCase {

	public WeiboEncryptTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Turns the following hash
	 * 
	 * {
	 *  "id" : 1975401453,
	 *  "description" : "如果要编程，就要疯狂的去编程！", 
	 *  "gender" : "m", 
	 *  "profile_image_url" : "http://tp2.sinaimg.cn/1975401453/50/5609752561/1",
	 *  "location"=>"海外 美国", 
	 *  "name" : "很久以前很瘦的kaka",
	 *  "screen_name" : "很久以前很瘦的kaka",
	 * }
	 * 
	 * into a encoded string
	 *
	 * @throws JSONException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public final void testEncodeAndDecode() throws JSONException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		JSONObject weiboJSON = new JSONObject();
		weiboJSON.put("id", "1975401453");
		weiboJSON.put("description", "如果要编程，就要疯狂的去编程！");
		weiboJSON.put("gender", "m");
		weiboJSON.put("profile_image_url", "http://tp2.sinaimg.cn/1975401453/50/5609752561/1");
		weiboJSON.put("location", "海外 美国");
		weiboJSON.put("name", "很久以前很瘦的kaka");
		weiboJSON.put("screen_name", "很久以前很瘦的kaka");
		
		String expectedEncodedString = "bBlEdbvoO4eEDh86AtzGpDlzOJy0LzH8V5KczVoAh0aT1emk7toIfIbbu691sOb+RwIAarsehcs4GjUmn6msERCEVM20v8E5BzHhJ9iGQcQlhAVER0owRNeDJheIUM90AMtai+3X7ap0DraWgRZ+sO/h0Vtz5TdCbNaslUMZVgUFPfgfkRtIqZNoNRYgXtqWhzh5u0LJkffvnytsHSr6YyAdP8LsRNgprq/+r92LQkRJ9HC4HOis/0x6RzQHlY9VJG3Xj8VcxKX3Zp6MPAWi9Pm13On0iLEmErE7mKRAyoSnk263MjHzqks9PmRdcvxdSXp0NJaK5fz6psMBQzYlZ/2+FZ110s8wF/waAyY3zezkQitla0avuycFJoEEvogA";
		assertEquals(expectedEncodedString, WeiboEncrypt.encode(weiboJSON.toString()).replace("\n", ""));
	}
}
