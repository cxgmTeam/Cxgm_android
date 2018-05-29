package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.io.order.SurplusTimeReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

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

    int mOrderId;
    long mSurplusTime = 0;
    CountDownTimer mTimer;
    float mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_order_pay);
        ButterKnife.bind(this);
        mOrderId = getIntent().getIntExtra("orderId",0);
        mAmount = getIntent().getFloatExtra("amount",0);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void init(){
        tvTitle.setText(R.string.pay_order);
        imgBack.setVisibility(View.VISIBLE);
        tvPay.setText(getString(R.string.verify_pay, StringHelper.getRMBFormat(mAmount)));
        //选择支付方式
        layoutWeChatPay.performClick();

    }

    private void loadData(){
        new SurplusTimeReq(this,mOrderId).execute(new Request.RequestCallback<Long>() {
            @Override
            public void onSuccess(Long aLong) {
                mSurplusTime = aLong;
                if (mSurplusTime>0){
                    //倒计时
                    mTimer = new CountDownTimer(mSurplusTime,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mSurplusTime = millisUntilFinished;
                            tvPayCountDown.setText(getString(R.string.pay_count_down_,millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {
                            ToastManager.sendToast(getString(R.string.order_pay_outtime));
                            finish();
                        }
                    };
                    mTimer.start();
                }else {
                    ToastManager.sendToast(getString(R.string.order_pay_outtime));
                    finish();
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

    @OnClick({R.id.imgBack, R.id.tvPay,R.id.layoutWeChatPay,R.id.layoutAlipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvPay:
                if (mSurplusTime>0) {
                    if (mTimer != null)
                        mTimer.cancel();
                    //TODO 调起支付
                    if (cbWeChatPay.isChecked()) {

                    } else {

                    }
                }else {
                    ToastManager.sendToast(getString(R.string.order_pay_outtime));
                    finish();
                }
                break;
            case R.id.layoutWeChatPay:
                cbWeChatPay.setChecked(true);
                cbAlipay.setChecked(false);
                break;
            case R.id.layoutAlipay:
                cbWeChatPay.setChecked(false);
                cbAlipay.setChecked(true);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer!=null)
            mTimer.cancel();
    }
}
