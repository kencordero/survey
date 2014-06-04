package com.executiveboard.wsa.survey;

import android.support.v4.app.Fragment;
import android.util.Log;

public class SurveyItemActivity extends SingleFragmentActivity {
	private static final String TAG = "SurveyItemActivity";
	
	@Override
	protected Fragment createFragment() {
		Log.i(TAG, "createFragment");
		return new SurveyItemFragment();
	}

}
