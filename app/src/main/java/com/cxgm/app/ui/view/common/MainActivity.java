package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.view.order.ShopCartFragment;
import com.cxgm.app.ui.view.user.UserFragment;
import com.deanlib.ootb.manager.PermissionManager;
import com.tbruyelle.rxpermissions.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 骨架
 *
 * @anthor dean
 * @time 2018/4/18 下午5:47
 */

public class MainActivity extends BaseActivity {

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

    IndexFragment mIndexFragment;
    UserFragment mUserFragment;
    ShopCartFragment mShopCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };

        //请求权限
        PermissionManager.requstPermission(this, new Action1<Permission>() {
            @Override
            public void call(Permission permission) {

                if (permission.granted) {


                }

            }
        }, permissions);
    }

    private void init() {

        mIndexFragment = new IndexFragment();
        mUserFragment = new UserFragment();
        mShopCartFragment = new ShopCartFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.layoutContainer, mIndexFragment)
                .add(R.id.layoutContainer, mUserFragment)
                .add(R.id.layoutContainer,mShopCartFragment)
                .hide(mIndexFragment).hide(mUserFragment)
                .hide(mShopCartFragment).commit();

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
                .hide(mShopCartFragment).commit();

        switch (checkedId) {

            case R.id.rbIndex:
                getSupportFragmentManager().beginTransaction().show(mIndexFragment).commit();
                break;
            case R.id.rbUser:
                getSupportFragmentManager().beginTransaction().show(mUserFragment).commit();
                break;
            case R.id.rbShopCart:
                getSupportFragmentManager().beginTransaction().show(mShopCartFragment).commit();
                break;
        }
    }

}
