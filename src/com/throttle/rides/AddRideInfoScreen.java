package com.throttle.rides;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.throttle.R;

public class AddRideInfoScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private EditText edtRideName = null;
	private EditText edtRideClubName = null;
	private EditText edtRideLeadName = null;
	private static EditText edtRideStartDate = null;
	private static EditText edtRideEndDate = null;
	private EditText edtRideApproxKM = null;
	private EditText edtRidePrice = null;
	private Spinner spnAddRideInfoOptions = null;

	private ImageView imgRideStartDatePicker = null;
	private ImageView imgRideEndDatePicker = null;

	private Button btnRideInfoNext = null;

	public AddRideInfoScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_ride_info, container,
				false);
		edtRideName = (EditText) rootView.findViewById(R.id.edtAddRideName);
		edtRideClubName = (EditText) rootView
				.findViewById(R.id.edtAddRideClubName);
		edtRideStartDate = (EditText) rootView
				.findViewById(R.id.edtAddRideStartDate);
		edtRideEndDate = (EditText) rootView
				.findViewById(R.id.edtAddRideEndDate);
		edtRideLeadName = (EditText) rootView
				.findViewById(R.id.edtAddRideLeadName);
		edtRideApproxKM = (EditText) rootView
				.findViewById(R.id.edtAddRideApproxKM);
		edtRidePrice = (EditText) rootView.findViewById(R.id.edtAddRidePrice);
		spnAddRideInfoOptions = (Spinner) rootView.findViewById(R.id.spnAddRideInfoOptions);
		ArrayAdapter<String> ride_option_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.add_ride_info_options));
		spnAddRideInfoOptions.setAdapter(ride_option_adapter);

		imgRideStartDatePicker = (ImageView) rootView
				.findViewById(R.id.imgAddRideStartDatePicker);
		imgRideStartDatePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		imgRideEndDatePicker = (ImageView) rootView
				.findViewById(R.id.imgAddRideEndDatePicker);
		imgRideEndDatePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnRideInfoNext = (Button) rootView
				.findViewById(R.id.btnAddRideInfoNext);
		btnRideInfoNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return rootView;
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			// String y = Integer.toString(year);
			edtRideStartDate.setText(day + "/" + month + "/" + year);
		}
	}

}
