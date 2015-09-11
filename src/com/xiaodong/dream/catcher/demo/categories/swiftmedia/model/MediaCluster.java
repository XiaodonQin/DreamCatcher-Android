package com.xiaodong.dream.catcher.demo.categories.swiftmedia.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/29.
 */
public class MediaCluster implements Serializable{

    private String categoryName;

    private List<MediaItem> mediaItemList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<MediaItem> getMediaItemList() {
        return mediaItemList;
    }

    public void setMediaItemList(List<MediaItem> mediaItemList) {
        this.mediaItemList = mediaItemList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MediaCluster{");
        stringBuilder.append("categoryName=").append(categoryName).append(", ");
        stringBuilder.append("mediaClusterList=").append("{");
        for (MediaItem item : mediaItemList){
            stringBuilder.append(item.toString()).append(", ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
