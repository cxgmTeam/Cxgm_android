package com.cxgm.app.data.io.goods;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ProductTransfer;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 根据商品类别推荐同类商品
 *
 * @anthor Dean
 * @time 2018/5/27 0027 0:36
 */
public class PushProductsReq extends Request {
    int shopId;
    int productCategoryTwoId,productCategoryThirdId;
    public PushProductsReq(Context context,int shopId,int productCategoryTwoId,int productCategoryThirdId) {
        super(context);
        this.shopId = shopId;
        this.productCategoryTwoId = productCategoryTwoId;
        this.productCategoryThirdId = productCategoryThirdId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/pushProducts");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        if (productCategoryTwoId!=0)
            params.addQueryStringParameter("productCategoryTwoId",productCategoryTwoId+"");
        if (productCategoryThirdId!=0){
            params.addQueryStringParameter("productCategoryThirdId",productCategoryThirdId+"");
        }
        return params;
    }

    @Override
    public List<ProductTransfer> parse(String json) {
        UserResult<List<ProductTransfer>> result = JSON.parseObject(json,new UserResult<List<ProductTransfer>>(){}.getEntityType());
        return result.data;
    }
}
