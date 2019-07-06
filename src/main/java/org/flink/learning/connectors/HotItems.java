package org.flink.learning.connectors;

import java.io.Serializable;

/**
 *@className HotItems
 *@description TODO
 *@author zhchxiao
 *
 *@date 19-7-5
 **/
 
public class HotItems implements Serializable {
    private int userId;
    private int itemId;
    private int categoryId;
    private String behavior;
    private int timestamps;

    public HotItems(int userId, int itemId, int categoryId, String behavior, int timestamps){
        this.userId = userId;
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.behavior = behavior;
        this.timestamps = timestamps;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public void setTimestamps(int timestamps) {
        this.timestamps = timestamps;
    }

    public long getUserId() {
        return userId;
    }

    public long getItemId() {
        return itemId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getBehavior() {
        return behavior;
    }

    public long getTimestamps() {
        return timestamps;
    }

    @Override
    public String toString() {
        return userId + "\t" + itemId + "\t" + categoryId + "\t" + behavior + "\t" + timestamps;
    }
}
