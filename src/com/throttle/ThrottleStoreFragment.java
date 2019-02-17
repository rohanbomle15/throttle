package com.throttle;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.utils.Util;

public class ThrottleStoreFragment extends Fragment {

	private ImageButton btnBikes;
	private ImageButton btnAccessories;
	private ImageButton btnSpares;

	private Button btnPostYourPorduct;
	
	private Spinner spnMoreCategories;

	private AppGetServiceListener mAppGetServiceListener = new AppGetServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.store_screen, container,
				false);
		spnMoreCategories = (Spinner) rootView
				.findViewById(R.id.spnStoreMoreCategories);
		spnMoreCategories.setBackgroundColor(getActivity().getResources()
				.getColor(R.color.white_color));
		ArrayAdapter<String> categories_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.store_more_catogries));
		spnMoreCategories.setAdapter(categories_adapter);
		btnBikes = (ImageButton) rootView.findViewById(R.id.storeBtnBikes);
		btnBikes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadProductListFragment(Util.BIKE_PRODCUT_ID);
			}
		});
		btnAccessories = (ImageButton) rootView
				.findViewById(R.id.storeBtnAccessories);
		btnAccessories.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadProductListFragment(Util.ACCESSORIES_PRODUCT_IS);
			}
		});
		btnSpares = (ImageButton) rootView.findViewById(R.id.storeBtnSpares);
		btnSpares.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadProductListFragment(Util.SPEARS_PRODCUT_ID);
			}
		});
		btnPostYourPorduct = (Button) rootView.findViewById(R.id.btnPostYourPorduct);
		btnPostYourPorduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment fragment = new TopViewFragment(
						Util.ADD_PRODCUT_FRAGMENT);
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
			}
		});
		return rootView;
	}

	public void loadProductListFragment(String listingID) {
		Fragment fragment = new TopViewFragment(Util.PRODCUT_LIST_FRAGMENT);
		BaseApplication.SELECTED_PRODUCT_ID = listingID;
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
	}
}
