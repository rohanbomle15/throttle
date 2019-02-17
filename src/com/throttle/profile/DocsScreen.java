package com.throttle.profile;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class DocsScreen extends Fragment {

	private EditText edtDocName = null;
	private EditText edtDocNumber = null;
	private static EditText edtDocExpDate = null;
	private ImageView imgDocDatePicker = null;
	private ImageView imgDocPic = null;

	private Button btnDocSave = null;
	private Button btnDocRemove = null;

	private Button txtOpenGallery = null;
	private Button txtTakePhoto = null;
	private String picName = "Bike.png";
	private static int IMAGE_PICKER_SELECT = 100;
	private static int CAMERA_CAPTURE = 101;
	private Uri picUri;
	final int PIC_CROP = 2;
	private Bitmap thePic;

	private GridLayout docsGridView = null;
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
			userDocsDetailsParser(jsonObj);
		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	public DocsScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.profile_docs, container,
				false);
		edtDocName = (EditText) rootView.findViewById(R.id.edtDocsExpDate);
		edtDocNumber = (EditText) rootView.findViewById(R.id.edtDocsNumber);
		edtDocExpDate = (EditText) rootView.findViewById(R.id.edtDocsExpDate);
		btnDocSave = (Button) rootView.findViewById(R.id.btnDocSave);
		imgDocPic = (ImageView) rootView.findViewById(R.id.imgDocUploadPic);
		btnDocSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					params.put(Util.USER_ID, AppPreferences.getUSER_ID());
					params.put(Util.USER_DOC_ID, "");
					params.put(Util.DOC_NAME, edtDocName.getText().toString());
					params.put(Util.DOC_NUMBER, edtDocNumber.getText()
							.toString());
					params.put(Util.EXPIRY_DATE, edtDocExpDate.getText()
							.toString());
					params.put(Util.DOC_IMAGE_URL, "Doc.png");
					new AppService(mAppServiceListener).callService(
							Urls.ADD_DOC_URL, params);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnDocRemove = (Button) rootView.findViewById(R.id.btnDocRemove);
		btnDocRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		imgDocDatePicker = (ImageView) rootView
				.findViewById(R.id.imgDocExpDatePicker);
		imgDocDatePicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});
		txtOpenGallery = (Button) rootView
				.findViewById(R.id.txtDocsOpenImageGallery);
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
		txtTakePhoto = (Button) rootView.findViewById(R.id.txtDocsTakeAPhoto);
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
		getUserDocs();
		column = 0;
		row = 0;
		docsGridView = (GridLayout) rootView.findViewById(R.id.docsGridview);
		docsGridView.setColumnCount(3);
		gridViewWidth = (BaseApplication.DEVICE_WIDTH / 3) - 25;
		return rootView;
	}

	public void onSaveBasic(View view) {

	}

	public void onRemoveBasic(View view) {

	}

	public void onOpenImageGallery(View view) {

	}

	public void onTakePhoto(View view) {

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
			edtDocExpDate.setText(day + "/" + month + "/" + year);
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
			imgDocPic.setImageBitmap(thePic);
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

	public void getUserDocs() {
		Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
		params.put(Util.CLUB_ID, Util.selectedClubID);
		new AppGetService(mAppGetServiceListener)
				.callService(
						Urls.GET_USER_DOCS_URL + AppPreferences.getUSER_ID(),
						getparams);
	}

	public void userDocsDetailsParser(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject bikesObj = jsonObj.getJSONObject(Util.RESULT);
			JSONArray jsonArray = bikesObj.getJSONArray(Util.MY_DOC);
			BaseApplication.docs_details.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				String UserDocumentID = "";
				String UserID = "";
				String DocumentName = "";
				String DocumentNumber = "";
				String ExpiryDate = "";
				String DocumentImageURL = "";
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
						if (clubsJSON.has(Util.USER_DOC_ID)) {
							UserDocumentID = clubsJSON
									.getString(Util.USER_DOC_ID);
						}
						if (clubsJSON.has(Util.USER_ID)) {
							UserID = clubsJSON.getString(Util.USER_ID);
						}
						if (clubsJSON.has(Util.DOC_NAME)) {
							DocumentName = clubsJSON.getString(Util.DOC_NAME);
						}
						if (clubsJSON.has(Util.DOC_NUMBER)) {
							DocumentNumber = clubsJSON
									.getString(Util.DOC_NUMBER);
						}
						if (clubsJSON.has(Util.EXPIRY_DATE)) {
							ExpiryDate = clubsJSON.getString(Util.EXPIRY_DATE);
						}
						if (clubsJSON.has(Util.DOC_IMAGE_URL)) {
							DocumentImageURL = clubsJSON
									.getString(Util.DOC_IMAGE_URL);
						}

						DocDetails item = new DocDetails();
						item.setUserDocumentID(UserDocumentID);
						item.setDocumentName(DocumentName);
						item.setDocumentNumber(DocumentNumber);
						item.setUserID(UserID);
						item.setExpiryDate(ExpiryDate);
						item.setDocumentImageURL(DocumentImageURL);
						BaseApplication.docs_details.add(item);
					}
				}
			}
			loadDocsGrid();
			// bikesGridView.setAdapter(new
			// BikesGridImagesAdapter(getActivity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDocsGrid() {
		int totalDocs = BaseApplication.docs_details.size();
		if (totalDocs != 0 && BaseApplication.docs_details != null) {
			for (int i = 0; i < totalDocs; i++) {
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
				String bikeImageURL = BaseApplication.docs_details.get(i)
						.getDocumentImageURL();
				if (bikeImageURL == null && bikeImageURL.equalsIgnoreCase("")) {
					bikeImage.setImageDrawable(getActivity().getResources()
							.getDrawable(R.drawable.doc_icon));
				} else {
					bikeImage.setImageDrawable(getActivity().getResources()
							.getDrawable(R.drawable.doc_icon));
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
				txtBikeName.setText(BaseApplication.docs_details.get(i).getDocumentName());

				mainLayout.addView(bikeImage, 0);
				mainLayout.addView(txtBikeName, 1);

				docsGridView.addView(mainLayout);
				if (column == 2) {
					column = 0;
					row = row + 1;
				} else {
					column = column + 1;
				}
			}
		}
	}

	public void addDocToGrid(View v) {
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
					.getDrawable(R.drawable.doc_icon));
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
		txtBikeName.setText(edtDocName.getText().toString());

		mainLayout.addView(bikeImage, 0);
		mainLayout.addView(txtBikeName, 1);

		docsGridView.addView(mainLayout);
		if (column == 2) {
			column = 0;
			row = row + 1;
		} else {
			column = column + 1;
		}
	}
}
