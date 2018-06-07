package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.event.PayEvent;
import com.cxgm.app.data.io.order.UpdateStatusReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.StringHelper;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付结果
 *
 * @anthor Dean
 * @time 2018/4/23 0023 19:30
 */
public class PayResultActivity extends BaseActivity {

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
    @BindView(R.id.tvPayTag)
    TextView tvPayTag;
    @BindView(R.id.tvPayAction2)
    TextView tvPayAction2;
    @BindView(R.id.tvPayAction1)
    TextView tvPayAction1;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView2)
    TextView textView2;

    boolean isPaySuccess = false;
    int mOrderId;
    int mStatus;
    String mPayType;
    float mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
        //传数据 订单ID 支付成功失败 支付类型 支付金额
        mOrderId = getIntent().getIntExtra("orderId",0);
        mStatus = getIntent().getIntExtra("status", PayEvent.STATUS_FAIL);
        mPayType = getIntent().getStringExtra("payType");
        mAmount = getIntent().getFloatExtra("amount",0f);
        isPaySuccess = mStatus == PayEvent.STATUS_SUCCESS;

        init();
        loadData();
    }

    private void init() {
        tvTitle.setText(isPaySuccess?R.string.pay_successful:R.string.pay_fail);
        imgBack.setVisibility(View.VISIBLE);
        imageView.setImageResource(isPaySuccess?R.mipmap.pay:R.mipmap.pay_fail);
        textView2.setText(isPaySuccess?R.string.pay_successful:R.string.pay_fail);
        textView2.setCompoundDrawablesWithIntrinsicBounds(isPaySuccess?R.mipmap.tick2:R.mipmap.error,0,0,0);
        textView2.setTextColor(getResources().getColor(isPaySuccess?R.color.textBlackTint:R.color.textRed));
        tvPayTag.setText(isPaySuccess?getString(PayEvent.PAY_WAY_CODE_WECHAT.equals(mPayType)?R.string.wechat_pay_:R.string.alipay_, StringHelper.getRMBFormat(mAmount)):getString(R.string.pay_fail_tag));
        tvPayTag.setTextSize(isPaySuccess?20:15);
        tvPayAction1.setText(isPaySuccess?R.string.check_order:R.string.pay_again);
        tvPayAction2.setText(isPaySuccess?R.string.return_index:R.string.check_order);

    }

    private void loadData(){
        if (isPaySuccess && mOrderId>0) {
            //通知服务器支付成功
            new UpdateStatusReq(this, mOrderId,mPayType).execute(new Request.RequestCallback<String>() {
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

                }
            },false);
        }
    }

    @OnClick({R.id.imgBack, R.id.tvPayAction1,R.id.tvPayAction2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvPayAction1:
                if (isPaySuccess){
                    //查看订单 传ID
                    ViewJump.toOrderDetail(this,mOrderId);
                    finish();
                }else {
                    //重新支付
                    ViewJump.toOrderPay(this,mOrderId,mAmount);
                    finish();
                }
                break;
            case R.id.tvPayAction2:
                if (isPaySuccess) {
                    //回到首页
                    ViewJump.toMain(this,R.id.rbIndex);
                    finish();
                }else {
                    //查看订单
                    ViewJump.toOrderDetail(this,mOrderId);
                    finish();
                }
                break;
        }
    }
}
