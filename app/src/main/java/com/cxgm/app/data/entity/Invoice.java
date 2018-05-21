package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

public class Invoice extends BaseEntity {


    /**
     * companyName : string
     * content : string
     * createTime : 2018-05-21T02:14:41.433Z
     * dutyParagraph : string
     * id : 0
     * orderId : 0
     * phone : string
     * type : string
     * userId : 0
     */

    private String companyName;
    private String content;
    private String createTime;
    private String dutyParagraph;  //税号
    private int id;
    private int orderId;
    private String phone;
    private String type; //0普通，1电子
    private int userId;

    public Invoice() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
