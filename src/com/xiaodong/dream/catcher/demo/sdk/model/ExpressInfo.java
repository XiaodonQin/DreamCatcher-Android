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
public class ExpressInfo {

    @JsonProperty("message")
    private String message;

    @JsonProperty("nu")
    private String nu;

    @JsonProperty("ischeck")
    private String ischeck;

    @JsonProperty("com")
    private String com;

    @JsonProperty("status")
    private String status;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("state")
    private String state;

    @JsonProperty("data")
    private LogisticsItem[] data;

    public String getMessage() {
        return message;
    }

    public String getNu() {
        return nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public String getCom() {
        return com;
    }

    public String getCondition() {
        return condition;
    }

    public String getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }

    public LogisticsItem[] getData() {
        return data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ExpressInfo");
        sb.append("{message=").append(this.message);
        sb.append(", nu=").append(this.nu);
        sb.append(", ischeck=").append(this.ischeck);
        sb.append(", com=").append(this.com);
        sb.append(", status=").append(this.status);
        sb.append(", condition=").append(this.condition);
        sb.append(", state=").append(this.state);
        sb.append(", data[");
        for (LogisticsItem item : data){
            sb.append("{time=").append(item.getTime());
            sb.append(", context=").append(item.getContext());
            sb.append(", ftime=").append(item.getFtime());
            sb.append("},");
        }
        sb.append("]}");

        return sb.toString();
    }
}
