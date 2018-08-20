package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 修改订单状态接口
 *
 * @author dean
 * @time 2018/6/7 上午11:16
 */
public class UpdateStatusReq extends Request {
    int orderId;
    String payType;//微信wx，支付宝zfb
    public UpdateStatusReq(Context context,int orderId,String payType) {
        super(context);
        this.orderId = orderId;
        this.payType = payType;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/payments/updateStatus");
        params.addQueryStringParameter("orderId",orderId+"");
        params.addQueryStringParameter("payType",payType);
        return params;
    }

    @Override
    public String parse(String json) {
        //{"msg":"ok"}
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("msg");
    }
}
