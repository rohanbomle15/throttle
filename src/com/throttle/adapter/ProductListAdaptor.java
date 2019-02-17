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
import com.throttle.store.ProductDetails;
import com.throttle.utils.ImageViewBitmapManager;
import com.throttle.utils.LayoutBitmapManager;
import com.throttle.utils.Urls;

public class ProductListAdaptor extends BaseAdapter {
	private LayoutInflater inflater = null;
	private Context ctx = null;
	private List<ProductDetails> products;

	public ProductListAdaptor(Context c, List<ProductDetails> mProducts) {
		inflater = LayoutInflater.from(c);
		ctx = c;
		products = mProducts;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ClubHolder holder = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.product_list_row, null);
			holder = new ClubHolder();
			holder.imgProduct = (ImageView) convertView
					.findViewById(R.id.imgProduct);
			holder.txtProductName = (TextView) convertView
					.findViewById(R.id.txtProdcutName);
			holder.txtProcutPrice = (TextView) convertView
					.findViewById(R.id.txtProcutPrice);
			holder.txtProdcutCity = (TextView) convertView
					.findViewById(R.id.txtProdcutCity);
			holder.txtProdcutDate = (TextView) convertView
					.findViewById(R.id.txtProdcutDate);
			holder.imgOldNew = (ImageView) convertView
					.findViewById(R.id.imgProductOldNew);
			holder.imgProdcutLikeUnlike = (ImageView) convertView
					.findViewById(R.id.imgProductLikeUnlike);
			holder.imgProdcutOwner = (ImageView) convertView
					.findViewById(R.id.imgProductOwner);
			convertView.setTag(holder);
		} else {
			holder = (ClubHolder) convertView.getTag();
		}

		ProductDetails item = products.get(position);
		holder.txtProcutPrice.setText(item.getListingPrice());
		holder.txtProductName.setText(item.getProductName());
		holder.txtProdcutCity.setText(item.getCityName());
		holder.txtProdcutDate.setText(item.getPostedDate());
		String imageURL = item.getProductImageURL();
		if (imageURL != null) {
			if (!imageURL.equalsIgnoreCase("")) {
				if (!imageURL.contains("http")) {
					imageURL = Urls.PRODUCT_IMG_URL + imageURL;
				}
				ImageViewBitmapManager.INSTANCE.loadBitmap(imageURL,
						holder.imgProduct, 100, 50);
			}
		}

		return convertView;
	}

	static class ClubHolder {
		ImageView imgProduct;
		TextView txtProductName;
		TextView txtProcutPrice;
		ImageView imgOldNew;
		TextView txtProdcutCity;
		TextView txtProdcutDate;
		ImageView imgProdcutLikeUnlike;
		ImageView imgProdcutOwner;
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
