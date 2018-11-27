package com.cxgm.app.ui.view.common;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.Advertisement;
import com.cxgm.app.data.entity.Motion;
import com.cxgm.app.data.entity.Postage;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.common.FindAdvertisementReq;
import com.cxgm.app.data.io.common.FindReportReq;
import com.cxgm.app.data.io.common.ShopListReq;
import com.cxgm.app.data.io.goods.FindFirstCategoryReq;
import com.cxgm.app.data.io.goods.FindHotProductReq;
import com.cxgm.app.data.io.goods.FindMotionReq;
import com.cxgm.app.data.io.goods.FindNewProductReq;
import com.cxgm.app.data.io.goods.FindTopProductReq;
import com.cxgm.app.data.io.order.OrderPostageReq;
import com.cxgm.app.ui.adapter.CategoryPageAdapter;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.adapter.GoodsRecyclerViewAdapter;
import com.cxgm.app.ui.adapter.MotionAdapter;
import com.cxgm.app.ui.adapter.ShopAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.widget.CustomScrollView;
import com.cxgm.app.ui.widget.SpaceItemDecoration;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.FormatUtils;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.deanlib.ootb.widget.ListViewForScrollView;
import com.kevin.loopview.BannerView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.ImageLoader;
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
    BannerView loopBanner;
    @BindView(R.id.lvShop)
    ListViewForScrollView lvShop;

    @BindView(R.id.layoutShopShow)
    LinearLayout layoutShopShow;
//    @BindView(R.id.gvFirstCategory)
//    GridViewForScrollView gvFirstCategory;
    @BindView(R.id.vpFirstCategory)
    ViewPager vpFirstCategory;
    @BindView(R.id.tvNewsContent)
    TextView tvNewsContent;
    @BindView(R.id.hlvRecommend)
    RecyclerView hlvRecommend;
    @BindView(R.id.hlvNewGoods)
    RecyclerView hlvNewGoods;
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
    @BindView(R.id.layoutPopLocation)
    LinearLayout layoutPopLocation;

    Unbinder unbinder;

//    PopupWindow popupWindow;

//    FirstCategoryAdapter mFCAdapter;
//    CategoryPageAdapter mCPAdapter;
//    List<ShopCategory> mFCList;
    @BindView(R.id.imgAD21)
    ImageView imgAD21;
    @BindView(R.id.imgAD22)
    ImageView imgAD22;
    @BindView(R.id.imgAD23)
    ImageView imgAD23;
    @BindView(R.id.layoutAD2)
    LinearLayout layoutAD2;
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.layoutScan)
    LinearLayout layoutScan;
    @BindView(R.id.tvAppTitle)
    TextView tvAppTitle;
    @BindView(R.id.layoutInput)
    LinearLayout layoutInput;
    @BindView(R.id.rgShopType)
    RadioGroup rgShopType;
    @BindView(R.id.rbDefault)
    RadioButton rbDefault;
    @BindView(R.id.rbSales)
    RadioButton rbSales;
    @BindView(R.id.rbDistance)
    RadioButton rbDistance;

    GoodsRecyclerViewAdapter mTopProductAdapter;
    List<ProductTransfer> mTopProductList;
    GoodsRecyclerViewAdapter mNewProductAdapter;
    List<ProductTransfer> mNewProductList;
    GoodsAdapter mHotProductAdapter;
    List<ProductTransfer> mHotProductList;
