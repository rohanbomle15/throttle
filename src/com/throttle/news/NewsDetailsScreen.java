package com.throttle.news;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.utils.ImageViewBitmapManager;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailsScreen extends Fragment {

	private ImageView imgNewsDetail;
	private TextView txtNewsDateDetails;
	private TextView txtNewsDayDetails;
	private TextView txtNewsMonthYearDetails;
	private TextView txtNewsDetailsTitle;
	private TextView txtNewsDetailsDes;
	private TextView txtNewsDetailsSoruce;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.news_details, container,
				false);

		NewsDetails item = BaseApplication.news_details
				.get(Util.selecteNewsIndex);
		imgNewsDetail = (ImageView) rootView.findViewById(R.id.imgNewsDetail);
		String imageURL = Urls.NEWS_BANNER_IMG_URL + item.getNewsImageURL();
		ImageViewBitmapManager.INSTANCE.loadBitmap(imageURL, imgNewsDetail,
				100, 50);

		txtNewsDateDetails = (TextView) rootView
				.findViewById(R.id.txtNewsDateDetails);
		String newsDate = item.getNewsDate();
		txtNewsDateDetails
				.setText(newsDate.substring(0, newsDate.indexOf("/")));

		txtNewsDayDetails = (TextView) rootView
				.findViewById(R.id.txtNewsDayDetails);
		String newsDay = item.getNewsDay().substring(0, 3).toUpperCase();
		txtNewsDayDetails.setText(newsDay);

		txtNewsMonthYearDetails = (TextView) rootView
				.findViewById(R.id.txtNewsMonthYearDetails);
		txtNewsMonthYearDetails.setText(item.getMonthYear());

		txtNewsDetailsTitle = (TextView) rootView
				.findViewById(R.id.txtNewsDetailsTitle);
		txtNewsDetailsTitle.setText(item.getHeadline());

		txtNewsDetailsDes = (TextView) rootView
				.findViewById(R.id.txtNewsDetailsDes);
		txtNewsDetailsDes.setText(item.getNewsdetails());

		txtNewsDetailsSoruce = (TextView) rootView
				.findViewById(R.id.txtNewsDetailsSoruce);
		txtNewsDetailsSoruce.setText("Source :" + item.getNewsSource());

		return rootView;
	}
}
