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
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.kevin.loopview.AdLoopView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        imgBack.setVisibility(View.VISIBLE);
        imgAction1.setImageResource(R.mipmap.shop_cart3);
        imgAction1.setVisibility(View.VISIBLE);

//        gvGoods.setAdapter(new GoodsAdapter(2,30));
    }

    @OnClick({R.id.imgBack, R.id.imgAction1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAction1:
                break;
        }
    }
}
