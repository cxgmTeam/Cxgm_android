package com.cxgm.app.data.io.order;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.CouponDetail;
import com.cxgm.app.data.entity.Order;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.RequestParams;

import java.util.List;

/**
 * 根据用户ID和所选商品类别查询可用优惠券
 *
 * @author dean
 * @time 2018/5/17 下午5:12
 */
public class CheckCouponReq extends Request {

    Order order; //只设置其中的 productDetails 字段就可以
    public CheckCouponReq(Context context,Order order) {
        super(context);
        this.order = order;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT7 + "/order/checkCoupon");
        params.setBodyContent(JSON.toJSONString(order,false));
        return params;
    }

    @Override
    public List<CouponDetail> parse(String json) {
        UserResult<List<CouponDetail>> result = JSON.parseObject(json,new UserResult<List<CouponDetail>>(){}.getEntityType());
        return result.data;
    }
}
