package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Postage;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 邮费查询
 *
 * @author dean
 * @time 2018/9/25 下午3:47
 */
public class OrderPostageReq extends Request {
    int shopId;
    public OrderPostageReq(Context context,int shopId) {
        super(context);
        this.shopId = shopId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/order/orderPostage");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        return params;
    }

    @Override
    public Postage parse(String json) {
        UserResult<Postage> result = JSON.parseObject(json,new UserResult<Postage>(){}.getEntityType());
        return result.data;
    }
}
