package com.throttle.clubs;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.throttle.AddClubFragment;
import com.throttle.R;
import com.throttle.utils.Util;

public class AddClubDetailsScreen extends Fragment {

	private EditText edtClubName = null;
	private EditText edtClubCity = null;
	private static EditText edtClubEstablished = null;
	private EditText edtClubContactNo = null;
	private EditText edtClubBikeBrand = null;
	private ImageView imgClubEstDatePicker = null;
	private ImageView imgClubBikePic = null;

	private Button txtOpenGallery = null;
	private Button txtTakePhoto = null;
	private Button btnCulbDetailsNext = null;

	private static int IMAGE_PICKER_SELECT = 101;
	private static int CAMERA_CAPTURE = 102;
	private Uri picUri;
	final int PIC_CROP = 4;
	private Bitmap thePic;

	public AddClubDetailsScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_club_details, container,
				false);
		edtClubName = (EditText) rootView.findViewById(R.id.edtAddClubName);
		edtClubCity = (EditText) rootView.findViewById(R.id.edtAddClubCity);
		edtClubEstablished = (EditText) rootView
				.findViewById(R.id.edtAddClubDate);
		edtClubContactNo = (EditText) rootView
				.findViewById(R.id.edtAddClubContactNo);
		edtClubBikeBrand = (EditText) rootView
				.findViewById(R.id.edtAddClubBikeBrand);
		imgClubEstDatePicker = (ImageView) rootView
				.findViewById(R.id.imgAddClubDate);
		imgClubEstDatePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		imgClubBikePic = (ImageView) rootView
				.findViewById(R.id.imgAddClubUploadPic);
		txtOpenGallery = (Button) rootView
				.findViewById(R.id.txtOpenImageGallery);
		txtOpenGallery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				getParentFragment().startActivityForResult(i,
						IMAGE_PICKER_SELECT);
			}
		});
		txtTakePhoto = (Button) rootView.findViewById(R.id.txtTakeAPhoto);
		txtTakePhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent captureIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					getParentFragment().startActivityForResult(captureIntent, CAMERA_CAPTURE);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
		btnCulbDetailsNext = (Button) rootView
				.findViewById(R.id.btnAddClubNext);
		btnCulbDetailsNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String clubname = edtClubName.getText().toString();
				String clundate = edtClubEstablished.getText().toString();
				String clubcity = edtClubCity.getText().toString();
				String clubcontactno = edtClubContactNo.getText().toString();
				String clubbkiebrand = edtClubBikeBrand.getText().toString();
				Bitmap clubbikepic = imgClubBikePic.getDrawingCache();

				ClubDetails.setClub_name(clubname);
				ClubDetails.setClub_est_date(clundate);
				ClubDetails.setClub_city(clubcity);
				ClubDetails.setClub_contact_no(clubcontactno);
				ClubDetails.setClub_brand_bike(clubbkiebrand);
				ClubDetails.setClub_bike_photo(clubbikepic);
				AddClubFragment.setClubFragmentTab(1);

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
			edtClubEstablished.setText(day + "/" + month + "/" + year);
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
			imgClubBikePic.setImageBitmap(thePic);
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
