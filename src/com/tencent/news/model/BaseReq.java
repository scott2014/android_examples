package com.tencent.news.model;

import java.io.Serializable;

import com.google.gson.Gson;
import com.tencent.news.command.HttpTagDispatch.HttpTag;

public class BaseReq implements Serializable{
	
	private String cmd ;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public void setCmd(HttpTag tag) {
		this.cmd = String.valueOf(tag);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	public boolean needAuth()
	{
		return true;
	}
}
