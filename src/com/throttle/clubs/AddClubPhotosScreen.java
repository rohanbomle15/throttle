package com.throttle.clubs;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.dbhelper.AppPreferences;
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class AddClubPhotosScreen extends Fragment {

	private Button btnOpenGallery = null;
	private Button btnTakePhoto = null;
	private Button btnClubCreate = null;

	private String picName = "photo.png";
	private static int CAMERA_CAPTURE = 101;
	private Uri picUri;
	final int PIC_CROP = 2;
	private Bitmap thePic;

	private GridLayout ridePhotoGridView = null;
	int gridViewWidth = 50;
	int gridViewHeight = 200;
	int total = 12;
	int column = 0;
	int row = 0;

	private static int IMAGE_PICKER_SELECT = 1002;

	private AppServiceListener mAppServiceListener = new AppServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), jsonObj, Toast.LENGTH_SHORT).show();
		}
	};
	private Map<String, String> params = new HashMap<String, String>();

	public AddClubPhotosScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_club_photos, container,
				false);
		btnOpenGallery = (Button) rootView
				.findViewById(R.id.btnOpenImageGallery);
		btnOpenGallery.setOnClickListener(new View.OnClickListener() {

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
		btnTakePhoto = (Button) rootView.findViewById(R.id.btnTakeAPhoto);
		btnTakePhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent captureIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				getParentFragment().startActivityForResult(captureIntent,
						CAMERA_CAPTURE);
			}
		});
		btnClubCreate = (Button) rootView
				.findViewById(R.id.btnCreateClubPhotos);
		btnClubCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				params.put(Util.USER_ID, AppPreferences.getUSER_ID());
				params.put(Util.CLUB_NAME, ClubDetails.getClub_name());
				params.put(Util.ESTABLISHEDDATE, ClubDetails.getClub_est_date());
				params.put(Util.CITY, ClubDetails.getClub_city());
				params.put(Util.CONTACTNUMBER, ClubDetails.getClub_contact_no());
				params.put(Util.BRAND, ClubDetails.getClub_brand_bike());
				params.put(Util.CLUBLOGOPATH, picName);// Urls.CLUB_LOGO_URL+picName);
				params.put(Util.CLUBIMAGEURL, picName);// Urls.CLUB_BANNER_URL+picName);
				params.put(Util.MEETINGPOINT,
						ClubDetails.getClub_meeting_point());
				params.put(Util.MEETINGDAY, ClubDetails.getClub_meeting_days());
				params.put(Util.CLUBDESCRIPTION, ClubDetails.getClub_vision());
				params.put(Util.CREATEDBYUSERID, AppPreferences.getUSER_ID());

				new AppService(mAppServiceListener).callService(
						Urls.ADD_CLUB_URL, params);
			}
		});
		column = 0;
		row = 0;
		ridePhotoGridView = (GridLayout) rootView.findViewById(R.id.ridePhotosGridview);
		ridePhotoGridView.setColumnCount(3);
		gridViewWidth = (BaseApplication.DEVICE_WIDTH / 3) - 25;
		return rootView;
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
					picName = picturePath.substring(
							picturePath.lastIndexOf("/") + 1,
							picturePath.length());
					cursor.close();
					picUri = selectedImage;
					cropImage();
					
					// if (imgClubBikePic != null) {
					// imgClubBikePic.setImageBitmap(BitmapFactory
					// .decodeFile(picturePath));
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if (requestCode == CAMERA_CAPTURE) {
			if (resultCode == Activity.RESULT_OK) {
				picUri = data.getData();
				cropImage();
			}
		} else if (requestCode == PIC_CROP) {
			// get the returned data
			Bundle extras = data.getExtras();
			// get the cropped bitmap
			thePic = extras.getParcelable("data");
			addPhotoToGridView();
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
			cropIntent.putExtra("outputX", 0);
			cropIntent.putExtra("outputY", 0);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			getParentFragment().startActivityForResult(cropIntent, PIC_CROP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPhotoToGridView() {

		LinearLayout mainLayout = new LinearLayout(getActivity()
				.getApplicationContext());
		GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
		layoutParams.width = gridViewWidth;
		layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setPadding(0, 0, 10, 5);
		mainLayout.setLayoutParams(layoutParams);

		ImageView photoImage = new ImageView(getActivity()
				.getApplicationContext());
		GridLayout.LayoutParams paramImage = new GridLayout.LayoutParams();
		paramImage.height = 300;
		paramImage.width = LayoutParams.WRAP_CONTENT;
		photoImage.setLayoutParams(paramImage);
		if (thePic == null) {
			photoImage.setImageDrawable(getActivity().getResources()
					.getDrawable(R.drawable.sample_bike));
		} else {
			photoImage.setImageBitmap(thePic);
		}

		mainLayout.addView(photoImage, 0);

		ridePhotoGridView.addView(mainLayout);
		if (column == 2) {
			column = 0;
			row = row + 1;
		} else {
			column = column + 1;
		}

	}
}
