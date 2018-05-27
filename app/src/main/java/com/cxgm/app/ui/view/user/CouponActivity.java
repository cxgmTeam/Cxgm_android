package com.cxgm.app.ui.view.user;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 优惠券
 *
 * @anthor Dean
 * @time 2018/4/23 0023 22:36
 */
public class CouponActivity extends BaseActivity {

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
    @BindView(R.id.tabAble)
    TabLayout tabAble;
    @BindView(R.id.layoutEmpty)
    LinearLayout layoutEmpty;
    @BindView(R.id.vpContainer)
    ViewPager vpContainer;

    int[] titles = {R.string.available,R.string.disabled};
    int[] titleNums = {0,0};
    CouponViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvTitle.setText(R.string.coupon);
        imgBack.setVisibility(View.VISIBLE);
        mViewPagerAdapter = new CouponViewPagerAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(mViewPagerAdapter);
        tabAble.setupWithViewPager(vpContainer);
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }

    /**
     * 更新tab标题上的数量
     * @param state
     * @param num
     */
    public void updateTitleNum(int state,int num){
        titleNums[state] = num;
        mViewPagerAdapter.notifyDataSetChanged();
    }

    class CouponViewPagerAdapter extends FragmentPagerAdapter{

        public CouponViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CouponFragment fragment = new CouponFragment();
            Bundle bundle = new Bundle();
            //分别传参
            bundle.putInt("state",position);
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
            return getString(titles[position],titleNums[position]);
        }
    }

}
