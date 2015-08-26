/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/7.
 */
package com.xiaodong.dream.catcher.demo.categories.dreamcatcher.express;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.express.myexpress.MyExpressFragment;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.express.search.SearchExpressFragment;
import com.xiaodong.dream.catcher.demo.image.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class ExpressFragment extends Fragment {
    private static String TAG = "ExpressFragment";

    private Activity mActivity;
    private View mRootView;
    private TextView mSearchTabView;
    private TextView mMyExpressTabView;
    private ViewPager mViewPager;
    private ExpressFragmentViewPagerAdapter expressFragmentViewPagerAdapter;

    private int mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT;

    private ImageView cursor;// animation picture

    private int offset = 0;// offset for animation
    private int bmpW;// width of animation picture


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.express_layout, container, false);

        initTabView();
        initImageView();
        initViewPager();

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                mViewPager.setCurrentItem(0);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return mRootView;
    }

    private void initTabView(){
        mSearchTabView = (TextView) mRootView.findViewById(R.id.express_search_tab);
        mMyExpressTabView = (TextView) mRootView.findViewById(R.id.express_my_tab);

        mSearchTabView.setSelected(true);
        mSearchTabView.setOnClickListener(new MyOnClickListener(0));
        mMyExpressTabView.setOnClickListener(new MyOnClickListener(1));
    }

    /**
     * init animation
     */
    private void initImageView() {
        cursor = (ImageView) mRootView.findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tab_indicator_bg)
                .getWidth();// get the width of animation picture
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// get width of screen
        offset = (screenW / 2 - bmpW) / 2;// offset
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// set the start position
    }

    private void initViewPager(){
        mViewPager = (ViewPager) mRootView.findViewById(R.id.express_main_container);

        Fragment mFragment1 = new SearchExpressFragment();
        Fragment mFragment2 = new MyExpressFragment();

        List<Fragment> mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(mFragment1);
        mFragmentList.add(mFragment2);
        expressFragmentViewPagerAdapter = new ExpressFragmentViewPagerAdapter(getChildFragmentManager(), mFragmentList);

        mViewPager.setAdapter(expressFragmentViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPagerChangeListener());
    }

    public class MyOnClickListener implements View.OnClickListener{
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            if(mViewPager != null)
                mViewPager.setCurrentItem(index);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.setCurrentItem(mSelectedItem);
    }

    public class ViewPagerChangeListener implements ViewPager.OnPageChangeListener{
        int one = offset * 2 + bmpW;// offset from first tab to second tab

        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageSelected(int i) {
            Animation animation = null;

            switch (i){
                case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                    mSearchTabView.setSelected(true);
                    mMyExpressTabView.setSelected(false);

                    switch (mSelectedItem){
                        case ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT:
                            animation = new TranslateAnimation(one, 0, 0, 0);
                            break;
                    }

                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT;

                    break;
                case ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT:
                    mSearchTabView.setSelected(false);
                    mMyExpressTabView.setSelected(true);

                    switch (mSelectedItem){
                        case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                            animation = new TranslateAnimation(0, one, 0, 0);
                            break;
                    }

                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT;

                    break;
            }

            if(animation != null){
                animation.setFillAfter(true);// True:set picture on the end of annimation
                animation.setDuration(300);
                cursor.startAnimation(animation);
            }else {
                Log.e(TAG, "animation is null");
            }


        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }
}
