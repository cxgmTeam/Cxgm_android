package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * 优惠劵
 *
 * @anthor Dean
 * @time 2018/5/12 0012 18:29
 */
public class CouponDetail extends BaseEntity {


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
}
