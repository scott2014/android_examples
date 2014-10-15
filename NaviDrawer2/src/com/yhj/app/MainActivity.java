package com.yhj.app;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private DrawerLayout mDrawerLayout = null;
	private ListView mDrawerList = null;
	
	private ActionBarDrawerToggle mDrawerTogge = null;
	private String[] animals = null;
	
	
	private CharSequence mTitle = null;
	private CharSequence mDrawerTitle = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		
		this.animals = getResources().getStringArray(R.array.title_list);
		this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		this.mDrawerList = (ListView) this.findViewById(R.id.drawer_list);
		
		mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item,animals));
		
		mDrawerList.setOnItemClickListener(new MyOnItemSelectted());
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		this.mDrawerTogge = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
			
		};
		
		this.mDrawerLayout.setDrawerListener(mDrawerTogge);
		
		if (savedInstanceState == null) {
			selectItem(0);
		}
	}
	
	private class MyOnItemSelectted implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
		
		
	}
	
	public void selectItem(int position) {
        Fragment fragment = new AnimalFragment();
        Bundle args = new Bundle();
        args.putInt("index", position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.drawer_content, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(animals[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	@SuppressLint("ValidFragment")
	public class AnimalFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.frame_animal, container,false);
			
			int i = getArguments().getInt("index");
			String animal_name = animals[i];
			
			int imgId = getResources().getIdentifier(animal_name.toLowerCase(Locale.getDefault()), "drawable",getActivity().getPackageName());
			ImageView image = (ImageView) rootView.findViewById(R.id.image);
			image.setImageResource(imgId);
			getActivity().setTitle(mDrawerTitle);
			
			Log.i("imgId", imgId + "");
			
			return rootView;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
   @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerTogge.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerTogge.onConfigurationChanged(newConfig);
    }

	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
		mDrawerTitle = title;
	}
    
    
	

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		if (drawerOpen) {
			setTitle(mTitle);
		}
		return super.onPrepareOptionsMenu(menu);
	}
}
