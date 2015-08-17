/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/10.
 */
package com.xiaodong.dream.catcher.demo.express.myexpress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaodong.dream.catcher.demo.express.search.OnSearchRecordListener;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class MyExpressFragment extends Fragment implements OnSearchRecordListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onRecord(String postId, String type) {

    }
}
