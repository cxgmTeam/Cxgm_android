package com.cxgm.app.ui.view;

import android.app.Activity;
import android.content.Intent;

import com.cxgm.app.ui.view.common.MainActivity;
import com.cxgm.app.ui.view.goods.SearchActivity;

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

}
