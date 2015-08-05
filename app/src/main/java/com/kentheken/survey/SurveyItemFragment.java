package com.kentheken.survey;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kentheken.survey.models.Item;
import com.kentheken.survey.models.ResponseOption;
import com.kentheken.survey.models.Survey;

public class SurveyItemFragment extends Fragment {
    private static final String TAG = "SurveyItemFragment";
    private static final String EXTRA_ITEM_ID = "com.executiveboard.wsa.com.kentheken.survey.item_id";
    private static final String EXTRA_IS_FIRST = "com.executiveboard.wsa.com.kentheken.survey.is_first";
    private static final String EXTRA_IS_LAST = "com.executiveboard.wsa.com.kentheken.survey.is_last";
    private Button mSubmitButton;
    private Item mItem;
    private Callbacks mCallbacks;
    private boolean mIsFirst;
    private boolean mIsLast;
    
    public interface Callbacks {
        void onLoadItem(Item item);
        void onSubmit();
        void onPrevious();
        void onNext();
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
        
        RadioGroup layout = (RadioGroup)getView().findViewById(R.id.responseOptionsRadioGroup);
        layout.removeAllViews();
        for (int i = 0; i < mItem.getOptionCount(); ++i) {
            ResponseOption option = mItem.getOptions().get(i);
            RadioButton button = new RadioButton(getActivity());
            button.setTag(option);
            button.setText(option.getText());
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mItem.setResponse((ResponseOption)v.getTag());
                    mSubmitButton.setEnabled(true);
                }
            });
            layout.addView(button);
        }

        if (mItem.getResponse() != null) {
            for (int i = 0; i < layout.getChildCount(); ++i) {
                View v = layout.getChildAt(i);
                if (v.getTag().equals(mItem.getResponse())) {
                    v.performClick();
                    break;
                }
            }
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        
        int itemId = getArguments().getInt(EXTRA_ITEM_ID);
        mIsFirst = getArguments().getBoolean(EXTRA_IS_FIRST);
        mIsLast = getArguments().getBoolean(EXTRA_IS_LAST);
        mItem = Survey.get(getActivity()).getItem(itemId);
        mCallbacks.onLoadItem(mItem);        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_survey_item, parent, false);
        
        mSubmitButton = (Button)view.findViewById(R.id.buttonSubmit);
        mSubmitButton.setEnabled(false);
        if (mIsLast)
            mSubmitButton.setText(R.string.submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {            
            public void onClick(View v) {
                if (mIsLast) {
                    mCallbacks.onSubmit();
                    mSubmitButton.setEnabled(false);
                }
                else
                    mCallbacks.onNext();
            }
        });
        
        Button previousButton = (Button)view.findViewById(R.id.buttonPrevious);
        if (mIsFirst) {            
            previousButton.setEnabled(false);
            previousButton.setVisibility(View.INVISIBLE);
        } else {
            previousButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mCallbacks.onPrevious();
                }
                
            });
        }
        
        return view;
    }
    
    public static SurveyItemFragment newInstance(int itemId, boolean isFirst, boolean isLast) {
        Log.i(TAG, "newInstance");
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ITEM_ID, itemId);
        bundle.putBoolean(EXTRA_IS_FIRST, isFirst);
        bundle.putBoolean(EXTRA_IS_LAST, isLast);
        
        SurveyItemFragment fragment = new SurveyItemFragment();
        fragment.setArguments(bundle);
        
        return fragment;
    }
}
