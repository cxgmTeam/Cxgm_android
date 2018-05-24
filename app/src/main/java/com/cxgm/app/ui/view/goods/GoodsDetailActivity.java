package com.cxgm.app.ui.view.goods;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.io.goods.FindProductDetailReq;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.kevin.loopview.AdLoopView;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 *
 * @anthor Dean
 * @time 2018/4/22 0022 17:17
 */
public class GoodsDetailActivity extends BaseActivity {

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
    @BindView(R.id.tvSpecification)
    TextView tvSpecification;
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
    @BindView(R.id.layoutGoodsDetailPic)
    LinearLayout layoutGoodsDetailPic;
    @BindView(R.id.gvGoods)
    GridViewForScrollView gvGoods;

    int mProductId;
    ProductTransfer mProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        mProductId = getIntent().getIntExtra("productId",0);
        init();
        loadData();
    }

    private void init(){
        imgBack.setVisibility(View.VISIBLE);
        imgAction1.setImageResource(R.mipmap.shop_cart3);
        imgAction1.setVisibility(View.VISIBLE);

//        gvGoods.setAdapter(new GoodsAdapter(2,30));
    }

    private void loadData(){
        if (mProductId>0){
            new FindProductDetailReq(this,mProductId).execute(new Request.RequestCallback<ProductTransfer>() {
                @Override
                public void onSuccess(ProductTransfer product) {
                    if (product!=null){
                        mProduct = product;
                        tvGoodsTitle.setText(mProduct.getName());
                        tvGoodsSubTitle.setText(mProduct.getIntroduction());
                        tvPrice.setText(StringHelper.getRMBFormat(mProduct.getPrice()));
                        tvUnit.setText("/"+mProduct.getUnit());
                        tvOriginal.setText(StringHelper.getStrikeFormat(StringHelper.getRMBFormat(mProduct.getOriginalPrice())));

                        //TODO 限时特惠的标记 原价与现价的不同？

                        //规格
                        layoutSpecification.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO 规格
                            }
                        });

                        tvTrademark.setText(mProduct.getBrandName());
                        tvOriginPlace.setText(mProduct.getOriginPlace());
                        tvProducedDate.setText(mProduct.getCreationDate());
                        //todo 保质期
//                        tvShelflife.setText(mProduct.get);
                        tvStorageCondition.setText(mProduct.getStorageCondition());

                        //todo 有两个地方需要显示图片 productImageList 只有一个

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

            //TODO 猜你喜欢
        }
    }

    @OnClick({R.id.imgBack, R.id.imgAction1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAction1:
                ViewJump.toMain(this,R.id.rbShopCart);
                break;
        }
    }
}
