package com.executiveboard.wsa.survey;

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

import com.executiveboard.wsa.survey.models.Item;
import com.executiveboard.wsa.survey.models.ResponseOption;
import com.executiveboard.wsa.survey.models.ResponseScale;

public class SurveyItemFragment extends Fragment {
	private static final String TAG = "SurveyItemFragment";
	private static final String EXTRA_ITEM_ID = "com.executiveboard.wsa.survey.item_id";
	private static final String DB_NAME = "survey.db3";
	private SQLiteDatabase mDatabase;
	
	public static class PlaceholderData {
		public static Item item;
		public static void init() {
			if (item == null) {
				ResponseScale scale = new ResponseScale();		
				scale.addOption(new ResponseOption("Strongly Agree"));
				scale.addOption(new ResponseOption("Agree"));
				scale.addOption(new ResponseOption("Neither Agree Nor Disagree"));
				scale.addOption(new ResponseOption("Disagree"));
				scale.addOption(new ResponseOption("Strongly Disagree"));
		
				item = new Item("The people I work with cooperate to get the job done.", scale);
			}
		}
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
		PlaceholderData.init();
		
		TextView itemTextView = (TextView)view.findViewById(R.id.surveyItemText);		
		itemTextView.setText(PlaceholderData.item.getText());
		
		final Button submitButton = (Button)view.findViewById(R.id.buttonSubmit);
		submitButton.setEnabled(false);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Pressed Submit", Toast.LENGTH_SHORT).show();
			}
		});
		
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.responseOptionsLayout);
		for (int i = 0; i < PlaceholderData.item.getOptionCount(); ++i) {
			final String text = PlaceholderData.item.getOption(i).getText();
			Button button = new Button(getActivity());
			button.setText(text);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					submitButton.setEnabled(true);
					Toast.makeText(getActivity(), "Pressed " + text, Toast.LENGTH_SHORT).show();					
				}
			});
			layout.addView(button);
		}
		
		Button previousButton = (Button)view.findViewById(R.id.buttonPrevious);
		previousButton.setEnabled(false);
		previousButton.setVisibility(View.INVISIBLE);
		
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
