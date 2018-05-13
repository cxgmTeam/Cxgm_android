package com.cxgm.app.ui.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.common.ShopListReq;
import com.cxgm.app.data.io.goods.FindFirstCategoryReq;
import com.cxgm.app.data.io.goods.FindHotProductReq;
import com.cxgm.app.data.io.goods.FindNewProductReq;
import com.cxgm.app.data.io.goods.FindTopProductReq;
import com.cxgm.app.ui.adapter.FirstCategoryAdapter;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.adapter.GoodsHorizontalAdapter;
import com.cxgm.app.ui.adapter.ShopAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.Helper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.deanlib.ootb.widget.HorizontalListView;
import com.deanlib.ootb.widget.ListViewForScrollView;
import com.kevin.loopview.AdLoopView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.LoopData;
import com.kevin.loopview.utils.JsonTool;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;

/**
 * 首页
 *
 * @anthor dean
 * @time 2018/4/18 下午5:46
 */

public class IndexFragment extends BaseFragment {

    @BindView(R.id.imgLocation)
    ImageView imgLocation;
    @BindView(R.id.etSearchWord)
    EditText etSearchWord;
    @BindView(R.id.imgTextClear)
    ImageView imgTextClear;
    @BindView(R.id.imgMessage)
    ImageView imgMessage;
    @BindView(R.id.layoutAppbar)
    View layoutAppbar;
    @BindView(R.id.layoutMessage)
    LinearLayout layoutMessage;

    @BindView(R.id.loopBanner)
    AdLoopView loopBanner;
    @BindView(R.id.lvShop)
    ListViewForScrollView lvShop;

    @BindView(R.id.layoutShopShow)
    LinearLayout layoutShopShow;
    @BindView(R.id.gvFirstCategory)
    GridViewForScrollView gvFirstCategory;
    @BindView(R.id.tvNewsContent)
    TextView tvNewsContent;
    @BindView(R.id.hlvRecommend)
    HorizontalListView hlvRecommend;
    @BindView(R.id.hlvNewGoods)
    HorizontalListView hlvNewGoods;
    @BindView(R.id.layoutGoodsShow)
    LinearLayout layoutGoodsShow;

    @BindView(R.id.imgAd1)
    ImageView imgAd1;
    @BindView(R.id.hlvAdGoods1)
    HorizontalListView hlvAdGoods1;
    @BindView(R.id.imgAd2)
    ImageView imgAd2;
    @BindView(R.id.hlvAdGoods2)
    HorizontalListView hlvAdGoods2;
    @BindView(R.id.imgAd3)
    ImageView imgAd3;
    @BindView(R.id.hlvAdGoods3)
    HorizontalListView hlvAdGoods3;
    @BindView(R.id.gvGoods)
    GridViewForScrollView gvGoods;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    Unbinder unbinder;

    PopupWindow popupWindow;

    FirstCategoryAdapter mFCAdapter;
    List<ShopCategory> mFCList;

    GoodsHorizontalAdapter mTopProductAdapter;
    List<ProductTransfer> mTopProductList;
    GoodsHorizontalAdapter mNewProductAdapter;
    List<ProductTransfer> mNewProductList;
    GoodsAdapter mHotProductAdapter;
    List<ProductTransfer> mHotProductList;

