package com.cxgm.app.data.io.goods;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ProductTransfer;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 根据商品ID查询商品详情
 *
 * @anthor Dean
 * @time 2018/5/24 0024 22:11
 */
public class FindProductDetailReq extends Request {
    int productId;
    public FindProductDetailReq(Context context,int productId) {
        super(context);
        this.productId = productId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findProductDetail");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("productId",productId+"");
        return params;
    }

    @Override
    public ProductTransfer parse(String json) {
        UserResult<ProductTransfer> result = JSON.parseObject(json,new UserResult<ProductTransfer>(){}.getEntityType());
        return result.data;
    }
}
