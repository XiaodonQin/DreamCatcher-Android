package com.xiaodong.dream.catcher.demo.categories.home;

/**
 * Created by Xiaodong on 2015/8/22.
 */
public class AppContent {
    private String appName;
    private String author;
    private String description;
    private String version;

    public AppContent() {
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
