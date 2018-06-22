package com.cxgm.app.ui.view.goods;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductImage;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.goods.FindProductDetailReq;
import com.cxgm.app.data.io.goods.PushProductsReq;
import com.cxgm.app.data.io.order.ShopCartListReq;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.widget.CustomScrollView;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.WebViewUtils;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.kevin.loopview.AdLoopView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.LoopData;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 *
 * @anthor Dean
 * @time 2018/4/22 0022 17:17
 */
public class GoodsDetailActivity extends BaseActivity implements ViewHelper.OnShopCartUpdateListener {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.imgAction2)
    ImageView imgAction2;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.tabNavigation)
    TabLayout tabNavigation;
    @BindView(R.id.loopBanner)
    AdLoopView loopBanner;
    @BindView(R.id.tvPicNum)
    TextView tvPicNum;
    @BindView(R.id.tvGoodsTitle)
    TextView tvGoodsTitle;
    @BindView(R.id.tvGoodsSubTitle)
    TextView tvGoodsSubTitle;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvUnit)
    TextView tvUnit;
    @BindView(R.id.tvOriginal)
    TextView tvOriginal;
    @BindView(R.id.imgDiscounts)
    ImageView imgDiscounts;
//    @BindView(R.id.tvSpecification)
//    TextView tvSpecification;
    @BindView(R.id.layoutSpecification)
    LinearLayout layoutSpecification;
    @BindView(R.id.tvTrademark)
    TextView tvTrademark;
    @BindView(R.id.tvOriginPlace)
    TextView tvOriginPlace;
    @BindView(R.id.tvProducedDate)
    TextView tvProducedDate;
    @BindView(R.id.tvShelflife)
    TextView tvShelflife;
    @BindView(R.id.tvStorageCondition)
    TextView tvStorageCondition;
    @BindView(R.id.wvIntroduction)
    WebView wvIntroduction;
    @BindView(R.id.gvGoods)
    GridViewForScrollView gvGoods;
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.layoutGoods)
    LinearLayout layoutGoods;
    @BindView(R.id.tvGuessLike)
    TextView tvGuessLike;
    @BindView(R.id.imgToTop)
    ImageView imgToTop;
    @BindView(R.id.tvAddShopCart)
    TextView tvAddShopCart;

    int mProductId;
    ProductTransfer mProduct;
    List<ProductTransfer> mGuessLikeList;
    GoodsAdapter mGuessLikeAdapter;

    boolean isScrollLock = false;
    //    boolean isSelectLock = false;
    int mBannerPosition = 1;
    int mShopCartNum = 0; //种类数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        mProductId = getIntent().getIntExtra("productId", 0);
        ViewHelper.addOnShopCartUpdateListener(this);
        init();
        loadData();
    }

    private void init() {
        tvTitle.setAlpha(0);
        imgBack.setVisibility(View.VISIBLE);
        imgAction1.setImageResource(R.mipmap.shop_cart3);
        imgAction1.setVisibility(View.VISIBLE);

        scrollView.setOnScrollChangeListener(new CustomScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(CustomScrollView scrollView, int l, int t, int oldl, int oldt) {
                float height = layoutGoods.getTop() - 500f;
                if (t > height) {
                    if (tabNavigation.getVisibility() == View.GONE)
                        tabNavigation.setVisibility(View.VISIBLE);
                    if (t < layoutGoods.getTop())
                        tabNavigation.setAlpha(((float) t - height) / 500f);
                    if (imgToTop.getVisibility() == View.GONE)
                        imgToTop.setVisibility(View.VISIBLE);
                } else {
                    if (tabNavigation.getVisibility() == View.VISIBLE)
                        tabNavigation.setVisibility(View.GONE);
                    if (imgToTop.getVisibility() == View.VISIBLE)
                        imgToTop.setVisibility(View.GONE);
                    tvTitle.setAlpha(((float) t) / height);
                }
                if (!isScrollLock) {
//                    isSelectLock = true;
                    int position = 0;
                    if (t > tvGuessLike.getTop()) {
                        position = 2;
                    } else if (t > wvIntroduction.getTop()) {
                        position = 1;
                    } else if (t > layoutGoods.getTop()) {
                        position = 0;
                    }
                    tabNavigation.setScrollPosition(position, 0, true);
//                    isSelectLock = false;
                }
            }
        });

        tabNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if (!isSelectLock) {
                isScrollLock = true;
                int scrollY = 0;
                switch (tab.getPosition()) {
                    case 0://商品
                        scrollY = layoutGoods.getTop();
                        break;
                    case 1://详情
                        scrollY = wvIntroduction.getTop();
                        break;
                    case 2://推荐
                        scrollY = tvGuessLike.getTop();
                        break;
                }
                scrollView.smoothScrollTo(0, scrollY + 5);
                isScrollLock = false;
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //猜你喜欢
        mGuessLikeList = new ArrayList<>();
        mGuessLikeAdapter = new GoodsAdapter(this, mGuessLikeList, 2, 30);
        gvGoods.setFocusable(false);
        gvGoods.setAdapter(mGuessLikeAdapter);
        gvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewJump.toGoodsDetail(GoodsDetailActivity.this, mGuessLikeList.get((int) id).getId());
            }
        });
    }

    private void loadData() {
        if (Constants.currentShop != null && mProductId > 0) {
            new FindProductDetailReq(this, mProductId, Constants.currentShop.getId()).execute(new Request.RequestCallback<ProductTransfer>() {
                @Override
                public void onSuccess(ProductTransfer product) {
                    if (product != null) {
                        mProduct = product;
                        tvTitle.setText(mProduct.getName());
                        tvGoodsTitle.setText(mProduct.getName());
                        tvGoodsSubTitle.setText(mProduct.getFullName());
                        tvPrice.setText(StringHelper.getRMBFormat(mProduct.getPrice()));
                        tvUnit.setText("/" + mProduct.getUnit());
                        if (mProduct.getPrice() != mProduct.getOriginalPrice()) {
                            tvOriginal.setText(StringHelper.getStrikeFormat(StringHelper.getRMBFormat(mProduct.getOriginalPrice())));
                            tvOriginal.setVisibility(View.VISIBLE);
                        } else tvOriginal.setVisibility(View.GONE);

                        // 限时特惠的标记 原价与现价的不同
                        if (mProduct.getPrice() < mProduct.getOriginalPrice()) {
                            imgDiscounts.setVisibility(View.VISIBLE);
                        } else {
                            imgDiscounts.setVisibility(View.GONE);
                        }

                        //规格
//                        if (mActionNum > 0)
//                            tvSpecification.setText(getString(R.string.select_, mActionNum + mProduct.getUnit()));
                        layoutSpecification.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ViewJump.toGoodsSpecificationDialog(GoodsDetailActivity.this, mProduct);
                            }
                        });

                        tvTrademark.setText(mProduct.getBrandName());
                        tvOriginPlace.setText(mProduct.getOriginPlace());
                        tvProducedDate.setText(mProduct.getCreationDate());
                        //保质期
                        tvShelflife.setText(mProduct.getWarrantyPeriod());
                        tvStorageCondition.setText(mProduct.getStorageCondition());

                        //商品轮播图
                        LoopData loopData = new LoopData();
                        loopData.items = new ArrayList<>();
                        if (mProduct.getProductImageList() != null) {
                            for (ProductImage image : mProduct.getProductImageList()) {
                                loopData.items.add(loopData.new ItemData("", image.getUrl(), "", "", ""));
                            }
                        } else {
                            loopData.items.add(loopData.new ItemData("", mProduct.getImage(), "", "", ""));
                        }

                        loopBanner.setLoopLayout(R.layout.layout_goods_loop);
                        loopBanner.refreshData(loopData);
                        loopBanner.startAutoLoop();
                        loopBanner.setOnClickListener(new BaseLoopAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
                                loopBanner.getLoopData();
                            }
                        });
                        tvPicNum.setText(mBannerPosition + "/" + loopBanner.getLoopData().items.size());
                        loopBanner.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {

                                tvPicNum.setText((mBannerPosition = getBannerPosition(mBannerPosition, loopBanner.getLoopData().items.size())) + "/" + loopBanner.getLoopData().items.size());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });


                        //商品介绍图
