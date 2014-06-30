package com.executiveboard.wsa.survey;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.executiveboard.wsa.survey.models.Item;
import com.executiveboard.wsa.survey.models.ResponseOption;

/**
 * Created by kcordero on 6/12/2014.
 */
public class ReportFragment extends Fragment {
    private static final String TAG = "ReportFragment";
    private static final String EXTRA_ITEM_ID = "com.executiveboard.wsa.survey.item_id";

    private Callbacks mCallbacks;
    private Item mItem;

    public interface Callbacks {
        Item onLoad();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, parent, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        mItem = mCallbacks.onLoad();
        // TODO bind data to views

        TextView itemTextView = (TextView)getView().findViewById(R.id.itemText);
        itemTextView.setText(mItem.getText());

        LinearLayout textLayout = (LinearLayout)getView().findViewById(R.id.layout_options_text);
        LinearLayout statLayout = (LinearLayout)getView().findViewById(R.id.layout_options_stats);

        for (int i = 0; i < mItem.getOptionCount(); ++i) {
            ResponseOption option = mItem.getOptions().get(i);
            TextView textView = new TextView(getActivity());
            TextView statView = new TextView(getActivity());
            textView.setText(option.getText());
            textView.setGravity(Gravity.RIGHT);
            textLayout.addView(textView);

            statView.setText(Integer.toString(option.getCount()));
            statView.setGravity(Gravity.CENTER_HORIZONTAL);
            statLayout.addView(statView);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        //String itemId = getArguments().getString(EXTRA_ITEM_ID);
        //mItem = Survey.get(getActivity()).getItem(itemId);
    }

    public static ReportFragment newInstance() {
        Log.i(TAG, "newInstance");

        return new ReportFragment();
    }

}
