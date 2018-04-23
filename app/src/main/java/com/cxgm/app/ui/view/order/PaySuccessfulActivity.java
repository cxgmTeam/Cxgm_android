package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付成功
 *
 * @anthor Dean
 * @time 2018/4/23 0023 19:30
 */
public class PaySuccessfulActivity extends BaseActivity {

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
    @BindView(R.id.tvPaySuccessful)
    TextView tvPaySuccessful;
    @BindView(R.id.tvBackIndex)
    TextView tvBackIndex;
    @BindView(R.id.tvCheckOrder)
    TextView tvCheckOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_pay_successful);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tvTitle.setText(R.string.pay_successful);
        imgBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imgBack, R.id.tvBackIndex, R.id.tvCheckOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvBackIndex:
                break;
            case R.id.tvCheckOrder:
                break;
        }
    }
}
