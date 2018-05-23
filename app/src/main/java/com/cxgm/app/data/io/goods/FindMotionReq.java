package com.cxgm.app.data.io.goods;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Motion;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 根据门店ID查询首页运营位置
 *
 * @anthor Dean
 * @time 2018/5/23 0023 22:30
 */
public class FindMotionReq extends Request {
    int shopId;
    public FindMotionReq(Context context,int shopId) {
        super(context);
        this.shopId = shopId;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/homePage/findMotion");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        return params;
    }

    @Override
    public List<Motion> parse(String json) {
        UserResult<List<Motion>> result = JSON.parseObject(json,new UserResult<List<Motion>>(){}.getEntityType());
        return result.data;
    }
}