//    MotionAdapter mMotionAdapter;
//    List<Motion> mMotionList;

    int mShopListPageNum = 1;
    List<Shop> mShopList;
    ShopAdapter mShopAdapter;

    List<Motion> mReportList;//简报
    int mCurrentReportPosition = 0;
    String mOrderType = ShopListReq.TYPE_DEFAULT;


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
        if (mShopList!=null) {
            mShopList.clear();
            mShopListPageNum = 1;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            doShowPop();
            Constants.updatedAddress = false;
            init();
            loadData();
        } else
            dismissPopLocationInfo();
    }

    private void doShowPop(){
        //地址提示
        if (Constants.getEnableDeliveryAddress() && Constants.getLocation(true) != null) {
            showPopLocationInfo(getString(R.string.destination_,
                    Constants.getLocation(true).address));
        } else {
            showPopLocationInfo(getString(R.string.out_of_distribution));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden())
            doShowPop();
        else
            dismissPopLocationInfo();

        if (Constants.updatedAddress){
            Constants.updatedAddress = false;
            init();
            loadData();
        }
        if (loopBanner.getData()!=null && loopBanner.getData().items!=null && loopBanner.getData().items.size()>0)
            loopBanner.startAutoLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissPopLocationInfo();
        loopBanner.stopAutoLoop();
    }

    private void init() {

        etSearchWord.setFocusable(false);
        etSearchWord.setKeyListener(null);

        doShowPop();

        if (Constants.currentShopId == 0) {
            //无可配送商铺
            layoutShopShow.setVisibility(View.VISIBLE);
            layoutGoodsShow.setVisibility(View.GONE);
            tvAppTitle.setVisibility(View.VISIBLE);
            layoutInput.setVisibility(View.GONE);
            layoutScan.setVisibility(View.INVISIBLE);
            rgShopType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    rbDefault.setTypeface(Typeface.DEFAULT);
                    rbSales.setTypeface(Typeface.DEFAULT);
                    rbDistance.setTypeface(Typeface.DEFAULT);
                    ((RadioButton)radioGroup.findViewById(i)).setTypeface(Typeface.DEFAULT_BOLD);
                    //排序
                    switch (i){
                        case R.id.rbDefault:
                            mOrderType = ShopListReq.TYPE_DEFAULT;
                            break;
                        case R.id.rbSales:
                            mOrderType = ShopListReq.TYPE_MONT_SALES;
                            break;
                        case R.id.rbDistance:
                            mOrderType = ShopListReq.TYPE_DISTANCE;
                            break;
                    }
                    loadData();
                }
            });
            rbDefault.setChecked(true);

            mShopList = new ArrayList<>();
            mShopAdapter = new ShopAdapter(mShopList);
            mShopListPageNum = 1;
            lvShop.setAdapter(mShopAdapter);
            lvShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Constants.currentShopId = mShopList.get((int) id).getId();
                    init();
                    loadData();
                    ViewHelper.updateShopCart(getActivity());
                }
            });

            srl.setEnableLoadMore(true);

        } else {
            tvAppTitle.setVisibility(View.GONE);
            layoutInput.setVisibility(View.VISIBLE);
            layoutScan.setVisibility(View.VISIBLE);
            loopBanner.setImageLoader(new ImageLoader() {
                @Override
                public void loadImage(ImageView imageView, String url, int placeholder) {
                    Glide.with(imageView.getContext()).load(url).apply(new RequestOptions()
                            .placeholder(placeholder).error(R.mipmap.default_img)).into(imageView);
                }
            });
            loopBanner.setOnItemClickListener(new BaseLoopAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View view, LoopData.ItemData itemData, int position) {
                    //广告点击事件
                    if ("1".equals(itemData.desc)){
                        String url = itemData.link;
//                        String url = "file:///android_asset/freshfruitbeauty/index.html";
                        if (UserManager.isUserLogin()) {
                            if (url.contains("?")) {
                                url += "&";
                            } else {
                                url += "?";
                            }
                            url += "token=" + UserManager.user.getToken();
                        }
                        ViewJump.toWebView(getActivity(),url);
                    }else if ("2".equals(itemData.desc)){
                        ViewJump.toGoodsDetail(getActivity(),(int) FormatUtils.convertStringToNum(itemData.link));
                    }
                }
            });


            layoutShopShow.setVisibility(View.GONE);
            layoutGoodsShow.setVisibility(View.VISIBLE);

            //First Category
//            mFCList = new ArrayList<>();
//            mCPAdapter = new CategoryPageAdapter(getActivity(),mFCList);
//            vpFirstCategory.setAdapter(mCPAdapter);
            vpFirstCategory.setFocusable(false);
