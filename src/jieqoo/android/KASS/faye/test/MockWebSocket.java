package jieqoo.android.KASS.faye.test;

import jieqoo.android.KASS.privatepub.PrivatePubClient;
import jieqoo.android.KASS.privatepub.WebSocketable;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class MockWebSocket implements WebSocketable {
	private Handler handler;
	private String lastMessage;
	
	MockWebSocket(Handler handler) {
		this.handler = handler;
	}
	
	public String getLastMessage() {
		return lastMessage;
	}

	@Override
	public void open() {
		receiveHandshake();
	}

	@Override
	public void send(String message) {
		Log.d("MockWebSocket", "send to server: " + message);
		lastMessage = message;
		
		JSONObject messageJSON;
		try {
			messageJSON = new JSONObject(lastMessage);
			String channel = messageJSON.optString("channel");
			
			if (channel.equals(PrivatePubClient.HANDSHAKE_CHANNEL)) {
				receiveFrame("[{\"channel\":\"/meta/handshake\",\"successful\":\"true\", \"clientId\":\"123456\"}]");
			} else if (channel.equals(PrivatePubClient.CONNECT_CHANNEL)) {
				receiveFrame("[{\"channel\":\"/meta/connect\",\"successful\":\"true\", \"clientId\":\"123456\"}]");
			} else if (channel.equals(PrivatePubClient.SUBSCRIBE_CHANNEL)) {
				receiveFrame("[{\"channel\":\"/meta/subscribe\",\"successful\":\"true\", \"clientId\":\"123456\", \"subscription\":\"/user/12345\"}]");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	// This actually is client sends a handshake message.
	public void receiveHandshake() {
		Log.d("MockWebSocket", "receiveHandshake");
		handler.handleMessage(Message.obtain(handler, PrivatePubClient.MESSAGE_ONOPEN));
	}
	
	public void receiveFrame(String message) {
		Log.d("MockWebSocket", "receiveFrame: " + message);
		handler.handleMessage(Message.obtain(handler, PrivatePubClient.MESSAGE_ONMESSAGE, message));
	}

	@Override
	public void close() {
		Log.d("MockWebSocket", "close");
		handler.handleMessage(Message.obtain(handler, PrivatePubClient.MESSAGE_ONCLOSE));
	}
}
