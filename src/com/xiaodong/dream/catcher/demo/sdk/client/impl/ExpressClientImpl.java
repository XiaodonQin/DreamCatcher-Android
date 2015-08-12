/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/11.
 */
package com.xiaodong.dream.catcher.demo.sdk.client.impl;

import cn.com.conversant.commons.http.*;
import cn.com.conversant.commons.http.adaptor.JsonErrorResponseParser;
import cn.com.conversant.commons.http.adaptor.JsonResponseParser;
import cn.com.conversant.commons.json.JacksonTypeReference;
import cn.com.conversant.commons.json.JsonParser;
import cn.com.conversant.commons.json.impl.JacksonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaodong.dream.catcher.demo.sdk.client.ExpressClient;
import com.xiaodong.dream.catcher.demo.sdk.consts.DreamCatcherAPI;
import com.xiaodong.dream.catcher.demo.sdk.consts.DreamCatcherConstants;
import com.xiaodong.dream.catcher.demo.sdk.model.ExpressInfo;

import java.lang.reflect.Type;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class ExpressClientImpl implements ExpressClient{
    private static String TAG = "ExpressClientImpl";

    private HttpClient client;
    private HttpHeaders headers;


    public ExpressClientImpl(String serverUrl, String userAgent, int maxConnectionPerRoute, int maxTotalConnection) {

        JsonParser jsonParser = new JacksonParser();
        client = new HttpClientDefault(serverUrl, new JsonErrorResponseParser(jsonParser));
        client.setResponseParse(new JsonResponseParser(jsonParser));
        client.setMaxConnections(maxConnectionPerRoute, maxTotalConnection);

        headers = new HttpHeaders();
        headers.put(HttpConstants.KEYS.CONNECTION, HttpConstants.KEYS.CONNECTION_CLOSE);
        headers.put(HttpConstants.KEYS.CACHE, HttpConstants.KEYS.NO_CACHE);
        headers.put(DreamCatcherConstants.HEADERS.KEY_X_USER_AGENT, userAgent);
    }

    @Override
    public ExpressInfo showExpressDetails(String type, String postId) {
        HttpParams params = new HttpParams();
        params.putParam(DreamCatcherAPI.EXPRESS_TYPE, type);
        params.putParam(DreamCatcherAPI.EXPRESS_POSTID, postId);

        Type tp = new TypeReference<ExpressInfo>(){}.getType();

        return client.getEntity(DreamCatcherAPI.EXPRESS_QUERY, headers, params, tp);
    }
}
