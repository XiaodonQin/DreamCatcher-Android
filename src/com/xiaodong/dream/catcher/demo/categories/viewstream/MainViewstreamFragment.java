package com.xiaodong.dream.catcher.demo.categories.viewstream;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaodong.dream.catcher.demo.Constants;
import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.adapter.PosterViewPagerAdapter;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaCluster;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.PosterItem;
import com.xiaodong.dream.catcher.demo.categories.viewstream.adapter.ContentsListViewAdapter;
import com.xiaodong.dream.catcher.demo.image.ImageUtils;
import com.xiaodong.dream.catcher.demo.views.CustomViewPager;

import java.util.List;

/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainViewstreamFragment extends MyFragment implements LoaderManager.LoaderCallbacks<List<Object>>,
        SwipeRefreshLayout.OnRefreshListener{
    private static String TAG = "MainViewstreamFragment";

    private Activity mActivity;
    private OnSetMainTitleListener onSetMainTitleListener;

    private FloatingActionButton mFabButton;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private ProgressBar mProgressBar;

    private List<PosterItem> posterItemList;
    private List<MediaCluster> mediaClusterList;

    private Handler mMainHandler;

    private ContentsListViewAdapter mContentsListViewAdapter;
    private View mHeaderView;
    private CustomViewPager mViewPager;
    private CirclePageIndicator mCirclePageIndicator;
    private PosterViewPagerAdapter mPosterViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        onSetMainTitleListener = (OnSetMainTitleListener) mActivity;

        onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_viewstream);

        Constants constants = Constants.getInstance();

        posterItemList = constants.posterItemList;
        mediaClusterList = constants.mediaClusterList;

        mMainHandler = new Handler();

        mContentsListViewAdapter = new ContentsListViewAdapter(MainViewstreamFragment.this);
        mPosterViewPagerAdapter = new PosterViewPagerAdapter(getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_viewstream, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView) mRootView.findViewById(R.id.listView);

        mHeaderView = inflater.inflate(R.layout.list_header_viewstream, container, false);
        mViewPager = (CustomViewPager) mHeaderView.findViewById(R.id.viewpager);

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        int width = 0;
        if (ImageUtils.hasIcecreamSandwich()){
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        }else{
            width = display.getWidth();
        }

        //            width*9/16
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, width * 328 / 600, Gravity.CENTER);
        mViewPager.setLayoutParams(layoutParams);

        mCirclePageIndicator = (CirclePageIndicator) mHeaderView.findViewById(R.id.viewpagerindicator);

        mViewPager.setAdapter(mPosterViewPagerAdapter);
        mCirclePageIndicator.setViewPager(mViewPager);
        mCirclePageIndicator.setOnPageChangeListener(onPageChangeListener);

        mListView.setAdapter(mContentsListViewAdapter);

        // Fab Button
        mFabButton = (FloatingActionButton) mRootView.findViewById(R.id.fab_viewstream);
        mFabButton.setImageDrawable(new IconicsDrawable(mActivity, GoogleMaterial.Icon.gmd_refresh).color(Color.WHITE).actionBar());
        mFabButton.setOnClickListener(fabClickListener);

        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progressBar);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mMainHandler != null)
            mMainHandler.postDelayed(mRunable, 2000);

    }

    @Override
    public Loader<List<Object>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Object>> loader, List<Object> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Object>> loader) {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        if (mPosterViewPagerAdapter != null){
            mListView.removeHeaderView(mHeaderView);
            mPosterViewPagerAdapter.addPosterItems(null);
        }

        if (mContentsListViewAdapter != null)
            mContentsListViewAdapter.deleteContent();

        if (mMainHandler != null)
            mMainHandler.postDelayed(mRunable, 2000);
    }

    private void generateAboutThisAppSection() {
        if (mPosterViewPagerAdapter != null && mContentsListViewAdapter != null) {
            if (mediaClusterList != null && mediaClusterList.size() > 0){
                mListView.addHeaderView(mHeaderView);
                mPosterViewPagerAdapter.addPosterItems(posterItemList);
                mContentsListViewAdapter.addContent(mediaClusterList);
            }else {
                Log.e(TAG, ">>>>mediaClusterList == null OR mediaClusterList.size() == 0");
            }

        }
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mSwipeRefreshLayout.isRefreshing())
                onRefresh();

        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if (mProgressBar.getVisibility() == View.VISIBLE)
                mProgressBar.setVisibility(View.GONE);
            if (mContentsListViewAdapter != null) {

                Animation fadeIn = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
                fadeIn.setDuration(500);
                mListView.setAnimation(fadeIn);

                generateAboutThisAppSection();

                mSwipeRefreshLayout.setRefreshing(false);

                mMainHandler.removeCallbacks(mRunable);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        if (onSetMainTitleListener != null)
            onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_viewstream);
    }
}
