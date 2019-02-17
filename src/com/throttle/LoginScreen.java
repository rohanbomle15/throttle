package com.throttle;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.throttle.dbhelper.AppPreferences;
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class LoginScreen extends BaseActivity implements AppServiceListener {

	private Intent intent = null;
	private Map<String, String> params = new HashMap<String, String>();

	private TextView txtForgotPasswrod = null;
	private EditText edtLoginUsername = null;
	private EditText edtLoginPassword = null;

	private static AlertDialog.Builder alertDialogBuilder;
	private static String title;
	private static String message;
	private static String buttontitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_screen);
		edtLoginUsername = (EditText) findViewById(R.id.edtLoginUserName);
		edtLoginUsername.setText("testuser007@gmail.com");
		edtLoginPassword = (EditText) findViewById(R.id.edtLoginPassword);
		edtLoginPassword.setText("testuser");
		txtForgotPasswrod = (TextView) findViewById(R.id.txtForgotPassword);
		txtForgotPasswrod.setText(Html.fromHtml("<html><body><u>"
				+ getResources().getString(R.string.str_forgot_password)
				+ "</u></body></html>"));
		txtForgotPasswrod.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void onLogin(View v) {
		if (edtLoginPassword.getText().toString().equalsIgnoreCase("")
				|| edtLoginUsername.getText().toString().equalsIgnoreCase("")) {
			Toast.makeText(this, "Please enter Username/Password",
					Toast.LENGTH_SHORT).show();
		} else {
			params.put(Util.USER_NAME, edtLoginUsername.getText().toString());
			params.put(Util.PASSWORD, edtLoginPassword.getText().toString());
			Util.showProgressDialog(this, "Please Wait...");
			new AppService(this).callService(Urls.LOGIN_URL, params);
		}
		// intent = new Intent(this, MainActivity.class);
		// Util.launchActivity(this, intent);
	}

	public void onSignUp(View v) {
		intent = new Intent(this, SignUpScreen.class);
		Util.launchActivity(this, intent);
	}

	@Override
	public void onServiceComplete(Boolean success, String jsonString) {
		// TODO Auto-generated method stub
		Util.closeProgressDialog();
		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = jsonObj.getJSONArray(Util.RESULT);
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
					if (loginJson.has(Util.USER_ID)) {
						userID = loginJson.getString(Util.USER_ID);
					}
					if (loginJson.has(Util.FULLNAME)) {
						fullName = loginJson.getString(Util.FULLNAME);
					}
					if (loginJson.has(Util.USER_NAME)) {
						username = loginJson.getString(Util.USER_NAME);
					}
					if (loginJson.has(Util.STATUS_DES)) {
						statusDes = loginJson.getString(Util.STATUS_DES);
					}

					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)
							&& statusDes
									.equalsIgnoreCase(Util.SUCESS_STATUS_DES)) {
						// showAlertDialog(this,
						// Util.INFO_TITLE, statusDes, Util.OK_TITLE, false);
						new AppPreferences(this, fullName, username, userID);
						launchHomeScreen();
						// if (useraction) {
						// intent = new Intent(this, MainActivity.class);
						// Util.launchActivity(this, intent);
						// }
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
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
