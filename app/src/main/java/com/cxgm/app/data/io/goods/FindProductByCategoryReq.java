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

import java.util.List;

/**
 * 根据门店ID和商品类别ID查询商品信息
 *
 * @anthor Dean
 * @time 2018/5/12 0012 11:18
 */
public class FindProductByCategoryReq extends Request {

    int shopId,productCategoryTwoId;

    public FindProductByCategoryReq(Context context,int shopId,int productCategoryTwoId) {
        super(context);
        this.shopId = shopId;
        this.productCategoryTwoId = productCategoryTwoId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findProductByCategory");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        params.addQueryStringParameter("productCategoryTwoId",productCategoryTwoId+"");
        return params;
    }

    @Override
    public List<ProductTransfer> parse(String json) {
        UserResult<List<ProductTransfer>> result = JSON.parseObject(json,new UserResult<List<ProductTransfer>>(){}.getEntityType());
        return result.data;
    }
}
