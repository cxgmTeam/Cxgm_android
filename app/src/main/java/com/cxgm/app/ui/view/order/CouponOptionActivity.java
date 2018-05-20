package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.CouponDetail;
import com.cxgm.app.ui.adapter.CouponAdapter;
import com.cxgm.app.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 下单 优惠券选择
 *
 * @anthor Dean
 * @time 2018/5/20 0020 22:51
 */
public class CouponOptionActivity extends BaseActivity {

    @BindView(R.id.lvCoupon)
    ListView lvCoupon;

    List<CouponDetail> mCouponList;
    CouponAdapter mCouponAdapter;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_coupon_option);
        ButterKnife.bind(this);
        mCouponList = getIntent().getParcelableArrayListExtra("couponList");
        init();
    }

    private void init() {
        tvTitle.setText(R.string.enable_coupon);
        imgBack.setVisibility(View.VISIBLE);
        if (mCouponList != null) {
            mCouponAdapter = new CouponAdapter(mCouponList);
            lvCoupon.setAdapter(mCouponAdapter);
            lvCoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent data = new Intent();
                    data.putExtra("coupon",mCouponList.get((int)id));
                    setResult(RESULT_OK,data);
                    finish();
                }
            });
        }
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }
}
