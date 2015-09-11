package com.xiaodong.dream.catcher.demo.manager;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mikepenz.materialdrawer.util.UIUtils;
import com.xiaodong.dream.catcher.demo.BuildConfig;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.image.ImageCache;
import com.xiaodong.dream.catcher.demo.image.ImageFetcher;
import com.xiaodong.dream.catcher.demo.image.ImageUtils;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class LocalCacheManager {
    private static String TAG = "LocalCacheManager";

    private static Application mContext;
    private static LocalCacheManager sInstance;
    private static boolean sInitialed = false;

    private static String THUMBNAIL_CACHE_DIR = "ThumbnailCacheDir";
    private static String THUMBNAIL_HTTP_CACHE_DIR = "ThumbnailHttpCacheDir";
    private static String POSTER_CACHE_DIR = "PosterCacheDir";
    private static String POSTER_HTTP_CACHE_DIR = "PosterHttpCacheDir";

    private ImageFetcher mThumbnailImageFetcher;
    private ImageCache mThumbnailImageCache;

    private ImageFetcher mPosterImageFetcher;
    private ImageCache mPosterImageCache;

    public LocalCacheManager() {
        super();
    }

    public static void init(Application context) {
        mContext = context;

        if (sInitialed) {
            return;
        }

        sInitialed = true;
    }

    public static LocalCacheManager getInstance() {
        if (!sInitialed) {
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "Should call init() first");
            }
            return null;
        }

        if (sInstance == null) {
            synchronized (LocalCacheManager.class) {
                if (sInstance == null) {
                    sInstance = new LocalCacheManager();
                }
            }
        }

        return sInstance;
    }


    public void initImageFetcher(Context context, FragmentManager fragmentManager) {

        initThumbnailImageFetcherAndCache(context, fragmentManager);

        initPosterImageFetcherAndCache(context, fragmentManager);

    }

    synchronized
    public void initThumbnailImageFetcherAndCache(Context context, FragmentManager fragmentManager) {

        if (mThumbnailImageFetcher == null) {
            Resources resources = context.getResources();
            int width = Math.round(resources.getDimension(R.dimen.item_thumbnail_width));
            int height = Math.round(resources.getDimension(R.dimen.item_thumbnail_height));

            mThumbnailImageFetcher = new ImageFetcher(context, width, height, THUMBNAIL_HTTP_CACHE_DIR);
            mThumbnailImageFetcher.setImageFadeIn(true);
            mThumbnailImageFetcher.setLoadingImage(ImageFetcher.decodeSampledBitmapFromResource(resources, R.drawable.default_thumbnail, width, height));
        } else {
            mThumbnailImageFetcher.setExitTasksEarly(false);
        }

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(context, THUMBNAIL_CACHE_DIR);

        //default memory cache size of 3MB is enough.
        // each 56-56 size is: 56*56*8 = 0.024MB. 3MB will cache 125 bitmaps
//		cacheParams.memCacheSize = 1024*1024*3;
        cacheParams.setMemCacheSizePercent(context, 0.20f);
        mThumbnailImageFetcher.addImageCache(fragmentManager, cacheParams);

        mThumbnailImageCache = mThumbnailImageFetcher.getImageCache();
        if (BuildConfig.DEBUG)
            Log.v(TAG, "success init thumbnail image cache: " + mThumbnailImageCache);
    }

    synchronized
    public void initPosterImageFetcherAndCache(Context context, FragmentManager fragmentManager) {
        if (mPosterImageFetcher == null) {
            Resources resources = context.getResources();
            DisplayMetrics display = context.getResources().getDisplayMetrics();
            int width = 0;
            int height = 0;
            if (ImageUtils.hasIcecreamSandwich()) {
//
//                width = display.widthPixels - (int) UIUtils.convertDpToPixel(mContext.getResources().getDimension(R.dimen.activity_horizontal_margin), mContext);
                width = display.widthPixels;

            } else {
                width = display.widthPixels;
            }
            height = width * 328 / 600;

            mPosterImageFetcher = new ImageFetcher(context, width, height, POSTER_HTTP_CACHE_DIR);
            mPosterImageFetcher.setImageFadeIn(true);
            mPosterImageFetcher.setLoadingImage(ImageFetcher.decodeSampledBitmapFromResource(resources, R.drawable.default_poster, width, height));
        } else {
            mPosterImageFetcher.setExitTasksEarly(false);
        }

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(context, POSTER_CACHE_DIR);

        //default memory cache size of 3MB is enough.
        // each 56-56 size is: 56*56*8 = 0.024MB. 3MB will cache 125 bitmaps
//		cacheParams.memCacheSize = 1024*1024*3;
        cacheParams.setMemCacheSizePercent(context, 0.20f);
        mPosterImageFetcher.addImageCache(fragmentManager, cacheParams);

        mPosterImageCache = mPosterImageFetcher.getImageCache();
        if (BuildConfig.DEBUG)
            Log.v(TAG, "success init poster image cache: " + mPosterImageCache);

    }

    public ImageFetcher getPosterImageFetcher() {
        return mPosterImageFetcher;
    }

    public ImageFetcher getThumbnailImageFetcher() {
        return mThumbnailImageFetcher;
    }
}
