package com.tencent.news.http;

import com.tencent.news.command.BaseHttpRequest;
import com.tencent.news.model.pojo.HttpCryptResult;
import com.tencent.news.model.pojo.HttpResult;

/**
 * 
 * @author wilson.song
 *
 */
public class HttpCryptPostEngine extends HttpPostEngine {

	public HttpCryptPostEngine(BaseHttpRequest request) {
		super(request);
	}
	
	@Override
	public HttpResult execute() {
		return new HttpCryptResult(super.execute());
	}
}
