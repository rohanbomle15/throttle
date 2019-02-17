package com.throttle;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.throttle.maps.MapsScreen;
import com.throttle.news.NewsDetailsScreen;
import com.throttle.store.AddProdcutBasicScreen;
import com.throttle.store.ProductListScreen;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class TopViewFragment extends Fragment {

	private int fragmnetToLoad = 1;
//	private FragmentManager fragmentManager = null;
//	private FragmentTransaction fragmentTransaction = null;
	private Fragment curentfragment = null;

	private RelativeLayout relTopLayout = null;

	// Profile
	private RelativeLayout relProfileBanner;
	private ImageButton imgUploadProfileBanner;

	// Add Club
	private RelativeLayout relAddClubBanner;
	private ImageButton imgClubBanner;

	// Club Details top view
	private TextView txtClubNameTopView;
	private TextView txtClubKMPerYaerTopView;
	private TextView txtClubScoreTopView;
	private RelativeLayout relClubDetailsBanner;

	// Add Club
	private RelativeLayout relAddRideBanner;
	private ImageButton imgUploadRideButton;

	// Ride Details top view
	private TextView txtRideNameTopView;
	private TextView txtRideDurationTopView;
	private TextView txtRideKMTopView;
	private RelativeLayout relRideDetailsBanner;

	private String picName = "Bike.png";
	private static int UPLOAD_PROFILE_BANNER = 100;
	private static int UPLOAD_CLUB_BANNER = 101;
	private static int UPLOAD_RIDE_BANNER = 102;
	private static int UPLOAD_PRODUCT_IMAGE = 100;
	
	private Uri picUri;
	private final int PIC_CROP_PROFILE = 2;
	private final int PIC_CROP_CLUB = 3;
	private final int PIC_CROP_RIDE = 4;
	private final int PIC_CROP_PRODUCT = 5;
	private Bitmap thePic;

	private boolean TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = false;

	public TopViewFragment(int mFragmentToLoad) {
		fragmnetToLoad = mFragmentToLoad;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.top_view_layout, container,
				false);

		BaseApplication.fragmentManager = getFragmentManager();
		BaseApplication.fragmentTransaction = BaseApplication.fragmentManager.beginTransaction();

		relTopLayout = (RelativeLayout) rootView
				.findViewById(R.id.relTopContent);

		loadTopContent(inflater, container);
		loadMainContent();

		return rootView;
	}

	private void loadTopContent(LayoutInflater inflater, ViewGroup container) {
		View topView = null;
		switch (fragmnetToLoad) {
		case Util.HOME_FRAGMENT:
			relTopLayout.setVisibility(View.GONE);
			break;
		case Util.USER_PROFILE_FRAGMENT:
			topView = inflater.inflate(R.layout.top_view_profile, container,
					false);
			relProfileBanner = (RelativeLayout) topView
					.findViewById(R.id.relProfileBanner);
			imgUploadProfileBanner = (ImageButton) topView
					.findViewById(R.id.imgUploadProfilebanner);
			imgUploadProfileBanner
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = true;
							startActivityForResult(i, UPLOAD_PROFILE_BANNER);
						}
					});
			relTopLayout.addView(topView);
			break;
		case Util.CLUB_FRAGMENT:
			relTopLayout.setVisibility(View.GONE);
			break;
		case Util.ADD_CLUB_FRAGENT:
			topView = inflater.inflate(R.layout.top_view_add_club, container,
					false);
			relAddClubBanner = (RelativeLayout) topView
					.findViewById(R.id.relAddClubBanner);
			imgClubBanner = (ImageButton) topView
					.findViewById(R.id.imgClubBanner);
			imgClubBanner.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = true;
					startActivityForResult(i, UPLOAD_CLUB_BANNER);
				}
			});
			relTopLayout.addView(topView);
			break;
		case Util.RIDES_FRAGMENT:
			relTopLayout.setVisibility(View.GONE);
			break;
		case Util.ADD_RIDES_FRAGMENT:
			topView = inflater.inflate(R.layout.top_view_add_ride, container,
					false);
			relAddRideBanner = (RelativeLayout) topView
					.findViewById(R.id.relAddRideBanner);
			imgUploadRideButton = (ImageButton) topView
					.findViewById(R.id.imgUploadRideBanner);
			imgUploadRideButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = true;
					startActivityForResult(i, UPLOAD_RIDE_BANNER);
				}
			});
			relTopLayout.addView(topView);
			break;
		case Util.CLUB_DETAIL_FRAGMENT:
			topView = inflater.inflate(R.layout.top_view_club_details,
					container, false);
			relClubDetailsBanner = (RelativeLayout) topView
					.findViewById(R.id.imgClubDetailsBanner);
			String imageURL = Urls.CLUB_BANNER_URL
					+ BaseApplication.club_list.get(Util.selectedClubIndex)
							.getClubPicUrl();
			LayoutBitmapManager.INSTANCE.loadBitmap(imageURL,
					relClubDetailsBanner, 100, 50);
			txtClubNameTopView = (TextView) topView
					.findViewById(R.id.txtClubNameDetails);
			txtClubKMPerYaerTopView = (TextView) topView
					.findViewById(R.id.txtClubKmPerYearDetails);
			txtClubScoreTopView = (TextView) topView
					.findViewById(R.id.txtClubScoreDetails);

			txtClubNameTopView.setText(BaseApplication.club_list.get(
					Util.selectedClubIndex).getClubName());
			txtClubKMPerYaerTopView.setText(BaseApplication.club_list.get(
					Util.selectedClubIndex).getKmPerYear()
					+ "Man km/yaer");
			txtClubScoreTopView.setText(BaseApplication.club_list.get(
					Util.selectedClubIndex).getClubScore());
			relTopLayout.addView(topView);
			break;
		case Util.RIDE_DETAIL_FRAGMENT:

			topView = inflater.inflate(R.layout.top_view_ride_details,
					container, false);
			relRideDetailsBanner = (RelativeLayout) topView
					.findViewById(R.id.imgRideDetailsBanner);
			String imageRideURL = Urls.RIDE_BANNER_URL
					+ BaseApplication.ride_list.get(Util.selectedClubIndex)
							.getRideImageName();
			LayoutBitmapManager.INSTANCE.loadBitmap(imageRideURL,
					relRideDetailsBanner, 100, 50);
			txtRideNameTopView = (TextView) topView
					.findViewById(R.id.txtRideNameDetails);
			txtRideDurationTopView = (TextView) topView
					.findViewById(R.id.txtRideDuration);
			txtRideKMTopView = (TextView) topView
					.findViewById(R.id.txtRideKmDetails);

			txtRideNameTopView.setText(BaseApplication.ride_list.get(
					Util.selectedRideIndex).getRideName());
			String duration = BaseApplication.ride_list.get(
					Util.selectedRideIndex).getDuration();
			String startDate = BaseApplication.ride_list.get(
					Util.selectedRideIndex).getStartDate();
			txtRideDurationTopView.setText(startDate + " [" + duration + "]");
			txtRideKMTopView.setText(BaseApplication.ride_list.get(
					Util.selectedRideIndex).getApproxKm());
			relTopLayout.addView(topView);
			break;
		case Util.THROTTLE_STORE_FRAGMENT:
			topView = inflater.inflate(R.layout.top_view_store, container,
					false);
			relTopLayout.addView(topView);
			break;
		case Util.NEWS_FRAGMENT:
			relTopLayout.setVisibility(View.GONE);
			break;
		case Util.NEWS_DETAILS_FRAGMENT:
			relTopLayout.setVisibility(View.GONE);
			break;
		case Util.PRODCUT_LIST_FRAGMENT:
			relTopLayout.setVisibility(View.GONE);
			break;
		case Util.ADD_PRODCUT_FRAGMENT:
			topView = inflater.inflate(R.layout.top_view_profile, container,
					false);
			relProfileBanner = (RelativeLayout) topView
					.findViewById(R.id.relProfileBanner);
			imgUploadProfileBanner = (ImageButton) topView
					.findViewById(R.id.imgUploadProfilebanner);
			imgUploadProfileBanner
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
							TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = true;
							startActivityForResult(i, UPLOAD_PRODUCT_IMAGE);
						}
					});
			relTopLayout.addView(topView);
			break;
		default:
			break;
		}
	}

	private void loadMainContent() {
		switch (fragmnetToLoad) {
		case Util.HOME_FRAGMENT:
			NewsDetailsScreen homeFragment = new NewsDetailsScreen();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, homeFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = homeFragment;
			break;
		case Util.USER_PROFILE_FRAGMENT:
			UserProfileFragment userProfileFragment = new UserProfileFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, userProfileFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = userProfileFragment;
			break;
		case Util.CLUB_FRAGMENT:
			ClubListFragment clubFragment = new ClubListFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, clubFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = clubFragment;
			break;
		case Util.ADD_CLUB_FRAGENT:
			AddClubFragment addClubFragment = new AddClubFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, addClubFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = addClubFragment;
			break;
		case Util.RIDES_FRAGMENT:
			RideListFragment rideListFragment = new RideListFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, rideListFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = rideListFragment;
			break;
		case Util.ADD_RIDES_FRAGMENT:
			AddRideFragment addRideFragment = new AddRideFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, addRideFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = addRideFragment;
			break;
		case Util.CLUB_DETAIL_FRAGMENT:
			ClubDetailsFragment clubDetailsFragment = new ClubDetailsFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, clubDetailsFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = clubDetailsFragment;
			break;
		case Util.RIDE_DETAIL_FRAGMENT:
			RideDetailsFragment rideDetailFragmnet = new RideDetailsFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, rideDetailFragmnet);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = rideDetailFragmnet;
			break;
		case Util.THROTTLE_STORE_FRAGMENT:
			ThrottleStoreFragment throttelStoreFragment = new ThrottleStoreFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, throttelStoreFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = throttelStoreFragment;
			break;
		case Util.NEWS_FRAGMENT:
			NewsFragment newsFragment = new NewsFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, newsFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = newsFragment;
			break;
		case Util.NEWS_DETAILS_FRAGMENT:
			NewsDetailsScreen newsDetailsFragment = new NewsDetailsScreen();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, newsDetailsFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = newsDetailsFragment;
			break;
		case Util.PRODCUT_LIST_FRAGMENT:
			ProductListScreen prodcutListFragment = new ProductListScreen();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, prodcutListFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = prodcutListFragment;
			break;
		case Util.ADD_PRODCUT_FRAGMENT:
			AddProductFragment addProductFragment = new AddProductFragment();
			BaseApplication.fragmentTransaction.add(R.id.relMainContent, addProductFragment);
			BaseApplication.fragmentTransaction.addToBackStack("");
			BaseApplication.fragmentTransaction.commit();
			curentfragment = addProductFragment;
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == UPLOAD_PROFILE_BANNER) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					cropImageWithXY(data, PIC_CROP_PROFILE,
							Util.PROFILE_BANNER_IMAGE_X,
							Util.PROFILE_BANNER_IMAGE_Y);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (requestCode == UPLOAD_CLUB_BANNER) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					cropImageWithXY(data, PIC_CROP_CLUB,
							Util.CLUB_BANNER_IMAGE_X, Util.CLUB_BANNER_IMAGE_Y);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (requestCode == UPLOAD_RIDE_BANNER) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					cropImageWithXY(data, PIC_CROP_RIDE,
							Util.CLUB_BANNER_IMAGE_X, Util.CLUB_BANNER_IMAGE_Y);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if (requestCode == UPLOAD_PRODUCT_IMAGE) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					cropImageWithXY(data, PIC_CROP_PRODUCT,
							Util.CLUB_BANNER_IMAGE_X, Util.CLUB_BANNER_IMAGE_Y);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}  else if (requestCode == PIC_CROP_PROFILE) {
			Bundle extras = data.getExtras();
			thePic = extras.getParcelable("data");
			BaseApplication.TOP_VIEW_UPLOADED_PIC = thePic;
			Drawable dr = new BitmapDrawable(thePic);
			relProfileBanner.setBackground(dr);
			TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = false;
		} else if (requestCode == PIC_CROP_CLUB) {
			Bundle extras = data.getExtras();
			thePic = extras.getParcelable("data");
			Drawable dr = new BitmapDrawable(thePic);
			relAddClubBanner.setBackground(dr);
			TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = false;
		} else if (requestCode == PIC_CROP_RIDE) {
			Bundle extras = data.getExtras();
			thePic = extras.getParcelable("data");
			Drawable dr = new BitmapDrawable(thePic);
			relAddRideBanner.setBackground(dr);
			TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = false;
		}else if (requestCode == PIC_CROP_PRODUCT) {
			Bundle extras = data.getExtras();
			thePic = extras.getParcelable("data");
			Drawable dr = new BitmapDrawable(thePic);
			relProfileBanner.setBackground(dr);
			TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS = false;
		}  else if (curentfragment != null) {
			if (!TOP_VIEW_IMAGE_UPLOAD_IN_PROGRESS) {
				curentfragment.onActivityResult(requestCode, resultCode, data);
			}
		}
	}

	public void cropImageWithXY(Intent data, int whichPicToCrop, int X, int Y) {
		try {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			picName = picturePath.substring(picturePath.lastIndexOf("/") + 1,
					picturePath.length());
			cursor.close();
			BaseApplication.TOP_VIEW_UPLOADED_PIC_NAME = picName;
			picUri = selectedImage;

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", X);
			cropIntent.putExtra("outputY", Y);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, whichPicToCrop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	
}