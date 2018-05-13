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
 * 根据门店ID和一级分类查询商品二级分类
 *
 * @anthor Dean
 * @time 2018/5/12 0012 11:58
 */
public class FindSecondCategoryReq extends Request {
    int shopId,productCategoryId;
    public FindSecondCategoryReq(Context context,int shopId,int productCategoryId) {
        super(context);
        this.shopId = shopId;
        this.productCategoryId = productCategoryId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findSecondCategory");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        params.addQueryStringParameter("productCategoryId",productCategoryId+"");
        return params;
    }

    @Override
    public List<ShopCategory> parse(String json) {
        UserResult<List<ShopCategory>> result = JSON.parseObject(json,new UserResult<List<ShopCategory>>(){}.getEntityType());
        return result.data;
    }
}
