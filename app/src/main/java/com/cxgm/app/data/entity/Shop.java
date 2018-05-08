package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * 商铺
 *
 * @author dean
 * @time 2018/5/8 上午11:57
 */
public class Shop extends BaseEntity {


    /**
     * description : string
     * id : 0
     * imageUrl : string
     * shopAddress : string
     * shopName : string
     */

    private String description;
    private int id;
    private String imageUrl;
    private String shopAddress;
    private String shopName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
