package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.io.order.AddOrderReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认订单
 *
 * @anthor Dean
 * @time 2018/4/21 0021 19:39
 */
public class VerifyOrderActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
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
    @BindView(R.id.tvGoodsTotal)
    TextView tvGoodsTotal;
    @BindView(R.id.tvDiscounts)
    TextView tvDiscounts;
    @BindView(R.id.tvCarriage)
    TextView tvCarriage;
    @BindView(R.id.tvCoupon)
    TextView tvCoupon;
    @BindView(R.id.layoutCoupon)
    LinearLayout layoutCoupon;
    @BindView(R.id.tvInvoice)
    TextView tvInvoice;
    @BindView(R.id.layoutInvoice)
    LinearLayout layoutInvoice;
    @BindView(R.id.cbWeChatPay)
    CheckBox cbWeChatPay;
    @BindView(R.id.layoutWeChatPay)
    LinearLayout layoutWeChatPay;
    @BindView(R.id.cbAlipay)
    CheckBox cbAlipay;
    @BindView(R.id.layoutAlipay)
    LinearLayout layoutAlipay;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    @BindView(R.id.tvCommitOrder)
    TextView tvCommitOrder;
    @BindView(R.id.imgAction2)
    ImageView imgAction2;
    @BindView(R.id.tvHintPay)
    TextView tvHintPay;
    @BindView(R.id.tvTimeRemaining)
    TextView tvTimeRemaining;
    @BindView(R.id.layoutHintPay)
    LinearLayout layoutHintPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_verify_order);
        ButterKnife.bind(this);

        init();
        loadData();
    }

    private void init() {
        tvTitle.setText(R.string.verify_order);
        imgBack.setVisibility(View.VISIBLE);
    }

    private void loadData(){

    }


    @OnClick({R.id.imgBack, R.id.layoutAddr, R.id.layoutReceiveTime, R.id.tvGoodsTotal, R.id.layoutCoupon, R.id.layoutInvoice, R.id.tvCommitOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.layoutAddr:
                //选择地址
                ViewJump.toAddrOption(this);
                break;
            case R.id.layoutReceiveTime:
                //选择送货时间
                break;
            case R.id.tvGoodsTotal:
                //商品清单
                break;
            case R.id.layoutCoupon:
                //优惠券
                break;
            case R.id.layoutInvoice:
                //发票
                break;
            case R.id.tvCommitOrder:
                //提交订单
                new AddOrderReq(this,null).execute(new Request.RequestCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {

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
                break;
        }
    }
}
