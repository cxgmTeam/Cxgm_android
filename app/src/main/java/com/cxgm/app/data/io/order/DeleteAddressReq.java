package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 根据用户ID和地址ID删除用户地址接口
 *
 * @anthor Dean
 * @time 2018/5/22 0022 22:57
 */
public class DeleteAddressReq extends Request {
    int addressId;
    public DeleteAddressReq(Context context,int addressId) {
        super(context);
        this.addressId = addressId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/deleteAddress");
        params.addQueryStringParameter("addressId",addressId+"");
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<Integer> result = JSON.parseObject(json,new UserResult<Integer>(){}.getEntityType());
        return result.data;
    }
}
