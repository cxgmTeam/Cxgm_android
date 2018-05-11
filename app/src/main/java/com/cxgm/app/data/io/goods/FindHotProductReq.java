package com.cxgm.app.data.io.goods;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.base.PageInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 根据门店ID查询首页热门推荐
 *
 * @author dean
 * @time 2018/5/11 上午10:45
 */
public class FindHotProductReq extends Request {

    int shopId,pageNum,pageSize;
    public FindHotProductReq(Context context, int shopId, int pageNum, int pageSize) {
        super(context);
        this.shopId = shopId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findHotProduct");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        params.addQueryStringParameter("pageNum",pageNum+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        return params;
    }

    @Override
    public PageInfo<ProductTransfer> parse(String json) {
        UserResult<PageInfo<ProductTransfer>> result = JSON.parseObject(json,new UserResult<PageInfo<ProductTransfer>>(){}.getEntityType());
        return result.data;
    }
}
