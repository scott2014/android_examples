package com.tencent.news.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.tencent.news.config.Constants;

public class ChannelLoadReceiver extends BroadcastReceiver {
	private Handler mHandler = null;
	
	public ChannelLoadReceiver(Handler mHandler){
		this.mHandler = mHandler;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//Message msg = Message.obtain();
		mHandler.sendEmptyMessage(Constants.WHAT_CHANNEL_LOAD);
	}
}
