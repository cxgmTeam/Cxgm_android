package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 取消订单接口
 *
 * @anthor Dean
 * @time 2018/5/30 0030 22:37
 */
public class DeleteOrderReq extends Request {
    int orderId;
    public DeleteOrderReq(Context context,int orderId) {
        super(context);
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/order/deleteOrder");
        params.addQueryStringParameter("orderId",orderId+"");
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<Integer> result = JSON.parseObject(json,new UserResult<Integer>(){}.getEntityType());
        return result.data;
    }
}
