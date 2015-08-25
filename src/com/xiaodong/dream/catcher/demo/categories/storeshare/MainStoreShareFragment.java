package com.xiaodong.dream.catcher.demo.categories.storeshare;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.listener.OnSetUpCustomUIListener;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.storeshare.adapter.ViewPagerAdapter;

import java.util.logging.Handler;

/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainStoreShareFragment extends MyFragment {
    private static String TAG = "MainStoreShareFragment";

    private Activity mActivity;
    private OnSetMainTitleListener onSetMainTitleListener;
    private OnSetUpCustomUIListener onSetUpCustomUIListener;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter mViewPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, ">>onCreate");

        super.onCreate(savedInstanceState);

        mActivity = getActivity();

        onSetMainTitleListener = (OnSetMainTitleListener) mActivity;
        onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_storeshare);

        onSetUpCustomUIListener = (OnSetUpCustomUIListener) mActivity;
        onSetUpCustomUIListener.onDeleteToolbarElevation();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, ">>onCreateView");

        View view = inflater.inflate(R.layout.fragment_storeshare, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_storeshare);
        viewPager = (ViewPager) view.findViewById(R.id.tab_view_pager_storeshare);

        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mActivity);
        setupViewPager();

        setupTab(savedInstanceState);

        return view;
    }


    private void setupTab(final Bundle savedInstanceState){
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }


    private void setupViewPager(){
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (onSetMainTitleListener != null)
            onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_storeshare);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, ">>onDestroy");

        super.onDestroy();

        if (onSetUpCustomUIListener != null)
            onSetUpCustomUIListener.onRestoreToolbarElevation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, ">>onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
