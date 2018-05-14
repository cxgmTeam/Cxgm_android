package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.ShopCart;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 商品添加到购物车接口
 *
 * @author dean
 * @time 2018/5/14 下午4:31
 */
public class AddCartReq extends Request {

    ShopCart shopCart;
    public AddCartReq(Context context,ShopCart shopCart) {
        super(context);
        this.shopCart = shopCart;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/shopCart/addCart");
        params.setBodyContent(JSON.toJSONString(shopCart,false));
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<Integer> result = JSON.parseObject(json,new UserResult<Integer>(){}.getEntityType());
        return result.data;
    }
}
