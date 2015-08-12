/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/11.
 */
package com.xiaodong.dream.catcher.demo.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class LogisticsItem {

    @JsonProperty("time")
    private String time;

    @JsonProperty("context")
    private String context;

    @JsonProperty("ftime")
    private String ftime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LogisticsItem");
        sb.append("{time=").append(this.time);
        sb.append(", context=").append(this.context);
        sb.append(", ftime=").append(this.ftime);
        sb.append("}");
        return super.toString();
    }
}
