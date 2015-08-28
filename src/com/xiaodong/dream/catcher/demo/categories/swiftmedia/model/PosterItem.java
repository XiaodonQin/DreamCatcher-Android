package com.xiaodong.dream.catcher.demo.categories.swiftmedia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class PosterItem implements Serializable{

    @JsonProperty("title")
    private String title;

    @JsonProperty("post_path")
    private String postPath;

    @JsonProperty("product_id")
    private long productId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostPath() {
        return postPath;
    }

    public void setPostPath(String postPath) {
        this.postPath = postPath;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
