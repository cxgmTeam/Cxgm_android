package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Order;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 用户下单接口
 *
 * @anthor Dean
 * @time 2018/5/14 0014 22:58
 */
public class AddOrderReq extends Request {
    Order order;//传入 productList 不关心productDetails
    public AddOrderReq(Context context,Order order) {
        super(context);
        this.order = order;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/order/addOrder");
        params.setBodyContent(JSON.toJSONString(order,false));
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<Integer> result = JSON.parseObject(json,new UserResult<Integer>(){}.getEntityType());
        return result.data;
    }
}
