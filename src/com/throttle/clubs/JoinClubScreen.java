package com.throttle.clubs;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.service.AppGetService;
import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class JoinClubScreen extends Fragment {

	private ImageView imgJoinClubRider1 = null;
	private ImageView imgJoinClubRider2 = null;
	private ImageView imgJoinClubRider3 = null;
	private ImageView imgJoinClubRider4 = null;
	private Button imgJoinClubButton = null;

	private ImageView imgJoinClubRides1 = null;
	private ImageView imgJoinClubRides2 = null;
	private Button imgJoinClubRidesButton = null;

	private ImageView imgJoinClubPhotos1 = null;
	private ImageView imgJoinClubPhotos2 = null;
	private Button imgJoinClubRidesPhotosButton = null;

	private TextView txtJoinClubEstablishedOn = null;
	private TextView txtJoinClubCity = null;
	private TextView txtJoinClubMeetingPoint = null;
	private TextView txtJoinClubBrand = null;
	private TextView txtJoinClubVision = null;

	private AppGetServiceListener mAppServiceListener = new AppGetServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			clubDetailsJSONParser(jsonObj);
		}
	};
	private Map<String, String> params = new HashMap<String, String>();

	public JoinClubScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.club_details, container,
				false);
		imgJoinClubRider1 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubRider1);
		imgJoinClubRider2 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubRider2);
		imgJoinClubRider3 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubRider3);
		imgJoinClubRider4 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubRider4);

		imgJoinClubRides1 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubRides1);
		imgJoinClubRides2 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubRides2);

		imgJoinClubPhotos1 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubPhotos1);
		imgJoinClubPhotos2 = (ImageView) rootView
				.findViewById(R.id.imgJoinClubPhotos2);

		imgJoinClubButton = (Button) rootView
				.findViewById(R.id.imgJoinClubButton);
		String count = BaseApplication.club_list.get(Util.selectedClubIndex)
				.getRiders();
		if (count.equalsIgnoreCase("1")) {
			imgJoinClubButton.setText(BaseApplication.club_list.get(
					Util.selectedClubIndex).getRiders()
					+ "\nRider");
		} else {
			imgJoinClubButton.setText(BaseApplication.club_list.get(
					Util.selectedClubIndex).getRiders()
					+ "\nRiders");
		}

		imgJoinClubRidesButton = (Button) rootView
				.findViewById(R.id.imgJoinClubRidesButton);

		imgJoinClubRidesPhotosButton = (Button) rootView
				.findViewById(R.id.imgJoinClubRidesPhotosButton);

		txtJoinClubEstablishedOn = (TextView) rootView
				.findViewById(R.id.txtJoinClubEstablishedOn);

		txtJoinClubCity = (TextView) rootView
				.findViewById(R.id.txtJoinClubCity);
		txtJoinClubMeetingPoint = (TextView) rootView
				.findViewById(R.id.txtJoinClubMeetingPoint);
		txtJoinClubBrand = (TextView) rootView
				.findViewById(R.id.txtJoinClubBrand);
		txtJoinClubVision = (TextView) rootView
				.findViewById(R.id.txtJoinClubVision);
		getClubDetailsByID();
		return rootView;
	}

	public void getClubDetailsByID() {
		try {
			Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
			params.put(Util.CLUB_ID, Util.selectedClubID);
			new AppGetService(mAppServiceListener).callService(
					Urls.GET_CLUB_BY_ID_URL + Util.selectedClubID, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clubDetailsJSONParser(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject clubsJSON = jsonObj.getJSONObject(Util.RESULT);

			if (clubsJSON != null) {
				String statusCode = "";
				String statusMsg = "";
				String clubID = "";
				String clubName = "";
				String EstablishedDate = "";
				String City = "";
				String ContactNumber = "";
				String Brand = "";
				String ClubLogoPath = "";
				String clubImageURL = "";
				String MeetingPoint = "";
				String MeetingDay = "";
				String ClubDescription = "";
				String createdByUserID = "";
				String ridersCount = "";

				if (clubsJSON.has(Util.STATUS_CODE)) {
					statusCode = clubsJSON.getString(Util.STATUS_CODE);
				}
				if (clubsJSON.has(Util.STATUS_DES)) {
					statusMsg = clubsJSON.getString(Util.STATUS_DES);
				}
				if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
					if (clubsJSON.has(Util.CLUB_ID)) {
						clubID = clubsJSON.getString(Util.CLUB_ID);

					}
					if (clubsJSON.has(Util.CLUB_NAME)) {
						clubName = clubsJSON.getString(Util.CLUB_NAME);

					}
					if (clubsJSON.has(Util.ESTABLISHEDDATE)) {
						EstablishedDate = clubsJSON
								.getString(Util.ESTABLISHEDDATE);
						txtJoinClubEstablishedOn.setText(EstablishedDate);
					}
					if (clubsJSON.has(Util.CITY)) {
						City = clubsJSON.getString(Util.CITY);
						txtJoinClubCity.setText(City);
					}
					if (clubsJSON.has(Util.CONTACTNUMBER)) {
						ContactNumber = clubsJSON.getString(Util.CONTACTNUMBER);

					}

					if (clubsJSON.has(Util.BRAND)) {
						Brand = clubsJSON.getString(Util.BRAND);
						txtJoinClubBrand.setText(Brand);
					}
					if (clubsJSON.has(Util.CLUBLOGOPATH)) {
						ClubLogoPath = clubsJSON.getString(Util.CLUBLOGOPATH);
					}
					if (clubsJSON.has(Util.CLUBIMAGEURL)) {
						clubImageURL = clubsJSON.getString(Util.CLUBIMAGEURL);
					}
					if (clubsJSON.has(Util.MEETINGPOINT)) {
						MeetingPoint = clubsJSON.getString(Util.MEETINGPOINT);
						txtJoinClubMeetingPoint.setText(MeetingPoint);
					}
					if (clubsJSON.has(Util.MEETINGDAY)) {
						MeetingDay = clubsJSON.getString(Util.MEETINGDAY);

					}
					if (clubsJSON.has(Util.CLUBDESCRIPTION)) {
						ClubDescription = clubsJSON
								.getString(Util.CLUBDESCRIPTION);
						txtJoinClubVision.setText(ClubDescription);
					}
					if (clubsJSON.has(Util.CREATEDBYUSERID)) {
						createdByUserID = clubsJSON
								.getString(Util.CREATEDBYUSERID);
					}

				}
			}

			Util.closeProgressDialog();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
