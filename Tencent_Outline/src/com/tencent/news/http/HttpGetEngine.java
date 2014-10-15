package com.tencent.news.http;

import org.apache.http.client.methods.HttpGet;

import com.tencent.news.command.BaseHttpRequest;

public class HttpGetEngine extends HttpEngine {

	public HttpGetEngine(BaseHttpRequest request) {
		super(request);
	}

	@Override
	protected void initTag() {
		TAG = "HttpGetEngine";
	}

	@Override
	protected void initRequest() {
		requestBase = new HttpGet(baseRequest.getUrl());
	}

	@Override
	protected void setRequestParams() {
	}

}
