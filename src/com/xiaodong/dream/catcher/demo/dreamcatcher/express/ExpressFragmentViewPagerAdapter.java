/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/10.
 */
package com.xiaodong.dream.catcher.demo.dreamcatcher.express;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.xiaodong.dream.catcher.demo.dreamcatcher.express.myexpress.MyExpressFragment;
import com.xiaodong.dream.catcher.demo.dreamcatcher.express.search.SearchExpressFragment;

import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class ExpressFragmentViewPagerAdapter extends FragmentPagerAdapter{

    public static final int ITEM_SEARCH_FRAGMENT = 0;
    public static final int ITEM_MY_EXPRESS_FRAGMENT = 1;

    private List<Fragment> mFragmentList;

    public ExpressFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment mFragment = null;

        switch (i){
            case ITEM_SEARCH_FRAGMENT:
                mFragment = new SearchExpressFragment();

                return mFragment;
            case ITEM_MY_EXPRESS_FRAGMENT:
                mFragment = new MyExpressFragment();

                return mFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
//        mFragmentList.set(position, null);
    }

}
