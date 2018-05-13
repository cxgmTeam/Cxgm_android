package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.UserAddress;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 修改用户地址接口
 *
 * @anthor Dean
 * @time 2018/5/13 0013 10:46
 */
public class UpdateAddressReq extends Request {
    UserAddress address;
    public UpdateAddressReq(Context context, UserAddress address) {
        super(context);
        this.address = address;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/updateAddress");
        params.setBodyContent(JSON.toJSONString(address,false));
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<Integer> result = JSON.parseObject(json,new UserResult<Integer>(){}.getEntityType());
        return result.data;
    }
}
