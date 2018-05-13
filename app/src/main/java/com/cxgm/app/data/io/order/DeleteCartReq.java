package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ShopCart;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 购物车移除商品接口
 *
 * @anthor Dean
 * @time 2018/5/13 0013 18:24
 */
public class DeleteCartReq extends Request {
    String shopCartIds;
    public DeleteCartReq(Context context, String shopCartIds) {
        super(context);
        this.shopCartIds = shopCartIds;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/shopCart/deleteShopCart");
        params.addQueryStringParameter("shopCartIds",shopCartIds);
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<Integer> result = JSON.parseObject(json,new UserResult<Integer>(){}.getEntityType());
        return result.data;
    }
}
