package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class Postage extends BaseEntity {

    int id;
    int shopId;
    String shopName;
    float reduceMoney;//正常运费
    float satisfyMoney;//满这个数免运费

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(float reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public float getSatisfyMoney() {
        return satisfyMoney;
    }

    public void setSatisfyMoney(float satisfyMoney) {
        this.satisfyMoney = satisfyMoney;
    }
}
