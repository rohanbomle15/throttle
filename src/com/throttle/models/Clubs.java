package com.throttle.models;

public class Clubs {
	private String clubid;
	private String clubname = null;
	private String clubpicUrl = null;
	private String clubEmail = null;
	private String clubowner = null;
	private String kmperyear = null;
	private String clubScore = null;
	private String riderscount = null;
	private String clubcity = null;
	private String riderslist = null;

	public String getRiders() {
		return riderscount;
	}

	public void setRiders(String riders) {
		this.riderscount = riders;
	}

	public String getRidersList() {
		return riderslist;
	}

	public String getClubid() {
		return clubid;
	}

	public String getClubName() {
		return clubname;
	}

	public String getClubPicUrl() {
		return clubpicUrl;
	}

	public String getClubEmail() {
		return clubEmail;
	}

	public String getClubOwner() {
		return clubowner;
	}

	public String getKmPerYear() {
		return kmperyear;
	}

	public String getClubScore() {
		return clubScore;
	}

	public void setClubid(String clubid) {
		this.clubid = clubid;
	}

	public void setClubName(String clubname) {
		this.clubname = clubname;
	}

	public void setClubPicUrl(String picUrl) {
		this.clubpicUrl = picUrl;
	}

	public void setClubEmail(String email) {
		this.clubEmail = email;
	}

	public void setClubOwner(String uri) {
		this.clubowner = uri;
	}

	public void setKmPerYear(String userName) {
		this.kmperyear = userName;
	}

	public void setClubScore(String metaEvent) {
		this.clubScore = metaEvent;
	}

	public void setRidersList(String commenText) {
		this.riderslist = commenText;
	}

	public String getClubcity() {
		return clubcity;
	}

	public void setClubcity(String clubcity) {
		this.clubcity = clubcity;
	}

}