//            gvFirstCategory.setAdapter(mCPAdapter);
//            gvFirstCategory.setFocusable(false);
//            gvFirstCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //分类点击事件
//                    ViewJump.toGoodsSecondClassify(getActivity(),mFCList.get((int)id));
//                }
//            });
            //精品推荐
            mTopProductList = new ArrayList<>();
            mTopProductAdapter = new GoodsRecyclerViewAdapter(getActivity(), mTopProductList);
            LinearLayoutManager recommendLayoutManager = new LinearLayoutManager(getActivity());
            recommendLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            hlvRecommend.setLayoutManager(recommendLayoutManager);
            hlvRecommend.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(5)));
            hlvRecommend.setAdapter(mTopProductAdapter);
            hlvRecommend.setFocusable(false);
            mTopProductAdapter.setOnItemClickListener(new GoodsRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ViewJump.toGoodsDetail(getActivity(), mTopProductList.get(position).getId());
                }
            });
            //新品上市
            mNewProductList = new ArrayList<>();
            mNewProductAdapter = new GoodsRecyclerViewAdapter(getActivity(), mNewProductList);
            LinearLayoutManager newGoodsLayoutManager = new LinearLayoutManager(getActivity());
            newGoodsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            hlvNewGoods.setLayoutManager(newGoodsLayoutManager);
            hlvNewGoods.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(5)));
            hlvNewGoods.setAdapter(mNewProductAdapter);
            hlvNewGoods.setFocusable(false);
            mNewProductAdapter.setOnItemClickListener(new GoodsRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ViewJump.toGoodsDetail(getActivity(), mNewProductList.get(position).getId());
                }
            });
            //热销推荐
            mHotProductList = new ArrayList<>();
            mHotProductAdapter = new GoodsAdapter(getActivity(), mHotProductList, 2, 30);
            gvGoods.setAdapter(mHotProductAdapter);
            gvGoods.setFocusable(false);
            gvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ViewJump.toGoodsDetail(getActivity(), mHotProductList.get((int) id).getId());
                }
            });
            //运营
