package com.cxgm.app.data.event;

public class PayEvent {

    public PayEvent(String payWayCode,int status){
        this.payWayCode = payWayCode;
        this.status = status;
    }

    public static final String PAY_WAY_CODE_ALIPAY = "zhifubao";
    public static final String PAY_WAY_CODE_WECHAT = "weixin";
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FAIL = 1;

    public int id;
    public String payWayCode;
    public int status;
}
