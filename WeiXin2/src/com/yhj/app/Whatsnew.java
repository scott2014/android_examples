package com.yhj.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Whatsnew extends Activity {
	
	private ViewPager mViewPager = null;
	private ImageView mPage0 = null;
	private ImageView mPage1 = null;
	private ImageView mPage2 = null;
	private ImageView mPage3 = null;
	private ImageView mPage4 = null;
	private ImageView mPage5 = null;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whatsnew_viewpager);
		
		this.mViewPager = (ViewPager) this.findViewById(R.id.viewPager);
		this.mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		this.mPage0 = (ImageView) this.findViewById(R.id.page0);
		this.mPage1 = (ImageView) this.findViewById(R.id.page1);
		this.mPage2 = (ImageView) this.findViewById(R.id.page2);
		this.mPage3 = (ImageView) this.findViewById(R.id.page3);
		this.mPage4 = (ImageView) this.findViewById(R.id.page4);
		this.mPage5 = (ImageView) this.findViewById(R.id.page5);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View v1 = inflater.inflate(R.layout.whats1, null);
		View v2 = inflater.inflate(R.layout.whats2, null);
		View v3 = inflater.inflate(R.layout.whats3, null);
		View v4 = inflater.inflate(R.layout.whats4, null);
		View v5 = inflater.inflate(R.layout.whats5, null);
		View v6 = inflater.inflate(R.layout.whats6, null);
		
		
		final List<View> data = new ArrayList<View>();
		data.add(v1);
		data.add(v2);
		data.add(v3);
		data.add(v4);
		data.add(v5);
		data.add(v6);
		
		PagerAdapter adpater = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return data.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(data.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(data.get(position));
				return data.get(position);
			}
			
			
		};
		
		this.mViewPager.setAdapter(adpater);
		
		Button startBtn = (Button) v6.findViewById(R.id.startButton);
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent();
				i.setClass(Whatsnew.this, WhatsnewDoor.class);
				startActivity(i);
			}
		});
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int pos) {
			switch (pos) {
			case 0:				
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 4:
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 5:
				mPage5.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage4.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.whatsnew, menu);
		return true;
	}

}
