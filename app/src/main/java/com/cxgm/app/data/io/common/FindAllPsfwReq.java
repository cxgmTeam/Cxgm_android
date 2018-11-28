package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.PsfwTransfer;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 查看所有配送范围接口
 * @deprecated 没有电子围栏，该接口没用了
 * @anthor Dean
 * @time 2018/5/13 0013 16:13
 */
@Deprecated
public class FindAllPsfwReq extends Request {
    int shopId;
    public FindAllPsfwReq(Context context) {
        super(context);
    }
    public FindAllPsfwReq(Context context,int shopId) {
        super(context);
        this.shopId = shopId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/user/findAllPsfw");
        params.setMethod(HttpMethod.GET);
        if (shopId!=0)
            params.addQueryStringParameter("shopId",shopId+"");
        return params;
    }

    @Override
    public List<PsfwTransfer> parse(String json) {
        UserResult<List<PsfwTransfer>> result = JSON.parseObject(json,new UserResult<List<PsfwTransfer>>(){}.getEntityType());
        return result.data;
    }
}
