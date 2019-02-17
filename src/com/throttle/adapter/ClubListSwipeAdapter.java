package com.throttle.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.throttle.R;
import com.throttle.models.Clubs;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class ClubListSwipeAdapter extends ArrayAdapter<Clubs> implements Filterable{

	ArrayList<Clubs> data;
	ArrayList<Clubs> filteredData;
	Context mContext;
	int layoutResID;
	private ItemFilter mFilter = new ItemFilter();

	public ClubListSwipeAdapter(Context context, int textViewResourceId,
			ArrayList<Clubs> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		data = objects;
		filteredData = objects;
		mContext = context;
		layoutResID = textViewResourceId;
	}

	public int getCount() {
        return filteredData.size();
    }

    public Clubs getItem(int position) {
        return filteredData.get(position);
    }
    
    
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ClubHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResID, parent, false);

			holder = new ClubHolder();
			holder.relBackgorund = (RelativeLayout) convertView
					.findViewById(R.id.frameClubListItem);
			// holder.imgView = (ImageView) convertView
			// .findViewById(R.id.imgScore);
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
			holder.clubOptionClubName = (TextView) convertView
					.findViewById(R.id.clubOptionClubName);

			holder.imgBtnClubOptions = (ImageButton) convertView
					.findViewById(R.id.imgBtnClubOptions);
			holder.imgBtnClubOptionClose = (ImageButton) convertView
					.findViewById(R.id.clubOptionClose);
			convertView.setTag(holder);
		} else {
			holder = (ClubHolder) convertView.getTag();
		}

		Clubs item = filteredData.get(position);
		holder.txtClubScore.setText(item.getClubScore());
		holder.txtClubName.setText(item.getClubName());
		holder.txtClubKMPerYear.setText(item.getKmPerYear() + " man km/year");
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

		holder.clubOptionClubName.setText(item.getClubName());
		holder.imgBtnJoin = (ImageButton) convertView
				.findViewById(R.id.clubOptJoinButton);
		holder.imgBtnRides = (ImageButton) convertView
				.findViewById(R.id.clubOptRidesButton);
		holder.imgBtnRiders = (ImageButton) convertView
				.findViewById(R.id.clubOptRidersButton);
		holder.imgBtnChat = (ImageButton) convertView
				.findViewById(R.id.clubOptChatButton);

		holder.imgBtnJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(mContext,
						"Want to Join " + filteredData.get(position).getClubName(),
						Toast.LENGTH_SHORT).show();
			}
		});

		holder.imgBtnRides.setOnClickListener(new OnClickListener() {

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
		
		holder.imgBtnClubOptions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
			}
		});
		
		holder.imgBtnClubOptionClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

			}
		});

		return convertView;

	}
	
	public Filter getFilter() {
        return mFilter;
    }

	static class ClubHolder {

		RelativeLayout relBackgorund;
		ImageView imgView;
		TextView txtClubScore;
		TextView txtClubName;
		TextView txtClubKMPerYear;
		TextView txtClubRiders;
		ImageButton imgBtnClubOptions;

		ImageButton imgBtnClubOptionClose;
		TextView clubOptionClubName;
		ImageButton imgBtnJoin;
		ImageButton imgBtnRides;
		ImageButton imgBtnRiders;
		ImageButton imgBtnChat;
	}
	
	private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Clubs> list = data;

            int count = list.size();
            final ArrayList<Clubs> nlist = new ArrayList<Clubs>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getClubName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                    System.out.println(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Clubs>) results.values;
            System.out.println(filteredData.size());
            notifyDataSetChanged();
        }

    }
}