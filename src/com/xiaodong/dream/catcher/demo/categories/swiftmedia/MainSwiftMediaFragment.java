package com.xiaodong.dream.catcher.demo.categories.swiftmedia;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.xiaodong.dream.catcher.demo.Constants;
import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.home.AppContent;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.adapter.ContentsRecyclerViewAdapter;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.PosterItem;
import com.xiaodong.dream.catcher.demo.listener.OnPostAndRemoveRunnableListener;

import java.util.List;

/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainSwiftMediaFragment extends MyFragment implements LoaderManager.LoaderCallbacks<List<Object>>,
        SwipeRefreshLayout.OnRefreshListener {

    private Activity mActivity;
    private OnSetMainTitleListener onSetMainTitleListener;

    private RecyclerView mRecyclerView;
    private ContentsRecyclerViewAdapter mRecyclerViewAdapter;
    private FloatingActionButton mFabButton;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private List<AppContent> appContentList;
    private List<PosterItem> posterItemList;

    private Handler mMainHandler;

    private OnPostAndRemoveRunnableListener onPostAndRemoveRunnableListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        onSetMainTitleListener = (OnSetMainTitleListener) mActivity;

        onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_swiftmedia);

        Constants constants = Constants.getInstance();

        appContentList = constants.appContentList;
        posterItemList = constants.posterItemList;

        mMainHandler = new Handler();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_swiftmedia, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (mRootView.getId() == R.id.cardListView) {
            mRecyclerView = (RecyclerView) mRootView;
        } else {
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.cardListView);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new ContentsRecyclerViewAdapter(MainSwiftMediaFragment.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        onPostAndRemoveRunnableListener = (OnPostAndRemoveRunnableListener) mRecyclerViewAdapter;

        // Fab Button
        mFabButton = (FloatingActionButton) mRootView.findViewById(R.id.fab_swiftmedia);
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

        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.deleteContent();

        if (mMainHandler != null)
            mMainHandler.postDelayed(mRunable, 2000);
    }

    private void generateAboutThisAppSection() {
        if (mRecyclerViewAdapter != null) {
            if (posterItemList != null)
                mRecyclerViewAdapter.setHeader(posterItemList);

            if (appContentList != null)
                mRecyclerViewAdapter.addContent(appContentList);

        }
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mSwipeRefreshLayout.isRefreshing())
                onRefresh();

        }
    };

    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if (mProgressBar.getVisibility() == View.VISIBLE)
                mProgressBar.setVisibility(View.GONE);
            if (mRecyclerViewAdapter != null) {

                Animation fadeIn = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
                fadeIn.setDuration(500);
                mRecyclerView.setAnimation(fadeIn);

                generateAboutThisAppSection();

                mSwipeRefreshLayout.setRefreshing(false);

                mMainHandler.removeCallbacks(mRunable);

                if (onPostAndRemoveRunnableListener != null) {
                    onPostAndRemoveRunnableListener.onRemoveRunnable();
                    onPostAndRemoveRunnableListener.onPostRunnable();
                }
            }
        }
    };

    public void setSwipeRefreshEnabled(boolean refresh) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(refresh);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (onSetMainTitleListener != null)
            onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_swiftmedia);

        if (onPostAndRemoveRunnableListener != null){
            onPostAndRemoveRunnableListener.onRemoveRunnable();
            onPostAndRemoveRunnableListener.onPostRunnable();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (onPostAndRemoveRunnableListener != null){
            onPostAndRemoveRunnableListener.onRemoveRunnable();

            if (!hidden){
                onPostAndRemoveRunnableListener.onPostRunnable();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (onPostAndRemoveRunnableListener != null)
            onPostAndRemoveRunnableListener.onRemoveRunnable();
    }


}
