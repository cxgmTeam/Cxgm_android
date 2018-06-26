package com.cxgm.app.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.View;
import android.widget.RemoteViews;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.entity.Version;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.common.VersionControlReq;
import com.cxgm.app.data.io.order.AddCartReq;
import com.cxgm.app.data.io.order.ShopCartListReq;
import com.cxgm.app.data.io.order.UpdateCartReq;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.VersionUtils;
import com.jakewharton.rxbinding.view.RxView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ViewHelper {

    /**
     * 新增或更新购物车
     *
     * @param activity
     * @param product
     * @param actionNum
     */
    public static void addOrUpdateShopCart(Activity activity, ProductTransfer product, int actionNum) {
        addOrUpdateShopCart(activity, product, actionNum, null);
    }

    public static void addOrUpdateShopCart(final Activity activity, final ProductTransfer product, int actionNum, final OnActionListener listener) {
        if (!UserManager.isUserLogin()) {
            ViewJump.toLogin(activity);
            return;
        }
        final int goodsNum = product.getShopCartNum() + actionNum;
        ShopCart cart = ShopCart.getShopCart(product, goodsNum);
        if (product.getShopCartNum() > 0) {
            //UPDATE
            new UpdateCartReq(activity, cart).execute(new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    product.setShopCartNum(goodsNum);
                    ToastManager.sendToast(activity.getString(R.string.update_shop_cart));
                    ViewHelper.updateShopCart(activity.getApplicationContext());
                    if (listener != null)
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
        } else {
            new AddCartReq(activity, cart).execute(new Request.RequestCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    product.setShopCartId(integer);
                    product.setShopCartNum(goodsNum);
                    ToastManager.sendToast(activity.getString(R.string.added_shop_cart));
                    ViewHelper.updateShopCart(activity.getApplicationContext());
                    if (listener != null)
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

    public interface OnActionListener {
        void onSuccess();
    }

    //购物车数量变化相关
    public interface OnShopCartUpdateListener {
        void onUpdate(int num);
    }

    static List<OnShopCartUpdateListener> mOnShopCartUpdateListeners = new ArrayList<>();

    public static void addOnShopCartUpdateListener(OnShopCartUpdateListener listener) {
        if (!mOnShopCartUpdateListeners.contains(listener))
            mOnShopCartUpdateListeners.add(listener);
    }

    public static void removeShopCartUpdateListener(OnShopCartUpdateListener listener) {
        mOnShopCartUpdateListeners.remove(listener);
    }

    /**
     * 更新购物车  提醒数据的变化
     * @param context
     */
    public static void updateShopCart(Context context) {
        if (UserManager.isUserLogin() && Constants.currentShop != null) {
            new ShopCartListReq(context, Constants.currentShop.getId(), 1, 1)
                    .execute(false, new Request.RequestCallback<PageInfo<ShopCart>>() {
                        @Override
                        public void onSuccess(PageInfo<ShopCart> pageInfo) {
                            if (pageInfo != null) {
                                for (OnShopCartUpdateListener listener : mOnShopCartUpdateListeners) {
                                    listener.onUpdate(pageInfo.getTotal());
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

                        }
                    });
        } else {
            for (OnShopCartUpdateListener listener : mOnShopCartUpdateListeners) {
                listener.onUpdate(0);
            }
        }
    }

    /**
     * 设置购物车小红点
     *
     * @param activity
     * @param view
     * @param num
     * @param gotoShopCart 由于使用QBadgeView 之前监听的点击事件失效，需要设置打开购物车界面时设置该值为true
     */
    public static void drawShopCartNum(Activity activity, View view, int num, boolean gotoShopCart) {
        if (activity == null || view == null)
            return;
        //商品种类，不是商品数量
        Badge mShopCartBadge = (Badge) view.getTag();
        if (mShopCartBadge==null) {
            mShopCartBadge = new QBadgeView(activity)
                    .bindTarget(view).setBadgeTextSize(8, true);
            view.setTag(mShopCartBadge);
        }
        if (gotoShopCart)
            RxView.clicks(view).throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        ViewJump.toMain(activity, R.id.rbShopCart);
                    });

        if (num > 0)
            mShopCartBadge.setBadgeNumber(num);
        else mShopCartBadge.hide(true);
    }

    /**
     * 筛选可用地址
     *
     * @param activity
     * @param userAddresses
     */
    public static void filterAddress(Activity activity, List<UserAddress> userAddresses, int currentShopId, OnActionListener listener) {
        for (int i = 0; i < userAddresses.size(); i++) {

            int finalI = i;
            new CheckAddressReq(activity, userAddresses.get(i).getLongitude(), userAddresses.get(i).getDimension())
                    .execute(new Request.RequestCallback<List<Shop>>() {
                        @Override
                        public void onSuccess(List<Shop> shops) {
                            if (shops != null) {
                                for (Shop sh : shops) {
                                    if (sh.getId() == currentShopId) {
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
                            if (finalI == userAddresses.size() - 1) {
                                //排序
                                //将有效地址提前
                                Collections.sort(userAddresses);
                                //默认值提前
                                int defPosition = -1;
                                for (int i = 0; i < userAddresses.size(); i++) {
                                    if (userAddresses.get(i).getIsDef() == 1) {
                                        defPosition = i;
                                        break;
                                    }
                                }
                                if (defPosition > 0) {
                                    userAddresses.add(0, userAddresses.remove(defPosition));
                                }

                                if (listener != null) {
                                    listener.onSuccess();
                                }
                            }
                        }
                    });

        }


    }



}
