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
 * 根据门店ID和条形码查询商品信息
 *
 * @anthor dean
 * @time 2018/11/28 2:40 PM
 */
public class FindProductBycodeReq extends Request {
    String barCode;
    int shopId;
    public FindProductBycodeReq(Context context,int shopId,String barCode) {
        super(context);
        this.shopId = shopId;
        this.barCode = barCode;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findProductBycode");
        params.addQueryStringParameter("barCode",barCode);
        params.addQueryStringParameter("shopId",shopId+"");
        params.setMethod(HttpMethod.GET);
        return params;
    }

    @Override
    public ProductTransfer parse(String json) {
        UserResult<ProductTransfer> result = JSON.parseObject(json,new UserResult<ProductTransfer>(){}.getEntityType());
        return result.data;
    }
}
