package com.yhj.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainWeiXin extends Activity {
	
	private ViewPager mViewPager = null;
	private ImageView mWeiXin = null;
	private ImageView mAddress = null;
	private ImageView mFriend = null;
	private ImageView mSetting = null;
	
	private ImageView mTab = null;
	
	private int currIndex = 0;
	
	private int one = 0;
	private int two = 0;
	private int three = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_weixin);
		
		this.mViewPager = (ViewPager) this.findViewById(R.id.main_viewPager);
		this.mWeiXin = (ImageView) this.findViewById(R.id.img_weixin);
		this.mAddress = (ImageView) this.findViewById(R.id.img_address);
		this.mFriend = (ImageView) this.findViewById(R.id.img_find);
		this.mSetting = (ImageView) this.findViewById(R.id.img_setting);
		
		OnClickListener clickListener = new MyOnClickListener(0);
		this.mWeiXin.setOnClickListener(clickListener);
		
		OnClickListener clickListener2 = new MyOnClickListener(1);
		this.mAddress.setOnClickListener(clickListener2);
		
		OnClickListener clickListener3 = new MyOnClickListener(2);
		this.mFriend.setOnClickListener(clickListener3);
		
		OnClickListener clickListener4 = new MyOnClickListener(3);
		this.mSetting.setOnClickListener(clickListener4);
		
		this.mTab = (ImageView) this.findViewById(R.id.tab_bg);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View v1 = inflater.inflate(R.layout.main_tab_weixin, null);
		View v2 = inflater.inflate(R.layout.main_tab_address, null);
		View v3 = inflater.inflate(R.layout.main_tab_friends, null);
		View v4 = inflater.inflate(R.layout.main_tab_setting, null);
		
		final List<View> list = new ArrayList<View>();
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		int width = dm.widthPixels;
		this.one = width / 4;
		this.two = 2 * one;
		this.three = 3 * one;
		
		PagerAdapter adapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return list.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				 ((ViewPager)container).removeView(list.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(list.get(position));
				return list.get(position);
			}
		};
		this.mViewPager.setAdapter(adapter);
		
		MyOnPageChangeListener pageListener = new MyOnPageChangeListener();
		this.mViewPager.setOnPageChangeListener(pageListener);
		
		ImageView btn = (ImageView) v1.findViewById(R.id.weixin_right_btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainWeiXin.this,WeixinTopDialog.class);
				startActivity(i);
			}
		});
		
		Button exit = (Button) v4.findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new  Intent();
				i.setClass(MainWeiXin.this, ExitFromSetting.class);
				startActivity(i);
			}
		});
	}
	
	private class MyOnClickListener implements View.OnClickListener {
		private int mIndex = 0;
		
		public MyOnClickListener(int index) {
			this.mIndex = index;
		}
		
		@Override
		public void onClick(View v) {
			mViewPager.setCurrentItem(mIndex);
		}
	}
	
	
	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int index) {
			Animation animation = null;
			switch (index) {
			case 0:
				mWeiXin.setImageResource(R.drawable.tab_weixin_pressed);
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mAddress.setImageResource(R.drawable.tab_address_normal);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mFriend.setImageResource(R.drawable.tab_find_frd_normal);
				}
				if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mSetting.setImageResource(R.drawable.tab_settings_normal);
				}
				break;
			case 1:
				mAddress.setImageResource(R.drawable.tab_address_pressed);
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, one, 0, 0);
					mWeiXin.setImageResource(R.drawable.tab_weixin_normal);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mFriend.setImageResource(R.drawable.tab_find_frd_normal);
				}
				if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mSetting.setImageResource(R.drawable.tab_settings_normal);
				}
				break;
			case 2:
				mFriend.setImageResource(R.drawable.tab_find_frd_pressed);
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, two, 0, 0);
					mAddress.setImageResource(R.drawable.tab_weixin_normal);
				}
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mAddress.setImageResource(R.drawable.tab_address_normal);
				}
				if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mSetting.setImageResource(R.drawable.tab_settings_normal);
				}
				break;
			case 3:
				mSetting.setImageResource(R.drawable.tab_settings_pressed);
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, three, 0, 0);
					mAddress.setImageResource(R.drawable.tab_weixin_normal);
				}
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mAddress.setImageResource(R.drawable.tab_address_normal);
				}
				if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mFriend.setImageResource(R.drawable.tab_find_frd_normal);
				}
				break;
			}
			currIndex = index;
			animation.setDuration(150);
			animation.setFillAfter(true);
			mTab.startAnimation(animation);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_wei_xin, menu);
		return true;
	}

}
