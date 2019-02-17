package com.throttle.rides;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.throttle.R;
import com.throttle.utils.Util;

public class AddRidePhotosScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private Button btnRideCreate = null;
	private Button btnPhotoSave = null;
	private Button btnPhotoRemove = null;

	private ImageView imgRidePhotoPic = null;
	private Button txtOpenGallery = null;
	private Button txtTakePhoto = null;
	private String picName = "Photos.png";
	private static int IMAGE_PICKER_SELECT = 100;
	private static int CAMERA_CAPTURE = 101;
	private Uri picUri;
	final int PIC_CROP = 3;

	public AddRidePhotosScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_ride_photos, container,
				false);
		imgRidePhotoPic = (ImageView) rootView
				.findViewById(R.id.imgRidePhotoPic);
		btnRideCreate = (Button) rootView.findViewById(R.id.btnCreateRide);
		btnRideCreate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		btnPhotoSave = (Button) rootView.findViewById(R.id.btnSavePhoto);
		btnPhotoSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					// params.put(Util.USER_ID, AppPreferences.getUSER_ID());
					// params.put(Util.USER_DOC_ID, "");
					// params.put(Util.DOC_IMAGE_URL, "Doc.png");
					// new AppService(mAppServiceListener).callService(
					// Urls.ADD_DOC_URL, params);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnPhotoRemove = (Button) rootView.findViewById(R.id.btnRemovePhoto);
		btnPhotoRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
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
					getParentFragment().startActivityForResult(captureIntent,
							CAMERA_CAPTURE);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
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
					// if (imgBikePic != null) {
					// imgBikePic.setImageBitmap(BitmapFactory
					// .decodeFile(picturePath));
					// }
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
			Bitmap thePic = extras.getParcelable("data");
			imgRidePhotoPic.setImageBitmap(thePic);
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
			cropIntent.putExtra("outputX", Util.DOC_PIC_IMAGE_X);
			cropIntent.putExtra("outputY", Util.DOC_PIC_IMAGE_Y);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			getParentFragment().startActivityForResult(cropIntent, PIC_CROP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
