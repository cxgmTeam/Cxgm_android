package com.cxgm.app.ui.view.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.CategoryAndAmount;
import com.cxgm.app.data.entity.CouponDetail;
import com.cxgm.app.data.entity.DeliveryTime;
import com.cxgm.app.data.entity.Invoice;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.io.order.AddOrderReq;
import com.cxgm.app.data.io.order.AddressListReq;
import com.cxgm.app.data.io.order.CheckCouponReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DeviceUtils;
import com.deanlib.ootb.utils.FormatUtils;
import com.jakewharton.rxbinding.view.RxView;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @BindView(R.id.layoutGooods)
    LinearLayout layoutGooods;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.text3)
    TextView text3;

    ArrayList<OrderProduct> mOrderProductList;
    UserAddress mUserAddress;
    float mOrderAmount = 0f;//总价 不包括优惠券和邮费
    float mDiscounts = 0f;//限时优惠 不包括优惠券
    List<CouponDetail> mCouponList;
    Order mOrder;
    @BindView(R.id.tvExtraction)
    TextView tvExtraction;
//    int mDeliveryTimePosition = 0;
    CouponDetail mCouponDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_verify_order);
        ButterKnife.bind(this);

        if (Constants.currentShopId == 0) {
            ToastManager.sendToast(getString(R.string.choice_shop));
            ViewJump.toMain(this, R.id.rbIndex);
            finish();
            return;
        }

        mOrderProductList = getIntent().getParcelableArrayListExtra("products");

        init();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DeviceUtils.backgroundAlpha(this, 1);
    }

    private void init() {
        tvTitle.setText(R.string.verify_order);
        imgBack.setVisibility(View.VISIBLE);

        mOrder = new Order();
        mOrder.setStoreId(Constants.currentShopId);

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int initHour = currentHour;

        long offTime = 0;
        if (currentHour < 9) {
            //当日
            initHour = 9;
        } else if (currentHour >= 20) {
            //次日

            offTime = 1000 * 60 * 60 * 24;
            initHour = 9;
        } else {
            initHour++;
        }

        String day = FormatUtils.convertDateTimestampToString(calendar.getTimeInMillis() + offTime, "yyyy年M月d日");

        String time = initHour + ":00-" + (initHour + 1) + ":00";

        tvReceiveTime.setText(getString(offTime != 0 ? R.string.tomorrow : R.string.today) + " " + time);
        tvReceiveTime.setTag(day + " " + time);

        //商品
        View itemView = View.inflate(this, R.layout.layout_3goods_1info, null);
        layoutGooods.addView(itemView, 0);
        ImageView imgView1 = itemView.findViewById(R.id.imgView1);
        ImageView imgView2 = itemView.findViewById(R.id.imgView2);
        ImageView imgView3 = itemView.findViewById(R.id.imgView3);
        TextView tvView = itemView.findViewById(R.id.tvView);
        //布局
        int width = (DeviceUtils.getSreenWidth() - 2 * DensityUtil.dip2px(15) - 3 * DensityUtil.dip2px(10)) / 4;
//        tvView.getLayoutParams().width = width;
        imgView1.getLayoutParams().width = width;
        imgView1.getLayoutParams().height = width;
        imgView2.getLayoutParams().width = width;
        imgView2.getLayoutParams().height = width;
        imgView3.getLayoutParams().width = width;
        imgView3.getLayoutParams().height = width;
        tvView.getLayoutParams().width = width;
        tvView.getLayoutParams().height = width;
        int number = mOrderProductList.size();
        number = number > 3 ? 3 : number;
        switch (number) {
            case 3:
                Glide.with(this).load(mOrderProductList.get(2).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                        )
                        .into(imgView3);
            case 2:
                Glide.with(this).load(mOrderProductList.get(1).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                        )
                        .into(imgView2);
            case 1:
                Glide.with(this).load(mOrderProductList.get(0).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                        )
                        .into(imgView1);
        }
        tvView.setText(getString(R.string._count, mOrderProductList.size()));

        mOrderAmount = 0;
        float goodsOriginalTotal = 0;//原价

        SparseArray<CategoryAndAmount> caaArray = new SparseArray<>();
        List<CategoryAndAmount> caaList = new ArrayList<>();

        for (OrderProduct product : mOrderProductList) {
            mOrderAmount = Helper.moneyAdd(product.getAmount(), mOrderAmount);
            //原价
            if (product.getOriginalPrice() == 0)
                product.setOriginalPrice(product.getPrice());
            goodsOriginalTotal = Helper.moneyAdd(Helper.moneyMultiply(product.getOriginalPrice(), product.getProductNum()), goodsOriginalTotal);

            //统计类总额
            CategoryAndAmount caa = caaArray.get(product.getCategoryId());
            if (caa != null) {
                caa.setAmount(Helper.moneyAdd(caa.getAmount(), product.getAmount()));
            } else {
                caa = new CategoryAndAmount(product.getCategoryId(), product.getAmount());
                caaArray.put(caa.getCategoryId(), caa);
            }

        }

        for (int i = 0; i < caaArray.size(); i++) {
            caaList.add(caaArray.valueAt(i));
        }

        tvGoodsTotal.setText(StringHelper.getRMBFormat(goodsOriginalTotal));
        mDiscounts = Helper.moneySubtract(goodsOriginalTotal, mOrderAmount);
        tvDiscounts.setText(StringHelper.getRMBFormat(mDiscounts));

        tvCoupon.setText(R.string.not_coupon);
        tvInvoice.setText(R.string.not_invoice);

        //设置查询优惠券要用到的字段
        mOrder.setProductList(mOrderProductList);
        mOrder.setCategoryAndAmountList(caaList);
        mOrder.setTotalAmount(goodsOriginalTotal);

        tvExtraction.setText(Order.TYPE_DISTRIBUTION);
        mOrder.setExtractionType(Order.TYPE_DISTRIBUTION);

        if (Constants.postage!=null) {
            BigDecimal decimal = new BigDecimal(Constants.postage.getSatisfyMoney());
            String str = getString(R.string.carriage, decimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
            SpannableString ss = new SpannableString(str);
            ss.setSpan(new ForegroundColorSpan(Color.RED),2,str.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            text3.setText(ss);
        }

        setCouponAndPostageAndAmount(mCouponDetail);

        //防抖
        RxView.clicks(tvCommitOrder).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //地址验证
                    if (
//                        Order.TYPE_DISTRIBUTION.equals(mOrder.getExtractionType())&&
                          TextUtils.isEmpty(mOrder.getAddressId())) {
                        ToastManager.sendToast(getString(R.string.empty_consignee));
                        return;
                    }

                    mOrder.setReceiveTime((String) tvReceiveTime.getTag());
                    mOrder.setRemarks(etRemark.getText().toString().trim());

                    //提交订单
                    new AddOrderReq(this, mOrder).execute(new Request.RequestCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            if (integer > 0) {
                                Request.dismissDialog();
                                ViewHelper.updateShopCart(getApplicationContext());
                                ViewJump.toOrderPay(VerifyOrderActivity.this, integer, mOrder.getOrderAmount());
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
                });


    }

    private void loadData() {
        new AddressListReq(this).execute(new Request.RequestCallback<List<UserAddress>>() {
            @Override
            public void onSuccess(List<UserAddress> userAddresses) {
                if (userAddresses != null && userAddresses.size() > 0) {
                    ViewHelper.filterAddress(VerifyOrderActivity.this, userAddresses, Constants.currentShopId, new ViewHelper.OnActionListener() {
                        @Override
                        public void onSuccess() {
                            for (UserAddress address : userAddresses) {
                                if (address.getIsDef() == 1) {
                                    mUserAddress = address;
                                    initUserAddress();
                                    break;
                                }
                            }
                            //如果没有设置默认
                            if (mUserAddress == null) {
                                mUserAddress = userAddresses.get(0);
                                initUserAddress();
                            }
                        }
                    });

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

        //查可用优惠券
        new CheckCouponReq(this, mOrder).execute(new Request.RequestCallback<List<CouponDetail>>() {
            @Override
            public void onSuccess(List<CouponDetail> couponDetails) {
                if (couponDetails != null && couponDetails.size() > 0) {
                    //优惠券 总价更新
                    mCouponList = couponDetails;
                    mCouponDetail = mCouponList.get(0);
                    setCouponAndPostageAndAmount(mCouponDetail);
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


    @OnClick({R.id.layoutGooods, R.id.imgBack, R.id.layoutAddr, R.id.layoutReceiveTime, R.id.layoutCoupon, R.id.layoutInvoice,R.id.layoutExtraction})
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
                ViewJump.toDeliveryTimeDialog(this, (String) tvReceiveTime.getTag());
                break;
            case R.id.layoutCoupon:
                //优惠券 满减
                if (mCouponList != null && mCouponList.size() > 0) {
                    ViewJump.toCouponOption(this, (ArrayList<CouponDetail>) mCouponList);
                } else {
                    ToastManager.sendToast(getString(R.string.not_coupon));
                }
                break;
            case R.id.layoutInvoice:
                //发票
                if (mUserAddress != null) {
                    ViewJump.toInvoice(this, mUserAddress);
                } else {
                    ToastManager.sendToast(getString(R.string.increase_receiving_information));
                }

                break;
            case R.id.layoutGooods:
                //商品清单
                ViewJump.toGoodsList(this, mOrderProductList);
                break;
            case R.id.layoutExtraction:
                //选择配送方式
                String[] items = {Order.TYPE_DISTRIBUTION,Order.TYPE_ONESELF};
                new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvExtraction.setText(items[which]);
                        mOrder.setExtractionType(items[which]);
                        setCouponAndPostageAndAmount(mCouponDetail);
                    }
                }).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ViewJump.CODE_ADDR_OPTION:
                    if (data != null) {
                        //更新配送信息
                        UserAddress address = (UserAddress) data.getSerializableExtra("address");
                        if (address != null) {
                            mUserAddress = address;
                            initUserAddress();
                        }
                    }
                    break;
                case ViewJump.CODE_COUPON_OPTION:
                    if (data != null) {
                        //更新总额
                        mCouponDetail = data.getParcelableExtra("coupon");
                        setCouponAndPostageAndAmount(mCouponDetail);

                    }
                    break;
                case ViewJump.CODE_INVOICE:
                    if (data != null) {
                        Invoice invoice = (Invoice) data.getSerializableExtra("invoice");
                        if (invoice != null) {
                            if (!TextUtils.isEmpty(invoice.getDutyParagraph())
                                    && !TextUtils.isEmpty(invoice.getCompanyName())) {
                                tvInvoice.setText(R.string.company_invoice);
                            } else {
                                tvInvoice.setText(R.string.person_invoice);
                            }
                        }
                        //发票信息
                        mOrder.setReceipt(invoice);
                    }
                    break;
                case ViewJump.CODE_DELIVERY_TIME_DIALOG:
                    if (data != null) {
                        DeliveryTime time = (DeliveryTime) data.getSerializableExtra("deliveryTime");
                        if (time != null) {
                            tvReceiveTime.setText(getString(time.isCurrentDay() ? R.string.today : R.string.tomorrow) + " " + time.getTime());
                            tvReceiveTime.setTag(time.getDate());
                        }
                    }
                    break;
            }
        }
    }

    private void initUserAddress() {
        if (mUserAddress != null) {
            tvName.setText(mUserAddress.getRealName());
            tvAddr.setText(mUserAddress.getArea() + mUserAddress.getAddress());
            tvPhoneNumber.setText(FormatUtils.hidePhoneNum(mUserAddress.getPhone()));
            mOrder.setAddressId(mUserAddress.getId() + "");
        }
    }

    private void setCouponAndPostageAndAmount(CouponDetail couponDetail) {

//        float temp = Helper.moneyAdd(mOrderAmount,Constants.postage);
        float preferential = 0;
        if (couponDetail != null) {
            //优惠券ID
            mOrder.setCouponCodeId(couponDetail.getCodeId());
            float exp = Helper.str2Float(couponDetail.getPriceExpression());
            tvCoupon.setText("- " + StringHelper.getRMBFormat(exp));
            preferential = Helper.moneyAdd(preferential, exp);
        }
        //折扣后，不含邮费
        float temp = Helper.moneySubtract(mOrderAmount, preferential);
        //邮费
        float postage = 0; //自取邮费是0
        if (Order.TYPE_DISTRIBUTION.equals(mOrder.getExtractionType())){
            postage = Helper.calculatePostage(temp, Constants.postage);
        }
        tvCarriage.setText(StringHelper.getRMBFormat(postage));
        mOrder.setPostage(postage);

        mOrder.setPreferential(preferential);
        mOrder.setOrderAmount(Helper.moneyAdd(temp, postage));
        tvPayment.setText(StringHelper.getRMBFormat(mOrder.getOrderAmount()));
    }

}
