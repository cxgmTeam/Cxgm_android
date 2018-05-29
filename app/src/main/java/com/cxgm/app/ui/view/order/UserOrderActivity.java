package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 *
 * @anthor Dean
 * @time 2018/4/24 0024 21:18
 */
public class UserOrderActivity extends BaseActivity {

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
    @BindView(R.id.tabOrderState)
    TabLayout tabOrderState;
    @BindView(R.id.vpContainer)
    ViewPager vpContainer;

    int[] titles = {R.string.all,R.string.to_be_paid,R.string.distribution,R.string.distributing,R.string.complete,R.string.refund2};
    String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_user_order);
        ButterKnife.bind(this);
        mStatus = getIntent().getStringExtra("status");
        init();
    }

    private void init(){
        tvTitle.setText(R.string.user_order);
        imgBack.setVisibility(View.VISIBLE);
        tabOrderState.setTabMode(TabLayout.MODE_SCROLLABLE);

        vpContainer.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabOrderState.setupWithViewPager(vpContainer);

        if (mStatus!=null) {
            switch (mStatus) {
                case Order.STATUS_TO_BE_PAID:
                    vpContainer.setCurrentItem(1);
                    break;
                case Order.STATUS_DISTRIBUTION:
                    vpContainer.setCurrentItem(2);
                    break;
                case Order.STATUS_DISTRIBUTING:
                    vpContainer.setCurrentItem(3);
                    break;
                case Order.STATUS_COMPLETE:
                    vpContainer.setCurrentItem(4);
                    break;
                case Order.STATUS_REFUND:
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new UserOrderFragment();
            Bundle bundle = new Bundle();
            switch (position){
                case 0:
                    //全部
                    break;
                case 1:
                    bundle.putString("status", Order.STATUS_TO_BE_PAID);
                    break;
                case 2:
                    bundle.putString("status", Order.STATUS_DISTRIBUTION);
                    break;
                case 3:
                    bundle.putString("status", Order.STATUS_DISTRIBUTING);
                    break;
                case 4:
                    bundle.putString("status", Order.STATUS_COMPLETE);
                    break;
                case 5:
                    bundle.putString("status", Order.STATUS_REFUND);
                    break;
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titles[position]);
        }
    }
}
