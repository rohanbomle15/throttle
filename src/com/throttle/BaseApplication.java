package com.throttle;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.throttle.models.Clubs;
import com.throttle.models.Ride;
import com.throttle.news.NewsDetails;
import com.throttle.profile.BikeDetails;
import com.throttle.profile.DocDetails;
import com.throttle.profile.StoryDetails;
import com.throttle.profile.BasicScreen.UserBasicInfo;
import com.throttle.store.ProductDetails;

public class BaseApplication extends Application {

	public static Context app_context = null;
	public static ArrayList<Clubs> club_list = new ArrayList<Clubs>();
	public static ArrayList<BikeDetails> bike_details = new ArrayList<BikeDetails>();
	public static ArrayList<DocDetails> docs_details = new ArrayList<DocDetails>();
	public static ArrayList<StoryDetails> story_details = new ArrayList<StoryDetails>();
	public static ArrayList<NewsDetails> news_details = new ArrayList<NewsDetails>();
	public static ArrayList<ProductDetails> prodcut_details = new ArrayList<ProductDetails>();
	public static ArrayList<Ride> ride_list = new ArrayList<Ride>();

	public static UserBasicInfo mUserBasicInfo = null;
	
	public static String SELECTED_PRODUCT_ID = "1";
	public static int DEVICE_HEIGHT = 0;
	public static int DEVICE_WIDTH = 0;

	public static Bitmap TOP_VIEW_UPLOADED_PIC = null;
	public static String TOP_VIEW_UPLOADED_PIC_NAME = "ProfilePic.png";
	
	public static FragmentManager fragmentManager = null;
	public static FragmentTransaction fragmentTransaction = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		app_context = this;

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	public static void Exit(){
		
	}
}
