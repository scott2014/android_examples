package com.tencent.news.system.observer;

import com.tencent.news.model.SettingInfo;

public interface SettingObserver {
	public void updateSetting(SettingInfo setting);
}
