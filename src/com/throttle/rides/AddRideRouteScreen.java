package com.throttle.rides;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.throttle.R;

public class AddRideRouteScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private EditText edtAddRideRouteDestination = null;
	private EditText edtAddRideRouteHaltLocation = null;
	private Spinner spnAddRideRouteOptions = null;

	private Button btnRideRouteNext = null;
	private Button btnRideRouteSave = null;
	private Button btnRideRouteRemove = null;

	public AddRideRouteScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_ride_route, container,
				false);
		edtAddRideRouteDestination = (EditText) rootView
				.findViewById(R.id.edtAddRideRouteDestination);
		edtAddRideRouteHaltLocation = (EditText) rootView
				.findViewById(R.id.edtAddRideRouteHaltLocation);
		spnAddRideRouteOptions = (Spinner) rootView.findViewById(R.id.spnAddRideRouteOptions);
		ArrayAdapter<String> ride_option_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.add_ride_route_options));
		spnAddRideRouteOptions.setAdapter(ride_option_adapter);
		
		btnRideRouteNext = (Button) rootView
				.findViewById(R.id.btnAddRideRouteNext);
		btnRideRouteNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		btnRideRouteSave = (Button) rootView
				.findViewById(R.id.btnAddRideRouteSave);
		btnRideRouteSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		btnRideRouteRemove = (Button) rootView
				.findViewById(R.id.btnAddRideRouteRemove);
		btnRideRouteRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return rootView;
	}

}
