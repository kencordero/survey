package com.executiveboard.wsa.survey;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.executiveboard.wsa.survey.models.Item;
import com.executiveboard.wsa.survey.models.Survey;

public class SurveyItemPagerActivity extends FragmentActivity implements SurveyItemFragment.Callbacks {
	private static final String TAG = "SurveyItemPagerActivity";
	private static final String DB_NAME = "survey.db3";
	
	private NonSwipeableViewPager mViewPager;
	private SurveyDatabaseHelper mHelper;
	private Survey mSurvey;
	private int mIdx;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
		setTitle();
				
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			public Fragment getItem(int idx) {				
				boolean isFirst = (idx == 0);
				boolean isLast = (idx == getCount() - 1);				
				String itemId = mSurvey.getItem(idx).getId();
				Log.i(TAG, "Load item #" + idx);
				return SurveyItemFragment.newInstance(itemId, isFirst, isLast);
			}

			public int getCount() {
				return mSurvey.getItemCount();				
			}
		});
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setTitle() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setTitle((mIdx+1) + " of " + mSurvey.getItemCount());
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
		mHelper.close();
	}

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        mHelper.getDb();
    }

	// Fragment callback interface methods
	@Override
	public void onLoadItem(Item item) {
		item.setResponseScale(mHelper.getItemResponseScale(item));		
	}

    @Override
    public void onSubmit() {
        mHelper.setSession();
        Toast.makeText(this, "Survey results submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
	public void onNext() {
		mViewPager.setCurrentItem(++mIdx, true);
		setTitle();
	}

	@Override
	public void onPrevious() {
		mViewPager.setCurrentItem(--mIdx, true);
		setTitle();
	}


}
