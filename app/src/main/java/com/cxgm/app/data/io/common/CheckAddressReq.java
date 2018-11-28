package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Shop;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 判断用户配送地址是否在配送范围
 * @deprecated 不再做范围检查
 * @author dean
 * @time 2018/5/8 上午11:52
 */
@Deprecated
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

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/checkAddress");
        params.addQueryStringParameter("longitude",longitude);
        params.addQueryStringParameter("dimension",dimension);

        return params;
    }

    @Override
    public List<Shop> parse(String json) {
        UserResult<List<Shop>> result = JSON.parseObject(json,new UserResult<List<Shop>>(){}.getEntityType());
        return result.data;
    }
}
