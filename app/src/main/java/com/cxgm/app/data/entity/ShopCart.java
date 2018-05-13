package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class ShopCart extends BaseEntity {

    /**
     * amount : 0
     * goodCode : string
     * goodName : string
     * goodNum : 0
     * id : 0
     * imageUrl : string
     * price : 0
     * shopId : 0
     * userId : 0
     */

    private float amount;
    private String goodCode;
    private String goodName;
    private int goodNum;
    private int id;
    private String imageUrl;
    private float price;
    private int shopId;
    private int userId;

    public boolean isChecked;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
}
