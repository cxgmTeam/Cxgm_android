package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.UserManager;

/**
 * 购物车
 * 用于从非主菜单点击进入的购物车
 * @author dean
 * @time 2018/4/20 下午5:19
 */

public class ShopCartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_shop_cart);//布局直接加载 fragment

//        ShopCartFragment fragment = new ShopCartFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.layoutContainer,fragment).commit();

        if (!UserManager.isUserLogin()){
            ViewJump.toLogin(this);
            finish();
        }
    }
}
