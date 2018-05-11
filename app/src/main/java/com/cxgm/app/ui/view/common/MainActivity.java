package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.ui.base.BaseActivity;

import com.cxgm.app.ui.view.goods.GoodsFirstClassifyFragment;
import com.cxgm.app.ui.view.order.ShopCartFragment;
import com.cxgm.app.ui.view.user.UserFragment;
import com.cxgm.app.utils.MapHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.manager.PermissionManager;
import com.tbruyelle.rxpermissions.Permission;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 骨架
 *
 * @anthor dean
 * @time 2018/4/18 下午5:47
 */

public class MainActivity extends BaseActivity{

    @BindView(R.id.layoutContainer)
    FrameLayout layoutContainer;
    @BindView(R.id.rbIndex)
    RadioButton rbIndex;
    @BindView(R.id.rbGoods)
    RadioButton rbGoods;
    @BindView(R.id.rbShopCart)
    RadioButton rbShopCart;
    @BindView(R.id.rbUser)
    RadioButton rbUser;
    @BindView(R.id.layoutMenu)
    RadioGroup layoutMenu;

    BDLocation mLocation;

    static IndexFragment mIndexFragment;
    static UserFragment mUserFragment;
    static ShopCartFragment mShopCartFragment;
    static GoodsFirstClassifyFragment mClassifyFragment;

    static {
        mIndexFragment = new IndexFragment();
        mUserFragment = new UserFragment();
        mShopCartFragment = new ShopCartFragment();
        mClassifyFragment = new GoodsFirstClassifyFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLocation = getIntent().getParcelableExtra("location");

        init();

    }

    private void init() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layoutContainer, mIndexFragment)
                .add(R.id.layoutContainer, mUserFragment)
                .add(R.id.layoutContainer,mShopCartFragment)
                .add(R.id.layoutContainer,mClassifyFragment)
                .hide(mIndexFragment).hide(mUserFragment)
                .hide(mShopCartFragment).hide(mClassifyFragment).commit();

        layoutMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeView(checkedId);
            }
        });

        rbIndex.setChecked(true);
    }

    private void loadData() {

    }

    private void changeView(int checkedId) {

        getSupportFragmentManager().beginTransaction().hide(mIndexFragment).hide(mUserFragment)
                .hide(mShopCartFragment).hide(mClassifyFragment).commit();

        switch (checkedId) {

            case R.id.rbIndex:
                Bundle bundle = new Bundle();
                bundle.putParcelable("location",mLocation);
                mIndexFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().show(mIndexFragment).commit();
                break;
            case R.id.rbUser:
                getSupportFragmentManager().beginTransaction().show(mUserFragment).commit();
                break;
            case R.id.rbShopCart:
                getSupportFragmentManager().beginTransaction().show(mShopCartFragment).commit();
                break;
            case R.id.rbGoods:
                getSupportFragmentManager().beginTransaction().show(mClassifyFragment).commit();
                break;
        }
    }

}
