package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

public class Promotion extends BaseEntity {


    /**
     * beginDate : 2018-05-23T14:24:05.952Z
     * couponList : []
     * creationDate : 2018-05-23T14:24:05.952Z
     * endDate : 2018-05-23T14:24:05.952Z
     * id : 0
     * introduction : string
     * isCouponAllowed : true
     * isFreeShipping : true
     * maximumPrice : 0
     * maximumQuantity : 0
     * minimumPrice : 0
     * minimumQuantity : 0
     * name : string
     * orders : 0
     * priceExpression : string
     * productCategoryId : 0
     * productId : 0
     * shopId : 0
     * title : string
     */

    private String beginDate;
    private String creationDate;
    private String endDate;
    private int id;
    private String introduction;
    private boolean isCouponAllowed;
    private boolean isFreeShipping;
    private int maximumPrice;
    private int maximumQuantity;
    private int minimumPrice;
    private int minimumQuantity;
    private String name;
    private int orders;
    private String priceExpression;
    private int productCategoryId;
    private int productId;
    private int shopId;
    private String title;
    private List<CouponDetail> couponList;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean isIsCouponAllowed() {
        return isCouponAllowed;
    }

    public void setIsCouponAllowed(boolean isCouponAllowed) {
        this.isCouponAllowed = isCouponAllowed;
    }

    public boolean isIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public int getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(int maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public int getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(int maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(int minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public String getPriceExpression() {
        return priceExpression;
    }

    public void setPriceExpression(String priceExpression) {
        this.priceExpression = priceExpression;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CouponDetail> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponDetail> couponList) {
        this.couponList = couponList;
    }
}
