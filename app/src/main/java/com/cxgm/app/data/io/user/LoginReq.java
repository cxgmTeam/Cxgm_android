package com.cxgm.app.data.io.user;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Login;
import com.cxgm.app.data.entity.User;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 登录
 *
 * @anthor Dean
 * @time 2018/5/3 0003 21:07
 */
public class LoginReq extends Request {
    Login mLogin;
    public LoginReq(Context context,String userAccount,String mobileValidCode) {
        super(context);
        mLogin = new Login(userAccount,mobileValidCode);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/login");
        params.setBodyContent(JSON.toJSONString(mLogin,false));
        return params;
    }

    @Override
    public User parse(String json) {
        UserResult<User> result = JSON.parseObject(json,new UserResult<User>(){}.getEntityType());
        return result.data;
    }
}
