package com.xiaodong.dream.catcher.demo.categories.swiftsms;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;

/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainSwiftSMSFragment extends MyFragment {

    private Activity mActivity;
    private OnSetMainTitleListener onSetMainTitleListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        onSetMainTitleListener = (OnSetMainTitleListener) mActivity;

        onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_swiftsms);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (onSetMainTitleListener != null)
            onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_swiftsms);
    }
}
