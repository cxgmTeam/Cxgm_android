package com.cxgm.app.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * 用户地址
 *
 * @anthor Dean
 * @time 2018/5/12 0012 22:49
 */
public class UserAddress extends BaseEntity implements Cloneable{


    /**
     * address : string
     * area : string
     * dimension : string
     * longitude : string
     * phone : string
     * realName : string
     */

    private int id;
    private String address;
    private String area;
    private String dimension;
    private String longitude;
    private String phone;
    private String realName;
    private int isDef;//是否为默认值
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDef() {
        return isDef;
    }

    public void setIsDef(int isDef) {
        this.isDef = isDef;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public UserAddress clone() {
        UserAddress o = null;
        try {
            o = (UserAddress) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        return o;
    }
}
