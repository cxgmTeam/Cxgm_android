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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.Advertisement;
import com.cxgm.app.data.entity.Motion;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.common.FindAdvertisementReq;
import com.cxgm.app.data.io.common.ShopListReq;
import com.cxgm.app.data.io.goods.FindFirstCategoryReq;
import com.cxgm.app.data.io.goods.FindHotProductReq;
import com.cxgm.app.data.io.goods.FindMotionReq;
import com.cxgm.app.data.io.goods.FindNewProductReq;
import com.cxgm.app.data.io.goods.FindTopProductReq;
import com.cxgm.app.ui.adapter.FirstCategoryAdapter;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.adapter.GoodsHorizontalAdapter;
import com.cxgm.app.ui.adapter.MotionAdapter;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.lvMotions)
    ListViewForScrollView lvMotions;
    @BindView(R.id.gvGoods)
    GridViewForScrollView gvGoods;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.layoutAD2Small)
    LinearLayout layoutAD2Small;

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
    MotionAdapter mMotionAdapter;
    List<Motion> mMotionList;

    int mShopListPageNum = 1;
    List<Shop> mShopList;
    ShopAdapter mShopAdapter;
    @BindView(R.id.imgAD21)
    ImageView imgAD21;
    @BindView(R.id.imgAD22)
    ImageView imgAD22;
    @BindView(R.id.imgAD23)
    ImageView imgAD23;
    @BindView(R.id.layoutAD2)
    LinearLayout layoutAD2;

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
        if (hidden && popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }

    private void init() {

        etSearchWord.setFocusable(false);
        etSearchWord.setKeyListener(null);

        //地址提示
        if (Constants.checkAddress && Constants.currentLocation != null) {
            showPopLocationInfo(getString(R.string.destination_,
                    Constants.currentLocation.getDistrict()
                            + Constants.currentLocation.getStreet()
                            + Constants.currentLocation.getLocationDescribe()));
        } else {
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
                    Constants.currentShop = mShopList.get((int) id);
                    init();
                    loadData();
                }
            });
            srl.setEnableRefresh(true);
            srl.setEnableLoadMore(true);
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
            loopBanner.setOnClickListener(new BaseLoopAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
                    loopBanner.getLoopData();
                }
            });

            layoutShopShow.setVisibility(View.GONE);
            layoutGoodsShow.setVisibility(View.VISIBLE);

            srl.setEnableRefresh(false);
            srl.setEnableLoadMore(false);
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
            //运营
            mMotionList = new ArrayList<>();
            mMotionAdapter = new MotionAdapter(mMotionList);
            lvMotions.setAdapter(mMotionAdapter);
        }

    }

    private void loadData() {


        if (Constants.currentShop == null) {
            //商铺列表
            new ShopListReq(getActivity(), mShopListPageNum, 10)
                    .execute(new Request.RequestCallback<PageInfo<Shop>>() {
                        @Override
                        public void onSuccess(PageInfo<Shop> shopPageInfo) {
                            if (shopPageInfo != null && shopPageInfo.getList() != null) {
                                mShopList.addAll(shopPageInfo.getList());
                                mShopAdapter.notifyDataSetChanged();
                            } else {
//                                if (srl.getState().isFooter)
//                                    srl.finishLoadMoreWithNoMoreData();
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

            //轮播广告
            new FindAdvertisementReq(getActivity(), Constants.currentShop.getId()).execute(new Request.RequestCallback<List<Advertisement>>() {
                @Override
                public void onSuccess(List<Advertisement> advertisements) {
                    //广告分组
                    List<Advertisement>[] ads = convertAdvertisements(advertisements);
                    if (ads != null) {
                        //banner
                        if (ads[0].size() > 0) {
                            LoopData loopData = new LoopData();
                            loopData.items = new ArrayList<>();
                            for (Advertisement ad : ads[0]) {
                                loopData.items.add(loopData.new ItemData(ad.getId() + "", ad.getImageUrl(), "", "", ""));
                            }
//                            LoopData loopData = JsonTool.toBean("", LoopData.class);
                            loopBanner.refreshData(loopData);
                            loopBanner.startAutoLoop();
                            loopBanner.setVisibility(View.VISIBLE);
                        } else {
                            loopBanner.setVisibility(View.GONE);
                        }

                        layoutAD2.setVisibility(View.VISIBLE);
                        layoutAD2Small.setVisibility(View.VISIBLE);
                        imgAD22.setVisibility(View.VISIBLE);
                        imgAD23.setVisibility(View.VISIBLE);
                        switch (ads[1].size()) {
                            case 3:
                                Glide.with(getActivity()).load(ads[1].get(2).getImageUrl()).apply(new RequestOptions()
                                        .placeholder(R.mipmap.default_img).error(R.mipmap.default_img)).into(imgAD23);
                            case 2:
                                imgAD23.setVisibility(View.GONE);
                                Glide.with(getActivity()).load(ads[1].get(1).getImageUrl()).apply(new RequestOptions()
                                        .placeholder(R.mipmap.default_img).error(R.mipmap.default_img)).into(imgAD22);
                            case 1:
                                layoutAD2Small.setVisibility(View.GONE);
                                Glide.with(getActivity()).load(ads[1].get(0).getImageUrl()).apply(new RequestOptions()
                                        .placeholder(R.mipmap.default_img).error(R.mipmap.default_img)).into(imgAD21);
                                break;
                            case 0:
                                //没有广告时
                                layoutAD2.setVisibility(View.GONE);

                        }

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

            //运营数据
            new FindMotionReq(getActivity(),Constants.currentShop.getId()).execute(new Request.RequestCallback<List<Motion>>() {
                @Override
                public void onSuccess(List<Motion> motions) {
                    if (motions!=null && motions.size()>0){
                        mMotionList.addAll(motions);
                        mMotionAdapter.notifyDataSetChanged();
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
                    if (mMotionList.size()>0){
                        lvMotions.setVisibility(View.VISIBLE);
                    }else {
                        lvMotions.setVisibility(View.GONE);
                    }
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
                ViewJump.toAddrList(getActivity(), this);
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
                    new CheckAddressReq(getActivity(), Constants.currentLocation.getLongitude() + "", Constants.currentLocation.getLatitude() + "")
                            .execute(new Request.RequestCallback<List<Shop>>() {
                                @Override
                                public void onSuccess(List<Shop> shops) {
                                    if (shops != null && shops.size() > 0) {
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

    private List<Advertisement>[] convertAdvertisements(List<Advertisement> list) {

        if (list != null) {
            List<Advertisement> adList1 = new ArrayList<>();//banner
            List<Advertisement> adList2 = new ArrayList<>();
            List<Advertisement>[] arr = new List[]{adList1, adList2};
            for (Advertisement ad : list) {
                switch (ad.getPosition()) {
                    case "1":
                        adList1.add(ad);
                        break;
                    case "2":
                        adList2.add(ad);
                        break;
                }
            }

            Collections.sort(adList1);
            Collections.sort(adList2);
            return arr;
        }
        return null;
    }
}
