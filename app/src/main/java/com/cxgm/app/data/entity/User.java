package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * Created by dean on 2017/7/8.
 */

public class User extends BaseEntity {

    public User(){}

    public User(String userAccount, String password) {
        this.password = password;
        this.userAccount = userAccount;
    }
    public String password;
    public String address;
    public String phone;
    public String sex;
    public String userAccount;
    public String headUrl;
    public String storeName;
    public String managerName;
    public String storeCode;
    public String token;
    public Double longitude;//经度
    public Double dimension;//纬度
    public Integer doorStatus;//店铺状态 1无人，2有人, 3无人自定义

    public long doorStatusTime;//记录修改时间
    public String deviceToken;//友盟推送设备ID
}
