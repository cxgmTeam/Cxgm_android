package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Shop;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 查询店铺
 *
 * @anthor dean
 * @time 2018/12/17 6:38 PM
 */
public class ShopDetailReq extends Request {

    int shopId;
    public ShopDetailReq(Context context,int shopId) {
        super(context);
        this.shopId = shopId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/shopDetail");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        return params;
    }

    @Override
    public Shop parse(String json) {
        UserResult<Shop> result = JSON.parseObject(json,new UserResult<Shop>(){}.getEntityType());
        return result.data;
    }
}
