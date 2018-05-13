package com.cxgm.app.data.io.user;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.CouponDetail;
import com.cxgm.app.data.entity.base.PageInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 根据用户查询优惠券
 *
 * @anthor Dean
 * @time 2018/5/12 0012 17:35
 */
public class FindCouponsReq extends Request {
    int pageNum,pageSize;
    public FindCouponsReq(Context context,int pageNum,int pageSize) {
        super(context);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/coupon/findCoupons");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("pageNum",pageNum+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        return params;
    }

    @Override
    public PageInfo<CouponDetail> parse(String json) {
        UserResult<PageInfo<CouponDetail>> result = JSON.parseObject(json,new UserResult<PageInfo<CouponDetail>>(){}.getEntityType());
        return result.data;
    }
}
