package com.tencent.news.shareprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tencent.news.config.Constants;
import com.tencent.news.model.pojo.OfflineChannel;
import com.tencent.news.system.Application;

public class SpOffline {

	public static void setChannelAppid(String channel, String appid) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("appid" + channel, appid);
		editor.commit();
	}

	public static String getChannelAppid(String channel) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		return sp.getString("appid" + channel, "");
	}

	public static void setUpdataAllTime(long time) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("lastUpdate", time);
		editor.commit();
	}

	public static long getUpdataAllTime() {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		return sp.getLong("lastUpdate", 0L);
	}

	public static void setChannelVersion(String channel, String Version) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(channel, Version);
		editor.commit();
	}

	public static String getChannelVersion(String channel) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		return sp.getString(channel, "0");
	}

	public static void setChannelVersion(OfflineChannel offlineChannels) {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		if (offlineChannels != null) {
			editor.putString(offlineChannels.getChlid(), offlineChannels.getVersion());
		}
		editor.commit();
	}

	public static void delAll() {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_OFFLINE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

}
