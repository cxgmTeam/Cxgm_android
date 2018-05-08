package com.cxgm.app.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.baidu.location.BDLocation;
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

    public static final int CODE_ADDR_LIST = 0;

    public static void toMain(Activity activity, BDLocation location){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("location",location);
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
