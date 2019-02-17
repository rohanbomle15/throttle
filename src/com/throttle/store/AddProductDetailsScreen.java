package com.throttle.store;

import com.throttle.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProductDetailsScreen extends Fragment {

	private EditText edtAddProductDescription;
	private Spinner spnAddProductContactType;
	private Button btnAddProductDetailsNext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_prodcut_details,
				container, false);

		edtAddProductDescription = (EditText) rootView
				.findViewById(R.id.edtAddProductDescription);

		spnAddProductContactType = (Spinner) rootView
				.findViewById(R.id.spnAddProductContactType);
		ArrayAdapter<String> sell_option_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.add_product_sell_options));
		spnAddProductContactType.setAdapter(sell_option_adapter);

		btnAddProductDetailsNext = (Button) rootView
				.findViewById(R.id.btnAddProductDetailsNext);
		btnAddProductDetailsNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		return rootView;
	}
}
