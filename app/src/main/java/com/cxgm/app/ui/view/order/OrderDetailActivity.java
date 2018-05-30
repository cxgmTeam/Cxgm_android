package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.Invoice;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.data.io.order.OrderDetailReq;
import com.cxgm.app.data.io.order.SurplusTimeReq;
import com.cxgm.app.ui.adapter.OrderGoodsListAdatpter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.StringHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.FormatUtils;
import com.deanlib.ootb.utils.TextUtils;
import com.deanlib.ootb.widget.ListViewForScrollView;

import org.xutils.common.Callback;

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
    @BindView(R.id.tvOrderTag)
    TextView tvOrderTag;
    @BindView(R.id.tvTime)
    TextView tvTime;
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
    @BindView(R.id.tvRemark)
    TextView tvRemark;

    int mOrderId;
    Order mOrder;
    CountDownTimer mTimer;
    @BindView(R.id.tvCancelOrder)
    TextView tvCancelOrder;
    @BindView(R.id.tvPayNow)
    TextView tvPayNow;
    @BindView(R.id.layoutBottomAction)
    LinearLayout layoutBottomAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        mOrderId = getIntent().getIntExtra("orderId", 0);
        init();
        loadData();
    }

    private void init() {
        tvTitle.setText(R.string.order_detail);
        imgBack.setVisibility(View.VISIBLE);

    }

    private void loadData() {
        if (mOrderId > 0) {
            new OrderDetailReq(this, mOrderId).execute(new Request.RequestCallback<Order>() {
                @Override
                public void onSuccess(Order order) {
                    if (order != null) {
                        mOrder = order;
                        countDownTime(order.getId());
                        //状态
                        setStateView(order);
                        //收货地址
                        if (order.getAddress() != null) {
                            tvName.setText(order.getAddress().getRealName());
                            tvAddr.setText(order.getAddress().getArea() + order.getAddress().getAddress());
                            tvPhoneNumber.setText(TextUtils.hidePhoneNum(order.getAddress().getPhone()));
                        }
                        //配送时间
                        tvReceiveTime.setText(order.getReceiveTime());
                        //商铺
                        tvShopName.setText(order.getShopName());
                        tvShopAddr.setText(order.getShopAddress());
                        //商品
                        if (order.getProductDetails() != null) {
                            lvGoods.setAdapter(new OrderGoodsListAdatpter(order.getProductDetails()));
                        }
                        //订单信息
                        tvOrderNum.setText(order.getOrderNum());
                        tvOrderTime.setText(order.getOrderTime());
                        //支付方式
                        tvPayWay.setText(order.getPayType());
                        //发票
                        if (order.getReceipt() != null) {
                            tvInvoiceType.setText(Invoice.TYPE_PERSON.equals(order.getReceipt().getType()) ? R.string.person : R.string.company);
                        } else {
                            tvInvoiceType.setText(R.string.not_invoice);
                        }
                        tvRemark.setText(order.getRemarks());
                        //付款
                        tvGoodsTotal.setText(StringHelper.getRMBFormat(order.getTotalAmount()));
                        tvDiscounts.setText(StringHelper.getRMBFormat(order.getPreferential()));
                        tvCarriage.setText(StringHelper.getRMBFormat(Constants.postage));
                        tvPayment.setText(StringHelper.getRMBFormat(order.getOrderAmount()));
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOrder != null && Order.STATUS_TO_BE_PAID.equals(mOrder.getStatus())) {
            countDownTime(mOrder.getId());
        }
    }


    private void setStateView(Order order) {
        if (order != null) {
            switch (order.getStatus()) {
                case Order.STATUS_TO_BE_PAID:
                    layoutOrderState.setBackgroundResource(R.color.colorOrange);
                    imgIcon.setImageResource(R.mipmap.status_pay);
                    tvOrderState.setText(R.string.unpaid);
                    tvOrderTag.setText(R.string.unpaid_tag);
                    tvTime.setText(R.string.remaining_);
                    layoutBottomAction.setVisibility(View.VISIBLE);
                    break;
                case Order.STATUS_DISTRIBUTION:
                    layoutOrderState.setBackgroundResource(R.color.colorGreen);
                    imgIcon.setImageResource(R.mipmap.status_distributing);
                    tvOrderState.setText(R.string.distribution);
                    tvOrderTag.setText(R.string.distributing_tag);
                    tvTime.setText("");
                    layoutBottomAction.setVisibility(View.GONE);
                    break;
                case Order.STATUS_DISTRIBUTING:
                    layoutOrderState.setBackgroundResource(R.color.colorGreen);
                    imgIcon.setImageResource(R.mipmap.status_distributing);
                    tvOrderState.setText(R.string.distributing);
                    tvOrderTag.setText(R.string.distributing_tag);
                    tvTime.setText("");
                    layoutBottomAction.setVisibility(View.GONE);
                    break;
                case Order.STATUS_COMPLETE:
                    layoutOrderState.setBackgroundResource(R.color.colorBlue);
                    imgIcon.setImageResource(R.mipmap.status_complete);
                    tvOrderState.setText(R.string.complete);
                    tvOrderTag.setText(R.string.complete_tag);
                    tvTime.setText("");
                    layoutBottomAction.setVisibility(View.GONE);
                    break;
                case Order.STATUS_REFUND:
                    //TODO 退款
                    break;
                case Order.STATUS_CANCEL:
                    layoutOrderState.setBackgroundResource(R.color.colorGrayDark2);
                    imgIcon.setImageResource(R.mipmap.status_cancel);
                    tvOrderState.setText(R.string.canceled);
                    tvOrderTag.setText(R.string.canceled_tag);
                    tvTime.setText("");
                    layoutBottomAction.setVisibility(View.GONE);
                    break;
            }

        }
    }

    private void countDownTime(int orderId) {
        new SurplusTimeReq(this, orderId).execute(new Request.RequestCallback<Long>() {
            @Override
            public void onSuccess(Long aLong) {
                if (aLong > 0) {
                    //倒计时
                    mTimer = new CountDownTimer(aLong, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tvTime.setText(getString(R.string.remaining_, FormatUtils.convertDateTimestampToString(millisUntilFinished, "mm:ss")));
                        }

                        @Override
                        public void onFinish() {
                            if (mOrder != null) {
                                mOrder.setStatus(Order.STATUS_CANCEL);
                                setStateView(mOrder);
                            }

                        }
                    };
                    mTimer.start();
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

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null)
            mTimer.cancel();
    }

    @OnClick({R.id.imgBack, R.id.tvCancelOrder, R.id.tvPayNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvCancelOrder:
                //TODO 取消订单
                break;
            case R.id.tvPayNow:
                //TODO 支付
                break;
        }
    }
}
