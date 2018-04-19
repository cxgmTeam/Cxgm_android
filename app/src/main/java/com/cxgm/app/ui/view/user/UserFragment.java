package com.cxgm.app.ui.view.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用户中心
 *
 * @author dean
 * @time 2018/4/19 上午9:35
 */
public class UserFragment extends BaseFragment {

    @BindView(R.id.imgUserCover)
    ImageView imgUserCover;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvEdit)
    ImageView tvEdit;
    @BindView(R.id.layoutUserBg)
    FrameLayout layoutUserBg;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.tvUnpaid)
    TextView tvUnpaid;
    @BindView(R.id.tvDistribution)
    TextView tvDistribution;
    @BindView(R.id.tvReceive)
    TextView tvReceive;
    @BindView(R.id.tvRefund)
    TextView tvRefund;
    @BindView(R.id.layoutGift)
    LinearLayout layoutGift;
    @BindView(R.id.layoutCoupon)
    LinearLayout layoutCoupon;
    @BindView(R.id.layoutReceiverAddr)
    LinearLayout layoutReceiverAddr;
    @BindView(R.id.layoutHelp)
    LinearLayout layoutHelp;
    @BindView(R.id.layoutService)
    LinearLayout layoutService;
    @BindView(R.id.layoutSettings)
    LinearLayout layoutSettings;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
