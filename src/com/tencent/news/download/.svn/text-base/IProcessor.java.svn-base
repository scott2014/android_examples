package com.tencent.news.download;


public interface IProcessor extends IErrorProcessor {
	void decode(HttpMsg request, HttpMsg response);

	boolean statusChanged(HttpMsg request, HttpMsg response, int status);
}
