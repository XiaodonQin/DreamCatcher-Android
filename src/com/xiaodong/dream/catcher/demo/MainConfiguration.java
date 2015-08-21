package com.xiaodong.dream.catcher.demo;

import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.MainDreamCatcherFragment;
import com.xiaodong.dream.catcher.demo.categories.escape.MainEscapeFragment;
import com.xiaodong.dream.catcher.demo.categories.home.MainHomeFragment;
import com.xiaodong.dream.catcher.demo.settings.MainSettingsFragment;
import com.xiaodong.dream.catcher.demo.categories.storeshare.MainStoreShareFragment;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.MainSwiftMediaFragment;
import com.xiaodong.dream.catcher.demo.categories.swiftsms.MainSwiftSMSFragment;
import com.xiaodong.dream.catcher.demo.categories.swiftsync.MainSwiftSyncFragment;
import com.xiaodong.dream.catcher.demo.categories.viewstream.MainViewstreamFragment;

import java.util.HashMap;

/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainConfiguration {

    /**
     * find a detail fragment in this map by drawer item identifier
     */
    public static HashMap<Integer, String> mFragmentMap;

    static {
        mFragmentMap = new HashMap<>();
        addItem(1, MainHomeFragment.class.getName());
        addItem(2, MainSwiftSyncFragment.class.getName());
        addItem(3, MainStoreShareFragment.class.getName());
        addItem(4, MainSwiftMediaFragment.class.getName());
        addItem(5, MainViewstreamFragment.class.getName());
        addItem(6, MainEscapeFragment.class.getName());
        addItem(7, MainSwiftSMSFragment.class.getName());
        addItem(8, MainDreamCatcherFragment.class.getName());
        addItem(9, MainSettingsFragment.class.getName());
    }

    public static void addItem(Integer drawerItemIdentifier, String fragmentClassName){
        if(mFragmentMap != null)
            mFragmentMap.put(drawerItemIdentifier, fragmentClassName);
    }

    public static String getFragmentClassName(Integer drawerItemIdentifier){
        return mFragmentMap.get(drawerItemIdentifier);
    }


}
