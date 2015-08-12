/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/12.
 */
package com.xiaodong.dream.catcher.demo.sdk.util;

import cn.com.conversant.commons.prop.ResourceProperty;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class DreamCatcherConfigUtil {
    private static ResourceProperty resourceProperty = ResourceProperty.getResourceProperty("sdk-client.properties");

    /**
     * Get API Service URL
     *
     * @return API Service URL
     */
    public static String getExpressServerUrl(){
        return resourceProperty.getStringProperty("express.server.url");
    }

    public static int getMaxHttpConnectionPerRoute(){
        return resourceProperty.getIntProperty("max.http.connection.per.route");
    }

    public static int getMaxTotalHttpConnection(){
        return resourceProperty.getIntProperty("max.total.http.connection");
    }

}
