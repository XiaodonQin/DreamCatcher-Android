/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/11.
 */
package com.xiaodong.dream.catcher.demo.sdk;

import com.xiaodong.dream.catcher.demo.sdk.client.ExpressClient;
import com.xiaodong.dream.catcher.demo.sdk.client.impl.ExpressClientImpl;

/**
 * TODO
 * dream catcher client sdk factory
 *
 * @author Xiaodong
 */
public class DreamCatcherClient {

    /**
     * Get Express Client
     *
     * @param serverUrl
     * @param userAgent
     * @param maxConnectionPerRoute
     * @param maxTotalConnection
     * @return Express Client
     */
    public static ExpressClient getExpressClient(String serverUrl, String userAgent, int maxConnectionPerRoute, int maxTotalConnection){
        return new ExpressClientImpl(serverUrl, userAgent, maxConnectionPerRoute, maxTotalConnection);
    }


}
