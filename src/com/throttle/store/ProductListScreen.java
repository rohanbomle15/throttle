package com.throttle.store;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.throttle.BaseApplication;
import com.throttle.R;
import com.throttle.TopViewFragment;
import com.throttle.adapter.NewsListAdapter;
import com.throttle.adapter.ProductListAdaptor;
import com.throttle.news.NewsDetails;
import com.throttle.service.AppGetService;
import com.throttle.service.AppGetService.AppGetServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProductListScreen extends Fragment {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private AppGetServiceListener mAppGetServiceListener = new AppGetServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();

		}
	};
	private Map<String, String> getparams = new HashMap<String, String>();

	private ListView listProdcut;
	private ProductListAdaptor prodcutListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.product_list_screen,
				container, false);
		listProdcut = (ListView) rootView.findViewById(R.id.productListView);
//		getProduct(BaseApplication.SELECTED_PRODUCT_ID);
		prodcutListAdapter = new ProductListAdaptor(getActivity(), BaseApplication.prodcut_details);
		listProdcut.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Util.selectedNewsID = BaseApplication.news_details
//						.get(position).getNewsID();
//				Util.selecteNewsIndex = position;
//				Fragment fragment = new TopViewFragment(
//						Util.NEWS_DETAILS_FRAGMENT);
//				FragmentManager fragmentManager = getActivity()
//						.getSupportFragmentManager();
//				fragmentManager.beginTransaction()
//						.replace(R.id.frame_container, fragment).commit();
			}
		});
		return rootView;
	}

	public void getProduct(String listingID) {
		Util.showProgressDialog(getActivity(), Util.LOADING_TITLE);
		new AppGetService(mAppGetServiceListener).callService(
				Urls.GET_PRODUCT_LIST_URL + listingID, getparams);
	}

	public void ProductDetailsParser(String jsonString) {

		try {
			JSONObject jsonObj = new JSONObject(jsonString);
			// JSONArray jsonArray = new JSONArray(jsonObj);
			JSONObject bikesObj = jsonObj.getJSONObject(Util.RESULT);
			JSONArray jsonArray = bikesObj.getJSONArray(Util.MY_PRODUCT);
			BaseApplication.prodcut_details.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				String productListingID = "";
				String createdByUserID = "";
				String productName = "";
				String categoryID = "";
				String categoryName = "";
				String categoryTitle = "";
				String subCategory1ID = "";
				String subCat1Name = "";
				String subCat1Title = "";
				String subCategory2ID = "";
				String subCat2Name = "";
				String subCat2Title = "";
				String cityID = "";
				String localityID = "";
				String cityName = "";
				String localityName = "";
				String priceType = "";
				String listingPrice = "";
				String bidPrice = "";
				String lastBidDate = "";
				String productStatus = "";
				String productStatusDesc = "";
				String productImageURL = "";
				String productDescription = "";
				String sellerType = "";
				String contactType = "";
				String postedBy = "";
				String mobileNumber = "";
				String emailID = "";
				String postedDate = "";
				String listingStatus = "";
				String statusCode = "";
				String statusDescription = "";
				JSONObject clubsJSON = jsonArray.getJSONObject(i);
				if (clubsJSON != null) {

					if (clubsJSON.has(Util.STATUS_CODE)) {
						statusCode = clubsJSON.getString(Util.STATUS_CODE);
					}
					if (clubsJSON.has(Util.STATUS_DES)) {
						statusDescription = clubsJSON
								.getString(Util.STATUS_DES);
					}
					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
						if (clubsJSON.has(Util.PRODUCTLISTINGID)) {
							productListingID = clubsJSON
									.getString(Util.PRODUCTLISTINGID);
						}
						if (clubsJSON.has(Util.CREATEDBYUSERID)) {
							createdByUserID = clubsJSON
									.getString(Util.CREATEDBYUSERID);
						}
						if (clubsJSON.has(Util.PRODUCTNAME)) {
							productName = clubsJSON.getString(Util.PRODUCTNAME);
						}
						if (clubsJSON.has(Util.CATEGORYID)) {
							categoryID = clubsJSON.getString(Util.CATEGORYID);
						}
						if (clubsJSON.has(Util.CATEGORYNAME)) {
							categoryName = clubsJSON
									.getString(Util.CATEGORYNAME);
						}
						if (clubsJSON.has(Util.CATEGORYTITLE)) {
							categoryTitle = clubsJSON
									.getString(Util.CATEGORYTITLE);
						}
						if (clubsJSON.has(Util.SUBCATEGORY1ID)) {
							subCategory1ID = clubsJSON
									.getString(Util.SUBCATEGORY1ID);
						}
						if (clubsJSON.has(Util.SUBCAT1NAME)) {
							subCat1Name = clubsJSON.getString(Util.SUBCAT1NAME);
						}
						if (clubsJSON.has(Util.SUBCAT1TITLE)) {
							subCat1Title = clubsJSON
									.getString(Util.SUBCAT1TITLE);
						}
						if (clubsJSON.has(Util.SUBCATEGORY2ID)) {
							subCategory2ID = clubsJSON
									.getString(Util.SUBCATEGORY2ID);
						}
						if (clubsJSON.has(Util.SUBCAT2NAME)) {
							subCat2Name = clubsJSON.getString(Util.SUBCAT2NAME);
						}
						if (clubsJSON.has(Util.SUBCAT2TITLE)) {
							subCat2Title = clubsJSON
									.getString(Util.SUBCAT2TITLE);
						}
						if (clubsJSON.has(Util.CITYID)) {
							cityID = clubsJSON.getString(Util.CITYID);
						}
						if (clubsJSON.has(Util.LOCALITYID)) {
							localityID = clubsJSON.getString(Util.LOCALITYID);
						}
						if (clubsJSON.has(Util.CITYNAME)) {
							cityName = clubsJSON.getString(Util.CITYNAME);
						}
						if (clubsJSON.has(Util.LOCALITYNAME)) {
							localityName = clubsJSON
									.getString(Util.LOCALITYNAME);
						}
						if (clubsJSON.has(Util.PRICETYPE)) {
							priceType = clubsJSON.getString(Util.PRICETYPE);
						}
						if (clubsJSON.has(Util.LISTINGPRICE)) {
							listingPrice = clubsJSON
									.getString(Util.LISTINGPRICE);
						}
						if (clubsJSON.has(Util.BIDPRICE)) {
							bidPrice = clubsJSON.getString(Util.BIDPRICE);
						}
						if (clubsJSON.has(Util.LASTBIDDATE)) {
							lastBidDate = clubsJSON.getString(Util.LASTBIDDATE);
						}
						if (clubsJSON.has(Util.PRODUCTSTATUS)) {
							productStatus = clubsJSON
									.getString(Util.PRODUCTSTATUS);
						}
						if (clubsJSON.has(Util.PRODUCTSTATUSDESC)) {
							productStatusDesc = clubsJSON
									.getString(Util.PRODUCTSTATUSDESC);
						}
						if (clubsJSON.has(Util.PRODUCTIMAGEURL)) {
							productImageURL = clubsJSON
									.getString(Util.PRODUCTIMAGEURL);
						}
						if (clubsJSON.has(Util.PRODUCTDESCRIPTION)) {
							productDescription = clubsJSON
									.getString(Util.PRODUCTDESCRIPTION);
						}
						if (clubsJSON.has(Util.SELLERTYPE)) {
							sellerType = clubsJSON.getString(Util.SELLERTYPE);
						}
						if (clubsJSON.has(Util.CONTACTTYPE)) {
							contactType = clubsJSON.getString(Util.CONTACTTYPE);
						}
						if (clubsJSON.has(Util.POSTEDBY)) {
							postedBy = clubsJSON.getString(Util.POSTEDBY);
						}
						if (clubsJSON.has(Util.POSTEDDATE)) {
							postedDate = clubsJSON.getString(Util.POSTEDDATE);
						}
						if (clubsJSON.has(Util.MOBILENUMBER)) {
							mobileNumber = clubsJSON
									.getString(Util.MOBILENUMBER);
						}
						if (clubsJSON.has(Util.EMAILID)) {
							emailID = clubsJSON.getString(Util.EMAILID);
						}
						if (clubsJSON.has(Util.LISTINGSTATUS)) {
							listingStatus = clubsJSON
									.getString(Util.LISTINGSTATUS);
						}

						ProductDetails item = new ProductDetails();
						item.setProductListingID(productListingID);
						item.setCreatedByUserID(createdByUserID);
						item.setProductName(productName);
						item.setCategoryID(categoryID);
						item.setCategoryName(categoryName);
						item.setCategoryTitle(categoryTitle);
						item.setSubCategory1ID(subCategory1ID);
						item.setSubCat1Name(subCat1Name);
						item.setSubCat1Title(subCat1Title);
						item.setSubCategory2ID(subCategory2ID);
						item.setSubCat2Name(subCat2Name);
						item.setSubCat2Title(subCat2Title);
						item.setCityID(cityID);
						item.setLocalityID(localityID);
						item.setCityName(cityName);
						item.setLocalityName(localityName);
						item.setPriceType(priceType);
						item.setListingPrice(listingPrice);
						item.setBidPrice(bidPrice);
						item.setLastBidDate(lastBidDate);
						item.setProductStatus(productStatus);
						item.setProductStatusDesc(productStatusDesc);
						item.setProductImageURL(productImageURL);
						item.setProductDescription(productDescription);
						item.setSellerType(sellerType);
						item.setContactType(contactType);
						item.setPostedBy(postedBy);
						item.setMobileNumber(mobileNumber);
						item.setEmailID(emailID);
						item.setPostedDate(postedDate);
						item.setListingStatus(listingStatus);
						item.setStatusCode(statusCode);
						item.setStatusDescription(statusDescription);
						BaseApplication.prodcut_details.add(item);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
