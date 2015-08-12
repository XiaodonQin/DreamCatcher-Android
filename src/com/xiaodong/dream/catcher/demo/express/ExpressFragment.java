/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/7.
 */
package com.xiaodong.dream.catcher.demo.express;

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

    private ImageView cursor;// 动画图片

    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度


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

        InitImageView();

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

    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) mRootView.findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tab_indicator_bg)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();

        Log.i(TAG, ">>mSelectedItem:" + mSelectedItem);

        switch (mSelectedItem){
            case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                matrix.postTranslate(offset, 0);
                break;

            case ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT:
                matrix.postTranslate(offset + screenW/2, 0);
                break;

        }
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    View.OnClickListener tabClickListener = new View.OnClickListener() {
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量

        @Override
        public void onClick(View v) {

            Animation animation = null;

            switch (v.getId()){
                case R.id.express_search_tab:
                    mSearchTabView.setSelected(true);
                    mMyExpressTabView.setSelected(false);

                    Log.i(TAG, ">>mSelectedItem:" + mSelectedItem);

                    switch (mSelectedItem){
                        case ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT:
                            animation = new TranslateAnimation(one, 0, 0, 0);
                            break;
                    }

                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT;
                    mViewPager.setCurrentItem(mSelectedItem);
                    break;
                case R.id.express_my_tab:
                    mSearchTabView.setSelected(false);
                    mMyExpressTabView.setSelected(true);

                    Log.i(TAG, ">>mSelectedItem:" + mSelectedItem);


                    switch (mSelectedItem){
                        case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                            animation = new TranslateAnimation(0, one, 0, 0);
                            break;
                    }

                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT;
                    mViewPager.setCurrentItem(mSelectedItem);
                    break;
            }

            if(animation != null){
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                cursor.startAnimation(animation);
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
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量

        @Override
        public void onPageScrolled(int i, float v, int i2) {
            Log.i(TAG, ">>onPageScrolled");

        }

        @Override
        public void onPageSelected(int i) {
            Log.e(TAG, ">>onPageSelected: " + i);

            Animation animation = null;

            switch (i){
                case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                    mSearchTabView.setSelected(true);
                    mMyExpressTabView.setSelected(false);

                    Log.i(TAG, ">>mSelectedItem:" + mSelectedItem);

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

                    Log.i(TAG, ">>mSelectedItem:" + mSelectedItem);

                    switch (mSelectedItem){
                        case ExpressFragmentViewPagerAdapter.ITEM_SEARCH_FRAGMENT:
                            animation = new TranslateAnimation(0, one, 0, 0);
                            break;
                    }

                    mSelectedItem = ExpressFragmentViewPagerAdapter.ITEM_MY_EXPRESS_FRAGMENT;


                    break;
            }

            if(animation != null){
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                cursor.startAnimation(animation);
            }else {
                Log.e(TAG, "animation is null");
            }


        }

        @Override
        public void onPageScrollStateChanged(int i) {
            Log.i(TAG, ">>onPageScrollStateChanged");

        }
    }
}
