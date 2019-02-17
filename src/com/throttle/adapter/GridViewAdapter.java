package com.throttle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
	Context mContext;
	Integer[] m;

	public GridViewAdapter(Context c, Integer[] x) {
	    mContext = c;
	    m = x;
	}



	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return m.length;
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return m[position];
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	   LinearLayout layout = new LinearLayout(mContext);
	    layout.setLayoutParams(new GridView.LayoutParams(
	            android.view.ViewGroup.LayoutParams.FILL_PARENT,
	            android.view.ViewGroup.LayoutParams.FILL_PARENT));
	    layout.setOrientation(LinearLayout.HORIZONTAL);

	    Button btn = new Button(mContext);
	    btn.setLayoutParams(new LinearLayout.LayoutParams(
	            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	            android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	    btn.setText("Btn " + position);

	    TextView textview = new TextView(mContext);
	    textview.setLayoutParams(new LinearLayout.LayoutParams(
	            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	            android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	    textview.setText("TV " + position);
	    textview.setTextColor(Color.RED);

	    layout.addView(textview);
	    layout.addView(btn);

	    return layout;
	}

	}