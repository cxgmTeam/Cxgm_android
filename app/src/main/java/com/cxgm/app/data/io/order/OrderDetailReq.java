package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Order;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 根据订单ID查询订单详情接口
 *
 * @author dean
 * @time 2018/5/30 下午1:49
 */
public class OrderDetailReq extends Request {
    int orderId;
    public OrderDetailReq(Context context,int orderId) {
        super(context);
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/order/orderDetail");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("orderId",orderId+"");
        return params;
    }

    @Override
    public Order parse(String json) {
        UserResult<Order> result = JSON.parseObject(json,new UserResult<Order>(){}.getEntityType());
        return result.data;
    }
}
