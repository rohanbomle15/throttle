package com.throttle.utils;

public class Urls {
	
	public static String THROTTLE_BASE_URL = "http://throttledev.talentick.com/";
	
	public static String USER_BASE_URL = "http://throttledev.talentick.com/api/User/";
	public static String CLUB_BASE_URL = "http://throttledev.talentick.com/api/Club/";
	public static String RIDE_BASE_URL = "http://throttledev.talentick.com/api/Ride/";
	public static String NEWS_BASE_URL = "http://throttledev.talentick.com/api/News/";
	public static String PRODUCT_BASE_URL = "http://throttledev.talentick.com/api/Product/";
	
	public static String USER_BASE_IMG_URL = "http://throttledev.talentick.com/img/Users/";
	public static String CLUB_BASE_IMG_URL = "http://throttledev.talentick.com/img/Clubs/";
	public static String RIDE_BASE_IMG_URL = "http://throttledev.talentick.com/img/Rides/";
	
	
	/** Post Request **/
	//User
	public static String LOGIN_URL = USER_BASE_URL+"Login";
	public static String REGISTER_URL = USER_BASE_URL+"Register";
	public static String ADD_BASIC_URL = USER_BASE_URL+"AddUserData";
	public static String ADD_BIKE_URL = USER_BASE_URL+"AddBike";
	public static String ADD_DOC_URL = USER_BASE_URL+"AddDoc";
	public static String ADD_STORY_URL = USER_BASE_URL+"AddStory";
	
	public static String USER_PROFILE_PIC = USER_BASE_IMG_URL+"ProfilePic/UserID/";
	public static String USER_BIKE_PIC = USER_BASE_IMG_URL+"Bikes/";
	
	//Club
	public static String ADD_CLUB_URL = CLUB_BASE_URL+"AddClub";
	public static String GET_CLUB_BY_ID_URL = CLUB_BASE_URL+"GetClubByID/";
	public static String SEARCH_CLUB_URL = CLUB_BASE_URL+"SearchClubList/";
	public static String SEARCH_CLUB_BY_ID_URL = CLUB_BASE_URL+"GetClubByID/ID/";
	
	public static String CLUB_LOGO_URL = CLUB_BASE_IMG_URL+"ClubLogos/";
	public static String CLUB_BANNER_URL = THROTTLE_BASE_URL;
	
	//Ride
	public static String ADD_RIDE_URL = RIDE_BASE_URL+"AddRide";
	public static String GET_RIDE_BY_ID_URL = RIDE_BASE_URL+"GetRideByID/";
	public static String SEARCH_RIDE_URL = RIDE_BASE_URL+"SearchRides/";
	public static String RIDE_BANNER_URL = THROTTLE_BASE_URL;
	
	//News
	public static String GET_NEWS_LIST_URL = NEWS_BASE_URL+"GetNewsList/";
	public static String NEWS_BANNER_IMG_URL = THROTTLE_BASE_URL;
	
	//Prodcut
	public static String GET_PRODUCT_LIST_URL = PRODUCT_BASE_URL+"GetProductByID/";
	public static String PRODUCT_IMG_URL = THROTTLE_BASE_URL;
	
	/** Get Request **/
	public static String GET_USER_BASIC_URL = USER_BASE_URL+"GetUserByID/";
	public static String GET_USER_BIKES_URL = USER_BASE_URL+"GetBikeByID/";
	public static String GET_USER_DOCS_URL = USER_BASE_URL+"GetDocByID/";
	public static String GET_USER_STORIES_URL = USER_BASE_URL+"GetStoryByID/";
}
