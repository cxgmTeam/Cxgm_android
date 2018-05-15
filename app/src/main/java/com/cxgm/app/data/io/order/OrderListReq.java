package com.cxgm.app.data.io.order;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.app.Constants;
import com.cxgm.app.app.UserResult;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.data.entity.base.PageInfo;
import com.deanlib.ootb.data.io.Request;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

/**
 * 我的订单列表
 *
 * @author dean
 * @time 2018/5/15 上午9:11
 */
public class OrderListReq extends Request {

    int pageNum,pageSize;
    String status;//订单状态0待支付，1待配送（已支付），2配送中，3已完成，4退货
    public OrderListReq(Context context,String status,int pageNum,int pageSize) {
        super(context);
        this.status = status;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public RequestParams params() {

        RequestParams params = new RequestParams(SERVER + Constants.PORT3 + "/order/list");
        params.setMethod(HttpMethod.GET);
        if (!TextUtils.isEmpty(status))
            params.addQueryStringParameter("status",status);
        params.addQueryStringParameter("pageNum",pageNum+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        return params;
    }

    @Override
    public PageInfo<Order> parse(String json) {
        UserResult<PageInfo<Order>> result = JSON.parseObject(json,new UserResult<PageInfo<Order>>(){}.getEntityType());
        return result.data;
    }
}
