package com.cxgm.app.ui.view.goods;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.StringHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsSpecificationDialogActivity extends BaseActivity {

    @BindView(R.id.imgCover)
    ImageView imgCover;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvUnit)
    TextView tvUnit;
    @BindView(R.id.tvOriginPlace)
    TextView tvOriginPlace;
    @BindView(R.id.tvSelectNum)
    TextView tvSelectNum;
    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.tvSpecification)
    TextView tvSpecification;
    @BindView(R.id.tvNumUnitTag)
    TextView tvNumUnitTag;
    @BindView(R.id.tvMinus)
    TextView tvMinus;
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.tvPlus)
    TextView tvPlus;
    @BindView(R.id.tvAddShopCart)
    TextView tvAddShopCart;

    ProductTransfer mProduct;
    int mNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_goods_specification_dialog);
        ButterKnife.bind(this);
        mProduct = (ProductTransfer) getIntent().getSerializableExtra("product");
        init();
    }

    private void init() {
        Glide.with(this).load(mProduct.getImage()).apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(imgCover);
        tvPrice.setText(StringHelper.getRMBFormat(mProduct.getPrice()));
        tvUnit.setText("/"+mProduct.getUnit());
        tvOriginPlace.setText(StringHelper.getStrikeFormat(StringHelper.getRMBFormat(mProduct.getOriginalPrice())));
        tvSelectNum.setText(getString(R.string.select_,mNum+mProduct.getUnit()));//数量要实时更新
        tvSpecification.setText(getString(R.string.specification_,StringHelper.getWeight(mProduct.getWeight())+"/"+mProduct.getUnit()));
        tvNumUnitTag.setText(getString(R.string.buy_num,mProduct.getUnit()));

    }

    @OnClick({R.id.imgClose, R.id.tvMinus, R.id.tvPlus, R.id.tvAddShopCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                finish();
                break;
            case R.id.tvMinus:
                if (mNum>1){
                    mNum--;
                }
                break;
            case R.id.tvPlus:
                //TODO 最大数量
                mNum++;
                break;
            case R.id.tvAddShopCart:
                //TODO

                finish();
                break;
        }
    }
}
