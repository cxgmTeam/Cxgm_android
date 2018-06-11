package com.cxgm.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.order.AddCartReq;
import com.cxgm.app.data.io.order.UpdateCartReq;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.view.goods.GoodsSecondClassifyActivity;
import com.cxgm.app.ui.view.order.VerifyOrderActivity;
import com.deanlib.ootb.data.io.Request;
import com.jakewharton.rxbinding.view.RxView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ViewHelper {

    /**
     * 新增或更新购物车
     * @param activity
     * @param product
     * @param actionNum
     */
    public static void addOrUpdateShopCart(Activity activity, ProductTransfer product, int actionNum){
        addOrUpdateShopCart(activity,product,actionNum,null);
    }
    public static void addOrUpdateShopCart(final Activity activity, final ProductTransfer product, int actionNum, final OnActionListener listener){
        if (!UserManager.isUserLogin()){
            ViewJump.toLogin(activity);
            return;
        }
        final int goodsNum = product.getShopCartNum()+actionNum;
        ShopCart cart = ShopCart.getShopCart(product,goodsNum);
        if (product.getShopCartNum()>0){
            //UPDATE
            new UpdateCartReq(activity,cart).execute(new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    product.setShopCartNum(goodsNum);
                    ToastManager.sendToast(activity.getString(R.string.update_shop_cart));
                    if (listener!=null)
                        listener.onSuccess();
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
        }else {
            new AddCartReq(activity,cart).execute(new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    product.setShopCartId(integer);
                    product.setShopCartNum(goodsNum);
                    ToastManager.sendToast(activity.getString(R.string.added_shop_cart));
                    if (listener!=null)
                        listener.onSuccess();
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
    }

    public interface OnActionListener{
        void onSuccess();
    }

    /**
     * 设置购物车小红点
     * @param activity
     * @param view
     * @param num
     */
    public static void updateShopCartNum(Activity activity, View view, int num){
        //商品种类，不是商品数量
            Badge mShopCartBadge = new QBadgeView(activity)
                    .bindTarget(view).setBadgeTextSize(8, true);
            RxView.clicks(view).throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(o->{ViewJump.toMain(activity,R.id.rbShopCart);});

        if (num>0)
            mShopCartBadge.setBadgeNumber(num);
        else mShopCartBadge.hide(true);
    }

    /**
     * 筛选可用地址
     * @param activity
     * @param userAddresses
     */
    public static void filterAddress(Activity activity,List<UserAddress> userAddresses,int currentShopId,OnActionListener listener){
        for (int i = 0;i<userAddresses.size();i++){

            int finalI = i;
            new CheckAddressReq(activity,userAddresses.get(i).getLongitude(),userAddresses.get(i).getDimension())
                    .execute(new Request.RequestCallback<List<Shop>>() {
                        @Override
                        public void onSuccess(List<Shop> shops) {
                            if (shops!=null){
                                for (Shop sh :shops){
                                    if (sh.getId() == currentShopId){
                                        userAddresses.get(finalI).isEnable = true;
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
                            if (finalI == userAddresses.size()-1){
                                //排序
                                //将有效地址提前
                                Collections.sort(userAddresses);
                                //默认值提前
                                int defPosition = -1;
                                for (int i = 0;i<userAddresses.size();i++){
                                    if (userAddresses.get(i).getIsDef() == 1){
                                        defPosition = i;
                                        break;
                                    }
                                }
                                if (defPosition>0){
                                    userAddresses.add(0,userAddresses.remove(defPosition));
                                }

                                if (listener!=null){
                                    listener.onSuccess();
                                }
                            }
                        }
                    });

        }


    }

}
