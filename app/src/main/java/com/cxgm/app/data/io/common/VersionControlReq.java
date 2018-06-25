package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Version;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 版本控制
 *
 * @anthor Dean
 * @time 2018/5/4 0004 22:27
 */
public class VersionControlReq extends Request {

    public VersionControlReq(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/version/getVersion");
        params.setMethod(HttpMethod.GET);
        return params;
    }

    @Override
    public Version parse(String json) {
        UserResult<Version> result = JSON.parseObject(json,new UserResult<Version>(){}.getEntityType());
        return result.data;
    }
}
