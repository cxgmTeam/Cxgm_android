package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.PayInfo;
import com.cxgm.app.data.event.PayEvent;
import com.cxgm.app.data.io.order.AliPayReq;
import com.cxgm.app.data.io.order.SurplusTimeReq;
import com.cxgm.app.data.io.order.UpdateStatusReq;
import com.cxgm.app.data.io.order.WeixinPayReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DLogUtils;
import com.deanlib.ootb.utils.FormatUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;

import java.util.Map;
import java.util.Set;

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
        mOrderId = getIntent().getIntExtra("orderId", 0);
        mAmount = getIntent().getFloatExtra("amount", 0);
        init();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayEvent(PayEvent event) {
        DLogUtils.d("***OnPayEvent***");
        //支付结果
//        if (event.payType == PayEvent.PAY_TYPE_WECHAT) {
//            BaseResp resp = ((BaseResp) event.obj);
//        }
        if (event.status == PayEvent.STATUS_SUCCESS)
            updatePaySuccess(event.payType);
        ViewJump.toPayResult(this, mOrderId, event.status, event.payType, mAmount);
    }

    private void updatePaySuccess(String payType) {
        //通知服务器支付成功
        new UpdateStatusReq(this, mOrderId, payType).execute(false,new Request.RequestCallback<String>() {
            @Override
            public void onSuccess(String o) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {
                finish();
            }
        }, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void init() {
        tvTitle.setText(R.string.pay_order);
        imgBack.setVisibility(View.VISIBLE);
        tvPay.setText(getString(R.string.verify_pay, StringHelper.getRMBFormat(mAmount)));
        tvAmount.setText(StringHelper.getRMBFormat(mAmount));
        //选择支付方式
        layoutWeChatPay.performClick();

    }

    private void loadData() {
        new SurplusTimeReq(this, mOrderId).execute(new Request.RequestCallback<Long>() {
            @Override
            public void onSuccess(Long aLong) {
                mSurplusTime = aLong;
                if (mSurplusTime > 0) {
                    //倒计时
                    mTimer = new CountDownTimer(mSurplusTime, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mSurplusTime = millisUntilFinished;
                            tvPayCountDown.setText(getString(R.string.pay_count_down_, FormatUtils.convertDateTimestampToString(millisUntilFinished, "mm:ss")));
                        }

                        @Override
                        public void onFinish() {
                            ToastManager.sendToast(getString(R.string.order_pay_outtime));
                            finish();
                        }
                    };
                    mTimer.start();
                } else {
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

    @OnClick({R.id.imgBack, R.id.tvPay, R.id.layoutWeChatPay, R.id.layoutAlipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvPay:
                if (mSurplusTime > 0) {
                    if (mTimer != null)
                        mTimer.cancel();
                    //调起支付
                    if (cbWeChatPay.isChecked()) {
                        new WeixinPayReq(this, mOrderId).execute(new Request.RequestCallback<PayInfo>() {
                            @Override
                            public void onSuccess(PayInfo payInfo) {
                                if (payInfo != null && "ok".equals(payInfo.getMsg())) {
                                    IWXAPI api = WXAPIFactory.createWXAPI(OrderPayActivity.this, null);
                                    api.registerApp(Constants.WECHAT_APP_ID);
                                    PayReq req = new PayReq();
                                    req.appId = payInfo.getAppid();
                                    req.partnerId = payInfo.getPartnerid();
                                    req.prepayId = payInfo.getPrepayid();
                                    req.packageValue = payInfo.getPackageValue();
                                    req.nonceStr = payInfo.getNoncestr();
                                    req.timeStamp = payInfo.getTimestamp();
                                    req.sign = payInfo.getSign();
                                    api.sendReq(req);
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
                        }, false);
                    } else {
                        //todo 支付宝
                        new AliPayReq(this,mOrderId).execute(new Request.RequestCallback<String>() {
                            @Override
                            public void onSuccess(String orderInfo) {
                                if (!TextUtils.isEmpty(orderInfo)){
                                    Runnable payRunnable = new Runnable() {

                                        @Override
                                        public void run() {
                                            PayTask alipay = new PayTask(OrderPayActivity.this);
                                            Map<String, String> result = alipay.payV2(orderInfo,true);

                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };
                                    // 必须异步调用
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();
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
                        },false);
                    }
                } else {
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
    private static final int SDK_PAY_FLAG = 1111;
    static Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;
//                    ToastManager.sendToast(result.);
                    Set<Map.Entry<String, String>> entries = result.entrySet();
                    for (Map.Entry<String, String> entry:entries){
                        System.out.println("MMMMMMM:"+entry.getKey()+"    "+entry.getValue());
                    }
                    break;
            }

        };
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null)
            mTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
