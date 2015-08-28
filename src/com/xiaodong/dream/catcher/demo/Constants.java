package com.xiaodong.dream.catcher.demo;

import android.content.Context;
import android.util.Log;

import com.xiaodong.dream.catcher.demo.categories.home.AppContent;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.PosterItem;

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

    public static List<PosterItem> posterItemList;

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
        setPosterList();

        return sInstance;
    }


    private static void setAppContentList(){

        if (appContentList == null){
            appContentList = new ArrayList<>();

        }else {
            appContentList.clear();

        }

        addItem(R.string.drawer_item_swiftsync, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
        addItem(R.string.drawer_item_swiftsms, R.string.app_swiftsync_author, R.string.about_swiftsms_description_text, R.string.app_swiftsms_version);
        addItem(R.string.drawer_item_storeshare, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_storeshare_version);
        addItem(R.string.drawer_item_swiftmedia, R.string.app_swiftsync_author, R.string.about_swiftsync_description_text, R.string.app_swiftsync_version);
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

    //net status
    public static final int NET_LINK_TYPE_ERROR = -1;
    public static final int NET_LINK_TYPE_MOBILE = 0;
    public static final int NET_LINK_TYPE_WIFI = 1;

    private static void setPosterList(){
        if (posterItemList == null){
            posterItemList = new ArrayList<>();

        }else {
            posterItemList.clear();

        }

        addPosterItem("Poster_0", "http://pic2.zhimg.com/4c3a3bbcabbfa08d4963e8718adf4911_b.png", 0);
        addPosterItem("Poster_1", "http://pic4.zhimg.com/7348a778db5c514b6b2ef646cad599d3_b.png", 1);
        addPosterItem("Poster_2", "http://pic3.zhimg.com/4e07045c91708d39c344b4830f557af2_b.png", 2);
        addPosterItem("Poster_3", "http://pic1.zhimg.com/034b217539995137e74c277189294f04_b.png", 3);
        addPosterItem("Poster_4", "http://pic4.zhimg.com/013baa01a05661180bf1ad537fc4abb7_b.png", 4);
    }

    private static void addPosterItem(String title, String posterPath, long productId){
        PosterItem posterItem = new PosterItem();
        posterItem.setTitle(title);
        posterItem.setPostPath(posterPath);
        posterItem.setProductId(productId);

        if (posterItemList != null)
            posterItemList.add(posterItem);
    }

}
