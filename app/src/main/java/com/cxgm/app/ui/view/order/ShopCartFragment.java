package com.cxgm.app.ui.view.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 购物车
 *
 * @author dean
 * @time 2018/4/20 下午5:19
 */

public class ShopCartFragment extends BaseFragment {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    Unbinder unbinder;
    @BindView(R.id.lvGoods)
    ListView lvGoods;
    @BindView(R.id.cbCheckAll)
    CheckBox cbCheckAll;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvSum)
    TextView tvSum;
    @BindView(R.id.tvDiscounts)
    TextView tvDiscounts;
    @BindView(R.id.tvGoDuoShou)
    TextView tvGoDuoShou;
    @BindView(R.id.tvGoShopping)
    TextView tvGoShopping;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_cart, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        tvTitle.setText(R.string.shop_cart);
        tvAction1.setText(R.string.edit);
        tvAction1.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