//                        Glide.with(GoodsDetailActivity.this).load(mProduct.getIntroduction()).apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
//                                .into(imgGoodsDetailPic);
                        wvIntroduction.loadData(WebViewUtils.getFitPicContent(mProduct.getIntroduction()), "text/html; charset=UTF-8", null);


                        //猜你喜欢
                        new PushProductsReq(GoodsDetailActivity.this, Constants.currentShop.getId(), mProduct.getProductCategoryTwoId(), mProduct.getProductCategoryThirdId())
                                .execute(new Request.RequestCallback<List<ProductTransfer>>() {
                                    @Override
                                    public void onSuccess(List<ProductTransfer> productTransfers) {
                                        if (productTransfers != null) {
                                            mGuessLikeList.addAll(productTransfers);
                                            mGuessLikeAdapter.notifyDataSetChanged();
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

            ViewHelper.updateShopCart(this);

        }
    }


    @OnClick({R.id.imgBack, R.id.imgAction1, R.id.imgToTop, R.id.tvAddShopCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAction1:
                //使用QBageView 导致失效
                ViewJump.toMain(this, R.id.rbShopCart);
                break;
            case R.id.imgToTop:
                isScrollLock = true;
                scrollView.smoothScrollTo(0, loopBanner.getTop());
                isScrollLock = false;
                break;
            case R.id.tvAddShopCart:
                if (mProduct != null) {
                    ViewJump.toGoodsSpecificationDialog(this, mProduct);
//                    if (mActionNum <= 0) {
//                        ViewJump.toGoodsSpecificationDialog(this, mProduct);
//                        return;
//                    }
//                    ViewHelper.addOrUpdateShopCart(this, mProduct, mActionNum, new ViewHelper.OnActionListener() {
//                        @Override
//                        public void onSuccess() {
//                            if (mProduct.getShopCartNum()==mActionNum) {
//                                mShopCartNum++;
//                                ViewHelper.drawShopCartNum(GoodsDetailActivity.this, imgAction1,mShopCartNum);
//                            }
//                        }
//                    });
                    //TODO 加入购物车  duang 加特效
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ViewJump.CODE_GOODS_SPECIFICATION_DIALOG:
                    if (data != null) {
                        int num = data.getIntExtra("num", 0);
                        if (mProduct!=null) {
                            mProduct.setShopCartNum(num);
                            ViewHelper.updateShopCart(this);
//                        if (mActionNum > 0)
//                            tvSpecification.setText(getString(R.string.select_, mActionNum + mProduct.getUnit()));
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("product",mProduct);
        setResult(RESULT_OK,data);
        super.finish();
    }

    /**
     * 自加程序
     *
     * @param position
     * @param maxPosition
     * @return
     */
    private int getBannerPosition(int position, int maxPosition) {
        if (position >= maxPosition) {
            return position = 1;
        }
        return ++position;
    }

    @Override
    public void onUpdate(int num) {
        mShopCartNum = num;
        ViewHelper.drawShopCartNum(GoodsDetailActivity.this, imgAction1, mShopCartNum,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewHelper.removeShopCartUpdateListener(this);
    }
}
