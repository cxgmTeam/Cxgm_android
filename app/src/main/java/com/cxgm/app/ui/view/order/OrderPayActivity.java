package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单支付
 *
 * @anthor Dean
 * @time 2018/5/22 0022 23:38
 */
public class OrderPayActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvPayCountDown)
    TextView tvPayCountDown;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.cbWeChatPay)
    CheckBox cbWeChatPay;
    @BindView(R.id.layoutWeChatPay)
    LinearLayout layoutWeChatPay;
    @BindView(R.id.cbAlipay)
    CheckBox cbAlipay;
    @BindView(R.id.layoutAlipay)
    LinearLayout layoutAlipay;
    @BindView(R.id.tvPay)
    TextView tvPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_order_pay);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        tvTitle.setText(R.string.pay_order);
        imgBack.setVisibility(View.VISIBLE);
        //TODO 倒计时
        //TODO 选择支付方式
    }

    @OnClick({R.id.imgBack, R.id.tvPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvPay:
                //TODO 调起支付
                break;
        }
    }
}
