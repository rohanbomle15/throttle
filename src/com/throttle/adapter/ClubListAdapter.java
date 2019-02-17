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
import com.throttle.dumpclass;


public class ClubListAdapter extends BaseAdapter{
	private Context activity;
	private ArrayList<dumpclass> data;
	public ClubListAdapter(Context app_context, ArrayList<dumpclass> basicList){
		activity	=	app_context;
		data		=	basicList;
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
        if(convertView == null)
        {holder				=	new ViewHolder();
		 convertView		=	LayoutInflater.from(activity).inflate(R.layout.club_list_item, null);
		 holder.text		=	(TextView) convertView.findViewById(R.id.txtClubName);
//		 holder.image		=	(ImageView) convertView.findViewById(R.id.imgScore);
		 convertView.setTag(holder);
        }else
        	holder			=	(ViewHolder) convertView.getTag();
        
        holder.text.setText(data.get(position).sampletext);
        
        
        return convertView;
	}

	
	public static class ViewHolder{
		TextView text;
		ImageView image;
		
	}
}
