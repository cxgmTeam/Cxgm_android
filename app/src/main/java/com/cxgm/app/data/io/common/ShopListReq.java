package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.base.PageInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 查询所有门店列表
 *
 * @anthor Dean
 * @time 2018/5/12 0012 18:29
 */
public class ShopListReq extends Request {
    int pageNum,pageSize;
    public ShopListReq(Context context,int pageNum,int pageSize) {
        super(context);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/shopList");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("pageNum",pageNum+"" );
        params.addQueryStringParameter("pageSize",pageSize+"" );
        return params;
    }

    @Override
    public PageInfo<Shop> parse(String json) {
        UserResult<PageInfo<Shop>> result = JSON.parseObject(json,new UserResult<PageInfo<Shop>>(){}.getEntityType());
        return result.data;
    }
}
