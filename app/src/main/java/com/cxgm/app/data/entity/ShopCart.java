package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class ShopCart extends BaseEntity {

    /**
      {
     "id": 26,
     "userId": 3,
     "shopId": 4,
     "goodName": "多宝鱼",
     "goodCode": "87",
     "specifications": "500/g",
     "goodNum": 1,
     "amount": 60,
     "imageUrl": null,
     "originalPrice": null,
     "price": 60,
     "coupon": null,
     "couponId": null
     }
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
    private float originalPrice;
    private String specifications;
    private CouponDetail coupon;
    private int couponId;
    private int categoryId;//二级分类ID
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public ShopCart(){

    }

    public ShopCart(int productId,String goodCode, String goodName, int goodNum,float amount, int shopId) {
        this.productId = productId;
        this.goodCode = goodCode;
        this.goodName = goodName;
        this.goodNum = goodNum;
        this.shopId = shopId;
        this.amount = amount;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public CouponDetail getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponDetail coupon) {
        this.coupon = coupon;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

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
