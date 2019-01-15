package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Advertisement;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

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
    public List<Advertisement> parse(String json) {
        UserResult<List<Advertisement>> result = JSON.parseObject(json,new UserResult<List<Advertisement>>(){}.getEntityType());
        return result.data;
    }
}
