package com.throttle.clubs;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class ClubDetails {
	private static String club_name = "";
	private static String club_est_date = "";
	private static String club_city = "";
	private static String club_contact_no = "";
	private static String club_brand_bike = "";
	private static Bitmap club_bike_photo;
	
	private static String club_meeting_point = "";
	private static String club_meeting_days = "";
	private static String club_vision = "";
	
	private static ArrayList<Bitmap> club_photos = new ArrayList<Bitmap>();

	public static String getClub_name() {
		return club_name;
	}

	public static void setClub_name(String club_name) {
		ClubDetails.club_name = club_name;
	}

	public static String getClub_est_date() {
		return club_est_date;
	}

	public static void setClub_est_date(String club_est_date) {
		ClubDetails.club_est_date = club_est_date;
	}

	public static String getClub_city() {
		return club_city;
	}

	public static void setClub_city(String club_city) {
		ClubDetails.club_city = club_city;
	}

	public static String getClub_contact_no() {
		return club_contact_no;
	}

	public static void setClub_contact_no(String club_contact_no) {
		ClubDetails.club_contact_no = club_contact_no;
	}

	public static String getClub_brand_bike() {
		return club_brand_bike;
	}

	public static void setClub_brand_bike(String club_brand_bike) {
		ClubDetails.club_brand_bike = club_brand_bike;
	}

	public static Bitmap getClub_bike_photo() {
		return club_bike_photo;
	}

	public static void setClub_bike_photo(Bitmap club_bike_photo) {
		ClubDetails.club_bike_photo = club_bike_photo;
	}

	public static String getClub_meeting_point() {
		return club_meeting_point;
	}

	public static void setClub_meeting_point(String club_meeting_point) {
		ClubDetails.club_meeting_point = club_meeting_point;
	}

	public static String getClub_meeting_days() {
		return club_meeting_days;
	}

	public static void setClub_meeting_days(String club_meeting_days) {
		ClubDetails.club_meeting_days = club_meeting_days;
	}

	public static String getClub_vision() {
		return club_vision;
	}

	public static void setClub_vision(String club_vision) {
		ClubDetails.club_vision = club_vision;
	}

	public static ArrayList<Bitmap> getClub_photos() {
		return club_photos;
	}

	public static void setClub_photos(ArrayList<Bitmap> club_photos) {
		ClubDetails.club_photos = club_photos;
	}
	
}
