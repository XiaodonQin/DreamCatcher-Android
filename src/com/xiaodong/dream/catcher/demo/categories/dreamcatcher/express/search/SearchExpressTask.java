/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/12.
 */
package com.xiaodong.dream.catcher.demo.categories.dreamcatcher.express.search;

import android.app.Activity;
import com.xiaodong.dream.catcher.demo.image.AsyncTask;
import com.xiaodong.dream.catcher.demo.utils.Utils;
import com.xiaodong.dream.catcher.demo.manager.ClientManager;
import com.xiaodong.dream.catcher.demo.sdk.client.ExpressClient;
import com.xiaodong.dream.catcher.demo.sdk.model.ExpressInfo;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class SearchExpressTask extends AsyncTask<String, Void, ExpressInfo>{

    private ExpressInfo expressInfo = null;

    private Activity mActivity;

    public SearchExpressTask(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected ExpressInfo doInBackground(String... params) {
        ClientManager clientManager = ClientManager.getInstance();

        if (clientManager == null){
            return null;
        }

        ExpressClient expressClient = clientManager.getExpressClient();

        try {
            if(isCancelled()){
                return null;
            }
            expressInfo = expressClient.showExpressDetails(params[0], params[1]);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.toastErrorMessage(mActivity, e);
        }

        return expressInfo;
    }
}
