package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

public class Order extends BaseEntity {

    //订单状态0待支付，1待配送（已支付），2配送中，3已完成，4退货
    public static final String STATUS_TO_BE_PAID = "0";
    public static final String STATUS_DISTRIBUTION = "1";
    public static final String STATUS_DISTRIBUTING = "2";
    public static final String STATUS_COMPLETE = "3";
    public static final String STATUS_REFUND = "4";
    /**
     * orderAmount : 0
     * orderNum : string
     * orderTime : 2018-05-14T14:57:09.017Z
     * payType : string
     * productDetails : [{"amount":0,"price":0,"productCode":0,"productName":"string","productNum":0,"productUrl":"string","unit":"string","weight":"string"}]
     * productList : [{"createTime":"2018-05-14T14:57:09.017Z","id":0,"orderId":0,"productId":0,"productName":"string","productNum":0}]
     * remarks : string
     * status : string
     * storeId : 0
     */

    private String orderNum;
    private String orderTime;
    private String payType;
    private String remarks;
    private String status;
    private int storeId;

    private float orderAmount;//订单金额
    private int couponCodeId;//优惠券ID
    private String addressId;//地址ID
    private String receiveTime;//送货时间
    private Invoice receipt;//发票信息
    private List<OrderProduct> productDetails;//查询用
    private List<OrderProduct> productList;//提交用

    private List<CategoryAndAmount> categoryAndAmountList;//二级分类下的总金额

    public List<CategoryAndAmount> getCategoryAndAmountList() {
        return categoryAndAmountList;
    }

    public void setCategoryAndAmountList(List<CategoryAndAmount> categoryAndAmountList) {
        this.categoryAndAmountList = categoryAndAmountList;
    }

    public int getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(int couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Invoice getReceipt() {
        return receipt;
    }

    public void setReceipt(Invoice receipt) {
        this.receipt = receipt;
    }

    public float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<OrderProduct> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<OrderProduct> productDetails) {
        this.productDetails = productDetails;
    }

    public List<OrderProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProduct> productList) {
        this.productList = productList;
    }

}
