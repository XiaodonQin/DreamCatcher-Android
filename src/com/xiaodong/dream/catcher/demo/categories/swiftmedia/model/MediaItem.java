package com.xiaodong.dream.catcher.demo.categories.swiftmedia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class MediaItem implements Serializable{

    @JsonProperty("name")
    private String name;

    @JsonProperty("thumbnail_path")
    private String thumbnailPath;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MediaItem{");
        stringBuilder.append("name=").append(name).append(", ");
        stringBuilder.append("thumbnailPath=").append(thumbnailPath).append("}");
        return stringBuilder.toString();
    }
}
