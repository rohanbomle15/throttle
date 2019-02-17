package com.throttle.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.models.Clubs;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class ClubListSimpleAdaptor extends BaseAdapter {
	private LayoutInflater inflater = null;
	private Context ctx = null;
	private List<Clubs> clubs;

	public ClubListSimpleAdaptor(Context c) {
		inflater = LayoutInflater.from(c);
		ctx = c;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ClubHolder holder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.club_list_row, null);
			holder = new ClubHolder();
			holder.relBackgorund = (RelativeLayout) convertView
					.findViewById(R.id.frameClubListItem);
//			holder.imgView = (ImageView) convertView
//					.findViewById(R.id.imgScore);
			holder.txtClubScore = (TextView) convertView
					.findViewById(R.id.txtClubScore);
			holder.txtClubName = (TextView) convertView
					.findViewById(R.id.txtClubName);
			holder.txtClubKMPerYear = (TextView) convertView
					.findViewById(R.id.txtClubKmPerYear);
			holder.txtClubRiders = (TextView) convertView
					.findViewById(R.id.txtClubRidersNumber);
			holder.imgBtnClubOptions = (ImageButton) convertView
					.findViewById(R.id.imgBtnClubOptions);
			convertView.setTag(holder);
		} else {
			holder = (ClubHolder) convertView.getTag();
		}

		Clubs item = BaseApplication.club_list.get(position);
		holder.txtClubScore.setText(item.getClubScore());
		holder.txtClubName.setText(item.getClubName());
		holder.txtClubKMPerYear.setText(item.getKmPerYear());
		holder.txtClubRiders.setText(item.getRiders());
		String imageURL = item.getClubPicUrl();
		if (imageURL != null) {
			if (!imageURL.equalsIgnoreCase("")) {
				if (!imageURL.contains("http")) {
					imageURL = Urls.CLUB_BANNER_URL + imageURL;
				}
				System.out.println("----" + imageURL);
				LayoutBitmapManager.INSTANCE.loadBitmap(imageURL,
						holder.relBackgorund, 100, 50);
			}
		}

		return convertView;
	}

	static class ClubHolder {

		RelativeLayout relBackgorund;
		ImageView imgView;
		TextView txtClubScore;
		TextView txtClubName;
		TextView txtClubKMPerYear;
		TextView txtClubRiders;
		ImageButton imgBtnClubOptions;
	}

	public final int getCount() {
		return BaseApplication.club_list.size();
	}

	public final Object getItem(int position) {
		return BaseApplication.club_list.get(position);
	}

	public final long getItemId(int position) {
		return position;
	}

}
