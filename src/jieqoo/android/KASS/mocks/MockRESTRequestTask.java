package jieqoo.android.KASS.mocks;

import jieqoo.android.KASS.models.RESTListener;
import jieqoo.android.KASS.models.RESTResponse;
import jieqoo.android.KASS.tasks.FetchRESTResponseTask;

public class MockRESTRequestTask extends FetchRESTResponseTask {
	private int statusCode;
	private String responseBody;
	
	// TODO: Add status code
	public MockRESTRequestTask(int statusCode, String responseBody, RESTListener listener) {
		this.statusCode = statusCode;
		this.responseBody = responseBody;
		this.listener = listener;
	}

	@Override
	protected RESTResponse getJSON(String targetUrl, String urlParameters) {
		// TODO: Add Some logic to match url to mock responses.
		MockHttpResponse httpResponse = new MockHttpResponse(statusCode, responseBody);
		return new RESTResponse(httpResponse, httpResponse.getResponseBody());
	}
}
