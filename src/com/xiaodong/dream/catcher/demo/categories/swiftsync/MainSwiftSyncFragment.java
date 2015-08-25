package com.xiaodong.dream.catcher.demo.categories.swiftsync;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.xiaodong.dream.catcher.demo.Constants;
import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.home.AppContent;

import java.util.List;
/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainSwiftSyncFragment extends MyFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static String TAG = "MainSwiftSyncFragment";

    private Activity mActivity;
    private OnSetMainTitleListener setMainTitleListener;

    private static List<AppContent> appContentList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFabButton;

    private AppsListViewAdapter mAppsListViewAdapter;

    private Handler mMainHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        setMainTitleListener = (OnSetMainTitleListener) mActivity;

        setMainTitleListener.onSetMainTitle(R.string.drawer_item_swiftsync);

        Constants constants = Constants.getInstance();
        appContentList = constants.appContentList;

        mMainHandler = new Handler();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swiftsync, container, false);

//        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView) view.findViewById(R.id.listView);
        if (appContentList != null && appContentList.size() > 0) {
            Log.i(TAG, ">>appContentList != null && appContentList.size() > 0");
            mAppsListViewAdapter = new AppsListViewAdapter(mActivity);
            Animation fadeIn = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
            fadeIn.setDuration(500);
            mListView.setAnimation(fadeIn);
            mListView.setAdapter(mAppsListViewAdapter);

            mAppsListViewAdapter.addContent(appContentList);
        }

        mListView.setOnScrollListener(onScrollListener);

        // Fab Button
        mFabButton = (FloatingActionButton) view.findViewById(R.id.fab_normal);
        mFabButton.setImageDrawable(new IconicsDrawable(mActivity, GoogleMaterial.Icon.gmd_refresh).color(Color.WHITE).actionBar());
        mFabButton.setOnClickListener(fabClickListener);

        return view;
    }


    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState){
                case SCROLL_STATE_IDLE:
                    mFabButton.setVisibility(View.VISIBLE);
                    if(mListView != null && mListView.getFirstVisiblePosition() != 0){
                        mFabButton.setImageDrawable(new IconicsDrawable(mActivity, GoogleMaterial.Icon.gmd_vertical_align_top).color(Color.WHITE).actionBar());
                    }else {
                        mFabButton.setImageDrawable(new IconicsDrawable(mActivity, GoogleMaterial.Icon.gmd_refresh).color(Color.WHITE).actionBar());
                    }
                    break;
                case SCROLL_STATE_FLING:
                case SCROLL_STATE_TOUCH_SCROLL:
                    mFabButton.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    /**
     * sample onClickListener with to-top as action
     */
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
//            alert.setMessage("FloatingActionButton (FAB)");
//            alert.create().show();

            if (mListView != null && mListView.getFirstVisiblePosition() != 0){
                mAppsListViewAdapter.notifyDataSetChanged();
                Animation fadeIn = AnimationUtils.loadAnimation(mActivity, android.R.anim.fade_in);
                fadeIn.setDuration(500);
                mListView.setAnimation(fadeIn);
                mListView.setSelection(0);
                mFabButton.setVisibility(View.VISIBLE);
                mFabButton.setImageDrawable(new IconicsDrawable(mActivity, GoogleMaterial.Icon.gmd_refresh).color(Color.WHITE).actionBar());
            }else {
                onRefresh();
            }
        }
    };

    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if (mAppsListViewAdapter != null){

                Animation fadeIn = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
                fadeIn.setDuration(500);
                mListView.setAnimation(fadeIn);
                mAppsListViewAdapter.addContent(appContentList);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        if (setMainTitleListener != null)
            setMainTitleListener.onSetMainTitle(R.string.drawer_item_swiftsync);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, ">>onRefresh");
        mSwipeRefreshLayout.setRefreshing(true);

        if (mAppsListViewAdapter != null)
            mAppsListViewAdapter.deleteContent();

        if (mMainHandler != null)
            mMainHandler.postDelayed(mRunable, 2000);

    }

}
