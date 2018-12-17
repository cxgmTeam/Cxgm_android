package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class Comment extends BaseEntity {


    public Comment() {
    }

    public Comment(String content, int score, int shopId, int userId, String userName) {
        this.content = content;
        this.score = score;
        this.shopId = shopId;
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * content : string
     * createTime : 2018-12-17T09:25:56.524Z
     * score : 0
     * shopId : 0
     * userId : 0
     * userName : string
     */


    private String content;
    private String createTime;
    private int score;
    private int shopId;
    private int userId;
    private String userName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
