package com.throttle.rides;

import java.util.HashMap;

public class RideDetails {
	private static String ride_id = "";
	private static String ride_name = "";
	private static String club_id = "";
	private static String lead_name = "";
	private static String start_date = "";
	private static String end_date = "";
	private static String approx_km;

	private static String ride_price = "";
	private static String ride_type = "";
	private static String ride_des = "";
	private static String ride_image = "";
	private static String ride_created_by_userid = "";

	private static String ride_details = "";

	private static HashMap<String, String> faq_list;

	public static String getRide_id() {
		return ride_id;
	}

	public static void setRide_id(String ride_id) {
		RideDetails.ride_id = ride_id;
	}

	public static String getRide_name() {
		return ride_name;
	}

	public static void setRide_name(String ride_name) {
		RideDetails.ride_name = ride_name;
	}

	public static String getClub_id() {
		return club_id;
	}

	public static void setClub_id(String club_id) {
		RideDetails.club_id = club_id;
	}

	public static String getLead_name() {
		return lead_name;
	}

	public static void setLead_name(String lead_name) {
		RideDetails.lead_name = lead_name;
	}

	public static String getStart_date() {
		return start_date;
	}

	public static void setStart_date(String start_date) {
		RideDetails.start_date = start_date;
	}

	public static String getEnd_date() {
		return end_date;
	}

	public static void setEnd_date(String end_date) {
		RideDetails.end_date = end_date;
	}

	public static String getApprox_km() {
		return approx_km;
	}

	public static void setApprox_km(String approx_km) {
		RideDetails.approx_km = approx_km;
	}

	public static String getRide_price() {
		return ride_price;
	}

	public static void setRide_price(String ride_price) {
		RideDetails.ride_price = ride_price;
	}

	public static String getRide_type() {
		return ride_type;
	}

	public static void setRide_type(String ride_type) {
		RideDetails.ride_type = ride_type;
	}

	public static String getRide_des() {
		return ride_des;
	}

	public static void setRide_des(String ride_des) {
		RideDetails.ride_des = ride_des;
	}

	public static String getRide_image() {
		return ride_image;
	}

	public static void setRide_image(String ride_image) {
		RideDetails.ride_image = ride_image;
	}

	public static String getRide_created_by_userid() {
		return ride_created_by_userid;
	}

	public static void setRide_created_by_userid(String ride_created_by_userid) {
		RideDetails.ride_created_by_userid = ride_created_by_userid;
	}

	public static String getRide_details() {
		return ride_details;
	}

	public static void setRide_details(String ride_details) {
		RideDetails.ride_details = ride_details;
	}

	public static HashMap<String, String> getFaq_list() {
		return faq_list;
	}

	public static void setFaq_list(HashMap<String, String> faq_list) {
		RideDetails.faq_list = faq_list;
	}

}
