package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 版本控制
 *
 * @anthor Dean
 * @time 2018/5/4 0004 22:27
 */
public class VersionControlReq extends Request {

    String versionCode;
    public VersionControlReq(Context context,String versionCode) {
        super(context);
        this.versionCode = versionCode;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + "/user/visionControl");
        params.addQueryStringParameter("visionCode",versionCode);
        return params;
    }

    @Override
    public <T> T parse(String json) {
        UserResult<T> result = JSON.parseObject(json,new UserResult<T>(){}.getEntityType());
        return result.data;
    }
}
