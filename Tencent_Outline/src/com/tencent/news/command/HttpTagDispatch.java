package com.tencent.news.command;

import com.google.gson.Gson;
import com.tencent.news.model.LoginRes;
import com.tencent.news.model.OrgData;

public class HttpTagDispatch {
	public enum HttpTag { //

		NONE("0"), 
		SMSCODE("101"), 
		REGIST("102"), 
		LOGIN("103"),
		GET_SYS_DICT("111"),
		FORGET_PASSWORD("115"),
		GET_PAY_METHOD("116"),
		
		MY_CARD("201"), 
		CARD_INFO("202"),
		//获取最新版本信息
		GET_LASTEST_VERSION("108"),
		//获取我的套餐
		GET_PRD_INSTS("203"),
		GET_PRDS("221"),
		GET_ORDER("302"),
		GET_ORDER_LIST("303"),
		WAP_PAY("306"),
		
		// 获取套餐实例详情
		GET_PRDINST_INFO("204"),
		// 获取套餐实例返还清单
		GET_PRD_INST_LOGS("205"),
		//绑卡
		BIND_CARD("207"),
		
		// 创建订单
		CREATE_ORDER("301"),
		//支付
		AGENT_CHARGE("305"),
		
		// 获取用户信息
		
		GET_USER_INFO("105"),
		
		GET_LATEST_ACT("503"),
		GET_CUST_PROP("110"),
		// 修改密码
		CHANGE_PWD("106");
		HttpTag(String s) {
			this.ns = s;
		}
		
		@Override
		public String toString() {
			return this.ns;
		}
		
		public static HttpTag fromString(String text) {
		    if (text != null) {
		      for (HttpTag b : HttpTag.values()) {
		        if (text.equalsIgnoreCase(b.ns)) {
		          return b;
		        }
		      }
		    }
		    return null;
		}
		
		final String ns;
	}

	public static OrgData dispatch(HttpDataRequest request, OrgData data)
			throws Exception {
		OrgData result = data;
		
		switch (request.getTag()) {
			case LOGIN: {
				String dataStr = (String) data.getData();
				
				Gson gson = new Gson();
				LoginRes res = gson.fromJson(dataStr, LoginRes.class);
				
				result.setData(res);
				
				break;
			}
	
			default:
				break;
			}
		
		return result;
	}
}
