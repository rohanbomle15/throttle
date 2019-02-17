package com.throttle;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.throttle.adapter.NewsListAdapter;
import com.throttle.dbhelper.AppPreferences;
import com.throttle.news.NewsDetails;
import com.throttle.profile.StoryDetails;
import com.throttle.service.AppGetService;
import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class NewsFragment extends Fragment {

	private ListView listNews;
	private NewsListAdapter newsListAdapter;

	private AppGetServiceListener mAppGetServiceListener = new AppGetServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
			NewsDetailsParser(jsonObj);
		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.news_list_screen, container,
				false);
		listNews = (ListView) rootView.findViewById(R.id.newsListView);
		getNews();
		newsListAdapter = new NewsListAdapter(BaseApplication.app_context,
				BaseApplication.news_details);
		listNews.setAdapter(newsListAdapter);
		listNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Util.selectedNewsID = BaseApplication.news_details
						.get(position).getNewsID();
				Util.selecteNewsIndex = position;
				Fragment fragment = new TopViewFragment(
						Util.NEWS_DETAILS_FRAGMENT);
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
			}
		});
		return rootView;
	}

	private void getNews() {
		if(BaseApplication.news_details.size() == 0){
		Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
		BaseApplication.news_details.clear();
		getparams.put(Util.CLUB_ID, Util.selectedClubID);
		new AppGetService(mAppGetServiceListener).callService(
				Urls.GET_NEWS_LIST_URL, getparams);
		}else{
			newsListAdapter = new NewsListAdapter(BaseApplication.app_context,
					BaseApplication.news_details);
			listNews.setAdapter(newsListAdapter);
			newsListAdapter.notifyDataSetChanged();
		}
	}

	public void NewsDetailsParser(String jsonString) {

		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject bikesObj = jsonObj.getJSONObject(Util.RESULT);
			JSONArray jsonArray = bikesObj.getJSONArray(Util.MY_NEWS);
			BaseApplication.story_details.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				String newsID = "";
				String headline = "";
				String shortHeadline = "";
				String newsdetails = "";
				String newsType = "";
				String newsTypeDesc = "";
				String newsDay = "";
				String newsDate = "";
				String calenderDay = "";
				String monthYear = "";
				String publishDate = "";
				String newsImageURL = "";
				String newsSource = "";
				String newsStatus = "";
				String createdByUserID = "";
				String statusCode = "";
				String StatusDescription = "";
				JSONObject clubsJSON = jsonArray.getJSONObject(i);
				if (clubsJSON != null) {

					if (clubsJSON.has(Util.STATUS_CODE)) {
						statusCode = clubsJSON.getString(Util.STATUS_CODE);
					}
					if (clubsJSON.has(Util.STATUS_DES)) {
						StatusDescription = clubsJSON
								.getString(Util.STATUS_DES);
					}
					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
						if (clubsJSON.has(Util.NEWSID)) {
							newsID = clubsJSON.getString(Util.NEWSID);
						}
						if (clubsJSON.has(Util.HEADLINE)) {
							headline = clubsJSON.getString(Util.HEADLINE);
						}
						if (clubsJSON.has(Util.SHORTHEADLINE)) {
							shortHeadline = clubsJSON
									.getString(Util.SHORTHEADLINE);
						}
						if (clubsJSON.has(Util.NEWSDETAILS)) {
							newsdetails = clubsJSON.getString(Util.NEWSDETAILS);
						}
						if (clubsJSON.has(Util.NEWSTYPEDESC)) {
							newsTypeDesc = clubsJSON
									.getString(Util.NEWSTYPEDESC);
						}
						if (clubsJSON.has(Util.NEWSDATE)) {
							newsDate = clubsJSON.getString(Util.NEWSDATE);
						}
						if (clubsJSON.has(Util.NEWSDAY)) {
							newsDay = clubsJSON.getString(Util.NEWSDAY);
						}
						if (clubsJSON.has(Util.CALENDERDAY)) {
							calenderDay = clubsJSON.getString(Util.CALENDERDAY);
						}
						if (clubsJSON.has(Util.MONTHYEAR)) {
							monthYear = clubsJSON.getString(Util.MONTHYEAR);
						}
						if (clubsJSON.has(Util.PUBLISHDATE)) {
							publishDate = clubsJSON.getString(Util.PUBLISHDATE);
						}
						if (clubsJSON.has(Util.NEWSIMAGEURL)) {
							newsImageURL = clubsJSON
									.getString(Util.NEWSIMAGEURL);
						}
						if (clubsJSON.has(Util.NEWSTYPE)) {
							newsType = clubsJSON.getString(Util.NEWSTYPE);
						}
						if (clubsJSON.has(Util.NEWSSOURCE)) {
							newsSource = clubsJSON.getString(Util.NEWSSOURCE);
						}
						if (clubsJSON.has(Util.NEWSSTATUS)) {
							newsStatus = clubsJSON.getString(Util.NEWSSTATUS);
						}
						if (clubsJSON.has(Util.CREATEDBYUSERID)) {
							createdByUserID = clubsJSON
									.getString(Util.CREATEDBYUSERID);
						}

						NewsDetails item = new NewsDetails();
						item.setNewsID(newsID);
						item.setHeadline(headline);
						item.setShortHeadline(shortHeadline);
						item.setNewsdetails(newsdetails);
						item.setNewsType(newsType);
						item.setNewsTypeDesc(newsTypeDesc);
						item.setNewsDate(newsDate);
						item.setNewsDay(newsDay);
						item.setCalenderDay(calenderDay);
						item.setMonthYear(monthYear);
						item.setPublishDate(publishDate);
						item.setNewsImageURL(newsImageURL);
						item.setNewsSource(newsSource);
						item.setNewsStatus(newsStatus);
						item.setCreatedByUserID(createdByUserID);
						item.setStatusCode(statusCode);
						item.setStatusDescription(StatusDescription);
						BaseApplication.news_details.add(item);
					}
				}
			}
			newsListAdapter.notifyDataSetChanged();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
