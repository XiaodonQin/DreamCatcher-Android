/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/7.
 */
package com.xiaodong.dream.catcher.demo.express;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.express.myexpress.MyExpressFragment;
import com.xiaodong.dream.catcher.demo.express.search.SearchExpressFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class ExpressFragment extends MyFragment {
    private static String TAG = "ExpressFragment";

    private Activity mActivity;
    private View mRootView;
    private TextView mSearchTabView;
    private TextView mMyExpressTabView;
    private ViewPager mViewPager;
    private ExpressFragmentViewPagerAdapter expressFragmentViewPagerAdapter;

    private int mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT;

    private OnSetMainTitleListener setMainTitleListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, ">>onCreate");

        mActivity = getActivity();
        setMainTitleListener = (OnSetMainTitleListener) mActivity;
        setMainTitleListener.onSetMainTitle(R.string.express_title);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, ">>onCreateView");

        mRootView = inflater.inflate(R.layout.express_layout, container, false);
        initView();

        return mRootView;
    }

    private void initView(){
        mSearchTabView = (TextView) mRootView.findViewById(R.id.express_search_tab);
        mMyExpressTabView = (TextView) mRootView.findViewById(R.id.express_my_tab);

        mSearchTabView.setSelected(true);
        mSearchTabView.setOnClickListener(tabClickListener);
        mMyExpressTabView.setOnClickListener(tabClickListener);

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

    View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.express_search_tab:
                    mSearchTabView.setSelected(true);
                    mMyExpressTabView.setSelected(false);
                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT;
                    mViewPager.setCurrentItem(mSelectedItem);
                    break;
                case R.id.express_my_tab:
                    mSearchTabView.setSelected(false);
                    mMyExpressTabView.setSelected(true);
                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT;
                    mViewPager.setCurrentItem(mSelectedItem);
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.setCurrentItem(mSelectedItem);

        if(setMainTitleListener != null)
            setMainTitleListener.onSetMainTitle(R.string.express_title);

    }

    public class ViewPagerChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int i, float v, int i2) {
            Log.i(TAG, ">>onPageScrolled");

        }

        @Override
        public void onPageSelected(int i) {
            Log.i(TAG, ">>onPageSelected");

            switch (i){
                case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                    mSearchTabView.setSelected(true);
                    mMyExpressTabView.setSelected(false);
                    break;
                case ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT:
                    mSearchTabView.setSelected(false);
                    mMyExpressTabView.setSelected(true);
                    break;
            }

            mSelectedItem = i;

            Fragment mFragment = expressFragmentViewPagerAdapter.getItem(i);

            if(mFragment != null && mFragment instanceof onSelectedListener){
                Log.i(TAG, "mFragment: " + mFragment.toString());
                ((onSelectedListener) mFragment).onSelected(i);
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {
            Log.i(TAG, ">>onPageScrollStateChanged");

        }
    }

    public static interface onSelectedListener{
        public void onSelected(int selectedIndex);
    }
}
