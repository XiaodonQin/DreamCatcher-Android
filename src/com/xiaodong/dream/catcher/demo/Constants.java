package com.xiaodong.dream.catcher.demo;

import android.content.Context;
import android.util.Log;

import com.xiaodong.dream.catcher.demo.categories.home.AppContent;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaCluster;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaItem;
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

    public static List<MediaItem> mediaItemList;
    public static List<MediaCluster> mediaClusterList;

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
        setMediaClusterList();

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

    private static void setMediaItemList(){
        if (mediaItemList == null){
            mediaItemList = new ArrayList<>();

        }else {
            mediaItemList.clear();

        }

        addMediaItem("Angelababy", "http://img0.bdstatic.com/img/image/6446027056db8afa73b23eaf953dadde1410240902.jpg");
        addMediaItem("Wallace Chung", "http://img0.bdstatic.com/img/image/df7a40feb5204a11ca1d00b75d415c561409125665.jpg");
        addMediaItem("Fan Bingbing", "http://img0.bdstatic.com/img/image/2043d07892fc42f2350bebb36c4b72ce1409212020.jpg");
        addMediaItem("Tiffany Tang", "http://img0.bdstatic.com/img/image/c6774aeee9cc323de700edcf4f2a40781409804177.jpg");
        addMediaItem("Jay Chou", "http://img0.bdstatic.com/img/image/a033770a5012f2720fc0f0fa17b6323d1408435351.jpg");
        addMediaItem("Avril Lavigne", "http://img0.bdstatic.com/img/image/98ea1d728b7fc03b6e025f4fe7ece8401409805181.jpg");
        addMediaItem("Lim YoonA", "http://img0.bdstatic.com/img/image/cabf92e62f1ad8be41844550b90cf0681409304953.jpg");
        addMediaItem("Hawick Lau", "http://img0.bdstatic.com/img/image/339c36f64e73e791ab3d312a4c090f9f1409801535.jpg");
        addMediaItem("LUHAN", "http://img0.bdstatic.com/img/image/a93a29d92270b9a3e3e78b6f5b472e1a1408334562.jpg");

    }

    private static void addMediaItem(String name, String thumbnailPath){
        MediaItem mediaItem = new MediaItem();
        mediaItem.setName(name);
        mediaItem.setThumbnailPath(thumbnailPath);

        if (mediaItemList != null)
            mediaItemList.add(mediaItem);
    }

    private static void setMediaClusterList(){
        setMediaItemList();

        if (mediaClusterList == null){
            mediaClusterList = new ArrayList<>();

        }else {
            mediaClusterList.clear();

        }

        addMediaCluster("Star");
        addMediaCluster("Music");
        addMediaCluster("Movie");
        addMediaCluster("Star");
        addMediaCluster("Music");
        addMediaCluster("Movie");
        addMediaCluster("Star");
        addMediaCluster("Music");
        addMediaCluster("Movie");

    }

    private static void addMediaCluster(String categoryName){
        MediaCluster mediaCluster = new MediaCluster();

        mediaCluster.setCategoryName(categoryName);

        if (mediaItemList != null && mediaItemList.size() > 0){
            mediaCluster.setMediaItemList(mediaItemList);

        }else {
            Log.e(TAG, ">>mediaItemList == null OR mediaItemList.size() == 0");
        }

        if (mediaClusterList != null)
            mediaClusterList.add(mediaCluster);
    }

}
