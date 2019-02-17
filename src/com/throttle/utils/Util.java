package com.throttle.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;

import com.throttle.R;

public class Util {

	// Fragment index
	public final static int HOME_FRAGMENT = 0;
	public final static int USER_PROFILE_FRAGMENT = 1;
	public final static int CLUB_FRAGMENT = 2;
	public final static int ADD_CLUB_FRAGENT = 3;
	public final static int RIDES_FRAGMENT = 4;
	public final static int ADD_RIDES_FRAGMENT = 5;
	public final static int CLUB_DETAIL_FRAGMENT = 6;
	public final static int RIDE_DETAIL_FRAGMENT = 7;
	public final static int THROTTLE_STORE_FRAGMENT = 8;
	public final static int NEWS_FRAGMENT = 9;
	public final static int NEWS_DETAILS_FRAGMENT = 10;
	public final static int PRODCUT_LIST_FRAGMENT = 11;
	public final static int PRODCUT_DETAILS_FRAGMENT = 12;
	public final static int ADD_PRODCUT_FRAGMENT = 13;

	/** JSON Keys **/
	// User

	public static final String USER_ID = "userID";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "Password";
	public static final String FULLNAME = "fullName";
	public static final String EMAIL = "Email";
	public static final String DOB = "DOB";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String USER_CITY = "userCity";
	public static final String MOBILE_NUMBER = "MobileNo";
	public static final String MOBILE_NUMBER_FULL = "mobileNumber";
	public static final String GENDER = "gender";
	public static final String EMG_NUMBER = "emergencyContact";
	public static final String BLOOD_GROUP = "bloodGroup";
	public static final String PROFILE_PIC = "profilePic";

	public static final String MY_BIKE = "MyBike";
	public static final String USER_BIKE_ID = "userBikeID";
	public static final String BIKE_REG_NO = "bikeRegNo";
	public static final String BIKE_CHASSIS_NO = "bikeChassisNo";
	public static final String BIKE_MAKE = "bikeMake";
	public static final String BIKE_MODEL_NO = "bikeModel";
	public static final String BIKE_IMAGE_URL = "bikeImageURL";

	public static final String MY_DOC = "MyDoc";
	public static final String USER_DOC_ID = "userDocumentID";
	public static final String DOC_NAME = "documentName";
	public static final String DOC_NUMBER = "documentNumber";
	public static final String EXPIRY_DATE = "expiryDate";
	public static final String DOC_IMAGE_URL = "documentImageURL";

	public static final String MY_STORY = "MyStory";
	public static final String USER_STORY_ID = "userStoryID";
	public static final String STORY_NAME = "storyTitle";
	public static final String STORY_DES = "storyDesc";
	public static final String STORY_IMAGE_URL = "storyImageURL";

	public static final String STATUS_CODE = "statusCode";
	public static final String STATUS_DES = "statusDescription";

	// Club
	public static final String CLUB_ID = "clubID";
	public static final String CLUB_NAME = "clubName";
	public static final String ESTABLISHEDDATE = "establishedDate";
	public static final String CITY = "city";
	public static final String CONTACTNUMBER = "ContactNumber";
	public static final String BRAND = "Brand";
	public static final String CLUBLOGOPATH = "clubLogoPath";
	public static final String CLUBIMAGEURL = "clubImageURL";
	public static final String MEETINGPOINT = "meetingPoint";
	public static final String MEETINGDAY = "meetingDay";
	public static final String CLUBDESCRIPTION = "clubDescription";
	public static final String CREATEDBYUSERID = "createdByUserID";

	public static final String RIDERS_COUNT = "riderCount";
	public static final String CLUB_SCORE = "clubScore";
	public static final String MAN_KM_YEAR = "manKmYear";

	// Ride
	public static final String RIDEID = "rideID";
	public static final String RIDENAME = "rideName";
	public static final String LEADNAME = "leadName";
	public static final String STARTDATE = "startDate";
	public static final String ENDDATE = "endDate";
	public static final String APPROXKM = "approxKm";
	public static final String RIDEPRICE = "ridePrice";
	public static final String RIDETYPE = "rideType";
	public static final String RIDEDESCRIPTION = "rideDescription";
	public static final String RIDEIMAGEURL = "rideImageURL";
	public static final String RIDEIMAGENAME = "rideImageName";
	public static final String RIDESTATUS = "rideStatus";
	public static final String DURATION = "duration";
	public static final String RIDERCOUNT = "riderCount";

	// news
	public static final String MY_NEWS = "newsList";
	public static final String NEWSID = "newsID";
	public static final String HEADLINE = "headLine";
	public static final String SHORTHEADLINE = "shortHeadline";
	public static final String NEWSDETAILS = "newsDetails";
	public static final String NEWSTYPE = "newsType";
	public static final String NEWSTYPEDESC = "newsTypeDesc";
	public static final String NEWSDAY = "newsDay";
	public static final String NEWSDATE = "newsDate";
	public static final String CALENDERDAY = "calenderDay";
	public static final String MONTHYEAR = "monthYear";
	public static final String PUBLISHDATE = "publishDate";
	public static final String NEWSIMAGEURL = "newsImageURL";
	public static final String NEWSSOURCE = "newsSource";
	public static final String NEWSSTATUS = "newsStatus";
	public static final String STATUSCODE = "statusCode";
	public static final String STATUSDESCRIPTION = "statusDescription";

	// Product
	public static final String MY_PRODUCT = "productList";
	public static final String BIKE_PRODCUT_ID = "1";
	public static final String ACCESSORIES_PRODUCT_IS = "2";
	public static final String SPEARS_PRODCUT_ID = "3";

