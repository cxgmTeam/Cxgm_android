package com.cxgm.app.app;

import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

public class UserParam implements Request.IRequestParam {
    @Override
    public RequestParams disposeParam(RequestParams params) {
        if (UserManager.isUserLogin())
            params.addHeader("token",UserManager.user.getToken());
        return params;
    }
}
