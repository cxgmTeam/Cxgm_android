package com.cxgm.app.ui.view.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
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
import com.cxgm.app.utils.ViewHelper;
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
    @BindView(R.id.layoutSumDiscounts)
    LinearLayout layoutSumDiscounts;

//    int mPageNum = 1;
    List<ShopCart> mCartList;
    CartGoodsAdapter mCartAdapter;
    List<Integer> mCheckedCartIdList;//选中的ID

    //用于拦截OnShopCartActionListener.onChangeCheck方法更新数据时
    // cbCheckAll的setOnCheckedChangeListener方法
    boolean mInterceptChecked = false;
    @BindView(R.id.layoutGoodsList)
    LinearLayout layoutGoodsList;
    @BindView(R.id.layoutEmptyShopCart)
    LinearLayout layoutEmptyShopCart;

    int mTextViewWidth;

    boolean isLoadData = false;

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            reset();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reset();
    }

    private void reset() {
        if (!isLoadData) {
            isLoadData = true;
//            mPageNum = 1;
            saveCheckedShopCartId();
            mCartList.clear();
            loadData();
            cbCheckAll.setChecked(false);
            ViewHelper.updateShopCart(getActivity());
        }
    }

    private void init() {
        tvTitle.setText(R.string.shop_cart);
        tvAction1.setText(R.string.delete);
        tvAction1.setVisibility(View.VISIBLE);

        mCartList = new ArrayList<>();
        mCheckedCartIdList = new ArrayList<>();
        mCartAdapter = new CartGoodsAdapter(mCartList, this);
        lvGoods.setAdapter(mCartAdapter);
        lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewJump.toGoodsDetail(getActivity(), mCartList.get((int) id).getProductId());
            }
        });
        srl.setEnableRefresh(true);
        srl.setEnableLoadMore(false);
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mPageNum++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mPageNum = 1;
                saveCheckedShopCartId();
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
                    loadBottomData();
                    mCartAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void loadData() {
        if (UserManager.isUserLogin() && Constants.currentShopId != 0) {
            new ShopCartListReq(getActivity(), Constants.currentShopId)
                    .execute(new Request.RequestCallback<PageInfo<ShopCart>>() {
                        @Override
                        public void onSuccess(PageInfo<ShopCart> shopCartPageInfo) {
                            if (shopCartPageInfo != null && shopCartPageInfo.getList() != null && shopCartPageInfo.getList().size() > 0) {
                                layoutGoodsList.setVisibility(View.VISIBLE);
                                layoutEmptyShopCart.setVisibility(View.GONE);
                                mCartList.addAll(shopCartPageInfo.getList());
                                syncCheckedShopCart();
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
                            isLoadData = false;
                            srl.finishLoadMore();
                            srl.finishRefresh();
                            if (mCartList.size() <= 0) {
                                layoutGoodsList.setVisibility(View.GONE);
                                layoutEmptyShopCart.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else
            isLoadData = false;
    }

    /**
     * 同步选中的状态，状态集
     */
    private void syncCheckedShopCart(){
        if (mCartList!=null && mCheckedCartIdList!=null){
            for (int i :mCheckedCartIdList){
                for (ShopCart shopCart : mCartList){
                    if (shopCart.getId() == i){
                        shopCart.isChecked = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * 保存选中阙状态
     */
    private void saveCheckedShopCartId(){
        if (mCartList!=null ){
            if (mCheckedCartIdList == null)
                mCheckedCartIdList = new ArrayList<>();
            mCheckedCartIdList.clear();
            for (ShopCart shopCart : mCartList){
                if (shopCart.isChecked)
                    mCheckedCartIdList.add(shopCart.getId());
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onUpdateGoods(final ShopCart cartGoods, int actionNum) {

        //限制最小为1
        if (cartGoods.getGoodNum() + actionNum <= 0) {
            ToastManager.sendToast(getString(R.string.num_not_0));
            return;
        }

        if ((cartGoods.getGoodNum() + actionNum) > 0) {
            final ShopCart cart = cartGoods.clone();
            cart.setGoodNum(cart.getGoodNum() + actionNum);
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
        } else {
            //如果限制最小为1 的话，删除 逻辑就走不到了
            new DeleteCartReq(getActivity(), cartGoods.getId() + "").execute(false, new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    mCartList.remove(cartGoods);
                    mCartAdapter.notifyDataSetChanged();
                    if (mCartList.size() == 0) {
                        layoutGoodsList.setVisibility(View.GONE);
                        layoutEmptyShopCart.setVisibility(View.VISIBLE);
                    }
                    loadBottomData();
                    if (mCartList.size() <= 0) {
                        layoutGoodsList.setVisibility(View.GONE);
                        layoutEmptyShopCart.setVisibility(View.VISIBLE);
                    }
                    ViewHelper.updateShopCart(getActivity());
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
            for (int i = 0; i < mCartList.size(); i++) {
                if (i != postion && mCartList.get(i).isChecked != isChecked) {
                    isCheckedAll = false;
                    break;
                }
            }
            cbCheckAll.setChecked(isCheckedAll);
        }
        mInterceptChecked = false;
        loadBottomData();
    }

    private void loadBottomData() {
        float totalAmount = 0.00f;
        float totalOriginal = 0.00f;
        int totalNum = 0;
        for (ShopCart cart : mCartList) {
            if (cart.isChecked) {
                totalAmount = Helper.moneyAdd(totalAmount, cart.getAmount());
                totalOriginal = Helper.moneyAdd(Helper.moneyMultiply((cart.getOriginalPrice() == 0 ? cart.getPrice():cart.getOriginalPrice()), cart.getGoodNum()), totalOriginal);
            /* 促销活动的不要了
                //满减
                if (cart.getCoupon()!=null) {
                    if (cart.getAmount()>100) {
                        float discountsAfterAmount = Helper.calculateDiscounted(cart.getAmount(),cart.getCoupon().getPriceExpression());
                        totalDiscounts += Helper.moneySubtract(cart.getAmount(),discountsAfterAmount);
                    }
                }
            */
                totalNum++;
            }
        }

        tvTotal.setText(getString(R.string.total_, StringHelper.getRMBFormat(totalAmount)));
        tvSum.setText(getString(R.string.sum_, StringHelper.getRMBFormat(totalOriginal)));
        tvDiscounts.setText(getString(R.string.discounts_, StringHelper.getRMBFormat(Helper.moneySubtract(totalOriginal, totalAmount))));
        //更新布局
        tvDiscounts.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvDiscounts.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                float textWidth = tvDiscounts.getPaint().measureText(tvDiscounts.getText().toString());
                if (mTextViewWidth == 0) {
                    int textViewWidth = tvDiscounts.getWidth();
                    if (textViewWidth < textWidth) {
                        mTextViewWidth = textViewWidth;
                        layoutSumDiscounts.setOrientation(LinearLayout.VERTICAL);
                    }
                } else {
                    if (mTextViewWidth < textWidth) {
                        layoutSumDiscounts.setOrientation(LinearLayout.VERTICAL);
                    } else {
                        layoutSumDiscounts.setOrientation(LinearLayout.HORIZONTAL);
                    }
                }
            }
        });
        if (totalNum > 0)
            tvGoDuoShou.setText(getString(R.string.go_duoshou_, totalNum));
        else
            tvGoDuoShou.setText(R.string.go_duoshou);
    }

    @OnClick({R.id.tvGoDuoShou, R.id.tvGoShopping, R.id.tvAction1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvGoDuoShou:
                ArrayList<OrderProduct> products = new ArrayList<>();
                for (ShopCart cart : mCartList) {
                    if (cart.isChecked) {
                        products.add(new OrderProduct(cart));
                    }
                }
                if (products.size() > 0) {
                    ViewJump.toVerifyOrder(getActivity(), products);
                } else {
                    ToastManager.sendToast(getString(R.string.do_not_have_selected_goods));
                }

                break;
            case R.id.tvGoShopping:
                ((MainActivity) getActivity()).publicChangeView(R.id.rbGoods);
                break;
            case R.id.tvAction1:
                if (!UserManager.isUserLogin()) {
                    ViewJump.toLogin(getActivity());
                    return;
                }
                //删除
                StringBuilder builder = new StringBuilder();
                for (ShopCart cart : mCartList) {
                    if (cart.isChecked)
                        builder.append(cart.getId() + ",");
                }
                if (builder.length() > 0) {
                    //提示框
                    new AlertDialog.Builder(getActivity()).setTitle(R.string.hint)
                            .setMessage(R.string.delete_tag).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String ids = builder.deleteCharAt(builder.length() - 1).toString();
                            new DeleteCartReq(getActivity(), ids).execute(new Request.RequestCallback<Integer>() {
                                @Override
                                public void onSuccess(Integer integer) {
                                    for (int i = mCartList.size() - 1; i >= 0; i--) {
                                        if (mCartList.get(i).isChecked) {
                                            mCartList.remove(i);
                                        }
                                    }
                                    mCartAdapter.notifyDataSetChanged();
                                    loadBottomData();

                                    if (mCartList.size() <= 0) {
                                        layoutGoodsList.setVisibility(View.GONE);
                                        layoutEmptyShopCart.setVisibility(View.VISIBLE);
                                    }
                                    ViewHelper.updateShopCart(getActivity());
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
                    }).setNegativeButton(R.string.cancel, null).show();

                } else {
                    ToastManager.sendToast(getString(R.string.nothing_selected));
                }

                break;
        }
    }
}
