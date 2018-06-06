package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.PayInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;


public class WeixinPayReq extends Request {
    int orderId;
    public WeixinPayReq(Context context,int orderId) {
        super(context);
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/payments/weixinPay");
        params.addQueryStringParameter("orderId",orderId+"");
        return params;
    }

    @Override
    public PayInfo parse(String json) {
        //{"msg":"ok","package":"Sign=WXPay","appid":"wxd2f7d73babd9de68","sign":"8D3C5BA9F9FF7DDD0EEB6E1250462DC9","partnerid":"1505765861","prepayid":"wx0615495901458857df238dbe2223333405","noncestr":"1550077710","timestamp":"1528271418"}
        PayInfo result = JSON.parseObject(json,PayInfo.class);
        return result;
    }
}
