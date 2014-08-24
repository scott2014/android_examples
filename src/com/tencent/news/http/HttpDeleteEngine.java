package com.tencent.news.http;

import org.apache.http.client.methods.HttpDelete;

import com.tencent.news.command.BaseHttpRequest;

public class HttpDeleteEngine extends HttpEngine {

	public HttpDeleteEngine(BaseHttpRequest request) {
		super(request);
	}


	@Override
	protected void initTag() {
		TAG = "HttpDeleteEngine";
	}

	@Override
	protected void initRequest() {
		requestBase = new HttpDelete(baseRequest.getUrl());
	}

	@Override
	protected void setRequestParams() {
	}

}
