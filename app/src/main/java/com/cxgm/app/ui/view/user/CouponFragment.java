package com.cxgm.app.ui.view.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.CouponDetail;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.user.FindCouponsReq;
import com.cxgm.app.ui.adapter.CouponAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.utils.Helper;
import com.deanlib.ootb.data.io.Request;
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
 * 优惠券可用不可用页
 *
 * @anthor Dean
 * @time 2018/4/23 0023 23:04
 */
public class CouponFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    Unbinder unbinder;

    int pageNum = 1;
    List<CouponDetail> mCouponList;
    CouponAdapter mCouponAdapter;

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
        init();
        loadData();
    }

    private void init(){

        mCouponList = new ArrayList<>();
        mCouponAdapter = new CouponAdapter(mCouponList);
        listView.setAdapter(mCouponAdapter);
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNum++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                mCouponList.clear();
                loadData();
            }
        });
    }

    private void loadData(){
        new FindCouponsReq(getActivity(),pageNum,10)
                .execute(new Request.RequestCallback<PageInfo<CouponDetail>>() {
                    @Override
                    public void onSuccess(PageInfo<CouponDetail> couponDetailPageInfo) {
                        if (couponDetailPageInfo!=null && couponDetailPageInfo.getList()!=null){
                            mCouponList.addAll(couponDetailPageInfo.getList());
                            mCouponAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        pageNum = Helper.getUnsuccessPageNum(pageNum);
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        pageNum = Helper.getUnsuccessPageNum(pageNum);
                    }

                    @Override
                    public void onFinished() {
                        srl.finishLoadMore();
                        srl.finishRefresh();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
