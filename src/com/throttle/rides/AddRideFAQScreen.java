package com.throttle.rides;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.throttle.R;

public class AddRideFAQScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private EditText edtAddRideFAQ = null;
	private EditText edtAddRideFAQAnswer = null;

	private Button btnRideFAQNext = null;
	private Button btnRideFAQSave = null;
	private Button btnRideFAQRemove = null;

	private LinearLayout layoutFAQHolder = null;
	private int questionViewAdded = 0;

	public AddRideFAQScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_ride_faq, container,
				false);
		edtAddRideFAQ = (EditText) rootView.findViewById(R.id.edtAddRideFAQ);
		edtAddRideFAQAnswer = (EditText) rootView
				.findViewById(R.id.edtAddRideFAQAnswer);

		btnRideFAQNext = (Button) rootView.findViewById(R.id.btnAddRideFAQNext);
		btnRideFAQNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		btnRideFAQSave = (Button) rootView.findViewById(R.id.btnAddRideFAQSave);
		btnRideFAQSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtAddRideFAQ.getText().toString().equalsIgnoreCase("")) {
					Toast.makeText(getActivity(), "Please enter question..",
							Toast.LENGTH_SHORT).show();
				} else {
					addFAQtoLayout(v);
				}
			}
		});

		btnRideFAQRemove = (Button) rootView
				.findViewById(R.id.btnAddRideFAQRemove);
		btnRideFAQRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				removeFAQfromLayout(v);

			}
		});
		layoutFAQHolder = (LinearLayout) rootView
				.findViewById(R.id.layoutAddFAQ);
		return rootView;
	}

	public void addFAQtoLayout(View v) {
		EditText edtFAQ = new EditText(v.getContext());
		edtFAQ.setBackground(getActivity().getResources().getDrawable(
				R.drawable.text_box_bg));
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 0, 0, 10);
		edtFAQ.setHeight(40);
		edtFAQ.setLayoutParams(layoutParams);
		String question = edtAddRideFAQ.getText().toString();
		edtFAQ.setText(question);
		layoutFAQHolder.addView(edtFAQ);
		edtAddRideFAQ.setText("");
		edtAddRideFAQAnswer.setText("");
	}

	public void removeFAQfromLayout(View v) {
		layoutFAQHolder.removeViewAt(1);
	}

}
