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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.layoutInvite)
    LinearLayout layoutInvite;
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        if (UserManager.isUserLogin()){
            tvUserName.setText(UserManager.user.getUserName());
            Glide.with(this).load(UserManager.user.getHeadUrl())
                    .apply(RequestOptions.circleCropTransform()
                    .placeholder(R.mipmap.default_head)
                    .error(R.mipmap.default_head)).into(imgUserCover);
        }else {
            tvUserName.setText(R.string.login_or_regist);
            imgUserCover.setImageResource(R.mipmap.default_head);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tvUserName, R.id.tvEdit, R.id.tvOrder, R.id.tvUnpaid, R.id.tvDistribution, R.id.tvReceive, R.id.tvRefund, R.id.layoutInvite, R.id.layoutCoupon, R.id.layoutReceiverAddr, R.id.layoutHelp, R.id.layoutService, R.id.layoutSettings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvUserName:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                }
                break;
            case R.id.tvEdit:
                break;
            case R.id.tvOrder:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toUserOrder(getActivity());
                break;
            case R.id.tvUnpaid:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toUserOrder(getActivity(), Order.STATUS_TO_BE_PAID);
                break;
            case R.id.tvDistribution:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toUserOrder(getActivity(), Order.STATUS_DISTRIBUTION);
                break;
            case R.id.tvReceive:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toUserOrder(getActivity(), Order.STATUS_DISTRIBUTING);
                break;
            case R.id.tvRefund:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toUserOrder(getActivity(), Order.STATUS_COMPLETE);
                break;
            case R.id.layoutInvite:
                //邀请有礼
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toInvite(getActivity());
                break;
            case R.id.layoutCoupon:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toCoupon(getActivity());
                break;
            case R.id.layoutReceiverAddr:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(getActivity());
                    return;
                }
                ViewJump.toAddrList(getActivity());
                break;
            case R.id.layoutHelp:
                break;
            case R.id.layoutService:
                break;
            case R.id.layoutSettings:
                ViewJump.toSettings(getActivity());
                break;
        }
    }
}
