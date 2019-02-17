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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.throttle.R;
import com.throttle.models.Clubs;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class ClubListExpandableAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	ArrayList<Clubs> searchItems = new ArrayList<Clubs>();
	ArrayList<Clubs> parentItems = new ArrayList<Clubs>();
	private ArrayList<String> child = new ArrayList<String>();

	public ClubListExpandableAdapter(ArrayList<Clubs> club_list,
			ArrayList<Object> childern) {
		// this.parentItems = club_list;
		this.parentItems.addAll(club_list);
		// this.searchItems = club_list;
		this.searchItems.addAll(club_list);
		this.childtems = childern;
//		this.child.addAll(child);
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
		ImageButton imgBtnRides = null;
		ImageButton imgBtnRiders = null;
		ImageButton imgBtnChat = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.club_options, null);
		}

		imgBtnJoin = (ImageButton) convertView.findViewById(R.id.clubOptJoinButton);
		imgBtnRides = (ImageButton) convertView.findViewById(R.id.clubOptRidesButton);
		imgBtnRiders = (ImageButton) convertView
				.findViewById(R.id.clubOptRidersButton);
		imgBtnChat = (ImageButton) convertView.findViewById(R.id.clubOptChatButton);

		imgBtnJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				System.out.println("Join :"
						+ parentItems.get(groupPosition).getClubName());
			}
		});

		imgBtnRides.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				System.out.println("Rides"
						+ parentItems.get(groupPosition).getClubName());
			}
		});

		imgBtnRiders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				System.out.println("Riders"
						+ parentItems.get(groupPosition).getClubName());
			}
		});

		imgBtnChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				System.out.println("Chat"
						+ parentItems.get(groupPosition).getClubName());
			}
		});

		return convertView;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, final ViewGroup parent) {
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

//		Clubs item = BaseApplication.club_list.get(groupPosition);
		Clubs item = this.parentItems.get(groupPosition);
		holder.txtClubScore.setText(item.getClubScore());
		holder.txtClubName.setText(item.getClubName());
		holder.txtClubKMPerYear.setText(item.getKmPerYear()+" man km/year");
		holder.txtClubRiders.setText(item.getRiders());
		String imageURL = item.getClubPicUrl();
		if (imageURL != null) {
			if (!imageURL.equalsIgnoreCase("")) {
				if (!imageURL.contains("http")) {
					imageURL = Urls.CLUB_BANNER_URL + imageURL;
				}

				LayoutBitmapManager.INSTANCE.loadBitmap(imageURL,
						holder.relBackgorund, 100, 50);
			}
		}
		int imageResourceId = isExpanded ? R.drawable.arrow_left
				: R.drawable.arrow_left;
		holder.imgBtnClubOptions.setImageResource(imageResourceId);
		holder.imgBtnClubOptions.setOnClickListener(new OnClickListener() {

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
		return this.parentItems.size();
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
		ImageView imgView;
		TextView txtClubScore;
		TextView txtClubName;
		TextView txtClubKMPerYear;
		TextView txtClubRiders;
		ImageButton imgBtnClubOptions;
	}

	public void filterData(String query) {

		try {
			query = query.toLowerCase();

			this.searchItems.clear();

			if (query.isEmpty()) {
				this.searchItems.addAll(this.parentItems);
			} else {
				ArrayList<Clubs> newList = new ArrayList<Clubs>();
				for (Clubs club : this.parentItems) {
//					ArrayList<Clubs> clubList = this.parentItems;
					// for (Clubs club : clubList) {
					if (club.getClubName().toLowerCase().contains(query)) {
						newList.add(club);
						this.searchItems.add(club);
					}
					// }
				}
				if (newList.size() > 0) {
					Clubs nContinent = new Clubs();
					this.parentItems.addAll(this.searchItems);
				}
			}
			this.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}