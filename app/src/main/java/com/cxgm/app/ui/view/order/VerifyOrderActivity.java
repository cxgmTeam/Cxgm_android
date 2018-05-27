package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.CategoryAndAmount;
import com.cxgm.app.data.entity.CouponDetail;
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
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DeviceUtils;
import com.deanlib.ootb.utils.TextUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

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

    ArrayList<OrderProduct> mOrderProductList;
    UserAddress mUserAddress;
    float mOrderAmount = 0f;//总价 不包括优惠券和邮费
    List<CouponDetail> mCouponList;
    Order mOrder;
    int mCarriage = 10;//邮费

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_verify_order);
        ButterKnife.bind(this);

        mOrderProductList = getIntent().getParcelableArrayListExtra("products");

        init();
        loadData();
    }

    private void init() {
        tvTitle.setText(R.string.verify_order);
        imgBack.setVisibility(View.VISIBLE);

        mOrder = new Order();

        //商品
        View itemView = View.inflate(this,R.layout.layout_3goods_1info,null);
        layoutGooods.addView(itemView,0);
        ImageView imgView1 = itemView.findViewById(R.id.imgView1);
        ImageView imgView2 = itemView.findViewById(R.id.imgView2);
        ImageView imgView3 = itemView.findViewById(R.id.imgView3);
        TextView tvView = itemView.findViewById(R.id.tvView);
        //TODO 布局大小有问题
        int width = (DeviceUtils.getSreenWidth()-2* DensityUtil.dip2px(15) - 3*DensityUtil.dip2px(10))/4;
        tvView.getLayoutParams().width = width;
        tvView.getLayoutParams().height = width;
        int number = mOrderProductList.size();
        number = number>3?3:number;
        switch (number){
            case 3:
                Glide.with(this).load(mOrderProductList.get(2).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                .override(width,width))
                        .into(imgView3);
            case 2:
                Glide.with(this).load(mOrderProductList.get(1).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                .override(width,width))
                        .into(imgView2);
            case 1:
                Glide.with(this).load(mOrderProductList.get(0).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                .override(width,width))
                        .into(imgView1);
        }
        tvView.setText(getString(R.string._count,mOrderProductList.size()));

        mOrderAmount = 0;
        float goodsOriginalTotal = 0;//原价

        SparseArray<CategoryAndAmount> caaArray = new SparseArray<>();
        List<CategoryAndAmount> caaList = new ArrayList<>();

        for (OrderProduct product:mOrderProductList){
            mOrderAmount = Helper.moneyAdd(product.getAmount(),mOrderAmount);
            //原价
            goodsOriginalTotal = Helper.moneyAdd(Helper.moneyMultiply(product.getOriginalPrice(),product.getProductNum()),goodsOriginalTotal);

            //统计类总额
            CategoryAndAmount caa = caaArray.get(product.getCategoryId());
            if (caa!=null){
                caa.setAmount(Helper.moneyAdd(caa.getAmount(),product.getAmount()));
            }else {
                caa = new CategoryAndAmount(product.getCategoryId(),product.getAmount());
                caaArray.put(caa.getCategoryId(),caa);
            }

        }

        for (int i = 0;i<caaArray.size();i++){
            caaList.add(caaArray.valueAt(i));
        }

        tvGoodsTotal.setText(StringHelper.getRMBFormat(goodsOriginalTotal));
        tvDiscounts.setText(StringHelper.getRMBFormat(Helper.moneySubtract(goodsOriginalTotal,mOrderAmount)));
        //邮费固定值
        tvCarriage.setText(StringHelper.getRMBFormat(mCarriage));
        tvCoupon.setText(StringHelper.getRMBFormat(0));
        tvInvoice.setText(R.string.not_invoice);

        //这个时候 优惠券接口还没调用，先设置一个价格上去，稍候会更新  算上邮费
        tvPayment.setText(StringHelper.getRMBFormat(Helper.moneySubtract(mOrderAmount,mCarriage)));

        //设置查询优惠券要用到的字段
        mOrder.setProductList(mOrderProductList);
        mOrder.setOrderAmount(mOrderAmount);
        mOrder.setCategoryAndAmountList(caaList);

    }

    private void loadData(){
        new AddressListReq(this).execute(new Request.RequestCallback<List<UserAddress>>() {
            @Override
            public void onSuccess(List<UserAddress> userAddresses) {
                if (userAddresses!=null && userAddresses.size()>0){
                    for (UserAddress address : userAddresses){
                        if (address.getIdDef() == 1){
                            mUserAddress = address;
                            initUserAddress();
                            break;
                        }
                    }
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
        new CheckCouponReq(this,mOrder).execute(new Request.RequestCallback<List<CouponDetail>>() {
            @Override
            public void onSuccess(List<CouponDetail> couponDetails) {
                if (couponDetails!=null && couponDetails.size()>0){
                    //优惠券 总价更新
                    mCouponList = couponDetails;
                    setCouponAndAmount(mCouponList.get(0));
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


    @OnClick({R.id.layoutGooods,R.id.imgBack, R.id.layoutAddr, R.id.layoutReceiveTime, R.id.layoutCoupon, R.id.layoutInvoice, R.id.tvCommitOrder})
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
                //TODO 选择送货时间
                break;
            case R.id.layoutCoupon:
                //优惠券 满减
                if (mCouponList!=null && mCouponList.size()>0) {
                    ViewJump.toCouponOption(this, (ArrayList<CouponDetail>) mCouponList);
                }else {
                    ToastManager.sendToast(getString(R.string.not_coupon));
                }
                break;
            case R.id.layoutInvoice:
                //发票
                if (mUserAddress!=null){
                    ViewJump.toInvoice(this,mUserAddress);
                }else {
                    ToastManager.sendToast(getString(R.string.increase_receiving_information));
                }

                break;
            case R.id.tvCommitOrder:
                //地址验证
                if (android.text.TextUtils.isEmpty(mOrder.getAddressId())){
                    ToastManager.sendToast(getString(R.string.empty_consignee));
                    return;
                }

                mOrder.setRemarks(etRemark.getText().toString().trim());

                //提交订单
                new AddOrderReq(this,mOrder).execute(new Request.RequestCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        //todo 数据返回的有问题，需要拿到唯一标识order id
                        if (integer>0){
                            ViewJump.toOrderPay(VerifyOrderActivity.this);
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
                break;
            case R.id.layoutGooods:
                //商品清单
                ViewJump.toGoodsList(this,mOrderProductList);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ViewJump.CODE_ADDR_OPTION:
                    if (data!=null){
                        //更新配送信息
                        UserAddress address = (UserAddress) data.getSerializableExtra("address");
                        if (address!=null){
                            mUserAddress = address;
                            initUserAddress();
                        }
                    }
                    break;
                case ViewJump.CODE_COUPON_OPTION:
                    if (data!=null){
                        //更新总额
                        CouponDetail couponDetail = data.getParcelableExtra("coupon");
                        setCouponAndAmount(couponDetail);

                    }
                    break;
                case ViewJump.CODE_INVOICE:
                    if (data!=null){
                        Invoice invoice = (Invoice) data.getSerializableExtra("invoice");
                        if (invoice!=null){
                            if (!android.text.TextUtils.isEmpty(invoice.getDutyParagraph())
                                    && !android.text.TextUtils.isEmpty(invoice.getCompanyName())){
                                tvInvoice.setText(R.string.company_invoice);
                            }else {
                                tvInvoice.setText(R.string.person_invoice);
                            }
                        }
                        //发票信息
                        mOrder.setReceipt(invoice);
                    }
                    break;
            }
        }
    }

    private void initUserAddress(){
        if (mUserAddress!=null) {
            tvName.setText(mUserAddress.getRealName());
            tvAddr.setText(mUserAddress.getArea() + mUserAddress.getAddress());
            tvPhoneNumber.setText(TextUtils.hidePhoneNum(mUserAddress.getPhone()));
            mOrder.setAddressId(mUserAddress.getId()+"");
        }
    }

    private void setCouponAndAmount(CouponDetail couponDetail){
        if (couponDetail!=null) {
            //实付款
            float temp = Helper.moneySubtract(mOrderAmount,mCarriage);
            mOrder.setOrderAmount(Helper.moneySubtract(temp,Helper.str2Float(couponDetail.getPriceExpression())));
            tvPayment.setText(StringHelper.getRMBFormat(mOrder.getOrderAmount()));
            //优惠券ID
            mOrder.setCouponCodeId(couponDetail.getCodeId());
        }
    }
}
