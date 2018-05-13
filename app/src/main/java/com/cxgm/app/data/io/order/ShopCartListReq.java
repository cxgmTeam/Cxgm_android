package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.base.PageInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 我的购物车列表
 *
 * @anthor Dean
 * @time 2018/5/13 0013 17:18
 */
public class ShopCartListReq extends Request {
    int pageNum,pageSize;
    public ShopCartListReq(Context context,int pageNum,int pageSize) {
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

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/shopCart/list");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("pageNum",pageNum+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        return params;
    }

    @Override
    public PageInfo<ShopCart> parse(String json) {
        UserResult<PageInfo<ShopCart>> result = JSON.parseObject(json,new UserResult<PageInfo<ShopCart>>(){}.getEntityType());
        return result.data;
    }
}
