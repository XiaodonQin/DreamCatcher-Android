package com.xiaodong.dream.catcher.demo;

import com.xiaodong.dream.catcher.demo.dreamcatcher.MainDreamCatcherFragment;
import com.xiaodong.dream.catcher.demo.escape.MainEscapeFragment;
import com.xiaodong.dream.catcher.demo.settings.MainSettingsFragment;
import com.xiaodong.dream.catcher.demo.storeshare.MainStoreShareFragment;
import com.xiaodong.dream.catcher.demo.swiftmedia.MainSwiftMediaFragment;
import com.xiaodong.dream.catcher.demo.swiftsms.MainSwiftSMSFragment;
import com.xiaodong.dream.catcher.demo.swiftsync.MainSwiftSyncFragment;
import com.xiaodong.dream.catcher.demo.viewstream.MainViewstreamFragment;

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
        addItem(1, MainSwiftSyncFragment.class.getName());
        addItem(2, MainStoreShareFragment.class.getName());
        addItem(3, MainSwiftMediaFragment.class.getName());
        addItem(4, MainViewstreamFragment.class.getName());
        addItem(5, MainEscapeFragment.class.getName());
        addItem(6, MainSwiftSMSFragment.class.getName());
        addItem(7, MainDreamCatcherFragment.class.getName());
        addItem(8, MainSettingsFragment.class.getName());
    }

    public static void addItem(Integer drawerItemIdentifier, String fragmentClassName){
        if(mFragmentMap != null)
            mFragmentMap.put(drawerItemIdentifier, fragmentClassName);
    }

    public static String getFragmentClassName(Integer drawerItemIdentifier){
        return mFragmentMap.get(drawerItemIdentifier);
    }


}
