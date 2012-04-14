package jieqoo.android.KASS.faye.test;

import jieqoo.android.KASS.OfferActivity;
import jieqoo.android.KASS.privatepub.ChannelListener;
import jieqoo.android.KASS.privatepub.PrivatePubClient;
import jieqoo.android.KASS.privatepub.WebSocketable;
import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

public class PrivatePubClientTests extends TestCase {
	private static final String TAG = "PrivatePubClientTests";
	
	private PrivatePubClient client;
	private MockWebSocket webSocket;
	private static boolean listenerCalled = false;
	private static boolean listener2Called = false;

	public PrivatePubClientTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		listenerCalled = false;
		client = new PrivatePubClient("") {
			@Override
			protected WebSocketable createWebSocket() {
				return new MockWebSocket(messageHandler);
			}
		};
		client.connectToServer();
		webSocket = (MockWebSocket) client.getWebSocket();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testHandshake() {
		assertTrue(client.isWebSocketConnected());
	}
	
	public final void testConnectAfterHandshake() {
		assertTrue(client.isFayeConnected());
	}
	
	public final void testSubscribeToChannel() throws JSONException {
		String subsStr = "{\"server\":\"http://localhost:9292/faye\",\"timestamp\":1334279432148,\"channel\":\"/user/4f2f3024cf60211f38000001\",\"signature\":\"90090859a8e39394ce082ddd6f62de3360b6664a\"}";
		client.subscribeToChannel(subsStr, new ChannelListener() {
			@Override
			public void handleMessage(JSONObject message) {
				listenerCalled = true;
			}
		});
		JSONObject messageJSON = new JSONObject(webSocket.getLastMessage());
		JSONObject extJSON = messageJSON.optJSONObject("ext");
		assertEquals("90090859a8e39394ce082ddd6f62de3360b6664a", extJSON.optString("private_pub_signature"));
		
		String receivedMessage = "[{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"event\":\"new_message\",\"message\":\"New message\",\"body\":\"\"}},\"ext\":{\"private_pub_token\":null}}]";
		webSocket.receiveFrame(receivedMessage);
		assertTrue(listenerCalled);
	}
	
	public final void testSubscribeToUniqueChannel() throws JSONException {
		String subsStr = "{\"server\":\"http://localhost:9292/faye\",\"timestamp\":1334279432148,\"channel\":\"/user/4f2f3024cf60211f38000001\",\"signature\":\"90090859a8e39394ce082ddd6f62de3360b6664a\"}";
		client.subscribeToChannel(subsStr, new ChannelListener() {
			@Override
			public void handleMessage(JSONObject message) {
				listenerCalled = true;
			}
		});
		client.subscribeToChannel(subsStr, new ChannelListener() {
			@Override
			public void handleMessage(JSONObject message) {
				listener2Called = true;
			}
		});
		
		String receivedMessage = "[{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"event\":\"new_message\",\"message\":\"New message\",\"body\":\"\"}},\"ext\":{\"private_pub_token\":null}}]";
		webSocket.receiveFrame(receivedMessage);
		assertTrue(listenerCalled);
		assertFalse(listener2Called);
	}
	
	public final void testUnsubscribeFromChannel() throws JSONException {
		String subsStr = "{\"server\":\"http://localhost:9292/faye\",\"timestamp\":1334279432148,\"channel\":\"/user/4f2f3024cf60211f38000001\",\"signature\":\"90090859a8e39394ce082ddd6f62de3360b6664a\"}";
		client.subscribeToChannel(subsStr, new ChannelListener() {
			@Override
			public void handleMessage(JSONObject message) {
				listenerCalled = true;
			}
		});
		client.unsubscribeFromChannel("/user/4f2f3024cf60211f38000001");
		JSONObject messageJSON = new JSONObject(webSocket.getLastMessage());
		assertEquals("/meta/unsubscribe", messageJSON.opt("channel"));
		
		// Server should not have sent this message. It is here for testing listeners.
		String receivedMessage = "[{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"event\":\"new_message\",\"message\":\"New message\",\"body\":\"\"}},\"ext\":{\"private_pub_token\":null}}]";
		webSocket.receiveFrame(receivedMessage);
		assertFalse(listenerCalled);
	}
	
	public final void testUnsubscribeFromAllChannels() throws JSONException {
		String subsStr = "{\"server\":\"http://localhost:9292/faye\",\"timestamp\":1334279432148,\"channel\":\"/user/4f2f3024cf60211f38000001\",\"signature\":\"90090859a8e39394ce082ddd6f62de3360b6664a\"}";
		client.subscribeToChannel(subsStr, new ChannelListener() {
			@Override
			public void handleMessage(JSONObject message) {
				listenerCalled = true;
			}
		});
		client.unsubscribeFromAllChannels();
		JSONObject messageJSON = new JSONObject(webSocket.getLastMessage());
		assertEquals("/meta/unsubscribe", messageJSON.opt("channel"));
		
		// Server should not have sent this message. It is here for testing listeners.
		String receivedMessage = "[{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"channel\":\"/user/4f2f3024cf60211f38000001\",\"data\":{\"event\":\"new_message\",\"message\":\"New message\",\"body\":\"\"}},\"ext\":{\"private_pub_token\":null}}]";
		webSocket.receiveFrame(receivedMessage);
		assertFalse(listenerCalled);
	}
}









