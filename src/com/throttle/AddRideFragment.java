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

import com.throttle.rides.AddRideDetailsScreen;
import com.throttle.rides.AddRideFAQScreen;
import com.throttle.rides.AddRideInfoScreen;
import com.throttle.rides.AddRidePhotosScreen;
import com.throttle.rides.AddRideRouteScreen;
import com.throttle.utils.FontManager;

public class AddRideFragment extends Fragment {

	private Context ctx;
	private static FragmentTabHost mTabHost;
	private Fragment fragment;

	private static final String TAB1 = "Info";
	private static final String TAB2 = "Details";
	private static final String TAB3 = "Route";
	private static final String TAB4 = "FAQs";
	private static final String TAB5 = "Photos";

	public AddRideFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mTabHost = new FragmentTabHost(getActivity());
		ctx = mTabHost.getContext();
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.layout.add_ride_screen);

		Bundle arg1 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB1)
						.setIndicator(createTabView(ctx, TAB1)),
				AddRideInfoScreen.class, arg1);

		Bundle arg2 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB2)
						.setIndicator(createTabView(ctx, TAB2)),
				AddRideDetailsScreen.class, arg2);

		Bundle arg3 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB3)
						.setIndicator(createTabView(ctx, TAB3)),
				AddRideRouteScreen.class, arg3);

		Bundle arg4 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB4)
						.setIndicator(createTabView(ctx, TAB4)),
				AddRideFAQScreen.class, arg4);

		Bundle arg5 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB5)
						.setIndicator(createTabView(ctx, TAB5)),
				AddRidePhotosScreen.class, arg5);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				// if (tabId.equalsIgnoreCase(TAB1)) {
				// fragment = getChildFragmentManager().findFragmentByTag(
				// TAB1);
				// } else if (tabId.equalsIgnoreCase(TAB2)) {
				// fragment = getChildFragmentManager().findFragmentByTag(
				// TAB2);
				// } else if (tabId.equalsIgnoreCase(TAB3)) {
				// fragment = getChildFragmentManager().findFragmentByTag(
				// TAB3);
				// } else if (tabId.equalsIgnoreCase(TAB4)) {
				// fragment = getChildFragmentManager().findFragmentByTag(
				// TAB4);
				// } else if (tabId.equalsIgnoreCase(TAB5)) {
				// fragment = getChildFragmentManager().findFragmentByTag(
				// TAB5);
				// }

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
		txtTitle.setText(tabText);
		txtTitle.setTypeface(FontManager.getFontManager(BaseApplication.app_context).geteBebas());
		img.setBackgroundResource(R.drawable.tab_selector);

		return view;
	}

	public static void setClubFragmentTab(int tab) {
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