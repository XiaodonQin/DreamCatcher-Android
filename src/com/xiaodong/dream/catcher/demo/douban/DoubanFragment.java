/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/6.
 */
package com.xiaodong.dream.catcher.demo.douban;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class DoubanFragment extends MyFragment {

    private Activity mActivity;

    private OnSetMainTitleListener setMainTitleListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        setMainTitleListener = (OnSetMainTitleListener) mActivity;
        setMainTitleListener.onSetMainTitle(R.string.douban_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (setMainTitleListener != null)
            setMainTitleListener.onSetMainTitle(R.string.douban_title);

    }
}
