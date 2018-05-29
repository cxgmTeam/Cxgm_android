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
 * 根据门店ID查询首页新品上市
 *
 * @author dean
 * @time 2018/5/29 上午11:06
 */
public class SearchReq extends Request {
    int shopId;
    String goodName;
    public SearchReq(Context context,int shopId,String goodName) {
        super(context);
        this.shopId = shopId;
        this.goodName = goodName;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/serch");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        params.addQueryStringParameter("goodName",goodName);
        return params;
    }

    @Override
    public PageInfo<ProductTransfer> parse(String json) {
        UserResult<PageInfo<ProductTransfer>> result = JSON.parseObject(json,new UserResult<PageInfo<ProductTransfer>>(){}.getEntityType());
        return result.data;
    }
}
