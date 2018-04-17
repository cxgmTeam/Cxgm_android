package com.cxgm.app.app;

import com.deanlib.ootb.data.io.Request;

public class UserResult extends Request.Result {

    public String code;
    public String msg;

    public UserResult() {
        super("200");
    }

    @Override
    public String getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return msg;
    }
}
