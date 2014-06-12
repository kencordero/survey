package com.executiveboard.wsa.survey;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(Context context) {
        super(context);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent me) {
        return false;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return false;
    }

}
