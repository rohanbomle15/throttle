package com.throttle.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.throttle.R;
import com.throttle.models.Ride;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class RideListSwipeAdapter extends ArrayAdapter<Ride> {

	ArrayList<Ride> data;
	Context mContext;
	int layoutResID;

	public RideListSwipeAdapter(Context context, int textViewResourceId,
			ArrayList<Ride> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		data = objects;
		mContext = context;
		layoutResID = textViewResourceId;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RideHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResID, parent, false);
			holder = new RideHolder();
			holder.relBackgorund = (RelativeLayout) convertView
					.findViewById(R.id.frameRideListItem);
			holder.txtRideName = (TextView) convertView
					.findViewById(R.id.txtRideName);
			holder.txtRideDateDay = (TextView) convertView
					.findViewById(R.id.txtRideDateDays);
			holder.txtRideKM = (TextView) convertView
					.findViewById(R.id.txtRideKM);
			holder.txtRideOptionRideName = (TextView) convertView
					.findViewById(R.id.rideOptionRideName);
			holder.imgBtnRideOptions = (ImageButton) convertView
					.findViewById(R.id.imgBtnRideOptions);
			holder.imgBtnJoin = (ImageButton) convertView
					.findViewById(R.id.rideOptJoinButton);
			holder.imgBtnClub = (ImageButton) convertView
					.findViewById(R.id.ridebOptClubButton);
			holder.imgBtnRiders = (ImageButton) convertView
					.findViewById(R.id.rideOptRidersButton);
			holder.imgBtnChat = (ImageButton) convertView
					.findViewById(R.id.rideOptChatButton);
			convertView.setTag(holder);
		} else {
			holder = (RideHolder) convertView.getTag();
		}

		Ride item = data.get(position);

		holder.txtRideName.setText(item.getRideName());
		holder.txtRideOptionRideName.setText(item.getRideName());
		holder.txtRideDateDay.setText(item.getStartDate() + " ["
				+ item.getDuration() + " Days]");
		holder.txtRideKM.setText(item.getApproxKm());
		String imageURL = item.getRideImageName();
		if (imageURL != null) {
			if (!imageURL.equalsIgnoreCase("")) {
				if (!imageURL.contains("http")) {
					System.out.println(imageURL);
					imageURL = Urls.RIDE_BANNER_URL + imageURL;
				}

				LayoutBitmapManager.INSTANCE.loadBitmap(imageURL,
						holder.relBackgorund, 100, 50);
			}
		}

		holder.imgBtnRideOptions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		holder.imgBtnJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});

		holder.imgBtnClub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});

		holder.imgBtnRiders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});

		holder.imgBtnChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});

		return convertView;

	}

	static class RideHolder {

		RelativeLayout relBackgorund;
		TextView txtRideName;
		TextView txtRideDateDay;
		TextView txtRideKM;
		TextView txtRideOptionRideName;
		ImageButton imgBtnRideOptions;
		ImageButton imgBtnJoin;
		ImageButton imgBtnClub;
		ImageButton imgBtnRiders;
		ImageButton imgBtnChat;
	}
}