/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/6.
 */
package com.xiaodong.dream.catcher.demo.weibo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class WeiboFragment extends Fragment{

    private Activity mActivity;

    private MyFragment.OnSetMainTitleListener setMainTitleListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        setMainTitleListener = (MyFragment.OnSetMainTitleListener) mActivity;
        setMainTitleListener.onSetMainTitle(R.string.weibo_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(setMainTitleListener != null)
            setMainTitleListener.onSetMainTitle(R.string.weibo_title);
    }
}
