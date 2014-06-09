package com.executiveboard.wsa.survey;

import android.database.Cursor;
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
import com.executiveboard.wsa.survey.models.ResponseScale;

public class SurveyItemFragment extends Fragment {
	private static final String TAG = "SurveyItemFragment";
	private static final String EXTRA_ITEM_ID = "com.executiveboard.wsa.survey.item_id";
	private static final String DB_NAME = "survey.db3";
	private SQLiteDatabase mDatabase;
	private Button mSubmitButton;
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
		mDatabase = new DatabaseOpenHelper(getActivity(), DB_NAME).openDatabase();
		Item item = getRandomItem();
		item.setResponseScale(getItemResponseScale(item));
		
		TextView itemTextView = (TextView)getView().findViewById(R.id.surveyItemText);		
		itemTextView.setText(item.getText());		
		
		LinearLayout layout = (LinearLayout)getView().findViewById(R.id.responseOptionsLayout);
		layout.removeAllViews();
		for (int i = 0; i < item.getOptionCount(); ++i) {
			final String text = item.getOption(i).getText();
			Button button = new Button(getActivity());
			button.setText(text);
			button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mSubmitButton.setEnabled(true);
					Toast.makeText(getActivity(), "Pressed " + text, Toast.LENGTH_SHORT).show();					
				}
			});
			layout.addView(button);
		}
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
		
		mSubmitButton = (Button)view.findViewById(R.id.buttonSubmit);
		mSubmitButton.setEnabled(false);
		mSubmitButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Pressed Submit", Toast.LENGTH_SHORT).show();
			}
		});
		
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
	
	private Item getRandomItem() {
		Cursor cursor = mDatabase.rawQuery("SELECT _id, text FROM items ORDER BY RANDOM()", null);
		String itemText = null;
		int itemId = -1;
		if (cursor != null) {
			try {
				cursor.moveToFirst();
				do {
					itemText = cursor.getString(cursor.getColumnIndex("text"));
					itemId = cursor.getInt(cursor.getColumnIndex("_id"));
				} while (cursor.moveToNext());
			} finally {
				cursor.close();
			}
		}
		Log.i(TAG, "Retrieved item " + itemId + ": " + itemText);
		return new Item(Integer.toString(itemId), itemText);
	}
	
	private ResponseScale getItemResponseScale(Item item) {
		Cursor cursor = mDatabase.rawQuery("SELECT ro.text FROM response_options ro " +
			"JOIN response_scale_response_options rsro ON ro._id = rsro.response_option_id " +
			"JOIN items i ON i.response_scale_id = rsro.response_scale_id " +
			"WHERE i._id = ? ORDER BY rsro.sequence_number", new String[] {item.getId()});
		ResponseScale scale = new ResponseScale();
		if (cursor != null) {
			try {
				cursor.moveToFirst();
				do {
					scale.addOption(cursor.getString(cursor.getColumnIndex("text")));
				} while (cursor.moveToNext());
			} finally {
				cursor.close();
			}
		}
		return scale;
	}
}
