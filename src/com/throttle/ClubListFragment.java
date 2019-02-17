package com.throttle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.throttle.adapter.ClubListSwipeAdapter;
import com.throttle.dbhelper.AppPreferences;
import com.throttle.models.Clubs;
import com.throttle.service.AppService;
import com.throttle.service.AppService.AppServiceListener;
import com.throttle.utils.Urls;
import com.throttle.utils.Util;

public class ClubListFragment extends Fragment implements OnItemClickListener {

	private Context ctx;
	// private ExpandableListView club_list_view;
	// private ClubListExpandableAdapter clubListExpandableAdapter;

	private SwipeListView club_list_view;
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private ArrayList<dumpclass> listdata;

	ClubListSwipeAdapter adapter;
	List<Clubs> itemData;

	private EditText edtSearchClub;
	private Button btnClubSearch;

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
				clubJSONParser(jsonObj);
			} else {
				Toast.makeText(getActivity(), "Unable to load data",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Map<String, String> params = new HashMap<String, String>();

	public ClubListFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.club_list_screen, container,
				false);

		ctx = rootView.getContext();
		club_list_view = (SwipeListView) rootView
				.findViewById(R.id.clubListView);
		// listdata = new ArrayList<dumpclass>();
		// InitializeValues();
		// final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
		// club_list_view, swipeListener, ctx);
		// touchListener.SwipeType = ListViewSwipeGesture.Double;
		// club_list_view.setOnTouchListener(touchListener);

		getClubInfo();
		// clubListSimpleAdapter = new ClubListSimpleAdaptor(getActivity());
		// club_list_view.setAdapter(clubListSimpleAdapter);
		// club_list_view.setOnItemClickListener(itemClikClickListener);

		btnClubSearch = (Button) rootView.findViewById(R.id.btnClubTopClubs);
		edtSearchClub = (EditText) rootView.findViewById(R.id.edtSearchClub);
		btnClubSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		edtSearchClub.addTextChangedListener(searchTextWatcher);

		club_list_view
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
						Log.d("swipe", String.format(
								"onStartOpen %d - action %d", position, action));
					}

					@Override
					public void onStartClose(int position, boolean right) {
						Log.d("swipe",
								String.format("onStartClose %d", position));
					}

					@Override
					public void onClickFrontView(int position) {
						Log.d("swipe",
								String.format("onClickFrontView %d", position));
//						Util.selectedClubID = BaseApplication.club_list.get(
//								position).getClubid();
						Util.selectedClubID = adapter.getItem(position).getClubid();
						Util.selectedClubIndex = position;
						Fragment fragment = new TopViewFragment(
								Util.CLUB_DETAIL_FRAGMENT);
						FragmentManager fragmentManager = getActivity()
								.getSupportFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.frame_container, fragment)
								.commit();
						// club_list_view.openAnimate(position); //when you
						// touch front view it will open
					}

					@Override
					public void onClickBackView(int position) {
						Log.d("swipe",
								String.format("onClickBackView %d", position));
						// club_list_view.closeAnimate(position);//when you
						// touch back view it will close
					}

					@Override
					public void onDismiss(int[] reverseSortedPositions) {

					}

				});

		// These are the swipe listview settings. you can change these
		// setting as your requirement
		club_list_view.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are
																	// five
																	// swiping
																	// modes
		club_list_view.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_DISMISS); // there
																				// are
																				// four
																				// swipe
																				// actions
		club_list_view.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
		club_list_view.setOffsetLeft(convertDpToPixel(0f)); // left side offset
		club_list_view.setOffsetRight(convertDpToPixel(80f)); // right side
																// offset
		club_list_view.setAnimationTime(100); // Animation time
		club_list_view.setSwipeOpenOnLongPress(true); // enable or disable
														// SwipeOpenOnLongPress
		return rootView;
	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	private void getClubInfo() {
		// for (int i = 0; i < 10; i++) {
		// Clubs item = new Clubs();
		// item.setClubid(Integer.toString(i));
		// item.setClubName("Club " + i);
		// item.setClubScore("10" + i);
		// item.setKmPerYear("2000" + i);
		// item.setRiders("20" + i);
		// BaseApplication.club_list.add(item);
		// }

		if (BaseApplication.club_list.size() == 0) {
			try {
				params.put(Util.VIEW_TYPE, "1");
				params.put(Util.USER_ID, AppPreferences.getUSER_ID());
				params.put(Util.CITY, "");
				params.put(Util.CLUB_NAME, "");
				Util.showProgressDialog(getActivity(), "Please Wait...");
				BaseApplication.club_list.clear();
				new AppService(mAppServiceListener).callService(
						Urls.SEARCH_CLUB_URL, params);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			adapter = new ClubListSwipeAdapter(getActivity(),
					R.layout.club_custom_row, BaseApplication.club_list);
			club_list_view.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
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

	public void clubJSONParser(String jsonString) {
		try {
			JSONObject clubResult = new JSONObject(jsonString);
			JSONObject clubArray = clubResult.getJSONObject(Util.RESULT);
			JSONArray jsonArray = clubArray.getJSONArray("clubList");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject clubsJSON = jsonArray.getJSONObject(i);
				if (clubsJSON != null) {
					String statusCode = "";
					String statusMsg = "";
					String clubID = "";
					String clubName = "";
					String clubImageURL = "";
					String createdByUserID = "";
					String ridersCount = "";
					String clubScore = "";
					String ManKmYear = "";
					String City = "";

					if (clubsJSON.has(Util.STATUS_CODE)) {
						statusCode = clubsJSON.getString(Util.STATUS_CODE);
					}
					if (clubsJSON.has(Util.STATUS_DES)) {
						statusMsg = clubsJSON.getString(Util.STATUS_DES);
					}
					if (statusCode.equalsIgnoreCase(Util.SUCESS_STATUS_CODE)) {
						if (clubsJSON.has(Util.CLUB_ID)) {
							clubID = clubsJSON.getString(Util.CLUB_ID);
						}
						if (clubsJSON.has(Util.CLUB_NAME)) {
							clubName = clubsJSON.getString(Util.CLUB_NAME);
						}
						if (clubsJSON.has(Util.CLUBIMAGEURL)) {
							clubImageURL = clubsJSON
									.getString(Util.CLUBIMAGEURL);
						}
						if (clubsJSON.has(Util.CREATEDBYUSERID)) {
							createdByUserID = clubsJSON
									.getString(Util.CREATEDBYUSERID);
						}

						if (clubsJSON.has(Util.CLUB_SCORE)) {
							clubScore = clubsJSON.getString(Util.CLUB_SCORE);
						}
						if (clubsJSON.has(Util.MAN_KM_YEAR)) {
							ManKmYear = clubsJSON.getString(Util.MAN_KM_YEAR);
						}
						if (clubsJSON.has(Util.RIDERS_COUNT)) {
							ridersCount = clubsJSON
									.getString(Util.RIDERS_COUNT);
						}
						if (clubsJSON.has(Util.CITY)) {
							City = clubsJSON.getString(Util.CITY);
						}

						Clubs item = new Clubs();
						item.setClubid(clubID);
						item.setClubName(clubName);
						item.setClubPicUrl(clubImageURL);
						item.setClubOwner(createdByUserID);
						item.setRiders(ridersCount);
						item.setClubScore(clubScore);
						item.setKmPerYear(ManKmYear);
						item.setClubcity(City);
						BaseApplication.club_list.add(item);
					}
				}
			}
			Util.closeProgressDialog();
			adapter = new ClubListSwipeAdapter(getActivity(),
					R.layout.club_custom_row, BaseApplication.club_list);
			club_list_view.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			// for (int i = 0; i < BaseApplication.club_list.size(); i++) {
			// ArrayList<String> child = new ArrayList<String>();
			// child.add("Core");
			// childItems.add(child);
			// }
			// clubListExpandableAdapter = new ClubListExpandableAdapter(
			// BaseApplication.club_list, childItems);
			// clubListExpandableAdapter.setInflater((LayoutInflater) ctx
			// .getSystemService(Context.LAYOUT_INFLATER_SERVICE),
			// getActivity());
			// club_list_view.setAdapter(clubListExpandableAdapter);
			// club_list_view.setOnGroupClickListener(new OnGroupClickListener()
			// {
			//
			// @Override
			// public boolean onGroupClick(ExpandableListView parent, View v,
			// int groupPosition, long id) {
			// // TODO Auto-generated method stub
			// Util.selectedClubID = BaseApplication.club_list.get(
			// groupPosition).getClubid();
			// Util.selectedClubIndex = groupPosition;
			// Fragment fragment = new TopViewFragment(
			// Util.CLUB_DETAIL_FRAGMENT);
			// FragmentManager fragmentManager = getActivity()
			// .getSupportFragmentManager();
			// fragmentManager.beginTransaction()
			// .replace(R.id.frame_container, fragment).commit();
			// return true;
			// }
			// });
			// club_list_view.setOnItemClickListener(itemClikClickListener);
			// if (clubListExpandableAdapter != null) {
			// clubListExpandableAdapter.notifyDataSetChanged();
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

	private TextWatcher searchTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// ignore
			adapter.getFilter().filter(s.toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// ignore
		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

}