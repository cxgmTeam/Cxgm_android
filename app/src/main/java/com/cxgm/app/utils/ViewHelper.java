package com.cxgm.app.utils;

import android.app.Activity;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.io.order.AddCartReq;
import com.cxgm.app.data.io.order.UpdateCartReq;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

public class ViewHelper {
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

}
