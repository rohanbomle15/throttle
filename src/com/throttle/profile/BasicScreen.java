package com.throttle.profile;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.dbhelper.AppPreferences;
import com.throttle.service.AppGetService;
import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.service.AppMultipartService;
import com.throttle.service.AppMultipartService.AppMultipartServiceListener;
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class BasicScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private EditText edtBasicName = null;
	private EditText edtBasicCity = null;
	private static EditText edtBasicDOB = null;
	private EditText edtBasicMobNo = null;
	private Spinner edtBasicGender = null;
	private EditText edtBasicEmailId = null;
	private EditText edtBasicEmergNo = null;
	private Spinner edtBasicBloodGrp = null;
	private ImageView imgBasicDOBPicker = null;

	private Button btnBasicSave = null;
	private Button btnBasicRemove = null;
	
	

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

	private AppMultipartServiceListener mAppMultipartServiceListener = new AppMultipartServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub

		}
	};
	private Map<String, String> multipart_params = new HashMap<String, String>();

	public BasicScreen() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.profile_basic, container,
				false);
		edtBasicName = (EditText) rootView.findViewById(R.id.edtBasicName);
		edtBasicCity = (EditText) rootView.findViewById(R.id.edtBasicCity);
		edtBasicDOB = (EditText) rootView.findViewById(R.id.edtBasicDOB);
		edtBasicMobNo = (EditText) rootView.findViewById(R.id.edtBasicMobileNo);
		edtBasicGender = (Spinner) rootView.findViewById(R.id.edtBasicGender);
		ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.gender_options));

		edtBasicGender.setAdapter(gender_adapter);
		edtBasicEmailId = (EditText) rootView.findViewById(R.id.edtBasicEmail);
		edtBasicEmergNo = (EditText) rootView
				.findViewById(R.id.edtBasicEmergencyNo);

		edtBasicBloodGrp = (Spinner) rootView
				.findViewById(R.id.edtBasicBloodGroup);
		ArrayAdapter<String> blood_grp_adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice,
				getActivity().getResources().getStringArray(
						R.array.blood_group_options));
		edtBasicBloodGrp.setAdapter(blood_grp_adapter);
		imgBasicDOBPicker = (ImageView) rootView
				.findViewById(R.id.imgBasicDatePicker);
		imgBasicDOBPicker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnBasicSave = (Button) rootView.findViewById(R.id.btnBasicSave);
		btnBasicSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = edtBasicName.getText().toString();
				String dob = edtBasicDOB.getText().toString();
				String city = edtBasicCity.getText().toString();
				String email = edtBasicEmailId.getText().toString();
				String mobno = edtBasicMobNo.getText().toString();
				String selectedGender = Integer.toString(edtBasicGender
						.getSelectedItemPosition());
				String emgno = edtBasicEmergNo.getText().toString();
				String selectedBloodGrp = Integer.toString(edtBasicBloodGrp
						.getSelectedItemPosition());

				try {
//					params.put(Util.USER_ID, AppPreferences.getUSER_ID());
//					params.put(Util.FULLNAME, edtBasicName.getText().toString());
//					params.put(Util.DOB, edtBasicDOB.getText().toString());
//					params.put(Util.USER_CITY, edtBasicCity.getText()
//							.toString());
//					params.put(Util.EMAIL, edtBasicEmailId.getText().toString());
//					params.put(Util.MOBILE_NUMBER, edtBasicMobNo.getText()
//							.toString());
//					params.put(Util.GENDER, selectedGender);
//					params.put(Util.EMG_NUMBER, edtBasicEmergNo.getText()
//							.toString());
//
//					params.put(Util.BLOOD_GROUP, selectedBloodGrp);
					// if(BaseApplication.USER_PROFILE_PIC != null){
					// params.put(Util.PROFILE_PIC,
					// Util.convertBitmapToBase64(BaseApplication.USER_PROFILE_PIC));//
					// Urls.USER_PROFILE_PIC+"ProfilePic.png");
					// }
					// new AppService(mAppServiceListener).callService(
					// Urls.ADD_BASIC_URL, params);
					
					
					multipart_params.put("UserID", AppPreferences.getUSER_ID());
					multipart_params.put("FullName", edtBasicName.getText().toString());
					multipart_params.put("DOB", edtBasicDOB.getText().toString());
					multipart_params.put("UserCity", edtBasicCity.getText()
							.toString());
					multipart_params.put("Email", edtBasicEmailId.getText().toString());
					multipart_params.put("MobileNo", edtBasicMobNo.getText()
							.toString());
					multipart_params.put("Gender", selectedGender);
					multipart_params.put("EmergencyContact", edtBasicEmergNo.getText()
							.toString());

					multipart_params.put("BloodGroup", selectedBloodGrp);
					new AppMultipartService(mAppMultipartServiceListener)
							.callService(Urls.ADD_BASIC_URL, multipart_params,
									BaseApplication.TOP_VIEW_UPLOADED_PIC, BaseApplication.TOP_VIEW_UPLOADED_PIC_NAME);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBasicRemove = (Button) rootView.findViewById(R.id.btnBasicRemove);
		btnBasicRemove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		edtBasicName.setText(AppPreferences.getUSER_NAME());
		edtBasicEmailId.setText(AppPreferences.getUSER_EMAIL());
		getUserBasicInfo();
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
			edtBasicDOB.setText(day + "/" + month + "/" + year);
		}
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
			userBasicDetailsParser(jsonObj);
		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	public void getUserBasicInfo() {
		if(BaseApplication.mUserBasicInfo == null){
		Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
		params.put(Util.CLUB_ID, Util.selectedClubID);
		new AppGetService(mAppGetServiceListener).callService(
				Urls.GET_USER_BASIC_URL + AppPreferences.getUSER_ID(),
				getparams);
		}else{
			updateScreenControl();
		}
	}

	public void userBasicDetailsParser(String jsonString) {
		try {
			JSONObject clubJson = new JSONObject(jsonString);
			// JSONArray jsonArray = clubJson.getJSONArray(Util.RESULT);
			// for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject clubsJSON = clubJson.getJSONObject(Util.RESULT);
			if (clubsJSON != null) {
				String statusCode = "";
				String statusMsg = "";
				String FullName = "";
				String DateofBirth = "";
				String UserCity = "";
				String Email = "";
				String MobileNo = "";
				String Gender = "";
				String EmergencyContact = "";
				String BloodGroup = "";
				BaseApplication.mUserBasicInfo = new UserBasicInfo();
				
				if (clubsJSON.has(Util.STATUS_CODE)) {
					statusCode = clubsJSON.getString(Util.STATUS_CODE);
				}
				if (clubsJSON.has(Util.STATUS_DES)) {
					statusMsg = clubsJSON.getString(Util.STATUS_DES);
				}
				if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
					if (clubsJSON.has(Util.FULLNAME)) {
						FullName = clubsJSON.getString(Util.FULLNAME);
//						edtBasicName.setText(FullName);
						BaseApplication.mUserBasicInfo.setFullName(FullName);
					}
					if (clubsJSON.has(Util.DATE_OF_BIRTH)) {
						DateofBirth = clubsJSON.getString(Util.DATE_OF_BIRTH);
//						edtBasicDOB.setText(DateofBirth);
						BaseApplication.mUserBasicInfo.setDateofBirth(DateofBirth);
					}
					if (clubsJSON.has(Util.USER_CITY)) {
						UserCity = clubsJSON.getString(Util.USER_CITY);
//						edtBasicCity.setText(UserCity);
						BaseApplication.mUserBasicInfo.setUserCity(UserCity);
					}
					if (clubsJSON.has(Util.EMAIL)) {
						Email = clubsJSON.getString(Util.EMAIL);
//						edtBasicEmailId.setText(Email);
						BaseApplication.mUserBasicInfo.setEmail(Email);
					}
					if (clubsJSON.has(Util.MOBILE_NUMBER_FULL)) {
						MobileNo = clubsJSON.getString(Util.MOBILE_NUMBER_FULL);
//						edtBasicMobNo.setText(MobileNo);
						BaseApplication.mUserBasicInfo.setMobileNo(MobileNo);
					}
					if (clubsJSON.has(Util.GENDER)) {
						Gender = clubsJSON.getString(Util.GENDER);
//						int gender_index = Integer.parseInt(Gender);
//						edtBasicGender.setSelection(gender_index);
						BaseApplication.mUserBasicInfo.setGender(Gender);
					}
					if (clubsJSON.has(Util.EMG_NUMBER)) {
						EmergencyContact = clubsJSON.getString(Util.EMG_NUMBER);
//						edtBasicEmergNo.setText(EmergencyContact);
						BaseApplication.mUserBasicInfo.setEmergencyContact(EmergencyContact);
					}
					if (clubsJSON.has(Util.BLOOD_GROUP)) {
						BloodGroup = clubsJSON.getString(Util.BLOOD_GROUP);
//						int bloodGrp_index = Integer.parseInt(BloodGroup);
//						edtBasicBloodGrp.setSelection(bloodGrp_index);
						BaseApplication.mUserBasicInfo.setBloodGroup(BloodGroup);
					}
				}
			}
			// }
		} catch (Exception e) {

		}
	}
	
	public class UserBasicInfo{
		String statusCode = "";
		String statusMsg = "";
		String FullName = "";
		String DateofBirth = "";
		String UserCity = "";
		String Email = "";
		String MobileNo = "";
		String Gender = "";
		String EmergencyContact = "";
		String BloodGroup = "";
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		public String getStatusMsg() {
			return statusMsg;
		}
		public void setStatusMsg(String statusMsg) {
			this.statusMsg = statusMsg;
		}
		public String getFullName() {
			return FullName;
		}
		public void setFullName(String fullName) {
			FullName = fullName;
		}
		public String getDateofBirth() {
			return DateofBirth;
		}
		public void setDateofBirth(String dateofBirth) {
			DateofBirth = dateofBirth;
		}
		public String getUserCity() {
			return UserCity;
		}
		public void setUserCity(String userCity) {
			UserCity = userCity;
		}
		public String getEmail() {
			return Email;
		}
		public void setEmail(String email) {
			Email = email;
		}
		public String getMobileNo() {
			return MobileNo;
		}
		public void setMobileNo(String mobileNo) {
			MobileNo = mobileNo;
		}
		public String getGender() {
			return Gender;
		}
		public void setGender(String gender) {
			Gender = gender;
		}
		public String getEmergencyContact() {
			return EmergencyContact;
		}
		public void setEmergencyContact(String emergencyContact) {
			EmergencyContact = emergencyContact;
		}
		public String getBloodGroup() {
			return BloodGroup;
		}
		public void setBloodGroup(String bloodGroup) {
			BloodGroup = bloodGroup;
		}
		
	}
	
	public void updateScreenControl(){
		edtBasicName.setText(BaseApplication.mUserBasicInfo.getFullName());
		edtBasicDOB.setText(BaseApplication.mUserBasicInfo.getDateofBirth());
		edtBasicCity.setText(BaseApplication.mUserBasicInfo.getUserCity());
		edtBasicEmailId.setText(BaseApplication.mUserBasicInfo.getEmail());
		edtBasicMobNo.setText(BaseApplication.mUserBasicInfo.getMobileNo());
		String Gender = BaseApplication.mUserBasicInfo.getGender();
		int gender_index = Integer.parseInt(Gender);
		edtBasicGender.setSelection(gender_index);
		edtBasicEmergNo.setText(BaseApplication.mUserBasicInfo.getEmergencyContact());
		String BloodGroup = BaseApplication.mUserBasicInfo.getBloodGroup();
		int bloodGrp_index = Integer.parseInt(BloodGroup);
		edtBasicBloodGrp.setSelection(bloodGrp_index);
	}
	
}
