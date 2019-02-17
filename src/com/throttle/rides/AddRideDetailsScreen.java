package com.throttle.rides;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.throttle.R;

public class AddRideDetailsScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private EditText edtRideDescription = null;

	private Button btnRideDetailsNext = null;

	public AddRideDetailsScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_ride_details, container,
				false);
		edtRideDescription = (EditText) rootView
				.findViewById(R.id.edtAddRideDetails);
		btnRideDetailsNext = (Button) rootView
				.findViewById(R.id.btnAddRideDetailsNext);
		btnRideDetailsNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return rootView;
	}

}