    int mShopListPageNum = 1;
    List<Shop> mShopList;
    ShopAdapter mShopAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        loadData();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden && popupWindow!=null && popupWindow.isShowing())
            popupWindow.dismiss();
    }

    private void init() {

        etSearchWord.setFocusable(false);
        etSearchWord.setKeyListener(null);

        loopBanner.setOnClickListener(new BaseLoopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
                loopBanner.getLoopData();
            }
        });

        //地址提示
        if (Constants.checkAddress && Constants.currentLocation!=null) {
            showPopLocationInfo(getString(R.string.destination_,
                    Constants.currentLocation.getDistrict()
                            + Constants.currentLocation.getStreet()
                            + Constants.currentLocation.getLocationDescribe()));
        }else {
            showPopLocationInfo(getString(R.string.out_of_distribution));
        }

        if (Constants.currentShop == null) {
            //无可配送商铺
            layoutShopShow.setVisibility(View.VISIBLE);
            layoutGoodsShow.setVisibility(View.GONE);

            mShopList = new ArrayList<>();
            mShopAdapter = new ShopAdapter(mShopList);
            lvShop.setAdapter(mShopAdapter);
            lvShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Constants.currentShop = mShopList.get((int)id);
                    init();
                    loadData();
                }
            });

            srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mShopListPageNum++;
                    loadData();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mShopListPageNum = 1;
                    mShopList.clear();
                    loadData();
                }
            });

        } else {
            layoutShopShow.setVisibility(View.GONE);
            layoutGoodsShow.setVisibility(View.VISIBLE);

            //First Category
            mFCList = new ArrayList<>();
            mFCAdapter = new FirstCategoryAdapter(mFCList);
            gvFirstCategory.setAdapter(mFCAdapter);
            //精品推荐
            mTopProductList = new ArrayList<>();
            mTopProductAdapter = new GoodsHorizontalAdapter(mTopProductList);
            hlvRecommend.setAdapter(mTopProductAdapter);
            //新品上市
            mNewProductList = new ArrayList<>();
            mNewProductAdapter = new GoodsHorizontalAdapter(mNewProductList);
            hlvNewGoods.setAdapter(mNewProductAdapter);
            //热销推荐
            mHotProductList = new ArrayList<>();
            mHotProductAdapter = new GoodsAdapter(mHotProductList, 2, 30);
            gvGoods.setAdapter(mHotProductAdapter);
        }

    }

    private void loadData() {
//        LoopData loopData = JsonTool.toBean("", LoopData.class);
//        loopBanner.refreshData(loopData);
//        loopBanner.startAutoLoop();

        if (Constants.currentShop == null) {
            //商铺列表
            new ShopListReq(getActivity(),mShopListPageNum,10)
                    .execute(new Request.RequestCallback<PageInfo<Shop>>() {
                        @Override
                        public void onSuccess(PageInfo<Shop> shopPageInfo) {
                            if (shopPageInfo!=null && shopPageInfo.getList()!=null){
                                mShopList.addAll(shopPageInfo.getList());
                                mShopAdapter.notifyDataSetChanged();
                            }else {
                                if (srl.getState().isFooter)
                                    srl.finishLoadMoreWithNoMoreData();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            mShopListPageNum = Helper.getUnsuccessPageNum(mShopListPageNum);
                        }

                        @Override
                        public void onCancelled(Callback.CancelledException cex) {
                            mShopListPageNum = Helper.getUnsuccessPageNum(mShopListPageNum);
                        }

                        @Override
                        public void onFinished() {
                            srl.finishLoadMore();
                            srl.finishRefresh();
                        }
                    });
        } else {
            //TODO 第一分类 接口错误，待修改
            new FindFirstCategoryReq(getActivity(), Constants.currentShop.getId()).execute(new Request.RequestCallback<List<ShopCategory>>() {
                @Override
                public void onSuccess(List<ShopCategory> list) {
                    if (list != null) {
                        mFCList.addAll(list);
                        mFCAdapter.notifyDataSetChanged();
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

                }
            });

            //精品推荐
            new FindTopProductReq(getActivity(), Constants.currentShop.getId(), 1, 10).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
                @Override
                public void onSuccess(PageInfo<ProductTransfer> productTransferPageInfo) {
                    if (productTransferPageInfo != null && productTransferPageInfo.getList() != null) {
                        mTopProductList.addAll(productTransferPageInfo.getList());
                        mTopProductAdapter.notifyDataSetChanged();
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

                }
            });

            //新品上市
            new FindNewProductReq(getActivity(), Constants.currentShop.getId(), 1, 10).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
                @Override
                public void onSuccess(PageInfo<ProductTransfer> productTransferPageInfo) {
                    if (productTransferPageInfo != null && productTransferPageInfo.getList() != null) {
                        mNewProductList.addAll(productTransferPageInfo.getList());
                        mNewProductAdapter.notifyDataSetChanged();
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

                }
            });

            //热销推荐
            new FindHotProductReq(getActivity(), Constants.currentShop.getId(), 1, 10).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
                @Override
                public void onSuccess(PageInfo<ProductTransfer> productTransferPageInfo) {
                    if (productTransferPageInfo != null && productTransferPageInfo.getList() != null) {
                        mHotProductList.addAll(productTransferPageInfo.getList());
                        mHotProductAdapter.notifyDataSetChanged();
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

                }
            });
        }
    }

    private void showPopLocationInfo(final String message) {
        x.task().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (popupWindow == null) {
                    popupWindow = new PopupWindow();
                    popupWindow.setContentView(View.inflate(getActivity(), R.layout.layout_location_info, null));
                    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                TextView tvContent = popupWindow.getContentView().findViewById(R.id.tvContent);
                tvContent.setText(message);
                if (!popupWindow.isShowing())
                    popupWindow.showAsDropDown(imgLocation, DensityUtil.dip2px(6), DensityUtil.dip2px(-6));
            }
        }, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgLocation, R.id.etSearchWord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgLocation:
                ViewJump.toAddrList(getActivity());
                break;
            case R.id.etSearchWord:
                ViewJump.toSearch(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case ViewJump.CODE_ADDR_LIST:
                    //更新位置以及对应商铺以及对应的商品
                    new CheckAddressReq(getActivity(),Constants.currentLocation.getLongitude()+"",Constants.currentLocation.getLatitude()+"")
                            .execute(new Request.RequestCallback<List<Shop>>() {
                                @Override
                                public void onSuccess(List<Shop> shops) {
                                    if (shops!=null && shops.size()>0){
                                        Constants.checkAddress = true;
                                        //todo 不能更新currentShop，用户可以只点了重新定位，而没有在地图上选择
                                        //返回得到的定位信息内仍然可能没有商铺
                                        //或者有商铺，而非用户最开始选择的商铺，直接替换，是否需要提示给用户
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
                                    init();
                                    loadData();
                                }
                            });
                    break;
            }
        }
    }
}
