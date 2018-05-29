package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 根据订单ID查询剩余支付时间接口
 *
 * @author dean
 * @time 2018/5/29 下午3:48
 */
public class SurplusTimeReq extends Request {
    int orderId;
    public SurplusTimeReq(Context context,int orderId) {
        super(context);
        this.orderId = orderId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/order/surplusTime");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("orderId",orderId+"");
        return params;
    }

    @Override
    public Long parse(String json) {
        UserResult<Long> result = JSON.parseObject(json,new UserResult<Long>(){}.getEntityType());
        return result.data;
    }
}
