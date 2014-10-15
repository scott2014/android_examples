package com.yhj.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatsnewDoor extends Activity {
	
	private ImageView left = null;
	private ImageView right = null;
	private TextView text = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whats_door);
		
		this.left = (ImageView) this.findViewById(R.id.imageLeft);
		this.right = (ImageView) this.findViewById(R.id.imageRight);
		this.text = (TextView) this.findViewById(R.id.text1);
		
		 	AnimationSet anim = new AnimationSet(true);
			TranslateAnimation mytranslateanim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
			mytranslateanim.setDuration(2000);
			anim.setStartOffset(800);
			anim.addAnimation(mytranslateanim);
			anim.setFillAfter(true);
			left.startAnimation(anim);
			
			AnimationSet anim1 = new AnimationSet(true);
			TranslateAnimation mytranslateanim1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
			mytranslateanim1.setDuration(1500);
			anim1.addAnimation(mytranslateanim1);
			anim1.setStartOffset(800);
			anim1.setFillAfter(true);
			right.startAnimation(anim1);
			
			AnimationSet anim2 = new AnimationSet(true);
			ScaleAnimation myscaleanim = new ScaleAnimation(1f,3f,1f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
			myscaleanim.setDuration(1000);
			AlphaAnimation myalphaanim = new AlphaAnimation(1,0.0001f);
			myalphaanim.setDuration(1500);
			anim2.addAnimation(myscaleanim);
			anim2.addAnimation(myalphaanim);
			anim2.setFillAfter(true);
			text.startAnimation(anim2);
			
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){
					Intent intent = new Intent (WhatsnewDoor.this,MainWeiXin.class);			
					startActivity(intent);			
					WhatsnewDoor.this.finish();
				}
			}, 2300);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.whatsnew_door, menu);
		return true;
	}

}
