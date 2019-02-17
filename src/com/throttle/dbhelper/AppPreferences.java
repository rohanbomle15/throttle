package com.throttle.dbhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

	private Context mContext;
	private static SharedPreferences sharedpreferences;

	public static final String APPPREFERENCES = "ThrottlePrefs";
	public static final String Name = "nameKey";
	public static final String Email = "emailKey";
	public static final String UserID = "useridKey";

	private static String USER_ID;
	private static String USER_NAME;
	private static String USER_EMAIL;

	public AppPreferences(Context ctx, String n, String e, String id) {
		mContext = ctx;
		sharedpreferences = mContext.getSharedPreferences(APPPREFERENCES,
				Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString(Name, n);
		editor.putString(Email, e);
		editor.putString(UserID, id);
		editor.commit();
	}

	public static String getUSER_ID() {
		if(sharedpreferences.contains(UserID)){
			USER_ID = sharedpreferences.getString(UserID,"");
		}
		return USER_ID;
	}

	public static String getUSER_NAME() {
		if(sharedpreferences.contains(Name)){
			USER_NAME = sharedpreferences.getString(Name,"");
		}
		return USER_NAME;
	}

	
	public static String getUSER_EMAIL() {
		if(sharedpreferences.contains(Email)){
			USER_EMAIL = sharedpreferences.getString(Email,"");
		}
		return USER_EMAIL;
	}

	
}
