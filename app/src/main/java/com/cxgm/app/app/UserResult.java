package com.cxgm.app.app;

import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;

/**
 * 自定义数据结果类
 *
 * @anthor dean
 * @time 2018/4/18 下午5:47
 */

public class UserResult<T> extends Request.Result {

    public int code;
    public String msg;
    public T data;

    public UserResult() {
        super("200");
    }

    @Override
    public String getResultCode() {
        return code+"";
    }

    @Override
    public String getResultMsg() {
        return msg;
    }

    @Override
    public boolean onResultParse(String code) {
        if ("403".equals(code)){
            //token失效请重新登录！
            UserManager.deleteUser();
        }
        return super.onResultParse(code);
    }
}
