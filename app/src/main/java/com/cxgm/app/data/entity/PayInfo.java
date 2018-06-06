package com.cxgm.app.data.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.deanlib.ootb.entity.BaseEntity;

public class PayInfo extends BaseEntity {


    /**
     * msg : ok
     * package : Sign=WXPay
     * appid : wxd2f7d73babd9de68
     * sign : 8D3C5BA9F9FF7DDD0EEB6E1250462DC9
     * partnerid : 1505765861
     * prepayid : wx0615495901458857df238dbe2223333405
     * noncestr : 1550077710
     * timestamp : 1528271418
     */

    private String msg;
    @JSONField(alternateNames = "package")
    private String packageValue;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
