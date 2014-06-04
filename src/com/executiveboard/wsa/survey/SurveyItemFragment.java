package com.executiveboard.wsa.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyItemFragment extends Fragment {
	private static final String TAG = "SurveyItemFragment";
	
	static private class DummyData
	{
		final static String item = "The people I work with cooperate to get the job done.";
		final static String[] responseOptions = {"Strongly Disagree", "Disagree", "Neither Agree Nor Disagree", "Agree", "Strongly Agree"};
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_survey_item, parent, false); //TODO build layout
		
		TextView itemTextView = (TextView)view.findViewById(R.id.surveyItemText);		
		itemTextView.setText(DummyData.item);
		LinearLayout ll = (LinearLayout)view.findViewById(R.id.surveyItemLayout);
		for (int i = 0; i < DummyData.responseOptions.length; ++i) {
			final String text = DummyData.responseOptions[i];
			Button button = new Button(getActivity());
			button.setText(text);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity(), "Pressed " + text, Toast.LENGTH_SHORT).show();					
				}				
			});
			ll.addView(button);
		}
		
		return view;
	}
	
	public static SurveyItemFragment newInstance() {
		Log.i(TAG, "newInstance");
		//Bundle bundle = new Bundle();
		// add to the intent here
		
		//SurveyItemFragment fragment = new SurveyItemFragment();
		//fragment.setArguments(bundle);
		
		return new SurveyItemFragment();
	}
}
