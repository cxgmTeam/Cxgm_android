package com.cxgm.app.ui.view.order;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.Invoice;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.data.event.PayEvent;
import com.cxgm.app.data.io.order.CancelOrderReq;
import com.cxgm.app.data.io.order.OrderDetailReq;
import com.cxgm.app.data.io.order.SurplusTimeReq;
import com.cxgm.app.ui.adapter.OrderGoodsListAdatpter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.FormatUtils;
import com.deanlib.ootb.widget.ListViewForScrollView;
import com.tbruyelle.rxpermissions.Permission;

import org.xutils.common.Callback;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

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
    @BindView(R.id.text3)
    TextView text3;

    int mOrderId;
    Order mOrder;
    CountDownTimer mTimer;
    @BindView(R.id.tvCancelOrder)
    TextView tvCancelOrder;
    @BindView(R.id.tvPayNow)
    TextView tvPayNow;
    @BindView(R.id.layoutBottomAction)
    LinearLayout layoutBottomAction;
    @BindView(R.id.layoutRefund)
    LinearLayout layoutRefund;
    @BindView(R.id.tvRefundAmount)
    TextView tvRefundAmount;
    @BindView(R.id.tvBuyAgain)
    TextView tvBuyAgain;
    @BindView(R.id.imgPhone)
    ImageView imgPhone;
    @BindView(R.id.tvExtraction)
    TextView tvExtraction;

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
        lvGoods.setFocusable(false);
    }

    private void loadData() {
        if (mOrderId > 0) {
            new OrderDetailReq(this, mOrderId).execute(new Request.RequestCallback<Order>() {
                @Override
                public void onSuccess(Order order) {
                    if (order != null) {
                        mOrder = order;
                        countDownTime();
                        //状态
                        setStateView(order);
                        //收货地址
                        if (order.getAddress() != null) {
                            tvName.setText(order.getAddress().getRealName());
                            tvAddr.setText(order.getAddress().getArea() + order.getAddress().getAddress());
                            tvPhoneNumber.setText(FormatUtils.hidePhoneNum(order.getAddress().getPhone()));
                        }
                        //配送时间
                        tvReceiveTime.setText(order.getReceiveTime());
                        //配送方式
                        tvExtraction.setText(order.getExtractionType());
                        //商铺
                        tvShopName.setText(order.getShopName());
                        tvShopAddr.setText(order.getShopAddress());
                        //商品
                        if (order.getProductDetails() != null) {
                            lvGoods.setAdapter(new OrderGoodsListAdatpter(order.getProductDetails()));
                            lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ViewJump.toGoodsDetail(OrderDetailActivity.this, order.getProductDetails().get((int) id).getProductId());
                                }
                            });
                        }
                        //订单信息
                        tvOrderNum.setText(order.getOrderNum());
                        tvOrderTime.setText(order.getOrderTime());
                        //支付方式
                        tvPayWay.setText(PayEvent.PAY_TYPE_WECHAT.equals(order.getPayType()) ? R.string.wechat_pay : R.string.alipay);
                        //发票
                        if (order.getReceipt() != null) {
                            tvInvoiceType.setText(Invoice.TYPE_PERSON.equals(order.getReceipt().getType()) ? R.string.person : R.string.company);
                        } else {
                            tvInvoiceType.setText(R.string.not_invoice);
                        }
                        tvRemark.setText(order.getRemarks());
                        //邮费说明
                        BigDecimal decimal = new BigDecimal(Constants.postage.getSatisfyMoney());
                        text3.setText(getString(R.string.carriage, decimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString()));
                        //付款
                        tvGoodsTotal.setText(StringHelper.getRMBFormat(order.getTotalAmount()));
                        tvDiscounts.setText(StringHelper.getRMBFormat(order.getPreferential()));
                        tvCarriage.setText(StringHelper.getRMBFormat(order.getPostage()));
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
        countDownTime();

    }


    private void setStateView(Order order) {
        if (order != null) {
            switch (order.getStatus()) {
                case Order.STATUS_TO_BE_PAID:
                    layoutOrderState.setBackgroundResource(R.color.colorOrangeTint);
                    imgIcon.setImageResource(R.mipmap.status_pay);
                    tvOrderState.setText(R.string.unpaid);
                    tvOrderTag.setText(R.string.unpaid_tag);
                    tvTime.setText(R.string.remaining_);
                    tvTime.setVisibility(View.VISIBLE);
                    layoutBottomAction.setVisibility(View.VISIBLE);
                    layoutRefund.setVisibility(View.GONE);
                    tvBuyAgain.setVisibility(View.GONE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                case Order.STATUS_DISTRIBUTION:
                case Order.STATUS_DISTRIBUTION2:
                case Order.STATUS_DISTRIBUTION3:
                    layoutOrderState.setBackgroundResource(R.color.colorPink);
                    imgIcon.setImageResource(R.mipmap.status_distributing);
                    tvOrderState.setText(R.string.distribution);
                    tvOrderTag.setText(R.string.distributing_tag);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.GONE);
                    tvBuyAgain.setVisibility(View.VISIBLE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                case Order.STATUS_DISTRIBUTING:
                    layoutOrderState.setBackgroundResource(R.color.colorPink);
                    imgIcon.setImageResource(R.mipmap.status_distributing);
                    tvOrderState.setText(R.string.distributing);
                    tvOrderTag.setText(R.string.distributing_tag);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.GONE);
                    tvBuyAgain.setVisibility(View.VISIBLE);
//                    if (ValidateUtils.isMobileNum(mOrder.getPsPhone()))
                    imgPhone.setVisibility(View.VISIBLE);
                    break;
                case Order.STATUS_COMPLETE:
                    layoutOrderState.setBackgroundResource(R.color.colorBlue);
                    imgIcon.setImageResource(R.mipmap.status_complete);
                    tvOrderState.setText(R.string.complete);
                    tvOrderTag.setText(R.string.complete_tag);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.GONE);
                    tvBuyAgain.setVisibility(View.VISIBLE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                case Order.STATUS_WAIT_REFUND:
                    //待退款  图标 文字 颜色
                    layoutOrderState.setBackgroundResource(R.color.colorGrayDark3);
                    imgIcon.setImageResource(R.mipmap.status_wait_refund);
                    tvOrderState.setText(R.string.wait_refund);
                    tvOrderTag.setText(R.string.wait_refund_tag);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.VISIBLE);
                    tvRefundAmount.setText(StringHelper.getRMBFormat(order.getOrderAmount()));
                    tvBuyAgain.setVisibility(View.VISIBLE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                case Order.STATUS_REFUND:
                    //已退款  图标 文字 颜色
                    layoutOrderState.setBackgroundResource(R.color.colorRed);
                    imgIcon.setImageResource(R.mipmap.status_refund);
                    tvOrderState.setText(R.string.refunded);
                    tvOrderTag.setText(R.string.refund_tag3);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.VISIBLE);
                    tvRefundAmount.setText(StringHelper.getRMBFormat(order.getOrderAmount()));
                    tvBuyAgain.setVisibility(View.VISIBLE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                case Order.STATUS_CANCEL:
                    layoutOrderState.setBackgroundResource(R.color.colorGrayDark2);
                    imgIcon.setImageResource(R.mipmap.status_cancel);
                    tvOrderState.setText(R.string.canceled);
                    tvOrderTag.setText(R.string.canceled_tag);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.GONE);
                    tvBuyAgain.setVisibility(View.VISIBLE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                case Order.STATUS_SYSTEM_CANCEL:
                    layoutOrderState.setBackgroundResource(R.color.colorGrayDark2);
                    imgIcon.setImageResource(R.mipmap.status_cancel);
                    tvOrderState.setText(R.string.canceled);
                    tvOrderTag.setText(R.string.canceled_tag2);
                    tvTime.setVisibility(View.GONE);
                    layoutBottomAction.setVisibility(View.GONE);
                    layoutRefund.setVisibility(View.GONE);
                    tvBuyAgain.setVisibility(View.VISIBLE);
                    imgPhone.setVisibility(View.GONE);
                    break;
                default:
                    layoutOrderState.setVisibility(View.GONE);
                    break;
            }

        }
    }

    private void countDownTime() {
        if (mOrder != null && Order.STATUS_TO_BE_PAID.equals(mOrder.getStatus())) {
            new SurplusTimeReq(this, mOrder.getId()).execute(new Request.RequestCallback<Long>() {
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
                                    mOrder.setStatus(Order.STATUS_SYSTEM_CANCEL);
                                    setStateView(mOrder);
                                }

                            }
                        };
                        mTimer.start();
                    } else {
                        tvTime.setText(getString(R.string.remaining_, FormatUtils.convertDateTimestampToString(0, "mm:ss")));
                        tvCancelOrder.setVisibility(View.GONE);
                        tvPayNow.setVisibility(View.GONE);
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
    protected void onPause() {
        super.onPause();
        if (mTimer != null)
            mTimer.cancel();
    }

    @OnClick({R.id.imgBack, R.id.tvCancelOrder, R.id.tvPayNow, R.id.tvBuyAgain, R.id.imgPhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvCancelOrder:
                //取消订单
                if (mOrder != null) {
                    new CancelOrderReq(this, mOrder.getId())
                            .execute(new Request.RequestCallback<Integer>() {
                                @Override
                                public void onSuccess(Integer integer) {
                                    ToastManager.sendToast(getString(R.string.canceled));
                                    mOrder.setStatus(Order.STATUS_CANCEL);
                                    setStateView(mOrder);
                                    if (mTimer != null)
                                        mTimer.cancel();
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
                break;
            case R.id.tvPayNow:
                //支付
                if (mOrder != null) {
                    ViewJump.toOrderPay(this, mOrder.getId(), mOrder.getOrderAmount());
                }
                break;
            case R.id.tvBuyAgain:
                //再次购买 这里有个BUG，当在限时优惠时购买的商品，再次购买时，可能限时优惠已过期，但还是可以用限时价格买到商品
                if (mOrder != null && mOrder.getProductDetails() != null)
                    ViewJump.toVerifyOrder(this, (ArrayList<OrderProduct>) mOrder.getProductDetails());
                break;
            case R.id.imgPhone:
                PermissionManager.requstPermission(this, new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {

                            String phoneNum = mOrder.getPsPhone();
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + phoneNum));
                            startActivity(intent);
                        }
                    }
                }, Manifest.permission.CALL_PHONE);
                break;
        }
    }
}
