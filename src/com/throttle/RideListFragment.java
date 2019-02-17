package com.throttle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.throttle.adapter.RideListSwipeAdapter;
import com.throttle.dbhelper.AppPreferences;
import com.throttle.models.Ride;
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class RideListFragment extends Fragment implements OnItemClickListener {

	private Context ctx;
	private SwipeListView ride_list_view;
	// private RideListExpandableAdapter rideListExpandableAdapter;

	private RideListSwipeAdapter listAdapter;
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private ArrayList<dumpclass> listdata;

	private Button btnAllRides;
	private Button btnFeaturedRides;
	private Button btnMyRides;

	private EditText edtSearchRide;
	private ImageView imgBtnRideSearch;

	private AppServiceListener mAppServiceListener = new AppServiceListener() {

		@Override
		public void onServiceError(String error) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
			Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceComplete(Boolean success, String jsonObj) {
			// TODO Auto-generated method stub
			Util.closeProgressDialog();
			if (jsonObj != null) {
				rideJSONParser(jsonObj);
			} else {
				Toast.makeText(getActivity(), "Unable to load data",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Map<String, String> params = new HashMap<String, String>();

	public RideListFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.ride_list_screen, container,
				false);

		ctx = rootView.getContext();
		ride_list_view = (SwipeListView) rootView
				.findViewById(R.id.rideListView);
		// listdata = new ArrayList<dumpclass>();
		// InitializeValues();
		// final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
		// club_list_view, swipeListener, ctx);
		// touchListener.SwipeType = ListViewSwipeGesture.Double;
		// club_list_view.setOnTouchListener(touchListener);

		getRideInfo();
		// clubListSimpleAdapter = new ClubListSimpleAdaptor(getActivity());
		// club_list_view.setAdapter(clubListSimpleAdapter);
		// club_list_view.setOnItemClickListener(itemClikClickListener);

		btnFeaturedRides = (Button) rootView.findViewById(R.id.btnFeaturedRide);
		btnAllRides = (Button) rootView.findViewById(R.id.btnAllRides);
		btnMyRides = (Button) rootView.findViewById(R.id.btnMyRides);

		edtSearchRide = (EditText) rootView.findViewById(R.id.edtSearchRide);
		imgBtnRideSearch = (ImageView) rootView
				.findViewById(R.id.imgBtnRideSearch);
		imgBtnRideSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		ride_list_view
				.setSwipeListViewListener(new BaseSwipeListViewListener() {
					@Override
					public void onOpened(int position, boolean toRight) {
					}

					@Override
					public void onClosed(int position, boolean fromRight) {
					}

					@Override
					public void onListChanged() {
					}

					@Override
					public void onMove(int position, float x) {
					}

					@Override
					public void onStartOpen(int position, int action,
							boolean right) {

					}

					@Override
					public void onStartClose(int position, boolean right) {

					}

					@Override
					public void onClickFrontView(int position) {
						Util.selectedRideID = BaseApplication.ride_list.get(
								position).getRideID();
						Util.selectedRideIndex = position;
						Fragment fragment = new TopViewFragment(
								Util.RIDE_DETAIL_FRAGMENT);
						FragmentManager fragmentManager = getActivity()
								.getSupportFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.frame_container, fragment)
								.commit();

					}

					@Override
					public void onClickBackView(int position) {
						Log.d("swipe",
								String.format("onClickBackView %d", position));
						// club_list_view.closeAnimate(position);
					}

					@Override
					public void onDismiss(int[] reverseSortedPositions) {

					}

				});

		ride_list_view.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		ride_list_view.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_DISMISS);
		ride_list_view.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
		ride_list_view.setOffsetLeft(convertDpToPixel(0f));
		ride_list_view.setOffsetRight(convertDpToPixel(80f));

		ride_list_view.setAnimationTime(100);
		ride_list_view.setSwipeOpenOnLongPress(true);
		return rootView;
	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	private void getRideInfo() {
		// for (int i = 0; i < 10; i++) {
		// Clubs item = new Clubs();
		// item.setClubid(Integer.toString(i));
		// item.setClubName("Club " + i);
		// item.setClubScore("10" + i);
		// item.setKmPerYear("2000" + i);
		// item.setRiders("20" + i);
		// BaseApplication.club_list.add(item);
		// }

		if (BaseApplication.ride_list.size() == 0) {
			try {
				params.put(Util.VIEW_TYPE, "1");
				params.put(Util.USER_ID, AppPreferences.getUSER_ID());
				params.put(Util.CLUB_ID, "");
				params.put(Util.RIDENAME, "");
				Util.showProgressDialog(getActivity(), "Please Wait...");
				BaseApplication.ride_list.clear();
				new AppService(mAppServiceListener).callService(
						Urls.SEARCH_RIDE_URL, params);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			listAdapter = new RideListSwipeAdapter(getActivity(),
					R.layout.ride_custom_row, BaseApplication.ride_list);
			ride_list_view.setAdapter(listAdapter);
			listAdapter.notifyDataSetChanged();
		}
	}

	private void InitializeValues() {
		// // TODO Auto-generated method stub
		// listdata.add(new dumpclass("one"));
		// listdata.add(new dumpclass("two"));
		// listdata.add(new dumpclass("three"));
		// listAdapter = new ClubListAdapter(ctx, listdata);
		// cmn_list_view.setAdapter(listAdapter);
	}

	// ListViewSwipeGesture.TouchCallbacks swipeListener = new
	// ListViewSwipeGesture.TouchCallbacks() {
	//
	// @Override
	// public void FullSwipeListView(int position) {
	// // // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void HalfSwipeListView(int position) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void LoadDataForScroll(int count) {
	// // // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onDismiss(ListView listView, int[] reverseSortedPositions) {
	// // // TODO Auto-generated method stub
	//
	// for (int i : reverseSortedPositions) {
	// listdata.remove(i);
	// listAdapter.notifyDataSetChanged();
	// }
	// }
	//
	// @Override
	// public void OnClickListView(int position) {
	// // // TODO Auto-generated method stub
	//
	// }
	//
	// };

	OnItemClickListener itemClikClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			System.out.println("----------------");
		}
	};

	public void rideJSONParser(String jsonString) {
		Util.closeProgressDialog();
		try {
			JSONObject rideResult = new JSONObject(jsonString);
			JSONObject rideArray = rideResult.getJSONObject(Util.RESULT);
			JSONArray jsonArray = rideArray.getJSONArray("rideList");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject rideJSON = jsonArray.getJSONObject(i);
				if (rideJSON != null) {
					String statusCode = "";
					String statusMsg = "";
					String RideID = "";
					String RideName = "";
					String ClubID = "";
					String LeadName = "";
					String StartDate = "";
					String EndDate = "";
					String ApproxKm = "";
					String RidePrice = "";
					String RideType = "";
					String RideDescription = "";
					String RideImageName = "";
					String CreatedByUserID = "";
					String Duration = "";
					String RiderCount = "";

					if (rideJSON.has(Util.STATUS_CODE)) {
						statusCode = rideJSON.getString(Util.STATUS_CODE);
					}
					if (rideJSON.has(Util.STATUS_DES)) {
						statusMsg = rideJSON.getString(Util.STATUS_DES);
					}
					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
						if (rideJSON.has(Util.RIDENAME)) {
							RideName = rideJSON.getString(Util.RIDENAME);
						}
						if (rideJSON.has(Util.RIDEID)) {
							RideID = rideJSON.getString(Util.RIDEID);
						}
						if (rideJSON.has(Util.CLUB_ID)) {
							ClubID = rideJSON.getString(Util.CLUB_ID);
						}
						if (rideJSON.has(Util.LEADNAME)) {
							LeadName = rideJSON.getString(Util.LEADNAME);
						}

						if (rideJSON.has(Util.STARTDATE)) {
							StartDate = rideJSON.getString(Util.STARTDATE);
						}
						if (rideJSON.has(Util.ENDDATE)) {
							EndDate = rideJSON.getString(Util.ENDDATE);
						}
						if (rideJSON.has(Util.APPROXKM)) {
							ApproxKm = rideJSON.getString(Util.APPROXKM);
						}
						if (rideJSON.has(Util.RIDEPRICE)) {
							RidePrice = rideJSON.getString(Util.RIDEPRICE);
						}
						if (rideJSON.has(Util.RIDETYPE)) {
							RideType = rideJSON.getString(Util.RIDETYPE);
						}
						if (rideJSON.has(Util.RIDEDESCRIPTION)) {
							RideDescription = rideJSON
									.getString(Util.RIDEDESCRIPTION);
						}
						if (rideJSON.has(Util.RIDEIMAGEURL)) {
							RideImageName = rideJSON
									.getString(Util.RIDEIMAGEURL);
						}
						if (rideJSON.has(Util.CREATEDBYUSERID)) {
							CreatedByUserID = rideJSON
									.getString(Util.CREATEDBYUSERID);
						}
						if (rideJSON.has(Util.DURATION)) {
							Duration = rideJSON.getString(Util.DURATION);
						}
						if (rideJSON.has(Util.RIDERS_COUNT)) {
							RiderCount = rideJSON.getString(Util.RIDERCOUNT);
						}

						Ride item = new Ride();
						item.setRideID(RideID);
						item.setRideName(RideName);
						item.setClubID(ClubID);
						item.setLeadName(LeadName);
						item.setStartDate(StartDate);
						item.setEndDate(EndDate);
						item.setDuration(Duration);
						item.setRiderCount(RiderCount);
						item.setApproxKm(ApproxKm);
						item.setRideType(RideType);
						item.setRideImageName(RideImageName);
						item.setCreatedByUserID(CreatedByUserID);

						BaseApplication.ride_list.add(item);

					}
				}
			}

			listAdapter = new RideListSwipeAdapter(getActivity(),
					R.layout.ride_custom_row, BaseApplication.ride_list);
			ride_list_view.setAdapter(listAdapter);
			listAdapter.notifyDataSetChanged();
			// for (int i = 0; i < BaseApplication.ride_list.size(); i++) {
			// ArrayList<String> child = new ArrayList<String>();
			// child.add("Core");
			// childItems.add(child);
			// }
			// rideListExpandableAdapter = new RideListExpandableAdapter(
			// BaseApplication.ride_list, childItems);
			// rideListExpandableAdapter.setInflater((LayoutInflater) ctx
			// .getSystemService(Context.LAYOUT_INFLATER_SERVICE),
			// getActivity());
			// ride_list_view.setAdapter(rideListExpandableAdapter);
			// ride_list_view.setOnGroupClickListener(new OnGroupClickListener()
			// {
			//
			// @Override
			// public boolean onGroupClick(ExpandableListView parent, View v,
			// int groupPosition, long id) {
			// // TODO Auto-generated method stub
			// Util.selectedRideID = BaseApplication.ride_list.get(
			// groupPosition).getRideID();
			// Util.selectedRideIndex = groupPosition;
			// Fragment fragment = new TopViewFragment(
			// Util.RIDE_DETAIL_FRAGMENT);
			// FragmentManager fragmentManager = getActivity()
			// .getSupportFragmentManager();
			// fragmentManager.beginTransaction()
			// .replace(R.id.frame_container, fragment).commit();
			// return true;
			// }
			// });
			// ride_list_view.setOnItemClickListener(itemClikClickListener);
			// if (rideListExpandableAdapter != null) {
			// rideListExpandableAdapter.notifyDataSetChanged();
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}