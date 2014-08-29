package com.tencent.news.model.pojo;

import android.database.CursorJoiner.Result;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.news.http.HttpEngine.HttpCode;
import com.tencent.news.model.OrgData;
import com.tencent.news.utils.Crypt;

public class HttpCryptResult extends HttpResult {
	
	public static final String 	RES_DATA = "data";
	
	private HttpResult result;
	
	public HttpCryptResult(HttpResult result) {
		this.result = result;
	}
	
	public OrgData getOrgData() {
		String res = new String(result.getData());
		
		Gson gson = new Gson();
		
		OrgData org = null;
		
		try {
			org = gson.fromJson(res, OrgData.class);
			
			String data = "";
			
			if (null != org.getData()) {
				data = (String) org.getData();
				
				data = Crypt.decode(data);
				org.setData(data);
			}
			
			return org;
		} catch (JsonSyntaxException e) {
			return new OrgData();
		}
	}

	@Override
	public byte[] getData() {
		return result.getData();
	}

	@Override
	public void setData(byte[] data) {
		// TODO Auto-generated method stub
		super.setData(data);
	}

	@Override
	public HttpCode getResultCode() {
		return result.getResultCode();
	}

	@Override
	public void setResultCode(HttpCode resultCode) {
		// TODO Auto-generated method stub
		super.setResultCode(resultCode);
	}
	
	
}
