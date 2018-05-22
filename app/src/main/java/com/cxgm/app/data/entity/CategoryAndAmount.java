package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class CategoryAndAmount extends BaseEntity {


    /**
     * categoryId : 90
     * amount : 24
     */

    private int categoryId;
    private float amount;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
