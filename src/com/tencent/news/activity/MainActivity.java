package com.tencent.news.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.news.R;
import com.tencent.news.http.HttpEngine.HttpCode;
import com.tencent.news.model.dataloader.BaseDataLoader;
import com.tencent.news.model.dataloader.BaseDataLoader.DataLoaderCallback;

public class MainActivity extends Activity implements DataLoaderCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);
	}

	@Override
	public void onQueryError(BaseDataLoader dataloader, HttpCode retCode,
			String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryComplete(BaseDataLoader dataloader, boolean cache) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartQuery(BaseDataLoader dataloader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQuerying(BaseDataLoader dataloader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryCanceled(BaseDataLoader dataloader) {
		// TODO Auto-generated method stub
		
	}
}
