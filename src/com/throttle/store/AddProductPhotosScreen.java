package com.throttle.store;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Util;

public class AddProductPhotosScreen extends Fragment {

	private ImageView imgPhotoPic = null;

	private Button btnPhotoSave = null;
	private Button btnPhotoRemove = null;

	private Button txtOpenGallery = null;
	private Button txtTakePhoto = null;
	private String picName = "Photos.png";
	private static int IMAGE_PICKER_SELECT = 100;
	private static int CAMERA_CAPTURE = 101;
	private Uri picUri;
	final int PIC_CROP = 3;
	private Bitmap thePic;

	private GridLayout photosGridView = null;
	int gridViewWidth = 50;
	int gridViewHeight = 200;
	int total = 12;
	int column = 0;
	int row = 0;

	public AddProductPhotosScreen() {

	}

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_product_photos, container,
				false);

		imgPhotoPic = (ImageView) rootView
				.findViewById(R.id.imgProductPhotosUploadPic);
		btnPhotoSave = (Button) rootView.findViewById(R.id.btnSavePhoto);
		btnPhotoSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (thePic == null) {
					Toast.makeText(getActivity(),
							"No picture found to upload.", Toast.LENGTH_SHORT)
							.show();
				} else {
					// try {
					// params.put(Util.USER_ID, AppPreferences.getUSER_ID());
					// params.put(Util.USER_DOC_ID, "");
					// params.put(Util.DOC_IMAGE_URL, "Doc.png");
					// new AppService(mAppServiceListener).callService(
					// Urls.ADD_DOC_URL, params);
					//
					// } catch (Exception e) {
					// e.printStackTrace();
					// }
					addPhotosToGrid(v);
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
		photosGridView = (GridLayout) rootView
				.findViewById(R.id.productPhotosGridview);
		photosGridView.setColumnCount(3);
		gridViewWidth = (BaseApplication.DEVICE_WIDTH / 3) - 20;
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
			thePic = extras.getParcelable("data");
			imgPhotoPic.setImageBitmap(thePic);
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

	public void loadPhotosGrid() {
		int totalBikes = BaseApplication.bike_details.size();
		if (totalBikes != 0 && BaseApplication.bike_details != null) {
			for (int i = 0; i < totalBikes; i++) {
				LinearLayout mainLayout = new LinearLayout(getActivity()
						.getApplicationContext());
				GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
				layoutParams.width = gridViewWidth;
				layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
				mainLayout.setOrientation(LinearLayout.VERTICAL);
				mainLayout.setPadding(0, 0, 10, 5);
				layoutParams.columnSpec = GridLayout.spec(column);
				layoutParams.rowSpec = GridLayout.spec(row);
				mainLayout.setLayoutParams(layoutParams);

				ImageView bikeImage = new ImageView(getActivity()
						.getApplicationContext());
				GridLayout.LayoutParams paramImage = new GridLayout.LayoutParams();
				paramImage.height = LayoutParams.WRAP_CONTENT;
				paramImage.width = LayoutParams.WRAP_CONTENT;
				bikeImage.setLayoutParams(paramImage);
				String bikeImageURL = BaseApplication.bike_details.get(i)
						.getBikeImageURL();
				if (bikeImageURL == null && bikeImageURL.equalsIgnoreCase("")) {
					bikeImage.setImageDrawable(getActivity().getResources()
							.getDrawable(R.drawable.sample_bike));
				} else {
					bikeImage.setImageDrawable(getActivity().getResources()
							.getDrawable(R.drawable.sample_bike));
					// ImageViewBitmapManager.INSTANCE.loadBitmap(bikeImageURL,
					// bikeImage, 100, 50);
				}

				GridLayout.LayoutParams paramText = new GridLayout.LayoutParams();
				paramText.height = LayoutParams.WRAP_CONTENT;
				paramText.width = LayoutParams.MATCH_PARENT;
				TextView txtBikeName = new TextView(getActivity()
						.getApplicationContext());
				txtBikeName.setTextColor(getActivity().getResources().getColor(
						R.color.black_color));
				txtBikeName.setTextSize(16);
				txtBikeName.setLayoutParams(paramText);
				txtBikeName.setGravity(Gravity.CENTER);
				txtBikeName.setText(BaseApplication.bike_details.get(i)
						.getBikeModel());

				mainLayout.addView(bikeImage, 0);
				mainLayout.addView(txtBikeName, 1);

				photosGridView.addView(mainLayout);
				if (column == 2) {
					column = 0;
					row = row + 1;
				} else {
					column = column + 1;
				}
			}
		}
	}

	public void addPhotosToGrid(View v) {

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout mainLayout = new LinearLayout(v.getContext());
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		layoutParams.width = gridViewWidth;
		// layoutParams.height = gridViewHeight;
		// layoutParams.setMargins(5, 5, 5, 5);
		layoutParams.gravity = Gravity.CENTER_VERTICAL;
		mainLayout.setLayoutParams(layoutParams);

		ImageView picImage = new ImageView(v.getContext());
		GridLayout.LayoutParams paramImage = new GridLayout.LayoutParams();
		paramImage.height = LayoutParams.WRAP_CONTENT;
		paramImage.width = LayoutParams.MATCH_PARENT;
		paramImage.rightMargin = 5;
		paramImage.topMargin = 5;
		// paramImage.setGravity(Gravity.CENTER);
		paramImage.columnSpec = GridLayout.spec(column);
		paramImage.rowSpec = GridLayout.spec(row);
		picImage.setLayoutParams(paramImage);
		if (thePic == null) {
			picImage.setImageDrawable(getActivity().getResources().getDrawable(
					R.drawable.user_icon));
		} else {
			picImage.setImageBitmap(thePic);
		}

		mainLayout.addView(picImage, 0);

		photosGridView.addView(mainLayout);
		if (column == 2) {
			column = 0;
			row = row + 1;
		} else {
			column = column + 1;
		}
	}

}
