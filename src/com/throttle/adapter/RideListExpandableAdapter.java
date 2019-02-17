package com.throttle.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.models.Ride;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class RideListExpandableAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	ArrayList<Ride> parentItems;
	private ArrayList<String> child;

	public RideListExpandableAdapter(ArrayList<Ride> club_list,
			ArrayList<Object> childern) {
		this.parentItems = club_list;
		this.childtems = childern;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		// child = (ArrayList<String>) childtems.get(groupPosition);

		ImageButton imgBtnJoin = null;
		ImageButton imgBtnClub = null;
		ImageButton imgBtnRiders = null;
		ImageButton imgBtnChat = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.ride_options, null);
		}

		imgBtnJoin = (ImageButton) convertView.findViewById(R.id.rideOptJoinButton);
		imgBtnClub = (ImageButton) convertView.findViewById(R.id.ridebOptClubButton);
		imgBtnRiders = (ImageButton) convertView
				.findViewById(R.id.rideOptRidersButton);
		imgBtnChat = (ImageButton) convertView.findViewById(R.id.rideOptChatButton);

		imgBtnJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				System.out.println("Join :"
						+ parentItems.get(groupPosition).getRideName());
			}
		});

		imgBtnClub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
			}
		});

		imgBtnRiders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
			}
		});

		imgBtnChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
			}
		});

		return convertView;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, final ViewGroup parent) {
		ClubHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.ride_list_row, null);
			holder = new ClubHolder();
			holder.relBackgorund = (RelativeLayout) convertView
					.findViewById(R.id.frameRideListItem);
			holder.txtRideName = (TextView) convertView
					.findViewById(R.id.txtRideName);
			holder.txtRideDateDay = (TextView) convertView
					.findViewById(R.id.txtRideDateDays);
			holder.txtRideKM = (TextView) convertView
					.findViewById(R.id.txtRideKM);
			holder.imgBtnRideOptions = (ImageButton) convertView
					.findViewById(R.id.imgBtnRideOptions);
			convertView.setTag(holder);
		} else {
			holder = (ClubHolder) convertView.getTag();
		}

		Ride item = BaseApplication.ride_list.get(groupPosition);
		
		holder.txtRideName.setText(item.getRideName());
		holder.txtRideDateDay.setText(item.getStartDate()+" ["+item.getDuration()+"]");
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
		int imageResourceId = isExpanded ? R.drawable.arrow_left
				: R.drawable.arrow_left;
		holder.imgBtnRideOptions.setImageResource(imageResourceId);
		holder.imgBtnRideOptions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isExpanded)
					((ExpandableListView) parent).collapseGroup(groupPosition);
				else
					((ExpandableListView) parent).expandGroup(groupPosition,
							true);
			}
		});
		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) childtems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	static class ClubHolder {

		RelativeLayout relBackgorund;
		TextView txtRideName;
		TextView txtRideDateDay;
		TextView txtRideKM;
		ImageButton imgBtnRideOptions;
	}
}