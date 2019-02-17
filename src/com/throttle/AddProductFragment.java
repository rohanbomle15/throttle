package com.throttle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.throttle.clubs.AddClubDescriptionScreen;
import com.throttle.clubs.AddClubDetailsScreen;
import com.throttle.clubs.AddClubPhotosScreen;
import com.throttle.store.AddProdcutBasicScreen;
import com.throttle.store.AddProductDetailsScreen;
import com.throttle.store.AddProductPhotosScreen;
import com.throttle.utils.FontManager;

public class AddProductFragment extends Fragment {

	private Context ctx;
	private static FragmentTabHost mTabHost;
	private Fragment fragment;
	
	private static final String TAB1 = "BASIC INFO";
	private static final String TAB2 = "DETAILS";
	private static final String TAB3 = "PHOTOS";
	
	public AddProductFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mTabHost = new FragmentTabHost(getActivity());
		ctx = mTabHost.getContext();
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.layout.add_club_screen);

		Bundle arg1 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB1).setIndicator(
						createTabView(ctx, TAB1)), AddProdcutBasicScreen.class, arg1);

		Bundle arg2 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB2).setIndicator(
						createTabView(ctx, TAB2)), AddProductDetailsScreen.class, arg2);

		Bundle arg3 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB3).setIndicator(
						createTabView(ctx, TAB3)), AddProductPhotosScreen.class, arg3);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
//				if (tabId.equalsIgnoreCase(TAB1)) {
//					fragment = getChildFragmentManager().findFragmentByTag(
//							TAB1);
//				} else if (tabId.equalsIgnoreCase(TAB2)) {
//					fragment = getChildFragmentManager().findFragmentByTag(
//							TAB2);
//				} else if (tabId.equalsIgnoreCase(TAB3)) {
//					fragment = getChildFragmentManager().findFragmentByTag(
//							TAB3);
//				} else if (tabId.equalsIgnoreCase(TAB4)) {
//					fragment = getChildFragmentManager().findFragmentByTag(
//							TAB4);
//				} else if (tabId.equalsIgnoreCase(TAB5)) {
//					fragment = getChildFragmentManager().findFragmentByTag(
//							TAB5);
//				}

				fragment = getChildFragmentManager().findFragmentByTag(tabId);
				System.out.println(fragment);
			}
		});
		fragment = getChildFragmentManager().findFragmentByTag(TAB1);
		return mTabHost;

	}

	private static View createTabView(Context context, String tabText) {

		View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,
				null, false);
		ImageView img = (ImageView) view.findViewById(R.id.imgTabBackground);
		TextView txtTitle = (TextView) view.findViewById(R.id.txtTabTitle);
		txtTitle.setTypeface(FontManager.getFontManager(BaseApplication.app_context).geteBebas());
		txtTitle.setText(tabText);
		img.setBackgroundResource(R.drawable.tab_selector);

		return view;
	}
	
	public static void setClubFragmentTab(int tab){
		mTabHost.setCurrentTab(tab);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (fragment != null) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}
	}
	
}