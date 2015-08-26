/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/11.
 */
package com.xiaodong.dream.catcher.demo.manager;

import android.content.Context;
import android.util.Log;
import com.xiaodong.dream.catcher.demo.BuildConfig;
import com.xiaodong.dream.catcher.demo.utils.Utils;
import com.xiaodong.dream.catcher.demo.sdk.DreamCatcherClient;
import com.xiaodong.dream.catcher.demo.sdk.client.ExpressClient;
import com.xiaodong.dream.catcher.demo.sdk.util.DreamCatcherConfigUtil;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class ClientManager {
    private static String TAG = "ClientManager";

    private static ClientManager sInstance;
    private static boolean sInitialed = false;
    private static Context mContext;

    private ExpressClient mExpressClient = null;

    public static void init(Context context){
        mContext = context;

        if(sInitialed){
            return;
        }

        sInitialed = true;
    }

    public static ClientManager getInstance(){
        if (!sInitialed){
            if (BuildConfig.DEBUG){
                Log.v(TAG, "Should call init() first");
            }
            return null;
        }

        if (sInstance == null){
            synchronized (ClientManager.class){
                if (sInstance == null){
                    sInstance = new ClientManager();
                }
            }
        }

        return sInstance;
    }


    /*
      DreamCatcher client

     */


    public ExpressClient getExpressClient(){
        if (mExpressClient == null){
            synchronized (ClientManager.class){
                if (mExpressClient == null){
                    mExpressClient = DreamCatcherClient.getExpressClient(
                            DreamCatcherConfigUtil.getExpressServerUrl(),
                            Utils.getUserAgent(),
                            DreamCatcherConfigUtil.getMaxHttpConnectionPerRoute(),
                            DreamCatcherConfigUtil.getMaxTotalHttpConnection());
                }

            }
        }

        return mExpressClient;
    }


    /*
      SwiftMedia client

     */

}
