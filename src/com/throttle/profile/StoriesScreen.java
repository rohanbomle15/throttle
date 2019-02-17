package com.throttle.profile;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class StoriesScreen extends Fragment {

	private EditText edtStoryTitle = null;
	private EditText edtStoryDes = null;

	private Button btnStoriesSave = null;
	private Button btnStoriesRemove = null;

	private ImageView imgStoriesUploadPic = null;

	private String picName = "Bike.png";
	private static int IMAGE_PICKER_SELECT = 100;
	private static int CAMERA_CAPTURE = 101;
	private Uri picUri;
	final int PIC_CROP = 2;
	private Bitmap thePic;

	private Button txtOpenGallery = null;
	private Button txtTakePhoto = null;

	private GridLayout storiesGridView = null;
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
			userStoriesDetailsParser(jsonObj);
		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	public StoriesScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			View rootView = inflater.inflate(R.layout.profile_stories,
					container, false);
			edtStoryTitle = (EditText) rootView
					.findViewById(R.id.edtStoriesTitle);
			edtStoryDes = (EditText) rootView.findViewById(R.id.edtStoriesDes);
			imgStoriesUploadPic = (ImageView) rootView
					.findViewById(R.id.imgStoriesUploadPic);
			btnStoriesSave = (Button) rootView
					.findViewById(R.id.btnStoriesSave);
			btnStoriesSave.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					String storyTitle = edtStoryTitle.getText().toString();
					String storyDes = edtStoryDes.getText().toString();
					if (storyTitle.equalsIgnoreCase("")
							|| storyDes.equalsIgnoreCase("")) {
						Toast.makeText(getActivity(),
								"Please enter story details.",
								Toast.LENGTH_SHORT).show();
					} else {
						// try {
						// params.put(Util.USER_ID, "");
						// params.put(Util.USER_STORY_ID, "");
						// params.put(Util.STORY_NAME, edtStoryTitle.getText()
						// .toString());
						// params.put(Util.STORY_DES, edtStoryDes.getText()
						// .toString());
						// params.put(Util.STORY_IMAGE_URL, "");
						// new AppService(mAppServiceListener).callService(
						// Urls.ADD_STORY_URL, params);
						//
						// } catch (Exception e) {
						// e.printStackTrace();
						// }
						addStoriesToGrid(v);
					}
				}
			});
			btnStoriesRemove = (Button) rootView
					.findViewById(R.id.btnStoriesRemove);
			btnStoriesRemove.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
			txtOpenGallery = (Button) rootView
					.findViewById(R.id.txtStoriesOpenImageGallery);
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
			txtTakePhoto = (Button) rootView
					.findViewById(R.id.txtStoriesTakeAPhoto);
			txtTakePhoto.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						Intent captureIntent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						getParentFragment().startActivityForResult(
								captureIntent, CAMERA_CAPTURE);
					} catch (ActivityNotFoundException anfe) {

					}
				}
			});
			storiesGridView = (GridLayout) rootView
					.findViewById(R.id.storiesGridview);
			storiesGridView.setColumnCount(3);
			gridViewWidth = (BaseApplication.DEVICE_WIDTH / 3) - 20;
			getUserStories();
			return rootView;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
			imgStoriesUploadPic.setImageBitmap(thePic);
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

	public void onSaveBasic(View view) {

	}

	public void onRemoveBasic(View view) {

	}

	public void onOpenImageGallery(View view) {

	}

	public void onTakePhoto(View view) {

	}

	public void userStoriesDetailsParser(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject bikesObj = jsonObj.getJSONObject(Util.RESULT);
			JSONArray jsonArray = bikesObj.getJSONArray(Util.MY_STORY);
			BaseApplication.story_details.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				String UserStoryID = "";
				String UserID = "";
				String StoryTitle = "";
				String StoryDesc = "";
				String StoryImageURL = "";
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
						if (clubsJSON.has(Util.USER_STORY_ID)) {
							UserStoryID = clubsJSON
									.getString(Util.USER_STORY_ID);
						}
						if (clubsJSON.has(Util.USER_ID)) {
							UserID = clubsJSON.getString(Util.USER_ID);
						}
						if (clubsJSON.has(Util.STORY_NAME)) {
							StoryTitle = clubsJSON.getString(Util.STORY_NAME);
						}
						if (clubsJSON.has(Util.STORY_DES)) {
							StoryDesc = clubsJSON.getString(Util.STORY_DES);
						}
						if (clubsJSON.has(Util.STORY_IMAGE_URL)) {
							StoryImageURL = clubsJSON
									.getString(Util.STORY_IMAGE_URL);
						}

						StoryDetails item = new StoryDetails();
						item.setStoryTitle(StoryTitle);
						item.setStoryDesc(StoryDesc);
						item.setStoryImageURL(StoryImageURL);
						item.setUserStoryID(UserStoryID);
						BaseApplication.story_details.add(item);
					}
				}
			}
			loadStoriedGrid();
			// bikesGridView.setAdapter(new
			// BikesGridImagesAdapter(getActivity()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getUserStories() {
		Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
		params.put(Util.CLUB_ID, Util.selectedClubID);
		new AppGetService(mAppGetServiceListener).callService(
				Urls.GET_USER_STORIES_URL + AppPreferences.getUSER_ID(),
				getparams);
	}

	public void loadStoriedGrid() {
		int totalDocs = BaseApplication.story_details.size();
		if (totalDocs != 0 && BaseApplication.story_details != null) {
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
				String bikeImageURL = BaseApplication.story_details.get(i).getStoryImageURL();
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
				txtBikeName.setText(BaseApplication.story_details.get(i).getStoryTitle());

				mainLayout.addView(bikeImage, 0);
				mainLayout.addView(txtBikeName, 1);

				storiesGridView.addView(mainLayout);
				if (column == 2) {
					column = 0;
					row = row + 1;
				} else {
					column = column + 1;
				}
			}
		}
	}

	public void addStoriesToGrid(View v) {

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

		ImageView bikeImage = new ImageView(v.getContext());
		GridLayout.LayoutParams paramImage = new GridLayout.LayoutParams();
		paramImage.height = LayoutParams.WRAP_CONTENT;
		paramImage.width = LayoutParams.MATCH_PARENT;
		paramImage.rightMargin = 5;
		paramImage.topMargin = 5;
		// paramImage.setGravity(Gravity.CENTER);
		paramImage.columnSpec = GridLayout.spec(column);
		paramImage.rowSpec = GridLayout.spec(row);
		bikeImage.setLayoutParams(paramImage);
		if (thePic == null) {
			bikeImage.setImageDrawable(getActivity().getResources()
					.getDrawable(R.drawable.story_icon));
		} else {
			bikeImage.setImageBitmap(thePic);
		}

		GridLayout.LayoutParams paramText = new GridLayout.LayoutParams();
		paramText.height = LayoutParams.WRAP_CONTENT;
		paramText.width = LayoutParams.MATCH_PARENT;
		paramText.setGravity(Gravity.CENTER);
		TextView txtBikeName = new TextView(v.getContext());
		txtBikeName.setLayoutParams(paramText);
		txtBikeName.setText(edtStoryTitle.getText().toString());

		mainLayout.addView(bikeImage, 0);
		mainLayout.addView(txtBikeName, 1);

		storiesGridView.addView(mainLayout);
		if (column == 2) {
			column = 0;
			row = row + 1;
		} else {
			column = column + 1;
		}
	}
}
