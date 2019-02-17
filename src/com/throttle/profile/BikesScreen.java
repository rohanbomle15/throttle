package com.throttle.profile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.dbhelper.AppPreferences;
import com.throttle.service.AppGetService;
import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class BikesScreen extends Fragment {

	private Context context = null;
	private EditText edtBikeMake = null;
	private EditText edtBikeCHassisNo = null;
	private static EditText edtBikeRegNo = null;
	private EditText edtBikeModelNo = null;
	private ImageView imgBikePic = null;

	private Button btnBikeSave = null;
	private Button btnBikeRemove = null;

	private String picName = "Bike.png";
	private static int IMAGE_PICKER_SELECT = 100;
	private static int CAMERA_CAPTURE = 101;
	private Uri picUri;
	final int PIC_CROP = 2;
	private Bitmap thePic;

	private TextView txtOpenGallery = null;
	private TextView txtTakePhoto = null;

	private GridLayout bikesGridView = null;
	int gridViewWidth = 50;
	int gridViewHeight = 200;
	int total = 12;
	int column = 0;
	int row = 0;

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

	public BikesScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.profile_bikes, container,
				false);
		context = getActivity();
		edtBikeMake = (EditText) rootView.findViewById(R.id.edtBasicName);
		edtBikeCHassisNo = (EditText) rootView
				.findViewById(R.id.edtBikesChassisNo);
		edtBikeRegNo = (EditText) rootView.findViewById(R.id.edtBikesRegNo);
		edtBikeModelNo = (EditText) rootView.findViewById(R.id.edtBikesModel);
		edtBikeMake = (EditText) rootView.findViewById(R.id.edtBikesMake);
		imgBikePic = (ImageView) rootView.findViewById(R.id.imgBikesUploadPic);
		btnBikeSave = (Button) rootView.findViewById(R.id.btnBikeSave);
		btnBikeSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String bikeID = edtBikeModelNo.getText().toString();
				String bikeRegNo = edtBikeRegNo.getText().toString();
				String bikeChassisNo = edtBikeCHassisNo.getText().toString();
				String bikeModel = edtBikeModelNo.getText().toString();
				String bikeMake = edtBikeMake.getText().toString();

				if (bikeID.equalsIgnoreCase("")
						|| bikeRegNo.equalsIgnoreCase("")
						|| bikeMake.equalsIgnoreCase("")
						|| bikeModel.equalsIgnoreCase("")) {
					Toast.makeText(getActivity(), "Please enter bike details.",
							Toast.LENGTH_SHORT).show();
				} else {

					// try {
					// params.put(Util.USER_BIKE_ID, "1010");
					// params.put(Util.USER_ID, AppPreferences.getUSER_ID());
					// params.put(Util.BIKE_REG_NO, bikeRegNo);
					// params.put(Util.BIKE_CHASSIS_NO, bikeChassisNo);
					// params.put(Util.BIKE_MODEL_NO, bikeModel);
					// params.put(Util.BIKE_MAKE, bikeMake);
					// params.put(Util.BIKE_IMAGE_URL, Urls.USER_BIKE_PIC
					// + picName);
					// new AppService(mAppServiceListener).callService(
					// Urls.ADD_BIKE_URL, params);
					//
					// } catch (Exception e) {
					// e.printStackTrace();
					// }

				}
				addBikesToGrid(v);
			}
		});
		btnBikeRemove = (Button) rootView.findViewById(R.id.btnBikeRemove);
		btnBikeRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		txtOpenGallery = (TextView) rootView
				.findViewById(R.id.txtBikesOpenImageGallery);
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
		txtTakePhoto = (TextView) rootView
				.findViewById(R.id.txtBikesTakeAPhoto);
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
		column = 0;
		row = 0;
		bikesGridView = (GridLayout) rootView.findViewById(R.id.bikesGridview);
		bikesGridView.setColumnCount(3);
		gridViewWidth = (BaseApplication.DEVICE_WIDTH / 3) - 25;

		getUserBikes();

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
			edtBikeRegNo.setText(day + "/" + month + "/" + year);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
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
			imgBikePic.setImageBitmap(thePic);
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

	private class serviceTempAsyncTask extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = postData(params[0]);
			return result;
		}

		protected void onPostExecute(String result) {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		public String postData(String valueIWantToSend) {
			// Create a new HttpClient and Post Header
			String serviceResult = "";
			try {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				thePic.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] imageBytes = baos.toByteArray();

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://throttledev.talentick.com/FileUploader.aspx");

				// String boundary = "-------------" +
				// System.currentTimeMillis();

				httpPost.setHeader("enctype", "multipart/form-data");

				ByteArrayBody bab = new ByteArrayBody(imageBytes, "pic.png");

				MultipartEntity entity = new MultipartEntity();
				entity.addPart("data", bab);
				httpPost.setEntity(entity);
				HttpResponse response = httpclient.execute(httpPost);
				int statusCode = response.getStatusLine().getStatusCode();
				// InputStream is = entity.getContent();
				// serviceResult = convertStreamToString(is);
				System.out.println("Service Respone : " + statusCode + ""
						+ serviceResult);
				return serviceResult;
			} catch (Exception e) {
				// TODO Auto-generated catch block;
				e.printStackTrace();
			}
			return serviceResult;
		}

	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private AppGetServiceListener mAppGetServiceListener = new AppGetServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
			userBikesDetailsParser(jsonObj);
		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	public void getUserBikes() {
		Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
		params.put(Util.CLUB_ID, Util.selectedClubID);
		new AppGetService(mAppGetServiceListener).callService(
				Urls.GET_USER_BIKES_URL + AppPreferences.getUSER_ID(),
				getparams);
	}

	public void userBikesDetailsParser(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject bikesObj = jsonObj.getJSONObject(Util.RESULT);
			JSONArray jsonArray = bikesObj.getJSONArray(Util.MY_BIKE);
			BaseApplication.bike_details.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				String UserBikeID = "";
				String UserID = "";
				String BikeRegNo = "";
				String BikeChassisNo = "";
				String BikeMake = "";
				String BikeModel = "";
				String BikeImageURL = "";
				String statusCode = "";
				String StatusDescription = "";
				JSONObject clubsJSON = jsonArray.getJSONObject(i);
				if (clubsJSON != null) {

					if (clubsJSON.has(Util.STATUS_CODE)) {
						statusCode = clubsJSON.getString(Util.STATUS_CODE);
					}
					if (clubsJSON.has(Util.STATUS_DES)) {
						StatusDescription = clubsJSON
								.getString(Util.STATUS_DES);
					}
					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
						if (clubsJSON.has(Util.USER_BIKE_ID)) {
							UserBikeID = clubsJSON.getString(Util.USER_BIKE_ID);
						}
						if (clubsJSON.has(Util.USER_ID)) {
							UserID = clubsJSON.getString(Util.USER_ID);
						}
						if (clubsJSON.has(Util.BIKE_REG_NO)) {
							BikeRegNo = clubsJSON.getString(Util.BIKE_REG_NO);
						}
						if (clubsJSON.has(Util.BIKE_CHASSIS_NO)) {
							BikeChassisNo = clubsJSON
									.getString(Util.BIKE_CHASSIS_NO);
						}
						if (clubsJSON.has(Util.BIKE_MAKE)) {
							BikeMake = clubsJSON.getString(Util.BIKE_MAKE);
						}
						if (clubsJSON.has(Util.BIKE_MODEL_NO)) {
							BikeModel = clubsJSON.getString(Util.BIKE_MODEL_NO);
						}
						if (clubsJSON.has(Util.BIKE_IMAGE_URL)) {
							BikeImageURL = clubsJSON
									.getString(Util.BIKE_IMAGE_URL);
						}
						BikeDetails item = new BikeDetails();
						item.setStatusCode(statusCode);
						item.setStatusDescription(StatusDescription);
						item.setUserBikeID(UserBikeID);
						item.setUserID(UserID);
						item.setBikeRegNo(BikeRegNo);
						item.setBikeChassisNo(BikeChassisNo);
						item.setBikeMake(BikeMake);
						item.setBikeModel(BikeModel);
						item.setBikeImageURL(BikeImageURL);
						BaseApplication.bike_details.add(item);
					}
				}
			}
			loadBikesGrid();
			// bikesGridView.setAdapter(new
			// BikesGridImagesAdapter(getActivity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addBikesToGrid(View v) {
		LinearLayout mainLayout = new LinearLayout(getActivity()
				.getApplicationContext());
		GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
		layoutParams.width = gridViewWidth;
		layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setPadding(0, 0, 10, 5);
		mainLayout.setLayoutParams(layoutParams);

		ImageView bikeImage = new ImageView(getActivity()
				.getApplicationContext());
		GridLayout.LayoutParams paramImage = new GridLayout.LayoutParams();
		paramImage.height = LayoutParams.WRAP_CONTENT;
		paramImage.width = LayoutParams.WRAP_CONTENT;
		bikeImage.setLayoutParams(paramImage);
		if (thePic == null) {
			bikeImage.setImageDrawable(getActivity().getResources()
					.getDrawable(R.drawable.sample_bike));
		} else {
			bikeImage.setImageBitmap(thePic);
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
		txtBikeName.setText(edtBikeModelNo.getText().toString());

		mainLayout.addView(bikeImage, 0);
		mainLayout.addView(txtBikeName, 1);

		bikesGridView.addView(mainLayout);
		if (column == 2) {
			column = 0;
			row = row + 1;
		} else {
			column = column + 1;
		}
	}

	public void loadBikesGrid() {
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

				bikesGridView.addView(mainLayout);
				if (column == 2) {
					column = 0;
					row = row + 1;
				} else {
					column = column + 1;
				}
			}
		}
	}
}
