package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

/**
 * 运营
 *
 * @anthor Dean
 * @time 2018/5/23 0023 22:54
 */
public class Motion extends BaseEntity {


    /**
     * createTime : 2018-05-23T14:24:05.951Z
     * id : 0
     * imageUrl : string
     * motionName : string
     * onShelf : 0
     * position : string
     * productIds : string
     * productList : []
     * shopId : 0
     */

    private String createTime;
    private int id;
    private String imageUrl;
    private String motionName;
    private int onShelf;
    private String position;
    private String productIds;
    private int shopId;
    private List<ProductTransfer> productList;
    private String urlType;//1是跳转网页2是跳转商品详情
    private String notifyUrl;
    private String productCode;

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

    public String getMotionName() {
        return motionName;
    }

    public void setMotionName(String motionName) {
        this.motionName = motionName;
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

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public List<ProductTransfer> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductTransfer> productList) {
        this.productList = productList;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
