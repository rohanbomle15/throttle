package com.throttle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.throttle.BaseApplication;
import com.throttle.profile.BikeDetails;
import com.throttle.utils.ImageViewBitmapManager;
import com.throttle.utils.Urls;

public class BikesGridImagesAdapter extends BaseAdapter {
	private Context mContext;

	// Constructor
	public BikesGridImagesAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return BaseApplication.bike_details.size();// mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		BikeDetails item = BaseApplication.bike_details.get(position);
		String imageURL = item.getBikeImageURL();
		System.out.println(imageURL);
		if (imageURL != null) {
			if (!imageURL.equalsIgnoreCase("")) {
				if (!imageURL.contains("http")) {
					imageURL = Urls.USER_BIKE_PIC + imageURL;
				}
				
				ImageViewBitmapManager.INSTANCE.loadBitmap(imageURL, imageView,
						100, 50);
			}
		}
		// imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

}