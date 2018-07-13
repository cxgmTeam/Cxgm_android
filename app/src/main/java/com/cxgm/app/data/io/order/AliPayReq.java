package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 支付宝支付接口
 *
 * @author dean
 * @time 2018/7/13 下午3:28
 */
public class AliPayReq extends Request {
    int orderId;
    public AliPayReq(Context context,int orderId) {
        super(context,false);
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/payments/aliPay");
        params.addQueryStringParameter("orderId",orderId+"");
        return params;
    }

    @Override
    public String parse(String json) {
        return json;
    }
}
