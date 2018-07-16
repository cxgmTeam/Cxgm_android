package com.cxgm.app.ui.view.goods;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductImage;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.io.goods.FindProductDetailReq;
import com.cxgm.app.data.io.goods.PushProductsReq;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.widget.CustomScrollView;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DeviceUtils;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.kevin.loopview.AdLoopView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.LoopData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    //    @BindView(R.id.wvIntroduction)
//    WebView wvIntroduction;
//    @BindView(R.id.lvIntroduction)
//    ListViewForScrollView lvIntroduction;
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
    int mShopId;
    @BindView(R.id.gapTrademark)
    View gapTrademark;
    @BindView(R.id.layoutTrademark)
    LinearLayout layoutTrademark;
    @BindView(R.id.gapOriginPlace)
    View gapOriginPlace;
    @BindView(R.id.layoutOriginPlace)
    LinearLayout layoutOriginPlace;
    @BindView(R.id.gapProducedDate)
    View gapProducedDate;
    @BindView(R.id.layoutProducedDate)
    LinearLayout layoutProducedDate;
    @BindView(R.id.gapShelflife)
    View gapShelflife;
    @BindView(R.id.layoutShelflife)
    LinearLayout layoutShelflife;
    @BindView(R.id.gapStorageCondition)
    View gapStorageCondition;
    @BindView(R.id.layoutStorageCondition)
    LinearLayout layoutStorageCondition;
    @BindView(R.id.layoutIntroduction)
    LinearLayout layoutIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        mProductId = getIntent().getIntExtra("productId", 0);
        if (Constants.currentShop == null) {
            mShopId = getIntent().getIntExtra("shopId", 0);
        } else {
            mShopId = Constants.currentShop.getId();
        }
        ViewHelper.addOnShopCartUpdateListener(this);
        init();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DeviceUtils.backgroundAlpha(this, 1);
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
                    } else if (t > layoutGoods.getTop()) {
                        position = 1;
                    } else if (t > loopBanner.getTop()) {
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
                        scrollY = loopBanner.getTop();
                        break;
                    case 1://详情
                        scrollY = layoutGoods.getTop();
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
                onTabSelected(tab);
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
        if (mShopId != 0 && mProductId > 0) {
            new FindProductDetailReq(this, mProductId, mShopId).execute(new Request.RequestCallback<ProductTransfer>() {
                @Override
                public void onSuccess(ProductTransfer product) {
                    if (product != null) {
                        mProduct = product;
                        tvTitle.setText(mProduct.getName());
                        tvGoodsTitle.setText(mProduct.getName());
                        tvGoodsSubTitle.setText(mProduct.getFullName());
                        tvPrice.setText(StringHelper.getRMBFormat(mProduct.getPrice()));
                        tvUnit.setText("/" + mProduct.getUnit());
                        if (mProduct.getPrice() < mProduct.getOriginalPrice()) {
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

                        if (!TextUtils.isEmpty(mProduct.getBrandName())) {
                            tvTrademark.setText(mProduct.getBrandName());
                            layoutTrademark.setVisibility(View.VISIBLE);
                            gapTrademark.setVisibility(View.VISIBLE);
                        }else {
                            layoutTrademark.setVisibility(View.GONE);
                            gapTrademark.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(mProduct.getOriginPlace())) {
                            tvOriginPlace.setText(mProduct.getOriginPlace());
                            layoutOriginPlace.setVisibility(View.VISIBLE);
                            gapOriginPlace.setVisibility(View.VISIBLE);
                        }else {
                            layoutOriginPlace.setVisibility(View.GONE);
                            gapOriginPlace.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(mProduct.getWarrantyPeriod())) {
                            //保质期
                            tvShelflife.setText(mProduct.getWarrantyPeriod());
                            layoutShelflife.setVisibility(View.VISIBLE);
                            gapShelflife.setVisibility(View.VISIBLE);
                        }else {
                            layoutShelflife.setVisibility(View.GONE);
                            gapShelflife.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(mProduct.getStorageCondition())) {
                            tvStorageCondition.setText(mProduct.getStorageCondition());
                            layoutStorageCondition.setVisibility(View.VISIBLE);
                            gapStorageCondition.setVisibility(View.VISIBLE);
                        }else {
                            layoutStorageCondition.setVisibility(View.GONE);
                            gapStorageCondition.setVisibility(View.GONE);
                        }

                        tvProducedDate.setText(R.string.look_pack);

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

                        /**
                         if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                         wvIntroduction.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                         }

                         //                         *LayoutAlgorithm是一个枚举，用来控制html的布局，总共有三种类型：
                         //                         *NORMAL：正常显示，没有渲染变化。
                         //                         *SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。这个是强制的，把网页都挤变形了
                         //                         *NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。
                         wvIntroduction.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

                         //设置webview推荐使用的窗口，设置为true
                         wvIntroduction.getSettings().setUseWideViewPort(true);

                         //设置webview加载的页面的模式，也设置为true。这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小。
                         wvIntroduction.getSettings().setLoadWithOverviewMode(true);
                         wvIntroduction.getSettings().setDomStorageEnabled(true);
                         wvIntroduction.getSettings().setBlockNetworkImage(false);
                         wvIntroduction.getSettings().setJavaScriptEnabled(true);
                         //                        wvIntroduction.getSettings().setDomStorageEnabled(true);
                         //                        wvIntroduction.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                         //商品介绍图
                         //                        Glide.with(GoodsDetailActivity.this).load(mProduct.getIntroduction()).apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                         //                                .into(imgGoodsDetailPic);
                         if (!android.text.TextUtils.isEmpty(mProduct.getIntroduction())) {
                         //                            String data = TextUtils.getFitPicContent(mProduct.getIntroduction());
                         String data = mProduct.getIntroduction();
                         //                            try {
                         //                                Document doc= Jsoup.parse(data);
                         //                                Elements elements=doc.getElementsByTag("img");
                         //                                for (Element element : elements) {
                         //                                    Pattern pattern = Pattern.compile("http:\\/\\/.+\\.jpg");
                         //                                    Matcher matcher = pattern.matcher(element.attr("src"));
                         //                                    if (matcher.find()){
                         //                                        element.attr("src",matcher.group());
                         //                                    }
                         //
                         //                                }
                         //
                         //                                data =  doc.toString();
                         //                            } catch (Exception e) {
                         //                                e.printStackTrace();
                         //                            }
                         data = "<html><head><style type=\"text/css\">p {margin:-10px 0} img{width:100% !important;}</style></head><body style='margin:0;padding:0'>" + data + "</body></html>";
                         wvIntroduction.loadData(data, "text/html; charset=UTF-8", null);

                         }
                         */

//                        List<String> urlList = new ArrayList<>();
                        layoutIntroduction.removeAllViews();
                        if (!TextUtils.isEmpty(mProduct.getIntroduction())) {
                            String data = mProduct.getIntroduction();
                            try {
                                Document doc = Jsoup.parse(data);
                                Elements elements = doc.getElementsByTag("img");
                                for (Element element : elements) {
//                                    urlList.add(element.attr("src"));
                                    ImageView view = (ImageView) View.inflate(GoodsDetailActivity.this, R.layout.layout_image_item, null);
                                    Glide.with(GoodsDetailActivity.this).load(element.attr("src"))
                                            .apply(new RequestOptions().placeholder(R.mipmap.default_img)
                                                    .error(R.mipmap.default_img)).into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            //System.out.println("mmmmmmmm："+resource.getIntrinsicWidth() +"   "+resource.getIntrinsicHeight());
                                            float height = ((float) DeviceUtils.getSreenWidth()) / (((float) resource.getIntrinsicWidth()) / ((float) resource.getIntrinsicHeight()));
                                            //System.out.println("wwwwww:"+view.getLayoutParams().width +"   "+height);
                                            view.getLayoutParams().width = DeviceUtils.getSreenWidth();
                                            view.getLayoutParams().height = (int) height;
                                            view.setImageDrawable(resource);
                                        }
                                    });
                                    layoutIntroduction.addView(view);
                                }

                                data = doc.toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            lvIntroduction.setAdapter(new ImageListAdapter(urlList));

                        }


                        //猜你喜欢
                        new PushProductsReq(GoodsDetailActivity.this, mShopId, mProduct.getProductCategoryTwoId(), mProduct.getProductCategoryThirdId())
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
                        if (mProduct != null) {
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
        data.putExtra("product", mProduct);
        setResult(RESULT_OK, data);
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
        ViewHelper.drawShopCartNum(GoodsDetailActivity.this, imgAction1, mShopCartNum, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewHelper.removeShopCartUpdateListener(this);
    }

    class ImageListAdapter extends BaseAdapter {
        List<String> urlList;

        public ImageListAdapter(List<String> urlList) {
            this.urlList = urlList;
        }

        @Override
        public int getCount() {
            return urlList.size();
        }

        @Override
        public Object getItem(int position) {
            return urlList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.layout_image_item, null);
            }
            Glide.with(convertView).load(urlList.get(position))
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img)
                            .error(R.mipmap.default_img)).into((ImageView) convertView);

            return convertView;
        }
    }


}