//            mMotionList = new ArrayList<>();
//            mMotionAdapter = new MotionAdapter(getActivity(), mMotionList);
//            lvMotions.setAdapter(mMotionAdapter);
            lvMotions.setFocusable(false);

            mReportList = new ArrayList<>();

            srl.setEnableLoadMore(false);

        }


        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mShopListPageNum++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mShopListPageNum = 1;
                if (mShopList!=null)
                    mShopList.clear();
                loadData();
            }
        });

        scrollView.setOnScrollChangeListener(new CustomScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
                if (t<10){
                    doShowPop();
                }else {
                    dismissPopLocationInfo();
                }
            }
        });

        x.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0,0);
            }
        },500);

    }

    private void loadData() {


        if (Constants.currentShopId == 0) {
            double longitude = 0;
            double latitude = 0;
            if (Constants.currentLocation!=null) {
                longitude = Constants.currentLocation.getLongitude();
                latitude = Constants.currentLocation.getLatitude();
            }
            //商铺列表
            new ShopListReq(getActivity(), mOrderType,longitude,latitude,mShopListPageNum, 10)
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

//            mFCList.clear();
            mTopProductList.clear();
            mNewProductList.clear();
            mHotProductList.clear();
//            mMotionList.clear();
            mReportList.clear();
            //首页分类
//            mFCList.add(new ShopCategory(113, getString(R.string.first_category_1), "file:///android_asset/category/c1.png"));
//            mFCList.add(new ShopCategory(102, getString(R.string.first_category_2), "file:///android_asset/category/c2.png"));
//            mFCList.add(new ShopCategory(112, getString(R.string.first_category_3), "file:///android_asset/category/c3.png"));
//            mFCList.add(new ShopCategory(109, getString(R.string.first_category_4), "file:///android_asset/category/c4.png"));
//            mFCList.add(new ShopCategory(105, getString(R.string.first_category_5), "file:///android_asset/category/c5.png"));
//            mFCList.add(new ShopCategory(88, getString(R.string.first_category_6), "file:///android_asset/category/c6.png"));
//            mFCList.add(new ShopCategory(115, getString(R.string.first_category_7), "file:///android_asset/category/c7.png"));
//            mFCList.add(new ShopCategory(106, getString(R.string.first_category_8), "file:///android_asset/category/c8.png"));
//            mFCAdapter.notifyDataSetChanged();

            new FindFirstCategoryReq(getActivity(), Constants.currentShopId).execute(new Request.RequestCallback<List<ShopCategory>>() {
                @Override
                public void onSuccess(List<ShopCategory> list) {
                    if (list != null) {
                        vpFirstCategory.setAdapter(new CategoryPageAdapter(getActivity(),list));
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

//            new FindFirstCategoryReq(getActivity(), Constants.currentShop.getId()).execute(new Request.RequestCallback<List<ShopCategory>>() {
//                @Override
//                public void onSuccess(List<ShopCategory> list) {
//                    if (list != null) {
//                        if (list.size()>4){
//                            mFCList.addAll(list.subList(0,4));
//                        }else mFCList.addAll(list);
//
//                        mFCAdapter.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//
//                }
//
//                @Override
//                public void onCancelled(Callback.CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//                     srl.finishRefresh();
//                }
//            });

            //精品推荐
            new FindTopProductReq(getActivity(), Constants.currentShopId, 1, 10).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
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
                    srl.finishRefresh();
                }
            });

            //新品上市
            new FindNewProductReq(getActivity(), Constants.currentShopId, 1, 10).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
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
                    srl.finishRefresh();
                }
            });

            //热销推荐
            new FindHotProductReq(getActivity(), Constants.currentShopId, 1, 10).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
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
                    srl.finishRefresh();
                }
            });

            //轮播广告
            new FindAdvertisementReq(getActivity(), Constants.currentShopId).execute(new Request.RequestCallback<List<Advertisement>>() {
                @Override
                public void onSuccess(List<Advertisement> advertisements) {
                    //广告分组
                    List<Advertisement>[] ads = convertAdvertisements(advertisements);
                    if (ads != null) {
                        //banner
                        if (ads.length>0 && ads[0].size() > 0) {
                            LoopData loopData = new LoopData();
                            loopData.items = new ArrayList<>();
                            for (Advertisement ad : ads[0]) {
                                loopData.items.add(loopData.new ItemData(ad.getImageUrl(), ad.getType(), "2".equals(ad.getType())?ad.getProductCode():ad.getNotifyUrl()));
                            }
//                            LoopData loopData = JsonTool.toBean("", LoopData.class);
                            loopBanner.setData(loopData);
                            loopBanner.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    if (mReportList!=null && mReportList.size()>0) {
                                        mCurrentReportPosition++;
                                        if (mCurrentReportPosition>=mReportList.size())
                                            mCurrentReportPosition = 0;
                                        tvNewsContent.setText(mReportList.get(mCurrentReportPosition).getMotionName());
                                    }
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                            loopBanner.startAutoLoop();
                            loopBanner.setVisibility(View.VISIBLE);
                        } else {
                            loopBanner.setVisibility(View.GONE);
                        }
                        if (ads.length>1) {
                            layoutAD2.setVisibility(View.GONE);
                            layoutAD2Small.setVisibility(View.GONE);
                            imgAD22.setVisibility(View.GONE);
                            imgAD23.setVisibility(View.GONE);
                            switch (ads[1].size()) {
                                case 3:
                                    Glide.with(getActivity()).load(ads[1].get(2).getImageUrl()).apply(new RequestOptions()
                                            .placeholder(R.mipmap.default_img).error(R.mipmap.default_img)).into(imgAD23);
                                    imgAD23.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if ("1".equals(ads[1].get(2).getType())){
                                                ViewJump.toWebView(getActivity(),ads[1].get(2).getNotifyUrl());
                                            }else if ("2".equals(ads[1].get(2).getType())){
                                                ViewJump.toGoodsDetail(getActivity(),(int) FormatUtils.convertStringToNum(ads[1].get(2).getProductCode()));
                                            }
                                        }
                                    });
                                    imgAD23.setVisibility(View.VISIBLE);
                                case 2:
                                    Glide.with(getActivity()).load(ads[1].get(1).getImageUrl()).apply(new RequestOptions()
                                            .placeholder(R.mipmap.default_img).error(R.mipmap.default_img)).into(imgAD22);
                                    imgAD22.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if ("1".equals(ads[1].get(1).getType())){
                                                ViewJump.toWebView(getActivity(),ads[1].get(1).getNotifyUrl());
                                            }else if ("2".equals(ads[1].get(1).getType())){
                                                ViewJump.toGoodsDetail(getActivity(),(int) FormatUtils.convertStringToNum(ads[1].get(1).getProductCode()));
                                            }
                                        }
                                    });
                                    imgAD22.setVisibility(View.VISIBLE);
                                    layoutAD2Small.setVisibility(View.VISIBLE);
                                case 1:
                                    Glide.with(getActivity()).load(ads[1].get(0).getImageUrl()).apply(new RequestOptions()
                                            .placeholder(R.mipmap.default_img).error(R.mipmap.default_img)).into(imgAD21);
                                    imgAD21.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if ("1".equals(ads[1].get(0).getType())){
                                                ViewJump.toWebView(getActivity(),ads[1].get(0).getNotifyUrl());
                                            }else if ("2".equals(ads[1].get(0).getType())){
                                                ViewJump.toGoodsDetail(getActivity(),(int) FormatUtils.convertStringToNum(ads[1].get(0).getProductCode()));
                                            }
                                        }
                                    });
                                    layoutAD2.setVisibility(View.VISIBLE);
                                    break;
                                case 0:
                                    //没有广告时
                                    layoutAD2.setVisibility(View.GONE);

                            }
                        }else {
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
                    srl.finishRefresh();
                }
            });

            //运营数据 todo 加载慢，暂时关闭
            /*new FindMotionReq(getActivity(), Constants.currentShopId).execute(new Request.RequestCallback<List<Motion>>() {
                @Override
                public void onSuccess(List<Motion> motions) {
                    if (motions != null && motions.size() > 0) {
                        //recyclerview的横向布局 第一个item 的间距存在问题，设置脏数据，配合MotionAdapter
                        motions.add(0,motions.get(0));
//                        mMotionList.add(motions.get(0));
//                        mMotionList.addAll(motions);
//                        mMotionAdapter.notifyDataSetChanged();
                        lvMotions.setAdapter(new MotionAdapter(getActivity(), motions));
                        lvMotions.setVisibility(View.VISIBLE);
                    }else {
                        lvMotions.setVisibility(View.GONE);
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
                    srl.finishRefresh();
                }
            });*/

            //简报
            /*new FindReportReq(getActivity(),Constants.currentShopId).execute(new Request.RequestCallback<List<Motion>>() {
                @Override
                public void onSuccess(List<Motion> motions) {
                    if (motions!=null &&  motions.size()>0){
                        mReportList.clear();
                        mReportList.addAll(motions);
                        //这里偷懒，借用banner的动画翻动简报 看 loopBanner.getViewPager().addOnPageChangeListener

                        mCurrentReportPosition = 0;
                        tvNewsContent.setText(mReportList.get(mCurrentReportPosition).getMotionName());

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
            });*/

            //查询邮费
            new OrderPostageReq(getActivity(),Constants.currentShopId).execute(new Request.RequestCallback<Postage>() {
                @Override
                public void onSuccess(Postage postage) {
                    if (postage!=null){
                        Constants.postage = postage;
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
                //使用Pop总是控制不好显示时机，存在一个异常，当APP长时候（几个小时）在后台，
                // 再次启动时（不走Lanuchacitivity），会出现pop泄漏，新旧两个pop，
                // 当切换到MainActivity的其他页面，有一个pop一直显示，不会消失，异常界面。
//                if (!isHidden()) {
//                    if (popupWindow == null) {
//                        popupWindow = new PopupWindow();
//                        popupWindow.setContentView(View.inflate(getActivity(), R.layout.layout_location_info, null));
//                        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//                        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                    }
//                    TextView tvContent = popupWindow.getContentView().findViewById(R.id.tvContent);
//                    tvContent.setText(message);
//                    if (!popupWindow.isShowing() && imgLocation!=null)
//                        popupWindow.showAsDropDown(imgLocation, DensityUtil.dip2px(6), DensityUtil.dip2px(-6));
//                }
                TextView tvContent = layoutPopLocation.findViewById(R.id.tvContent);
                tvContent.setText(message);
                layoutPopLocation.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    private void dismissPopLocationInfo(){
//        if (popupWindow != null && popupWindow.isShowing())
//            popupWindow.dismiss();
        layoutPopLocation.setVisibility(View.GONE);
    }

    @OnClick({R.id.imgLocation, R.id.etSearchWord, R.id.layoutMessage,R.id.tvNewsContent,R.id.layoutScan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgLocation:
                ViewJump.toAddrList(getActivity(), this);
                break;
            case R.id.etSearchWord:
                if (Constants.currentShopId != 0) {
                    ViewJump.toSearch(getActivity());
                } else {
                    ToastManager.sendToast(getString(R.string.choice_shop));
                }
                break;
            case R.id.layoutMessage:
                ViewJump.toMessageList(getActivity());
                break;
            case R.id.tvNewsContent:
                if (mReportList!=null && mReportList.size()>0){
                    Motion motion = mReportList.get(mCurrentReportPosition);
                    //简报点击
                    if ("1".equals(motion.getUrlType())){
                        ViewJump.toWebView(getActivity(),motion.getNotifyUrl());
                    }else if ("2".equals(motion.getUrlType())){
                        ViewJump.toGoodsDetail(getActivity(),(int) FormatUtils.convertStringToNum(motion.getProductCode()));
                    }
                }
                break;
            case R.id.layoutScan:
                //todo 扫码
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
                    if (data!=null) {
                        boolean isDefAddr = data.getBooleanExtra("isDefAddr",false);
                        if (Constants.getLocation(isDefAddr)!=null) {
                            new CheckAddressReq(getActivity(), Constants.getLocation(isDefAddr).location.longitude + "", Constants.getLocation(isDefAddr).location.latitude + "")
                                    .execute(new Request.RequestCallback<List<Shop>>() {
                                        @Override
                                        public void onSuccess(List<Shop> shops) {
                                            if (shops != null && shops.size() > 0) {
                                                if (shops.size() == 1 && Constants.currentShopId != 0
                                                        && shops.get(0).getId() == Constants.currentShopId)
                                                    return;

                                                /**
                                                 //返回得到的定位信息内仍然可能没有商铺
                                                 //或者有商铺，而非用户最开始选择的商铺，直接替换，是否需要提示给用户
                                                 //地址配送区域重叠
                                                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                 builder.setTitle(R.string.according_location_recommend_shop);
                                                 String[] shopNames = new String[shops.size()];
                                                 for (int i = 0; i < shops.size(); i++) {
                                                 shopNames[i] = shops.get(i).getShopName();
                                                 }
                                                 builder.setItems(shopNames, new DialogInterface.OnClickListener() {
                                                @Override public void onClick(DialogInterface dialog, int which) {
                                                Constants.currentShop = shops.get(which);
                                                Constants.setEnableDeliveryAddress(true);//可配送
                                                init();
                                                loadData();
                                                ViewHelper.updateShopCart(getActivity());
                                                }
                                                });
                                                 builder.setNegativeButton(R.string.cancel, null);
                                                 builder.show();
                                                 */
                                                Constants.currentShopId = shops.get(0).getId();
                                                Constants.setEnableDeliveryAddress(true);//可配送
                                                init();
                                                loadData();
                                                ViewHelper.updateShopCart(getActivity());
                                            } else {
                                                if (Constants.currentShopId != 0) {
                                                    //shop 从有到无
                                                /*
                                                new AlertDialog.Builder(getActivity()).setTitle(R.string.hint)
                                                        .setMessage(R.string.show_shop_list).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //取消掉用户指定的地址
                                                        Constants.currentUserLocation = null;
                                                    }
                                                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //shop置Null
                                                        Constants.setEnableDeliveryAddress(false);
                                                        Constants.currentShop = null;
                                                        init();
                                                        loadData();
                                                        ViewHelper.updateShopCart(getActivity());
                                                    }
                                                }).show();
                                                */
                                                    //shop置Null
                                                    Constants.setEnableDeliveryAddress(false);
                                                    Constants.currentShopId = 0;
                                                    init();
                                                    loadData();
                                                    ViewHelper.updateShopCart(getActivity());
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
                        }
                    }
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