	public static final String PRODUCTLISTINGID = "productListingID";
	public static final String PRODUCTNAME = "productName";
	public static final String CATEGORYID = "categoryID";
	public static final String CATEGORYNAME = "categoryName";
	public static final String CATEGORYTITLE = "categoryTitle";
	public static final String SUBCATEGORY1ID = "subCategory1ID";
	public static final String SUBCAT1NAME = "subCat1Name";
	public static final String SUBCAT1TITLE = "subCat1Title";
	public static final String SUBCATEGORY2ID = "subCategory2ID";
	public static final String SUBCAT2NAME = "subCat2Name";
	public static final String SUBCAT2TITLE = "subCat2Title";
	public static final String CITYID = "cityID";
	public static final String LOCALITYID = "localityID";
	public static final String CITYNAME = "cityName";
	public static final String LOCALITYNAME = "localityName";
	public static final String PRICETYPE = "priceType";
	public static final String LISTINGPRICE = "listingPrice";
	public static final String BIDPRICE = "bidPrice";
	public static final String LASTBIDDATE = "lastBidDate";
	public static final String PRODUCTSTATUS = "productStatus";
	public static final String PRODUCTSTATUSDESC = "productStatusDesc";
	public static final String PRODUCTIMAGEURL = "productImageURL";
	public static final String PRODUCTDESCRIPTION = "productDescription";
	public static final String SELLERTYPE = "sellerType";
	public static final String CONTACTTYPE = "contactType";
	public static final String POSTEDBY = "postedBy";
	public static final String MOBILENUMBER = "mobileNumber";
	public static final String EMAILID = "emailID";
	public static final String POSTEDDATE = "postedDate";
	public static final String LISTINGSTATUS = "listingStatus";

	public static final String VIEW_TYPE = "ViewType";

	// Error, Messages, Info
	public static final String SUCESS_TITLE = "Success";
	public static final String ERROR_TITLE = "Error!";
	public static final String INFO_TITLE = "Info";
	public static final String MESSAGE_TITLE = "Message";
	public static final String LOADING_TITLE = "Loading...";
	public static final String OK_TITLE = "Ok";
	public static final String CANCEL_TITLE = "Cancel";

	public static final String SUCESS_STATUS_CODE = "1";
	public static final String ERROR_STATUS_CODE = "0";
	public static final String SUCESS_STATUS_DES = "Success";
	public static final String ERROR_STATUS_DES = "Error";
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String RESULT = "result";
	public static final String ERROR = "error";

	private static Activity activity;
	private static AlertDialog.Builder alertDialogBuilder;
	private static String title;
	private static String message;
	private static String buttontitle;
	private static boolean userAction = false;

	// Image croping width/height
	public static int BIKE_PIC_IMAGE_X = 201;
	public static int BIKE_PIC_IMAGE_Y = 155;

	public static int DOC_PIC_IMAGE_X = 201;
	public static int DOC_PIC_IMAGE_Y = 155;

	public static int PHOTO_PIC_IMAGE_X = 201;
	public static int PHOTO_PIC_IMAGE_Y = 155;

	public static int PROFILE_BANNER_IMAGE_X = 300;
	public static int PROFILE_BANNER_IMAGE_Y = 120;

	public static int CLUB_BANNER_IMAGE_X = 300;
	public static int CLUB_BANNER_IMAGE_Y = 120;

	private static ProgressDialog progressDialog;

	public static String selectedClubID = "0";
	public static int selectedClubIndex = 0;

	public static String selectedRideID = "0";
	public static int selectedRideIndex = 0;

	public static String selectedNewsID = "0";
	public static int selecteNewsIndex = 0;

	public static final String getPackageName() {
		return "com.throttle";
	}

	public static void launchActivity(Activity activity, Intent intent) {
		try {
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			activity.startActivity(intent);
			activity.overridePendingTransition(R.anim.abc_fade_in,
					R.anim.abc_fade_out);
		} catch (ActivityNotFoundException e) {

		}
	}

	public static void showAlertDialog(Activity activity, String mTitle,
			String mMessage, String mButtontitle, boolean needCancelButton) {

		activity = activity;
		title = mTitle;
		message = mMessage;
		buttontitle = mButtontitle;
		alertDialogBuilder = new AlertDialog.Builder(activity);
		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(buttontitle,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

								userAction = true;
							}
						});
		if (needCancelButton) {
			alertDialogBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
							userAction = false;
						}
					});
		}
		AlertDialog alertDialog = alertDialogBuilder.create();

		if (alertDialog.isShowing()) {
			alertDialog.dismiss();
			alertDialogBuilder = null;
		}
		if (!activity.isFinishing()) {
			alertDialog.show();
		}

	}

	public static void showProgressDialog(Context context, String message) {
		// closeProgressDialog();
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.cancel();
				progressDialog = null;

			}
		}
		progressDialog = new ProgressDialog(context);
		if (message.equalsIgnoreCase(null) || message.equalsIgnoreCase("")) {
			progressDialog.setMessage(message);
		} else {
			progressDialog.setMessage(message);
		}
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	public static void closeProgressDialog() {
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.cancel();
				progressDialog = null;
			}
		}

	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String convertBitmapToBase64(Bitmap imgToConvert) {
		String base64Output = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			imgToConvert.compress(Bitmap.CompressFormat.PNG, 100,
					byteArrayOutputStream);
			byte[] byteArray = byteArrayOutputStream.toByteArray();
			base64Output = Base64.encodeToString(byteArray, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64Output;
	}

}
