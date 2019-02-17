package com.throttle.profile;

public class StoryDetails {

	private String UserStoryID = "";
	private String UserID = "";
	private String StoryTitle = "";
	private String StoryDesc = "";
	private String StoryImageURL = "";

	public String getUserStoryID() {
		return UserStoryID;
	}

	public void setUserStoryID(String userStoryID) {
		UserStoryID = userStoryID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getStoryTitle() {
		return StoryTitle;
	}

	public void setStoryTitle(String storyTitle) {
		StoryTitle = storyTitle;
	}

	public String getStoryDesc() {
		return StoryDesc;
	}

	public void setStoryDesc(String storyDesc) {
		StoryDesc = storyDesc;
	}

	public String getStoryImageURL() {
		return StoryImageURL;
	}

	public void setStoryImageURL(String storyImageURL) {
		StoryImageURL = storyImageURL;
	}

}
