package com.kentheken.survey;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kcordero on 6/30/2014.
 */
public class ItemEditFragment extends Fragment {
    private static final String TAG = "ItemEditFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_edit, parent, false);
    }

    public static ItemEditFragment newInstance() {
        Log.i(TAG, "newInstance");

        return new ItemEditFragment();
    }
}
