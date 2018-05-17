package com.cxgm.app.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocation;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.ui.view.common.MainActivity;
import com.cxgm.app.ui.view.common.SettingsActivity;
import com.cxgm.app.ui.view.goods.GoodsDetailActivity;
import com.cxgm.app.ui.view.goods.GoodsSecondClassifyActivity;
import com.cxgm.app.ui.view.order.AddrListActivity;
import com.cxgm.app.ui.view.order.AddrOptionActivity;
import com.cxgm.app.ui.view.order.InvoiceActivity;
import com.cxgm.app.ui.view.order.MapLocationActivity;
import com.cxgm.app.ui.view.goods.SearchActivity;
import com.cxgm.app.ui.view.order.NewAddrActivity;
import com.cxgm.app.ui.view.order.UserOrderActivity;
import com.cxgm.app.ui.view.order.VerifyOrderActivity;
import com.cxgm.app.ui.view.user.CouponActivity;
import com.cxgm.app.ui.view.user.InviteActivity;
import com.cxgm.app.ui.view.user.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 界面跳转总控
 *
 * @author dean
 * @time 2018/4/17 下午5:10
 */
public class ViewJump {

    public static final int CODE_ADDR_LIST = 0;
    public static final int CODE_MAP_LOCATION = 1;
    public static final int CODE_NEW_ADDRESS = 2;
    public static final int CODE_ADDR_OPTION = 3;
    public static final int CODE_INVOICE = 4;

    public static void toMain(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void toSearch(Activity activity){
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    public static void toAddrList(Activity activity){
        toAddrList(activity,null);
    }

    public static void toAddrList(Activity activity,Fragment fragment){
        Intent intent = new Intent(activity, AddrListActivity.class);
        if (fragment!=null)
            fragment.startActivityForResult(intent,CODE_ADDR_LIST);
        else activity.startActivityForResult(intent,CODE_ADDR_LIST);
    }

    public static void toNewAddr(Activity activity){
        toNewOrUpdateAddr(activity,null);
    }

    public static void toNewOrUpdateAddr(Activity activity, UserAddress address){
        Intent intent = new Intent(activity, NewAddrActivity.class);
        intent.putExtra("address",address);
        activity.startActivityForResult(intent,CODE_NEW_ADDRESS);
    }

    public static void toMapLocation(Activity activity){
        toMapLocation(activity,-1,-1);
    }

    public static void toMapLocation(Activity activity,double longitude,double latitude){
        Intent intent = new Intent(activity, MapLocationActivity.class);
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);
        activity.startActivityForResult(intent,CODE_MAP_LOCATION);
    }

    public static void toInvite(Activity activity){
        Intent intent = new Intent(activity, InviteActivity.class);
        activity.startActivity(intent);
    }

    public static void toLogin(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void toGoodsDetail(Activity activity){
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        activity.startActivity(intent);
    }

    public static void toGoodsSecondClassify(Activity activity,ShopCategory category){
        Intent intent = new Intent(activity, GoodsSecondClassifyActivity.class);
        intent.putExtra("category",category);
        activity.startActivity(intent);
    }

    public static void toCoupon(Activity activity){
        Intent intent = new Intent(activity,CouponActivity.class);
        activity.startActivity(intent);
    }

    public static void toVerifyOrder(Activity activity, ArrayList<OrderProduct> products){
        Intent intent = new Intent(activity,VerifyOrderActivity.class);
        intent.putParcelableArrayListExtra("products",products);
        activity.startActivity(intent);
    }

    public static void toAddrOption(Activity activity){
        Intent intent = new Intent(activity,AddrOptionActivity.class);
        activity.startActivityForResult(intent,CODE_ADDR_OPTION);
    }
    public static void toUserOrder(Activity activity){
        Intent intent = new Intent(activity,UserOrderActivity.class);
        activity.startActivity(intent);
    }

    public static void toInvoice(Activity activity,UserAddress address){
        Intent intent = new Intent(activity,InvoiceActivity.class);
        intent.putExtra("address",address);
        activity.startActivityForResult(intent,CODE_INVOICE);
    }

    public static void toSettings(Activity activity){
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

}
