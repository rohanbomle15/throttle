package com.throttle;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.throttle.dbhelper.AppPreferences;
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class SignUpScreen extends BaseActivity implements AppServiceListener {

	Intent intent = null;

	private EditText edtRegisterFullName = null;
	private EditText edtRegisterEmail = null;
	private Map<String, String> params = new HashMap<String, String>();

	private static AlertDialog.Builder alertDialogBuilder;
	private static String title;
	private static String message;
	private static String buttontitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sign_up_screen);
		edtRegisterFullName = (EditText) findViewById(R.id.edtSignUpFullName);
		edtRegisterEmail = (EditText) findViewById(R.id.edtSignUpEmail);
	}

	public void onRegister(View v) {
		if (edtRegisterFullName.getText().toString().equalsIgnoreCase("")
				|| edtRegisterEmail.getText().toString().equalsIgnoreCase("")) {
			Toast.makeText(this, "Please enter your details",
					Toast.LENGTH_SHORT).show();
		} else {
			params.put(Util.FULLNAME, edtRegisterFullName.getText().toString());
			params.put(Util.EMAIL, edtRegisterEmail.getText().toString());
			Util.showProgressDialog(this, "Pleaase wait...");
			new AppService(this).callService(Urls.REGISTER_URL, params);
		}
	}

	@Override
	public void onServiceComplete(Boolean success, String jsonString) {
		// TODO Auto-generated method stub
		Util.closeProgressDialog();
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			if (jsonObj.has(Util.RESULT)) {
				JSONObject loginJson = jsonObj.getJSONObject(Util.RESULT);
				if (loginJson != null) {
					String statusCode = "";
					String userID = "";
					String fullName = "";
					String username = "";
					String statusDes = "";
					if (loginJson.has(Util.STATUS_CODE)) {
						statusCode = loginJson.getString(Util.STATUS_CODE);
					}
					if (loginJson.has(Util.STATUS_CODE)) {
						userID = loginJson.getString(Util.USER_ID);
					}
					if (loginJson.has(Util.STATUS_CODE)) {
						fullName = loginJson.getString(Util.FULLNAME);
					}
					if (loginJson.has(Util.USER_NAME)) {
						username = loginJson.getString(Util.USER_NAME);
					}
					if (loginJson.has(Util.STATUS_CODE)) {
						statusDes = loginJson.getString(Util.STATUS_DES);
					}

					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)
							&& statusDes
									.equalsIgnoreCase(Util.SUCESS_STATUS_DES)) {
						showAlertDialog(this, Util.INFO_TITLE, statusDes,
								Util.OK_TITLE, false);

						new AppPreferences(this, fullName, username, userID);
						//
						// intent = new Intent(this, MainActivity.class);
						// Util.launchActivity(this, intent);

					} else {
						Util.showAlertDialog(this, Util.ERROR_TITLE, statusDes,
								Util.OK_TITLE, false);
					}
				}
			} else if (jsonObj.has(Util.ERROR)) {
				String statusCode = "";
				String statusDes = "";
				JSONObject errorJson = jsonObj.getJSONObject(Util.ERROR);
				statusCode = errorJson.getString(Util.STATUS_CODE);
				statusDes = errorJson.getString(Util.ERROR_MESSAGE);
				if (statusCode.equalsIgnoreCase(Util.ERROR_STATUS_CODE)) {
					Util.showAlertDialog(this, Util.ERROR_TITLE, statusDes,
							Util.OK_TITLE, false);
				}
			} else {
				Util.showAlertDialog(this,
						getResources().getString(R.string.str_sorry),
						getResources().getString(R.string.str_service_error),
						Util.OK_TITLE, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onServiceError(String error) {
		// TODO Auto-generated method stub
		Util.closeProgressDialog();
		Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
	}

	public void launchHomeScreen() {
		intent = new Intent(this, MainActivity.class);
		Util.launchActivity(this, intent);
	}

	private void showAlertDialog(Activity activity, String mTitle,
			String mMessage, String mButtontitle, boolean needCancelButton) {

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
								launchHomeScreen();
							}
						});
		if (needCancelButton) {
			alertDialogBuilder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
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
}
