package com.cxgm.app.data.io.user;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 验证短信码
 *
 * @anthor Dean
 * @time 2018/5/4 0004 22:59
 */
public class ValidaSMSReq extends Request {
    String phone,checkcode;
    public ValidaSMSReq(Context context,String phone,String checkcode) {
        super(context);
        this.phone = phone;
        this.checkcode = checkcode;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/validaSMS");
        params.addQueryStringParameter("phone",phone);
        params.addQueryStringParameter("checkcode",checkcode);
        return params;
    }

    @Override
    public Boolean parse(String json) {
        UserResult<Boolean> result = JSON.parseObject(json,new UserResult<Boolean>(){}.getEntityType());
        return result.data;
    }
}
