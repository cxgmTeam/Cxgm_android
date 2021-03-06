package com.cxgm.app.ui.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.CouponDetail;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.ui.view.common.AboutActivity;
import com.cxgm.app.ui.view.common.MainActivity;
import com.cxgm.app.ui.view.common.Scan2Activity;
import com.cxgm.app.ui.view.common.ScanActivity;
import com.cxgm.app.ui.view.common.SettingsActivity;
import com.cxgm.app.ui.view.common.WebViewActivity;
import com.cxgm.app.ui.view.goods.GoodsDetailActivity;
import com.cxgm.app.ui.view.goods.GoodsSecondClassifyActivity;
import com.cxgm.app.ui.view.goods.GoodsSpecificationDialogActivity;
import com.cxgm.app.ui.view.goods.SearchResultActivity;
import com.cxgm.app.ui.view.news.MessageListActivity;
import com.cxgm.app.ui.view.order.AddrListActivity;
import com.cxgm.app.ui.view.order.AddrOptionActivity;
import com.cxgm.app.ui.view.order.CouponOptionActivity;
import com.cxgm.app.ui.view.order.DeliveryTimeDialogActivity;
import com.cxgm.app.ui.view.order.GoodsListActivity;
import com.cxgm.app.ui.view.order.InvoiceActivity;
import com.cxgm.app.ui.view.order.MapLocationActivity;
import com.cxgm.app.ui.view.goods.SearchActivity;
import com.cxgm.app.ui.view.order.NewAddrActivity;
import com.cxgm.app.ui.view.order.OrderDetailActivity;
import com.cxgm.app.ui.view.order.OrderPayActivity;
import com.cxgm.app.ui.view.order.PayResultActivity;
import com.cxgm.app.ui.view.order.ShopCartActivity;
import com.cxgm.app.ui.view.order.UserOrderActivity;
import com.cxgm.app.ui.view.order.VerifyOrderActivity;
import com.cxgm.app.ui.view.user.CouponActivity;
import com.cxgm.app.ui.view.user.InviteActivity;
import com.cxgm.app.ui.view.user.LoginActivity;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.DeviceUtils;
import com.tbruyelle.rxpermissions.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.functions.Action1;

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
    public static final int CODE_COUPON_OPTION = 5;
    public static final int CODE_DELIVERY_TIME_DIALOG = 6;
    public static final int CODE_GOODS_SPECIFICATION_DIALOG = 7;
    public static final int CODE_GOODS_DETAIL = 8;
    public static final int CODE_SCAN = 9;

    public static void toMain(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
    public static void toMain(Activity activity,int resId){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("resId",resId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        PermissionManager.requstPermission(activity, new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if(permission.granted) {
                    Intent intent = new Intent(activity, MapLocationActivity.class);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("latitude", latitude);
                    activity.startActivityForResult(intent, CODE_MAP_LOCATION);
                }
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public static void toInvite(Activity activity){
        Intent intent = new Intent(activity, InviteActivity.class);
        activity.startActivity(intent);
    }

    public static void toLogin(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void toGoodsDetail(Activity activity, int productId){
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        intent.putExtra("productId",productId);
        activity.startActivityForResult(intent,CODE_GOODS_DETAIL);
    }

    public static void toGoodsDetail(Activity activity,int shopId, int productId){
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        intent.putExtra("productId",productId);
        intent.putExtra("shopId",shopId);
        activity.startActivityForResult(intent,CODE_GOODS_DETAIL);
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
        toUserOrder(activity,null);
    }
    public static void toUserOrder(Activity activity,String status){
        Intent intent = new Intent(activity,UserOrderActivity.class);
        if (status != null)
            intent.putExtra("status",status);
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

    public static void toCouponOption(Activity activity, ArrayList<CouponDetail> list){
        Intent intent = new Intent(activity,CouponOptionActivity.class);
        intent.putParcelableArrayListExtra("couponList",list);
        activity.startActivityForResult(intent,CODE_COUPON_OPTION);
    }

    public static void toOrderPay(Activity activity,int orderId,float amount){
        Intent intent = new Intent(activity,OrderPayActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("amount",amount);
        activity.startActivity(intent);
    }

    public static void toGoodsSpecificationDialog(Activity activity,ProductTransfer product){
        Intent intent = new Intent(activity,GoodsSpecificationDialogActivity.class);
        intent.putExtra("product",product);
        activity.startActivityForResult(intent,CODE_GOODS_SPECIFICATION_DIALOG);
        DeviceUtils.backgroundAlpha(activity,0.5f);
    }

    public static void toGoodsList(Activity activity,ArrayList<OrderProduct> products){
        Intent intent = new Intent(activity, GoodsListActivity.class);
        intent.putParcelableArrayListExtra("products",products);
        activity.startActivity(intent);
    }

    public static void toDeliveryTimeDialog(Activity activity,String date){
        Intent intent = new Intent(activity, DeliveryTimeDialogActivity.class);
        intent.putExtra("date",date);
        activity.startActivityForResult(intent,CODE_DELIVERY_TIME_DIALOG);
        DeviceUtils.backgroundAlpha(activity,0.5f);
    }

    public static void toSearchResult(Activity activity,String keyword){
        Intent intent = new Intent(activity,SearchResultActivity.class);
        intent.putExtra("keyword",keyword);
        activity.startActivity(intent);
    }

    public static void toOrderDetail(Activity activity, int orderId){
        Intent intent = new Intent(activity,OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        activity.startActivity(intent);
    }

    public static void toMessageList(Activity activity){
        Intent intent = new Intent(activity,MessageListActivity.class);
        activity.startActivity(intent);
    }

    public static void toPayResult(Activity activity,int orderId,int status,String payType,float amount){
        Intent intent = new Intent(activity,PayResultActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("status",status);
        intent.putExtra("payType",payType);
        intent.putExtra("amount",amount);
        activity.startActivity(intent);
    }

    public static void toWebView(Activity activity,String url){
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url",url);
        activity.startActivity(intent);
    }

    public static void toAbout(Activity activity){
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    public static void toScan(Activity activity){
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            intent = new Intent(activity, ScanActivity.class);
        }else {
            intent = new Intent(activity, Scan2Activity.class);
        }

        activity.startActivityForResult(intent,CODE_SCAN);
    }

    public static void toScan(Activity activity,Fragment fragment){
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            intent = new Intent(activity, ScanActivity.class);
        }else {
            intent = new Intent(activity, Scan2Activity.class);
        }
        fragment.startActivityForResult(intent,CODE_SCAN);
    }

    public static void toShopCart(Activity activity){
        activity.startActivity(new Intent(activity, ShopCartActivity.class));
    }

}
