package com.tencent.news.model.req;

import com.tencent.news.model.BaseReq;

public class LoginReq extends BaseReq {
	private String tel;
	private String pwd;
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
