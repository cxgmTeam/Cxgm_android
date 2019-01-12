package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 门店列表页面广告
 *
 * @anthor dean
 * @time 2019/1/11 3:30 PM
 */
public class ShopAdvertisementReq extends Request {
    public ShopAdvertisementReq(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/shopAdvertisement");
        params.setMethod(HttpMethod.GET);
        return params;
    }

    @Override
    public <T> T parse(String json) {
        //todo
        UserResult<T> result = JSON.parseObject(json,new UserResult<T>(){}.getEntityType());
        return result.data;
    }
}
