package com.tencent.news.http.request;

import com.tencent.news.command.HttpCryptPostRequest;
import com.tencent.news.command.HttpDataRequest;
import com.tencent.news.command.HttpPostRequest;
import com.tencent.news.command.HttpTagDispatch.HttpTag;
import com.tencent.news.config.Constants;
import com.tencent.news.model.BaseReq;

public class HttpRequestFactory {
	
	//开发环境
	public static String READ_BASE_URL = "http://218.17.118.27:8081/";
	
	private static HttpRequestFactory requestFactory = null;
	
	/**
	 * 获取单例HttpRequestFactory实例
	 * @return
	 */
	public static HttpRequestFactory getInstance() {
		if (null == requestFactory) {
			requestFactory = new HttpRequestFactory();
		}
		
		return requestFactory;
	}
	
	private HttpPostRequest post()
	{
		HttpPostRequest request = new HttpCryptPostRequest();
		request.setGzip(false);
		request.setNeedAuth(false);
		request.setRetry(false);
		request.setSort(Constants.REQUEST_METHOD_POST);
		request.setUrl(READ_BASE_URL);
		return request;
	}
	
	public HttpDataRequest post(BaseReq reqObj) {
		
		HttpPostRequest request = post();
		if(reqObj.needAuth())
		{
			request.setNeedAuth(true);
		}
		request.setTag(HttpTag.fromString(reqObj.getCmd()));
		request.setBody(String.valueOf(reqObj));
		return request;
	}
	

	public HttpDataRequest request(String body, String sign) {
		HttpPostRequest request = new HttpPostRequest();
		request.setBody(body);
		request.setGzip(false);
		request.setNeedAuth(false);
		request.setRetry(false);
		request.setTag(HttpTag.NONE);
		request.setUrl(READ_BASE_URL);
		request.setSort(Constants.REQUEST_METHOD_POST);

		request.addUrlParams("sign", sign);
		return request;

	}
	
}
