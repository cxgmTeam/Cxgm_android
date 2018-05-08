package com.cxgm.app.data.entity;

import com.deanlib.ootb.entity.BaseEntity;

/**
 * 登录信息
 *
 * @author dean
 * @time 2018/5/8 上午11:56
 */
public class Login extends BaseEntity {

    public Login(String userAccount, String mobileValidCode) {
        this.userAccount = userAccount;
        this.mobileValidCode = mobileValidCode;
    }

    /**
     * mobileValidCode : string
     * password : string
     * userAccount : string
     */

    private String userAccount;
    private String password;
    private String mobileValidCode;

    public String getMobileValidCode() {
        return mobileValidCode;
    }

    public void setMobileValidCode(String mobileValidCode) {
        this.mobileValidCode = mobileValidCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
