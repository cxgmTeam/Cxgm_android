package com.cxgm.app.data.event;

public class PayEvent {

    public PayEvent(String payType,int status){
        this.payType = payType;
        this.status = status;
    }

    public static final String PAY_TYPE_ALIPAY = "zfb";
    public static final String PAY_TYPE_WECHAT = "wx";
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_FAIL = 1;

    public int id;
    public String payType;
    public int status;
    public Object obj;
}
