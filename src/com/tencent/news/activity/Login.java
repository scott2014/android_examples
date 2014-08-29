package com.tencent.news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.news.R;
import com.tencent.news.command.HttpDataRequest;
import com.tencent.news.command.HttpTagDispatch.HttpTag;
import com.tencent.news.http.HttpEngine.HttpCode;
import com.tencent.news.http.request.HttpRequestFactory;
import com.tencent.news.model.OrgData;
import com.tencent.news.model.dataloader.BaseDataLoader;
import com.tencent.news.model.dataloader.BaseDataLoader.DataLoaderCallback;
import com.tencent.news.model.dataloader.CacheDataLoader;
import com.tencent.news.model.req.LoginReq;
import com.tencent.news.utils.Crypt;
import com.tencent.news.utils.SHA1;

public class Login extends Activity implements DataLoaderCallback {
	
	private EditText et_phone_no = null;
	private EditText et_passwd = null;
	private Button btn_login = null;
	
	CacheDataLoader loginLoader = new CacheDataLoader(Login.class.getSimpleName(), this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		this.et_phone_no = (EditText) this.findViewById(R.id.phone_no);
		this.et_passwd = (EditText) this.findViewById(R.id.password);
		this.btn_login = (Button) this.findViewById(R.id.login_btn);
		
		this.btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LoginReq reqObj = new LoginReq();
				reqObj.setCmd(HttpTag.LOGIN);
				
				reqObj.setTel(et_phone_no.getText().toString());
				reqObj.setPwd(SHA1.crypt(et_passwd.getText().toString()));
				
				HttpDataRequest request = HttpRequestFactory.getInstance().post(reqObj);
				
				loginLoader.loadData(BaseDataLoader.LOADER_FROM_SRV, request);
			}
		});
	}

	@Override
	public void onQueryError(BaseDataLoader dataloader, HttpCode retCode,
			String msg) {
		
	}

	@Override
	public void onQueryComplete(BaseDataLoader dataloader, boolean cache) {
		OrgData org = dataloader.getOrgData();
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
