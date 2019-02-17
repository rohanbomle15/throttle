package com.throttle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.throttle.utils.Util;

public class SplashScreen extends BaseActivity {

	private Context ctx;

	private String[] CHOSE_ACTIVITY = { ".LoginScreen", ".SignUpScreen",
			".MainActivity" };
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);
		ctx = this;
//		String pkg = Util.getPackageName();
//		i.setClassName(ctx, pkg + CHOSE_ACTIVITY[2]);
//		try {
//			Timer tm = new Timer();
//			tm.schedule(new TimerTask() {
//
//				@Override
//				public void run() {
//					if (i != null) {
//						Util.launchActivity(SplashScreen.this, i);
//					}
//				}
//			}, 1000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	
	public void onTestDrive(View v){
		
	}
	
	public void onGetStarted(View v){
		intent = new Intent(SplashScreen.this, LoginScreen.class);
		Util.launchActivity(this, intent);
	}
}
