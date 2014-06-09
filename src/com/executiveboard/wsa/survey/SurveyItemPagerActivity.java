package com.executiveboard.wsa.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.executiveboard.wsa.survey.models.Item;
import com.executiveboard.wsa.survey.models.Survey;

public class SurveyItemPagerActivity extends FragmentActivity implements SurveyItemFragment.Callbacks {
	private static final String TAG = "SurveyItemPagerActivity";
	private static final String DB_NAME = "survey.db3";
	
	private NonSwipeableViewPager mViewPager;
	private SurveyDatabaseHelper mHelper;
	private Survey mSurvey;
	private int mIdx;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		mViewPager = new NonSwipeableViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mHelper = new SurveyDatabaseHelper(this, DB_NAME);
		mHelper.openDatabase();
		
		mSurvey = mHelper.getSurvey();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			public Fragment getItem(int idx) {
				String itemId = mSurvey.getItem(idx).getId();
				mIdx = idx;
				return SurveyItemFragment.newInstance(itemId);
			}

			public int getCount() {
				return mSurvey.getItemCount();				
			}
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
		mHelper.close();
	}

	@Override
	public void onLoadItem(Item item) {
		item.setResponseScale(mHelper.getItemResponseScale(item));		
	}

	@Override
	public void onSubmit() {
		mViewPager.setCurrentItem(mIdx++, true);		
	}
}
