package com.throttle.store;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.throttle.utils.Util;

public class AddProdcutBasicScreen extends Fragment {

	private EditText edtProductName = null;
	private EditText edtProductCategory = null;
	private EditText edtProductSubCategory1 = null;
	private EditText edtProductSubCategory2 = null;
	private EditText edtProductCity = null;
	private EditText edtProductLocality = null;
	
	private Spinner spnSellAuction = null;
	private Spinner spnOldNew = null;
	
	private static EditText edtAddProductBidDate = null;

	private EditText edtProductPrice = null;
	private ImageView imgProductEstDatePicker = null;

	private Button btnCulbDetailsNext = null;

	private static int IMAGE_PICKER_SELECT = 101;
	private static int CAMERA_CAPTURE = 102;
	private Uri picUri;
	final int PIC_CROP = 4;
	private Bitmap thePic;

	public AddProdcutBasicScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_product_basicinfo,
				container, false);
		edtProductName = (EditText) rootView
				.findViewById(R.id.edtAddProductName);
		edtProductCategory = (EditText) rootView
				.findViewById(R.id.edtAddProductCategory);
		edtProductSubCategory1 = (EditText) rootView
				.findViewById(R.id.edtAddProductSubCategory1);
		edtProductSubCategory2 = (EditText) rootView
				.findViewById(R.id.edtAddProductSubCategory2);
		
		
		edtProductCity = (EditText) rootView
				.findViewById(R.id.edtAddProductCity);
		
		spnSellAuction = (Spinner) rootView.findViewById(R.id.spnAddProductSellAuction);
		ArrayAdapter<String> sell_option_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.add_product_sell_options));
		spnSellAuction.setAdapter(sell_option_adapter);
		
		spnOldNew = (Spinner) rootView.findViewById(R.id.spnAddProductSellAuction);
		ArrayAdapter<String> oldnew_option_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.add_product_old_new));
		spnOldNew.setAdapter(oldnew_option_adapter);
		
		edtAddProductBidDate = (EditText) rootView
				.findViewById(R.id.edtAddProductBidDate);
		edtProductPrice = (EditText) rootView
				.findViewById(R.id.edtAddProductMidBidPrice);
		
		imgProductEstDatePicker = (ImageView) rootView
				.findViewById(R.id.imgAddProductBidDate);
		imgProductEstDatePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnCulbDetailsNext = (Button) rootView
				.findViewById(R.id.btnAddProductNext);
		btnCulbDetailsNext.setOnClickListener(new View.OnClickListener() {

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
			edtAddProductBidDate.setText(day + "/" + month + "/" + year);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IMAGE_PICKER_SELECT) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					Uri selectedImage = data.getData();
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					Cursor cursor = getActivity().getContentResolver().query(
							selectedImage, filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String picturePath = cursor.getString(columnIndex);
					cursor.close();

					picUri = selectedImage;
					cropImage();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (requestCode == CAMERA_CAPTURE) {
			if (resultCode == Activity.RESULT_OK) {
				picUri = data.getData();
				cropImage();
			}
		} else if (requestCode == PIC_CROP) {
			// get the returned data
			Bundle extras = data.getExtras();
			// get the cropped bitmap
			thePic = extras.getParcelable("data");
			
			// new serviceTempAsyncTask().execute("");
		}
	}

	public void cropImage() {
		try {
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", Util.BIKE_PIC_IMAGE_X);
			cropIntent.putExtra("outputY", Util.BIKE_PIC_IMAGE_Y);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			getParentFragment().startActivityForResult(cropIntent, PIC_CROP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
