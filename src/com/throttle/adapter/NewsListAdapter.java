package com.throttle.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.throttle.R;
import com.throttle.news.NewsDetails;
import com.throttle.utils.ImageViewBitmapManager;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class NewsListAdapter extends BaseAdapter {
	private Context activity;
	private ArrayList<NewsDetails> data;

	public NewsListAdapter(Context app_context, ArrayList<NewsDetails> basicList) {
		activity = app_context;
		data = basicList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.news_list_row, null);
			holder.textNewsTitle = (TextView) convertView
					.findViewById(R.id.txtNews);
			holder.textNewsDate = (TextView) convertView
					.findViewById(R.id.txtNewsDate);

			holder.textNewsDay = (TextView) convertView
					.findViewById(R.id.txtNewsDay);
			holder.textNewsMonthYear = (TextView) convertView
					.findViewById(R.id.txtNewsMonthYear);

			holder.imgNews = (ImageView) convertView.findViewById(R.id.imgNews);
			String imageURL = data.get(position).getNewsImageURL();
			if (imageURL != null) {
				if (!imageURL.equalsIgnoreCase("")) {
					if (!imageURL.contains("http")) {
						imageURL = Urls.NEWS_BANNER_IMG_URL + imageURL;
					}

					ImageViewBitmapManager.INSTANCE.loadBitmap(imageURL,
							holder.imgNews, 100, 50);
				}
			}
			

			holder.textNewsTitle.setText(data.get(position).getHeadline());
			String newsDate = data.get(position).getNewsDate();
			holder.textNewsDate.setText(newsDate.substring(0, newsDate.indexOf("/")));
			String newsDay = data.get(position).getNewsDay().substring(0, 3).toUpperCase();
			holder.textNewsDay.setText(newsDay);
			holder.textNewsMonthYear.setText(data.get(position).getMonthYear());
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		return convertView;
	}

	public static class ViewHolder {
		TextView textNewsTitle;
		TextView textNewsDate;
		TextView textNewsDay;
		TextView textNewsMonthYear;
		ImageView imgNews;

	}
}
