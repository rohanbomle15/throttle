package com.throttle.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "throttleDatabase";

	// Table Names
	private static final String USER = "user";
	private static final String USER_PROFILE = "userprofile";

	private static final String KEY_ID = "id";
	private static final String KEY_USER_ID = "userid";
	private static final String KEY_USER_NAME = "username";
	private static final String KEY_USER_EMAIL = "useremail";
	private static final String KEY_USER_PASS = "userpassword";

	private static final String CREATE_TABLE_USER = "CREATE TABLE " + USER
			+ "(" + KEY_ID + " PRIMARY KEY," + KEY_USER_NAME + " TEXT,"
			+ KEY_USER_EMAIL + " TEXT," + KEY_USER_PASS + " TEXT" + ")";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
