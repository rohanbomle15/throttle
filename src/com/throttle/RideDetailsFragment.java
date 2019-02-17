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

import com.throttle.clubs.ChatClubScreen;
import com.throttle.rides.JoinRideScreen;
import com.throttle.utils.FontManager;

public class RideDetailsFragment extends Fragment {

	private Context ctx;
	private FragmentTabHost mTabHost;
	private Fragment fragment;

	private static final String TAB1 = "Join";
	private static final String TAB2 = "FAQs";
	private static final String TAB3 = "Chat";

	public RideDetailsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mTabHost = new FragmentTabHost(getActivity());
		ctx = mTabHost.getContext();
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.layout.profile_screen);

		Bundle arg1 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB1)
						.setIndicator(createTabView(ctx, TAB1)),
				JoinRideScreen.class, arg1);

		Bundle arg2 = new Bundle();
		mTabHost.addTab(
				mTabHost.newTabSpec(TAB2)
						.setIndicator(createTabView(ctx, TAB2)),
				ChatClubScreen.class, arg2);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				// if(tabId.equalsIgnoreCase("Tab1")){
				// fragment =
				// getChildFragmentManager().findFragmentByTag("Tab1");
				// }else if(tabId.equalsIgnoreCase("Tab2")){
				// fragment =
				// getChildFragmentManager().findFragmentByTag("Tab2");
				// }else if(tabId.equalsIgnoreCase("Tab3")){
				// fragment =
				// getChildFragmentManager().findFragmentByTag("Tab3");
				// }else if(tabId.equalsIgnoreCase("Tab4")){
				// fragment =
				// getChildFragmentManager().findFragmentByTag("Tab4");
				// }else if(tabId.equalsIgnoreCase("Tab5")){
				// fragment =
				// getChildFragmentManager().findFragmentByTag("Tab5");
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (fragment != null) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}
	}

}