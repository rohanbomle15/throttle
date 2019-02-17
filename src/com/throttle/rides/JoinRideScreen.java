package com.throttle.rides;

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

import com.throttle.R;
import com.throttle.service.AppGetService;
import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class JoinRideScreen extends Fragment {

	private ImageView imgRideDetailRider1 = null;
	private ImageView imgRideDetailRider2 = null;
	private ImageView imgRideDetailRider3 = null;
	private ImageView imgRideDetailRider4 = null;
	private Button imgRideDetailRidesButton = null;

	private ImageView imgRideDetailPhotos1 = null;
	private ImageView imgRideDetailPhotos2 = null;
	private Button imgRideDetailRidesPhotosButton = null;

	private TextView txtRideDetailDuration = null;
	private TextView txtRideDetailLeadBy = null;
	private TextView txtRideDetailOrganizer = null;

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

	public JoinRideScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.ride_details, container,
				false);
		imgRideDetailRider1 = (ImageView) rootView
				.findViewById(R.id.imgRideDetailsRider1);
		imgRideDetailRider2 = (ImageView) rootView
				.findViewById(R.id.imgRideDetailsRider2);
		imgRideDetailRider3 = (ImageView) rootView
				.findViewById(R.id.imgRideDetailsRider3);
		imgRideDetailRider4 = (ImageView) rootView
				.findViewById(R.id.imgRideDetailsRider4);

		imgRideDetailPhotos1 = (ImageView) rootView
				.findViewById(R.id.imgRideDetailsPhotos1);
		imgRideDetailPhotos2 = (ImageView) rootView
				.findViewById(R.id.imgRideDetailsPhotos2);

		imgRideDetailRidesButton = (Button) rootView
				.findViewById(R.id.imgRideDetailsRidesPhotosButton);

		imgRideDetailRidesPhotosButton = (Button) rootView
				.findViewById(R.id.imgRideDetailsRidesPhotosButton);

		txtRideDetailDuration = (TextView) rootView
				.findViewById(R.id.txtRideDetailsDuration);

		txtRideDetailLeadBy = (TextView) rootView
				.findViewById(R.id.txtRideDetailsLeadBy);
		txtRideDetailOrganizer = (TextView) rootView
				.findViewById(R.id.txtRideDetailsOrganizer);

		getRideDetailsByID();
		return rootView;
	}

	public void getRideDetailsByID() {
		try {
			Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
			params.put(Util.CLUB_ID, Util.selectedRideID);
			new AppGetService(mAppServiceListener).callService(
					Urls.GET_RIDE_BY_ID_URL + Util.selectedRideID, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clubDetailsJSONParser(String jsonString) {
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject rideJSON = jsonObj.getJSONObject(Util.RESULT);
			if (rideJSON != null) {
				String statusCode = "";
				String statusMsg = "";
				String RideID = "";
				String RideName = "";
				String ClubID = "";
				String LeadName = "";
				String StartDate = "";
				String EndDate = "";
				String ApproxKm = "";
				String RidePrice = "";
				String RideType = "";
				String RideDescription = "";
				String RideImageName = "";
				String CreatedByUserID = "";
				String RideStatus = "";

				if (rideJSON.has(Util.STATUS_CODE)) {
					statusCode = rideJSON.getString(Util.STATUS_CODE);
				}
				if (rideJSON.has(Util.STATUS_DES)) {
					statusMsg = rideJSON.getString(Util.STATUS_DES);
				}
				if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
					if (rideJSON.has(Util.RIDEID)) {
						RideID = rideJSON.getString(Util.RIDEID);

					}
					if (rideJSON.has(Util.RIDENAME)) {
						RideName = rideJSON.getString(Util.RIDENAME);

					}
					if (rideJSON.has(Util.CLUB_ID)) {
						ClubID = rideJSON.getString(Util.CLUB_ID);

					}
					if (rideJSON.has(Util.LEADNAME)) {
						LeadName = rideJSON.getString(Util.LEADNAME);

					}
					if (rideJSON.has(Util.STARTDATE)) {
						StartDate = rideJSON.getString(Util.STARTDATE);

					}

					if (rideJSON.has(Util.ENDDATE)) {
						EndDate = rideJSON.getString(Util.ENDDATE);

					}

					if (rideJSON.has(Util.APPROXKM)) {
						ApproxKm = rideJSON.getString(Util.APPROXKM);

					}
					if (rideJSON.has(Util.RIDEPRICE)) {
						RidePrice = rideJSON.getString(Util.RIDEPRICE);
					}
					if (rideJSON.has(Util.RIDETYPE)) {
						RideType = rideJSON.getString(Util.RIDETYPE);
					}
					if (rideJSON.has(Util.RIDEDESCRIPTION)) {
						RideDescription = rideJSON
								.getString(Util.RIDEDESCRIPTION);

					}
					if (rideJSON.has(Util.CREATEDBYUSERID)) {
						CreatedByUserID = rideJSON
								.getString(Util.CREATEDBYUSERID);

					}
					if (rideJSON.has(Util.RIDESTATUS)) {
						RideStatus = rideJSON.getString(Util.RIDESTATUS);

					}
					if (rideJSON.has(Util.RIDEIMAGENAME)) {
						RideImageName = rideJSON.getString(Util.RIDEIMAGENAME);
					}

					txtRideDetailDuration.setText(StartDate + "\n" + EndDate);
					txtRideDetailLeadBy.setText(LeadName);
					txtRideDetailOrganizer.setText(LeadName);
				}
			}

			Util.closeProgressDialog();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
