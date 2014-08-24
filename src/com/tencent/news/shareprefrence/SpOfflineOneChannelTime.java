package com.tencent.news.shareprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tencent.news.config.Constants;
import com.tencent.news.system.Application;
import com.tencent.news.utils.StringUtil;

public class SpOfflineOneChannelTime {

	public static void setUpdataChannelTime(String channelName, long time) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE_CHANNEL_TIME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("lastChannelUpdate" + channelName, time);
		editor.commit();
	}

	public static void delAll() {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE_CHANNEL_TIME, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	public static String getUpdataChannelTime(String channelName) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE_CHANNEL_TIME, Context.MODE_PRIVATE);
		long time = sp.getLong("lastChannelUpdate" + channelName, 0L);
		return StringUtil.getRelativeTimeStringEx(time);
	}

	public static long getUpdataChannelTimeNum(String channelName) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE_CHANNEL_TIME, Context.MODE_PRIVATE);
		long time = sp.getLong("lastChannelUpdate" + channelName, 0L);
		return time;
	}

}
