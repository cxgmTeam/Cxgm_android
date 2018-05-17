package com.cxgm.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderProduct implements Parcelable {

    private int orderId;
    private int productId;
    private String productName;
    private int productNum;
    private String createTime;
    private String productUrl;
    private String productCode;
    private float amount;
    private float price;
    private String unit;
    private String weight;
    private int id;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.orderId);
        dest.writeInt(this.productId);
        dest.writeString(this.productName);
        dest.writeInt(this.productNum);
        dest.writeString(this.createTime);
        dest.writeString(this.productUrl);
        dest.writeString(this.productCode);
        dest.writeFloat(this.amount);
        dest.writeFloat(this.price);
        dest.writeString(this.unit);
        dest.writeString(this.weight);
        dest.writeInt(this.id);
    }

    public OrderProduct() {
    }

    public OrderProduct(String productName, int productNum, String productUrl, String productCode, float amount, float price) {
        this.productName = productName;
        this.productNum = productNum;
        this.productUrl = productUrl;
        this.productCode = productCode;
        this.amount = amount;
        this.price = price;
    }

    protected OrderProduct(Parcel in) {
        this.orderId = in.readInt();
        this.productId = in.readInt();
        this.productName = in.readString();
        this.productNum = in.readInt();
        this.createTime = in.readString();
        this.productUrl = in.readString();
        this.productCode = in.readString();
        this.amount = in.readFloat();
        this.price = in.readFloat();
        this.unit = in.readString();
        this.weight = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<OrderProduct> CREATOR = new Creator<OrderProduct>() {
        @Override
        public OrderProduct createFromParcel(Parcel source) {
            return new OrderProduct(source);
        }

        @Override
        public OrderProduct[] newArray(int size) {
            return new OrderProduct[size];
        }
    };
}
