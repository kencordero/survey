package com.kentheken.survey;

import android.support.v4.app.Fragment;

import com.kentheken.survey.models.Item;

/**
 * Created by kcordero on 6/12/2014.
 */
public class ReportActivity extends SingleFragmentActivity implements ReportFragment.Callbacks {
    private static final String TAG = "ReportActivity";
    private static final String DB_NAME = "survey.db3";

    private DatabaseHelper mHelper;

    protected Fragment createFragment() {
        mHelper = new DatabaseHelper(this, DB_NAME);
        mHelper.openDatabase();
        return ReportFragment.newInstance();
    }

    @Override
    public Item onLoad() {
        Item item = mHelper.getRandomItem();
        item.setResponseScale(mHelper.getOptionStats(item));
        return item;
    }

    @Override
    public void onPause() {
        super.onPause();
        mHelper.close();
    }
}
