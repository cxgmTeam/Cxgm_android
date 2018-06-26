package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

import java.util.List;

public class Order extends BaseEntity {

    //订单状态0待支付，1待配送（已支付），4配送中，5已完成，6待退款，7已退款，8系统取消 ,9自主取消
    public static final String STATUS_TO_BE_PAID = "0";
    public static final String STATUS_DISTRIBUTION = "1";
    public static final String STATUS_DISTRIBUTING = "4";
    public static final String STATUS_COMPLETE = "5";
    public static final String STATUS_WAIT_REFUND = "6";
    public static final String STATUS_REFUND = "7";
    public static final String STATUS_SYSTEM_CANCEL = "8";
    public static final String STATUS_CANCEL = "9";
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

    private int id;
    private int userId;
    private String orderNum;
    private String orderTime;
    private String payType;
    private String remarks;
    private String status;
    private int storeId;

    private String shopName;
    private String shopAddress;

    private float orderAmount;//订单金额
    private int couponCodeId;//优惠券ID
    private String addressId;//地址ID
    private String receiveTime;//送货时间
    private Invoice receipt;//发票信息
    private List<OrderProduct> productDetails;//查询用
    private List<OrderProduct> productList;//提交用
    private List<CategoryAndAmount> categoryAndAmountList;//二级分类下的总金额
    private UserAddress address;//收货地址信息

    private float totalAmount;//商品总额，没有优惠前
    private float preferential;//优惠总额

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getPreferential() {
        return preferential;
    }

    public void setPreferential(float preferential) {
        this.preferential = preferential;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
