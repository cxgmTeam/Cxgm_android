package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Shop;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 判断用户配送地址是否在配送范围
 *
 * @author dean
 * @time 2018/5/8 上午11:52
 */
public class CheckAddressReq extends Request {

    String longitude;
    String dimension;

    public CheckAddressReq(Context context,String longitude,String dimension) {
        super(context);
        this.longitude = longitude;
        this.dimension = dimension;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + "/user/checkAddress");
        params.addQueryStringParameter("longitude",longitude);
        params.addQueryStringParameter("dimension",dimension);

        return params;
    }

    @Override
    public Shop parse(String json) {
        UserResult<Shop> result = JSON.parseObject(json,new UserResult<Shop>(){}.getEntityType());
        return result.data;
    }
}
