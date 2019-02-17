package com.throttle;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		BaseApplication.app_context = this;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		BaseApplication.DEVICE_HEIGHT = displaymetrics.heightPixels;
		BaseApplication.DEVICE_WIDTH = displaymetrics.widthPixels;
//		Thread.setDefaultUncaughtExceptionHandler(new UnknownExceptionHandler(this));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

}
