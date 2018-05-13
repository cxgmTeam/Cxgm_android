package com.cxgm.app.data.io.goods;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ShopCategory;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 根根据门店ID和二级分类查询商品三级分类
 *
 * @anthor Dean
 * @time 2018/5/12 0012 12:20
 */
public class FindThirdCategoryReq extends Request {
    int shopId,productCategoryTwoId;
    public FindThirdCategoryReq(Context context, int shopId, int productCategoryTwoId) {
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

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findThirdCategory");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        params.addQueryStringParameter("productCategoryTwoId",productCategoryTwoId+"");
        return params;
    }

    @Override
    public List<ShopCategory> parse(String json) {
        UserResult<List<ShopCategory>> result = JSON.parseObject(json,new UserResult<List<ShopCategory>>(){}.getEntityType());
        return result.data;
    }
}
