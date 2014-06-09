package com.executiveboard.wsa.survey;

import android.app.Activity;
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
import com.executiveboard.wsa.survey.models.Survey;

public class SurveyItemFragment extends Fragment {
	private static final String TAG = "SurveyItemFragment";
	private static final String EXTRA_ITEM_ID = "com.executiveboard.wsa.survey.item_id";
	private Button mSubmitButton;
	private Item mItem;
	private Callbacks mCallbacks;
	
	public interface Callbacks {
		void onLoadItem(Item item);
		void onSubmit();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "onAttach");
		mCallbacks = (Callbacks)activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		Log.i(TAG, "onDetach");
		mCallbacks = null;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
				
		TextView itemTextView = (TextView)getView().findViewById(R.id.surveyItemText);		
		itemTextView.setText(mItem.getText());		
		
		LinearLayout layout = (LinearLayout)getView().findViewById(R.id.responseOptionsLayout);
		layout.removeAllViews();
		for (int i = 0; i < mItem.getOptionCount(); ++i) {
			final String text = mItem.getOption(i).getText();
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		String itemId = getArguments().getString(EXTRA_ITEM_ID);
		mItem = Survey.get(getActivity()).getItem(itemId);
		mCallbacks.onLoadItem(mItem);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_survey_item, parent, false);
		
		mSubmitButton = (Button)view.findViewById(R.id.buttonSubmit);
		mSubmitButton.setEnabled(false);
		mSubmitButton.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				mCallbacks.onSubmit();
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
}
