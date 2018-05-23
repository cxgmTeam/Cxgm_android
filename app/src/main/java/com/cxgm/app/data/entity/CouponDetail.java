package com.cxgm.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * 优惠劵
 *
 * @anthor Dean
 * @time 2018/5/12 0012 18:29
 */
public class CouponDetail implements Parcelable {

    public static final int STATUS_ENABLE = 0;//可用
    public static final int STATUS_DISABLE = 1;//不可用

    /**
     * beginDate : 2018-05-12T01:54:52.634Z
     * categoryId : 0
     * codeId : 0
     * endDate : 2018-05-12T01:54:52.634Z
     * introduction : string
     * name : string
     * productId : 0
     * status : 0
     */

    private String beginDate;
    private int categoryId;
    private int codeId;
    private String endDate;
    private String introduction;
    private String name;
    private int productId;
    private int status;
    private String priceExpression;//减
    private float maximumPrice;//满

    public float getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(float maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public String getPriceExpression() {
        return priceExpression;
    }

    public void setPriceExpression(String priceExpression) {
        this.priceExpression = priceExpression;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.beginDate);
        dest.writeInt(this.categoryId);
        dest.writeInt(this.codeId);
        dest.writeString(this.endDate);
        dest.writeString(this.introduction);
        dest.writeString(this.name);
        dest.writeInt(this.productId);
        dest.writeInt(this.status);
        dest.writeString(this.priceExpression);
    }

    public CouponDetail() {
    }

    protected CouponDetail(Parcel in) {
        this.beginDate = in.readString();
        this.categoryId = in.readInt();
        this.codeId = in.readInt();
        this.endDate = in.readString();
        this.introduction = in.readString();
        this.name = in.readString();
        this.productId = in.readInt();
        this.status = in.readInt();
        this.priceExpression = in.readString();
    }

    public static final Creator<CouponDetail> CREATOR = new Creator<CouponDetail>() {
        @Override
        public CouponDetail createFromParcel(Parcel source) {
            return new CouponDetail(source);
        }

        @Override
        public CouponDetail[] newArray(int size) {
            return new CouponDetail[size];
        }
    };
}
