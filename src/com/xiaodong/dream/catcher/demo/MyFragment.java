/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/10.
 */
package com.xiaodong.dream.catcher.demo;

import android.support.v4.app.Fragment;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class MyFragment extends Fragment{
    public interface OnSetMainTitleListener{
        public void onSetMainTitle(String string);

        public void onSetMainTitle(int resourceId);
    }
}
