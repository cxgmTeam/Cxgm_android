package com.cxgm.app.ui.view.goods;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 规格选择
 *
 * @anthor Dean
 * @time 2018/5/25 0025 22:10
 */
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
    @BindView(R.id.tvOK)
    TextView tvOK;

    ProductTransfer mProduct;
    int mNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.anim_down_up,R.anim.anim_up_down);
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
        mNum = mProduct.getShopCartNum();
        if (mNum==0) mNum = 1;
        Glide.with(this).load(mProduct.getImage()).apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(imgCover);
        tvPrice.setText(StringHelper.getRMBFormat(mProduct.getPrice()));
        tvUnit.setText("/"+mProduct.getUnit());
        if (mProduct.getPrice()< mProduct.getOriginalPrice()) {
            tvOriginPlace.setText(StringHelper.getStrikeFormat(StringHelper.getRMBFormat(mProduct.getOriginalPrice())));
            tvOriginPlace.setVisibility(View.VISIBLE);
        }else tvOriginPlace.setVisibility(View.GONE);

        tvSelectNum.setText(getString(R.string.select_,mNum+mProduct.getUnit()));//数量要实时更新
        tvSpecification.setText(getString(R.string.specification_,
                StringHelper.getSpecification(mProduct.getWeight(),mProduct.getUnit())));
        tvNumUnitTag.setText(getString(R.string.buy_num,mProduct.getUnit()));
        tvNum.setText(mNum+"");

    }

    @OnClick({R.id.imgClose, R.id.tvMinus, R.id.tvPlus, R.id.tvOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                finish();
                break;
            case R.id.tvMinus:
                if (mNum>1){
                    mNum--;
                    tvSelectNum.setText(getString(R.string.select_,mNum+mProduct.getUnit()));
                    tvNum.setText(mNum+"");
                }
                break;
            case R.id.tvPlus:
                //最大数量
//                if (mNum < mProduct.getMaximumQuantity()) {
                if (mNum < 99) {
                    mNum++;
                    tvSelectNum.setText(getString(R.string.select_,mNum+mProduct.getUnit()));
                    tvNum.setText(mNum+"");
                }else {
                    ToastManager.sendToast(getString(R.string.max_quantity));
                }
                break;
            case R.id.tvOK:
                ViewHelper.addOrUpdateShopCart(this, mProduct, mNum - mProduct.getShopCartNum(), new ViewHelper.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        Intent data = new Intent();
                        data.putExtra("num",mProduct.getShopCartNum());
                        setResult(RESULT_OK,data);
                        finish();
                    }
                });
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_down_up,R.anim.anim_up_down);
    }
}
