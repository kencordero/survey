package com.kentheken.survey;

import android.support.v4.app.Fragment;

/**
 * Created by kcordero on 6/30/2014.
 */
public class ItemEditActivity extends SingleFragmentActivity {
    protected Fragment createFragment() {
        return ItemEditFragment.newInstance();
    }
}
