package com.cxgm.app.data.io.user;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 发短信
 *
 * @anthor Dean
 * @time 2018/5/4 0004 22:45
 */
public class SendSMSReq extends Request {
    String phone;
    public SendSMSReq(Context context,String phone) {
        super(context);
        this.phone = phone;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/sendSMS");
        params.addQueryStringParameter("phone",phone);
        return params;
    }

    @Override
    public String parse(String json) {
        UserResult<String> result = JSON.parseObject(json,new UserResult<String>(){}.getEntityType());
        return result.data;
    }
}
