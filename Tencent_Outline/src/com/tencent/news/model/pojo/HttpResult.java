package com.tencent.news.model.pojo;

import com.tencent.news.http.HttpEngine.HttpCode;

public class HttpResult {

	public static final int RES_OK = 1; 
	
	/**
	 * 结果码
	 */
	private HttpCode resultCode;
	
	/**
	 * 结果数据
	 */
	private byte[] data;
	
	public HttpCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(HttpCode resultCode) {
		this.resultCode = resultCode;
	}


	public byte[] getData() {
		if (data == null) {
			data = new byte[0];
		}
		return data;
	}

	
	public void setData(byte[] data) {
		this.data = data;
	}


}
