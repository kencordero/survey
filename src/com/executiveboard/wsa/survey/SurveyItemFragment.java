package com.executiveboard.wsa.survey;

import com.executiveboard.wsa.survey.models.Item;

import android.database.sqlite.SQLiteDatabase;
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
	private static final String EXTRA_ITEM_ID = "com.executiveboard.wsa.survey.item_id";
	private static final String DB_NAME = "survey.db3";
	private SQLiteDatabase mDatabase;
	
	private static class PlaceholderData
	{
		final static String item = "The people I work with cooperate to get the job done.";
		final static String[] responseOptions = {"Strongly Disagree", "Disagree", "Neither Agree Nor Disagree", "Agree", "Strongly Agree"};
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
		mDatabase = new DatabaseOpenHelper(getActivity(), DB_NAME).openDatabase();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
		mDatabase.close();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_survey_item, parent, false);
		
		TextView itemTextView = (TextView)view.findViewById(R.id.surveyItemText);		
		itemTextView.setText(PlaceholderData.item);
		
		final Button submitButton = (Button)view.findViewById(R.id.buttonSubmit);
		submitButton.setEnabled(false);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Pressed Submit", Toast.LENGTH_SHORT).show();
			}			
		});
		
		LinearLayout ll = (LinearLayout)view.findViewById(R.id.responseOptionsLayout);
		for (int i = 0; i < PlaceholderData.responseOptions.length; ++i) {
			final String text = PlaceholderData.responseOptions[i];
			Button button = new Button(getActivity());
			button.setText(text);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					submitButton.setEnabled(true);
					Toast.makeText(getActivity(), "Pressed " + text, Toast.LENGTH_SHORT).show();					
				}				
			});
			ll.addView(button);
		}
		
		Button previousButton = (Button)view.findViewById(R.id.buttonPrevious);
		previousButton.setEnabled(false);			
		
		return view;
	}
	
	public static SurveyItemFragment newInstance(String itemId) {
		Log.i(TAG, "newInstance");
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_ITEM_ID, itemId);
		
		SurveyItemFragment fragment = new SurveyItemFragment();
		fragment.setArguments(bundle);
		
		return fragment;
	}
}
