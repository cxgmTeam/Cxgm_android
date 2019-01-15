package com.cxgm.app.data.entity;

import android.support.annotation.NonNull;

import com.deanlib.ootb.entity.BaseEntity;


/**
 * 广告
 *
 * @author dean
 * @time 2018/5/23 上午11:01
 */
public class Advertisement extends BaseEntity implements Comparable<Advertisement>{


    /**
     * adverName : string
     * createTime : 2018-05-23T02:58:14.823Z
     * id : 0
     * imageUrl : string
     * notifyUrl : string
     * number : 0
     * onShelf : 0
     * position : string
     * productCode : string
     * shopId : 0
     * type : string
     */

    private String adverName;
    private String createTime;
    private String goodName;
    private int id;
    private String imageUrl;
    private int isShop;
    private String notifyUrl;
    private int number;
    private int onShelf;
    private String position;
    private String productCode;
    private int shopId;
    private String shopName;
    private String type;//1是网页 2是商品详情

    public String getAdverName() {
        return adverName;
    }

    public void setAdverName(String adverName) {
        this.adverName = adverName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOnShelf() {
        return onShelf;
    }

    public void setOnShelf(int onShelf) {
        this.onShelf = onShelf;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getIsShop() {
        return isShop;
    }

    public void setIsShop(int isShop) {
        this.isShop = isShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public int compareTo(@NonNull Advertisement o) {
        return this.number - o.getNumber();
    }
}
