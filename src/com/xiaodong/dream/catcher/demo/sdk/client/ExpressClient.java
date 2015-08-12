/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/11.
 */
package com.xiaodong.dream.catcher.demo.sdk.client;

import com.xiaodong.dream.catcher.demo.sdk.model.ExpressInfo;

import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public interface ExpressClient {

    public ExpressInfo showExpressDetails(String type, String postId);
}
