package com.cxgm.app.data.io.common;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Comment;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

/**
 * 提交评论接口
 *
 * @anthor dean
 * @time 2018/12/17 5:28 PM
 */
public class AddCommentReq extends Request {

    Comment comment;
    public AddCommentReq(Context context,Comment comment) {
        super(context);
        this.comment = comment;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/comment/addComment");
        params.setBodyContent(JSON.toJSONString(comment,false));
        return params;
    }

    @Override
    public Integer parse(String json) {
        UserResult<java.lang.Integer> result = JSON.parseObject(json,new UserResult<java.lang.Integer>(){}.getEntityType());
        return result.data;
    }
}
