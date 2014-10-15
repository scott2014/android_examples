package com.tencent.news.shareprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tencent.news.cache.UserDBHelper;
import com.tencent.news.config.Constants;
import com.tencent.news.model.SettingInfo;
import com.tencent.news.system.Application;

public class SpSetting {
	public static SharedPreferences getSettingSharedPreferences() {
		SharedPreferences sp = Application.getInstance().getSharedPreferences(Constants.SP_SETTING, Context.MODE_PRIVATE);
		sp.edit().commit();
		return sp;
	}

	public static void saveSetting(SettingInfo setting) {

		if (setting.getUserInfo() != null) {
			UserDBHelper.getInstance().saveUserInfo(setting.getUserInfo());
		} else {
			UserDBHelper.getInstance().logoutUserInfo();
		}

		Editor editor = getSettingSharedPreferences().edit();
		editor.putBoolean(Constants.SETTING_KEY_IF_AUTO_LOAD_MORE, setting.isIfAutoLoadMore());
		editor.putBoolean(Constants.SETTING_KEY_IF_TEXT_MODE, setting.isIfTextMode());
		editor.putBoolean(Constants.SETTING_KEY_IF_PUSH, setting.isIfPush());
		editor.putInt(Constants.SETTING_KEY_TEXT_SIZE, setting.getTextSize());
		//editor.putInt(Constants.SETTING_THEME, setting.getThemeSetting());		
		editor.commit();
	}
}
