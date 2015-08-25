package com.xiaodong.dream.catcher.demo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.xiaodong.dream.catcher.demo.categories.home.AppContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/23.
 */
public class Constants {
    private static String TAG = "Constants";

    private static Constants sInstance;
    private static boolean sInitialed = false;
    private static Context mContext;

    public static List<AppContent> appContentList;

    public static void init(Context context){
        mContext = context;

        if(sInitialed){
            return;
        }
        sInitialed = true;
    }

    public static Constants getInstance(){
        if (!sInitialed){
            if (BuildConfig.DEBUG){
                Log.v(TAG, "Should call init() first");
            }
            return null;
        }

        if (sInstance == null){
            synchronized (Constants.class){
                if (sInstance == null){
                    sInstance = new Constants();
                }
            }
        }

        setAppContentList();

        return sInstance;
    }


    private static void setAppContentList(){

        if (appContentList == null){
            appContentList = new ArrayList<>();

        }else {
            appContentList.clear();

        }

        addItem(R.string.drawer_item_dreamcatcher, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsms, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
    }

    private static void addItem(int appNameId, int authorId, int descriptionId, int versionId) {
        AppContent appContent = new AppContent();

        if (mContext == null)
            return;
        appContent.setAppName(mContext.getResources().getString(appNameId));
        appContent.setAuthor(mContext.getResources().getString(authorId));
        appContent.setDescription(mContext.getResources().getString(descriptionId));
        appContent.setVersion(mContext.getResources().getString(versionId));

        if (appContentList != null)
            appContentList.add(appContent);
    }

}
