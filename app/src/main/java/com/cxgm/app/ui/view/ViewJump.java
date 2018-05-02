package com.cxgm.app.ui.view;

import android.app.Activity;
import android.content.Intent;

import com.cxgm.app.ui.view.common.MainActivity;
import com.cxgm.app.ui.view.order.AddrListActivity;
import com.cxgm.app.ui.view.order.MapLocationActivity;
import com.cxgm.app.ui.view.goods.SearchActivity;
import com.cxgm.app.ui.view.order.NewAddrActivity;
import com.cxgm.app.ui.view.user.InviteActivity;
import com.cxgm.app.ui.view.user.LoginActivity;

/**
 * 界面跳转总控
 *
 * @author dean
 * @time 2018/4/17 下午5:10
 */
public class ViewJump {

    public static void toMain(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public static void toSearch(Activity activity){
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    public static void toAddrList(Activity activity){
        Intent intent = new Intent(activity, AddrListActivity.class);
        activity.startActivity(intent);
    }

    public static void toNewAddr(Activity activity){
        Intent intent = new Intent(activity, NewAddrActivity.class);
        activity.startActivity(intent);
    }

    public static void toMapLocation(Activity activity){
        Intent intent = new Intent(activity, MapLocationActivity.class);
        activity.startActivity(intent);
    }

    public static void toInvite(Activity activity){
        Intent intent = new Intent(activity, InviteActivity.class);
        activity.startActivity(intent);
    }

    public static void toLogin(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

}
