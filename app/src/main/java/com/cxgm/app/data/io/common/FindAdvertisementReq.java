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
 * 根据门店ID查询首页广告
 *
 * @author dean
 * @time 2018/5/23 上午10:58
 */
public class FindAdvertisementReq extends Request {
    int shopId;
    public FindAdvertisementReq(Context context,int shopId) {
        super(context);
        this.shopId = shopId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findAdvertisement");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        return params;
    }

    @Override
    public List<Advertisement> parse(String json) {
        UserResult<List<Advertisement>> result = JSON.parseObject(json,new UserResult<List<Advertisement>>(){}.getEntityType());
        return result.data;
    }
}
