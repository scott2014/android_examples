package com.example.switchbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.switchbutton.SwitchButton.OnSwitchChangedListener;

public class MainActivity extends Activity {
	
	private SwitchButton sw = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.sw = (SwitchButton) this.findViewById(R.id.sw);
		
		this.sw.setStatus(true);
		
		this.sw.setOnSwitchChangedListener(new OnSwitchChangedListener() {
			
			@Override
			public void onSwitchChanged(SwitchButton obj, int status) {
				Toast.makeText(MainActivity.this, "status:" + status, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
