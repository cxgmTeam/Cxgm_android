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
 * 根据门店ID查询商品一级分类
 *
 * @author dean
 * @time 2018/5/10 上午10:58
 */
public class FindFirstCategoryReq extends Request {

    int shopId;
    public FindFirstCategoryReq(Context context,int shopId) {
        super(context);
        this.shopId = shopId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findFirstCategory");
        params.addQueryStringParameter("shopId",shopId+"");
        params.setMethod(HttpMethod.GET);
        return params;
    }

    @Override
    public List<ShopCategory> parse(String json) {
        UserResult<List<ShopCategory>> result = JSON.parseObject(json,new UserResult<List<ShopCategory>>(){}.getEntityType());
        return result.data;
    }
}
