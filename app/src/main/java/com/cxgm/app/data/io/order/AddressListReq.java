package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.UserAddress;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 根据用户ID查询用户地址接口
 *
 * @anthor Dean
 * @time 2018/5/12 0012 22:32
 */
public class AddressListReq extends Request {

    public AddressListReq(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/addressList");
        params.setMethod(HttpMethod.GET);
        return params;
    }

    @Override
    public List<UserAddress> parse(String json) {
        UserResult<List<UserAddress>> result = JSON.parseObject(json,new UserResult<List<UserAddress>>(){}.getEntityType());
        return result.data;
    }
}
