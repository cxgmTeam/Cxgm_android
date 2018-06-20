package com.cxgm.app.ui.view.common;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.order.ShopCartListReq;
import com.cxgm.app.ui.base.BaseActivity;

import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.view.goods.GoodsFirstClassifyFragment;
import com.cxgm.app.ui.view.order.ShopCartFragment;
import com.cxgm.app.ui.view.user.UserFragment;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 骨架
 *
 * @anthor dean
 * @time 2018/4/18 下午5:47
 */

public class MainActivity extends BaseActivity implements ViewHelper.OnShopCartUpdateListener{

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
    @BindView(R.id.viewShopcartShadow)
    View viewShopcartShadow;

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

    int mCheckResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCheckResId = getIntent().getIntExtra("resId",R.id.rbIndex);
        ViewHelper.addOnShopCartUpdateListener(this);
        init();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mCheckResId = intent.getIntExtra("resId",R.id.rbIndex);
        checkRadioBtn(mCheckResId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新shopcart num
        ViewHelper.updateShopCart(this);
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

        checkRadioBtn(mCheckResId);

    }

    private void checkRadioBtn(int resId){
        View view = findViewById(resId);
        if (view!=null &&view instanceof RadioButton)
            ((RadioButton)view).setChecked(true);
    }

    public void publicChangeView(int resId){
        mCheckResId = resId;
        checkRadioBtn(resId);
    }

    private void changeView(int checkedId) {
        mCheckResId = checkedId;
        getSupportFragmentManager().beginTransaction().hide(mIndexFragment).hide(mUserFragment)
                .hide(mShopCartFragment).hide(mClassifyFragment).commit();

        switch (checkedId) {

            case R.id.rbIndex:
                getSupportFragmentManager().beginTransaction().show(mIndexFragment).commit();
                break;
            case R.id.rbUser:
                getSupportFragmentManager().beginTransaction().show(mUserFragment).commit();
                break;
            case R.id.rbShopCart:
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(this);
                    rbIndex.setChecked(true);
                    return;
                }
                if (Constants.currentShop==null){
                    ToastManager.sendToast(getString(R.string.choice_shop));
                    rbIndex.setChecked(true);
                    return;
                }
                getSupportFragmentManager().beginTransaction().show(mShopCartFragment).commit();
                break;
            case R.id.rbGoods:
                if (Constants.currentShop==null){
                    ToastManager.sendToast(getString(R.string.choice_shop));
                    rbIndex.setChecked(true);
                    return;
                }
                getSupportFragmentManager().beginTransaction().show(mClassifyFragment).commit();
                break;
        }
    }

    @Override
    public void onUpdate(int num) {
        //由于viewShopcartShadow 不是监听点击事件的View,所以不会影响到rbShopCart的点击事件，这里gotoShopCart 设置 false
        ViewHelper.drawShopCartNum(MainActivity.this,viewShopcartShadow,num,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewHelper.removeShopCartUpdateListener(this);
    }
}
