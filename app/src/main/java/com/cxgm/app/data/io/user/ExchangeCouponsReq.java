package com.cxgm.app.data.io.user;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.CouponDetail;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 优惠券兑换
 *
 * @anthor Dean
 * @time 2018/5/20 0020 17:35
 */
public class ExchangeCouponsReq extends Request {
    String couponCode;
    public ExchangeCouponsReq(Context context,String couponCode) {
        super(context);
        this.couponCode = couponCode;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {
        RequestParams params = new RequestParams(SERVER + Constants.PORT2 + "/coupon/exchangeCoupons");
        params.setMethod(HttpMethod.GET);
        params.addQueryStringParameter("couponCode",couponCode);
        return params;
    }

    @Override
    public CouponDetail parse(String json) {
        UserResult<CouponDetail> result = JSON.parseObject(json,new UserResult<CouponDetail>(){}.getEntityType());
        return result.data;
    }
}
