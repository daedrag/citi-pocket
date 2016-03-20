package com.d3.duy.citipocket.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by daoducduy0511 on 3/21/16.
 */
public class CustomViewPager extends ViewPager {
    private boolean pagingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPagingEnabled(boolean enabled) {
        pagingEnabled = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!pagingEnabled) {
            return false; // do not intercept
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!pagingEnabled) {
            return false; // do not consume
        }
        return super.onTouchEvent(event);
    }
}
