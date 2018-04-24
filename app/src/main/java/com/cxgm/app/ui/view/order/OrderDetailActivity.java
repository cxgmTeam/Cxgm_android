package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.adapter.GoodsOrderListAdatpter;
import com.cxgm.app.ui.base.BaseActivity;
import com.deanlib.ootb.widget.ListViewForScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单详情
 *
 * @anthor Dean
 * @time 2018/4/24 0024 22:27
 */
public class OrderDetailActivity extends BaseActivity {

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
    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    @BindView(R.id.tvOrderState)
    TextView tvOrderState;
    @BindView(R.id.OrderTag)
    TextView OrderTag;
    @BindView(R.id.layoutOrderState)
    RelativeLayout layoutOrderState;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvAddr)
    TextView tvAddr;
    @BindView(R.id.layoutAddr)
    LinearLayout layoutAddr;
    @BindView(R.id.tvReceiveTime)
    TextView tvReceiveTime;
    @BindView(R.id.layoutReceiveTime)
    FrameLayout layoutReceiveTime;
    @BindView(R.id.tvShopName)
    TextView tvShopName;
    @BindView(R.id.tvShopAddr)
    TextView tvShopAddr;
    @BindView(R.id.lvGoods)
    ListViewForScrollView lvGoods;
    @BindView(R.id.tvOrderNum)
    TextView tvOrderNum;
    @BindView(R.id.tvOrderTime)
    TextView tvOrderTime;
    @BindView(R.id.tvPayWay)
    TextView tvPayWay;
    @BindView(R.id.tvInvoiceType)
    TextView tvInvoiceType;
    @BindView(R.id.tvGoodsTotal)
    TextView tvGoodsTotal;
    @BindView(R.id.tvDiscounts)
    TextView tvDiscounts;
    @BindView(R.id.tvCarriage)
    TextView tvCarriage;
    @BindView(R.id.tvPayment)
    TextView tvPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        tvTitle.setText(R.string.order_detail);
        imgBack.setVisibility(View.VISIBLE);

        lvGoods.setAdapter(new GoodsOrderListAdatpter());
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }
}
