package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Comment;
import com.cxgm.app.data.entity.base.PageInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 根据门店查询评论
 *
 * @anthor dean
 * @time 2018/12/17 5:49 PM
 */
public class FindCommentReq extends Request {

    int shopId,pageNum,pageSize;
    public FindCommentReq(Context context,int shopId,int pageNum,int pageSize) {
        super(context);
        this.shopId = shopId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/comment/findComment");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("shopId",shopId+"");
        params.addQueryStringParameter("pageNum",pageNum+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        return params;
    }

    @Override
    public PageInfo<Comment> parse(String json) {
        UserResult<PageInfo<Comment>> result = JSON.parseObject(json,new UserResult<PageInfo<Comment>>(){}.getEntityType());
        return result.data;
    }
}
