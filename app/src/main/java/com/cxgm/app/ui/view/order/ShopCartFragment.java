package com.cxgm.app.ui.view.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.order.DeleteCartReq;
import com.cxgm.app.data.io.order.ShopCartListReq;
import com.cxgm.app.data.io.order.UpdateCartReq;
import com.cxgm.app.ui.adapter.CartGoodsAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.view.common.MainActivity;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 购物车
 *
 * @author dean
 * @time 2018/4/20 下午5:19
 */

public class ShopCartFragment extends BaseFragment implements CartGoodsAdapter.OnShopCartActionListener {

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
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    int mPageNum = 1;
    List<ShopCart> mCartList;
    CartGoodsAdapter mCartAdapter;

    //用于拦截OnShopCartActionListener.onChangeCheck方法更新数据时
    // cbCheckAll的setOnCheckedChangeListener方法
    boolean mInterceptChecked = false;
    @BindView(R.id.layoutGoodsList)
    LinearLayout layoutGoodsList;
    @BindView(R.id.layoutEmptyShopCart)
    LinearLayout layoutEmptyShopCart;

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

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            init();
            loadData();
        }
    }

    private void init() {
        tvTitle.setText(R.string.shop_cart);
        tvAction1.setText(R.string.delete);
        tvAction1.setVisibility(View.VISIBLE);

        mCartList = new ArrayList<>();
        mCartAdapter = new CartGoodsAdapter(mCartList, this);
        lvGoods.setAdapter(mCartAdapter);
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageNum++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = 1;
                mCartList.clear();
                loadData();
            }
        });

        cbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mInterceptChecked) {
                    for (ShopCart cart : mCartList) {
                        cart.isChecked = isChecked;
                    }
                    mCartAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void loadData() {
        new ShopCartListReq(getActivity(), mPageNum, 10)
                .execute(new Request.RequestCallback<PageInfo<ShopCart>>() {
                    @Override
                    public void onSuccess(PageInfo<ShopCart> shopCartPageInfo) {
                        if (shopCartPageInfo != null && shopCartPageInfo.getList() != null && shopCartPageInfo.getList().size()>0) {
                            layoutGoodsList.setVisibility(View.VISIBLE);
                            layoutEmptyShopCart.setVisibility(View.GONE);
                            mCartList.addAll(shopCartPageInfo.getList());
                            mCartAdapter.notifyDataSetChanged();
                            loadBottomData();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        srl.finishLoadMore();
                        srl.finishRefresh();
                        if (mCartList.size() == 0){
                            layoutGoodsList.setVisibility(View.GONE);
                            layoutEmptyShopCart.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onUpdateGoods(final ShopCart cartGoods,int actionNum) {
        if ((cartGoods.getGoodNum() + actionNum)>0) {
            final ShopCart cart = cartGoods.clone();
            cart.setGoodNum(cart.getGoodNum() + 1);
            cart.setAmount(Helper.moneyMultiply(cart.getPrice(), cart.getGoodNum()));
            new UpdateCartReq(getActivity(), cart).execute(false, new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    cartGoods.setGoodNum(cart.getGoodNum());
                    cartGoods.setAmount(cart.getAmount());
                    mCartAdapter.notifyDataSetChanged();
                    loadBottomData();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(Callback.CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }else {
            //删除
            new DeleteCartReq(getActivity(), cartGoods.getId()+"").execute(false, new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    mCartList.remove(cartGoods);
                    mCartAdapter.notifyDataSetChanged();
                    loadBottomData();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(Callback.CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    @Override
    public void onChangeCheck(int postion, boolean isChecked) {
        mCartList.get(postion).isChecked = isChecked;
        mInterceptChecked = true;
        if (cbCheckAll.isChecked() || !isChecked) {
            cbCheckAll.setChecked(isChecked);
        } else {
            boolean isCheckedAll = isChecked;
            for (int i =0;i<mCartList.size();i++) {
                if (i != postion && mCartList.get(i).isChecked != isChecked) {
                    isCheckedAll = false;
                    break;
                }
            }
            cbCheckAll.setChecked(isCheckedAll);
        }
        mInterceptChecked = false;
    }

    private void loadBottomData() {
        float totalAmount = 0.00f;
        float totalOriginal = 0.00f;
//        int totalNum = 0;
        for (ShopCart cart : mCartList) {
            totalAmount = Helper.moneyAdd(totalAmount, cart.getAmount());
            totalOriginal =Helper.moneyAdd(Helper.moneyMultiply(cart.getOriginalPrice(),cart.getGoodNum()),totalOriginal);
            /* 促销活动的不要了
                //满减
                if (cart.getCoupon()!=null) {
                    if (cart.getAmount()>100) {
                        float discountsAfterAmount = Helper.calculateDiscounted(cart.getAmount(),cart.getCoupon().getPriceExpression());
                        totalDiscounts += Helper.moneySubtract(cart.getAmount(),discountsAfterAmount);
                    }
                }
            */
//            totalNum += cart.getGoodNum();
        }

        tvTotal.setText(getString(R.string.total_, StringHelper.getRMBFormat(totalAmount)));
        tvSum.setText(getString(R.string.sum_, StringHelper.getRMBFormat(totalOriginal)));
        tvDiscounts.setText(getString(R.string.discounts_, StringHelper.getRMBFormat(Helper.moneySubtract(totalOriginal,totalAmount))));
        tvGoDuoShou.setText(getString(R.string.go_duoshou_, mCartList.size()));
    }

    @OnClick({R.id.tvGoDuoShou, R.id.tvGoShopping,R.id.tvAction1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvGoDuoShou:
                ArrayList<OrderProduct> products = new ArrayList<>();
                for (ShopCart cart : mCartList){
                    if (cart.isChecked){
                        products.add(new OrderProduct(cart));
                    }
                }
                if (products.size() > 0){
                    ViewJump.toVerifyOrder(getActivity(),products);
                }else {
                    ToastManager.sendToast(getString(R.string.do_not_have_selected_goods));
                }

                break;
            case R.id.tvGoShopping:
                ((MainActivity)getActivity()).publicChangeView(R.id.rbGoods);
                break;
            case R.id.tvAction1:
                //删除
                StringBuilder builder = new StringBuilder();
                for (ShopCart cart : mCartList){
                    if (cart.isChecked)
                        builder.append(cart.getId()+",");
                }
                if (builder.length()>0) {
                    String ids = builder.deleteCharAt(builder.length() - 1).toString();
                    new DeleteCartReq(getActivity(), ids).execute(new Request.RequestCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            for (int i = mCartList.size()-1;i>=0;i--){
                                if (mCartList.get(i).isChecked){
                                    mCartList.remove(i);

                                }
                            }
                            mCartAdapter.notifyDataSetChanged();
                            loadBottomData();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(Callback.CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }else {
                    ToastManager.sendToast(getString(R.string.nothing_selected));
                }
                break;
        }
    }
}
