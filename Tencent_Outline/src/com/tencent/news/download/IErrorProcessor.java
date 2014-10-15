package com.tencent.news.download;


public interface IErrorProcessor {
	void handleError(HttpMsg request, HttpMsg response);
}
