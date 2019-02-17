package com.throttle;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.throttle.adapter.NavDrawerListAdapter;
import com.throttle.models.NavDrawerItem;
import com.throttle.utils.Util;

public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

//	public static FragmentManager fragmentManager;
	private Fragment currentFragment = null;
	private static String backFragmentName;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private String[] navSubMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public static Menu mMenu = null;
	private static int selectedPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		// BaseApplication.app_context = this;
		// Thread.setDefaultUncaughtExceptionHandler(new
		// UnknownExceptionHandler(this));
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navSubMenuTitles = getResources().getStringArray(
				R.array.nav_drawer_sub_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(6, -1)));
		
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
//				.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (currentFragment != null) {
			currentFragment.onActivityResult(requestCode, resultCode, data);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		mMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			if (mTitle.toString().equalsIgnoreCase(navMenuTitles[0])) {

			} else if (mTitle.toString().equalsIgnoreCase(navMenuTitles[1])) {
				Fragment fragment = new TopViewFragment(Util.ADD_CLUB_FRAGENT);
				BaseApplication.fragmentManager = getSupportFragmentManager();
				BaseApplication.fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
				currentFragment = fragment;
				// update selected item and title, then close the drawer
				mDrawerList.setItemChecked(1, true);
				mDrawerList.setSelection(1);
				setTitle(navSubMenuTitles[1]);
				mDrawerLayout.closeDrawer(mDrawerList);
			} else if (mTitle.toString().equalsIgnoreCase(navMenuTitles[2])) {
				Fragment fragment = new TopViewFragment(Util.ADD_RIDES_FRAGMENT);
				BaseApplication.fragmentManager = getSupportFragmentManager();
				BaseApplication.fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
				currentFragment = fragment;
				// update selected item and title, then close the drawer
				mDrawerList.setItemChecked(2, true);
				mDrawerList.setSelection(2);
				setTitle(navSubMenuTitles[2]);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		mMenu = menu;
		if (selectedPosition == 1 || selectedPosition == 2) {
			mMenu.getItem(0).setIcon(R.drawable.ic_action_new);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		// Fragment fragment = null;
		switch (position) {
		case 0:
			currentFragment = new TopViewFragment(Util.NEWS_FRAGMENT);
			break;
		case 1:
			currentFragment = new TopViewFragment(Util.USER_PROFILE_FRAGMENT);
			break;
		case 2:
			currentFragment = new TopViewFragment(Util.CLUB_FRAGMENT);
			break;
		case 3:
			currentFragment = new TopViewFragment(Util.RIDES_FRAGMENT);
			break;
		case 4:
			currentFragment = new TopViewFragment(Util.THROTTLE_STORE_FRAGMENT);
			break;
		case 5:
			//Logout
			currentFragment = null;
			break;
		case 6:
			
			break;

		default:
			break;
		}
		
		if (currentFragment != null) { // && position !=
										// Util.CLUB_DETAIL_FRAGMENT) {
			BaseApplication.fragmentManager = getSupportFragmentManager();
			String backStateName = navMenuTitles[position];
			BaseApplication.fragmentManager.beginTransaction()
					.replace(R.id.frame_container, currentFragment)
					.addToBackStack(backStateName).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			selectedPosition = position;
		} else {
			// error in creating fragment
			// Log.e("MainActivity", "Error in creating fragment");
			this.finish();
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		if (BaseApplication.fragmentManager != null) {
			Fragment fr = BaseApplication.fragmentManager
					.findFragmentById(R.id.frame_container);
			int backStackEntryCount = BaseApplication.fragmentManager.getBackStackEntryCount();
			if (backStackEntryCount == 1) {
				finish();
			} else {
				String fragmentSimpleName = fr.getClass().getSimpleName();
				super.onBackPressed();
			}
		}
	}

	public String getTitleForFragment(String fragmentName) {
		String title = null;
		return title;
	}

}