package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

public class Order extends BaseEntity {


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

    private int orderAmount;
    private String orderNum;
    private String orderTime;
    private String payType;
    private String remarks;
    private String status;
    private int storeId;
    private List<OrderProduct> productDetails;
    private List<OrderProduct> productList;

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
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
