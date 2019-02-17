package com.throttle.clubs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.throttle.AddClubFragment;
import com.throttle.R;

public class AddClubDescriptionScreen extends Fragment{

	
	private EditText edtClubMeetingPoint = null;
	private EditText edtClubMeetingDays = null;
	private EditText edtClubVision = null;
	private Button btnDesNext = null;
	public AddClubDescriptionScreen(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.add_club_description, container,
				false);

		edtClubMeetingPoint = (EditText) rootView.findViewById(R.id.edtAddClubDesMeetingPoint);
		edtClubMeetingDays = (EditText) rootView.findViewById(R.id.edtAddClubDesMeetingDays);
		edtClubVision = (EditText) rootView.findViewById(R.id.edtAddClubVision);
		btnDesNext = (Button) rootView.findViewById(R.id.btnAddClubDesNext);
		btnDesNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String clubmeetingpoint = edtClubMeetingPoint.getText().toString();
				String clubmeetingdays = edtClubMeetingDays.getText().toString();
				String clubvision = edtClubVision.getText().toString();
				
				ClubDetails.setClub_meeting_point(clubmeetingpoint);
				ClubDetails.setClub_meeting_days(clubmeetingdays);
				ClubDetails.setClub_vision(clubvision);
				
				AddClubFragment.setClubFragmentTab(2);
			}
		});
		return rootView;
	}
	
	public void btnAddClubDesNext(View view){
		
	}
}
