package com.throttle.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Process;

public class UnknownExceptionHandler implements UncaughtExceptionHandler {

	private Context mContext;

	public UnknownExceptionHandler(Context context) {
		this.mContext = context;
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {		
		Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
}
