package jieqoo.android.KASS.mocks;

import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.models.RESTResponse;
import jieqoo.android.KASS.tasks.HttpJSONGetTask;

// To mock JSON request tasks
public class MockHttpJSONGetTask extends HttpJSONGetTask {
	private int statusCode;
	private String responseBody;
	
	public MockHttpJSONGetTask(int statusCode, String responseBody, RESTListener listener) {
		super("", "", listener);
		this.statusCode = statusCode;
		this.responseBody = responseBody;
	}

	@Override
	protected RESTResponse getJSON(String targetUrl, String urlParameters) {
		MockHttpResponse httpResponse = new MockHttpResponse(statusCode, responseBody);
		return new RESTResponse(httpResponse, httpResponse.getResponseBody());
	}
}
