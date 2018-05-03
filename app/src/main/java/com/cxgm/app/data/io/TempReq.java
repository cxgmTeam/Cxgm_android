package com.cxgm.app.data.io;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;


public class TempReq extends Request {
    public TempReq(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + "");

        return params;
    }

    @Override
    public <T> T parse(String json) {
        UserResult<T> result = JSON.parseObject(json,new UserResult<T>(){}.getEntityType());
        return result.data;
    }
}
