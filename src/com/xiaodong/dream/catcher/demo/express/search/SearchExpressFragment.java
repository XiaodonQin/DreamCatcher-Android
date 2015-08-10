/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/10.
 */
package com.xiaodong.dream.catcher.demo.express.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.express.ExpressFragment;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class SearchExpressFragment extends Fragment implements ExpressFragment.onSelectedListener{
    private static String TAG = "SearchExpressFragment";

    private View mRootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, ">>onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, ">>onCreateView");

        mRootView = inflater.inflate(R.layout.express_search_layout, null);

        return mRootView;
    }

    @Override
    public void onSelected(int selectedIndex) {

    }
}
