package com.cxgm.app.ui.view.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.order.OrderListReq;
import com.cxgm.app.ui.adapter.UserOrderAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.utils.Helper;
import com.deanlib.ootb.data.io.Request;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的订单
 *
 * @anthor Dean
 * @time 2018/4/24 0024 22:23
 */
public class UserOrderFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    Unbinder unbinder;

    int mPageNum = 1;
    List<Order> mOrderList;
    UserOrderAdapter mOrderAdapter;
    String mStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_refresh_listview, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            mStatus = getArguments().getString("status");
        }
        init();
        loadData();
    }

    private void init(){
        mOrderList = new ArrayList<>();
        mOrderAdapter = new UserOrderAdapter(mOrderList);
        listView.setAdapter(mOrderAdapter);
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageNum++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = 1;
                mOrderList.clear();
                loadData();
            }
        });
    }

    private void loadData(){

        new OrderListReq(getActivity(),mStatus,mPageNum,10).execute(new Request.RequestCallback<PageInfo<Order>>() {
            @Override
            public void onSuccess(PageInfo<Order> orderPageInfo) {
                if (orderPageInfo!=null && orderPageInfo.getList()!=null){
                    mOrderList.addAll(orderPageInfo.getList());
                    mOrderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mPageNum = Helper.getUnsuccessPageNum(mPageNum);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                mPageNum = Helper.getUnsuccessPageNum(mPageNum);
            }

            @Override
            public void onFinished() {
                srl.finishRefresh();
                srl.finishLoadMore();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
