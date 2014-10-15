package com.tencent.news.command;

import com.tencent.news.http.HttpEngine.HttpCode;

public interface HttpDataResponse {

	void onHttpRecvOK(HttpDataRequest request, Object result);

	void onHttpRecvError(HttpDataRequest request, HttpCode retCode, String msg);

	void onHttpRecvCancelled(HttpDataRequest request);
}
