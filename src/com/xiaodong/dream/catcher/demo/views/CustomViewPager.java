package com.xiaodong.dream.catcher.demo.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Xiaodong on 2015/8/27.
 */
public class CustomViewPager extends ViewPager{
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean res = super.onInterceptTouchEvent(ev);
        float preX = 0f;
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            preX = ev.getX();
        } else {
            if( Math.abs(ev.getX() - preX)> 4 ) {
                return true;
            } else {
                preX = ev.getX();
            }
        }
        return res;
    }
}
