package com.cxgm.app.app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Message;
import com.cxgm.app.ui.view.common.LaunchActivity;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.OotbConfig;
import com.deanlib.ootb.data.PersistenceUtils;
import com.deanlib.ootb.data.db.DB;
import com.deanlib.ootb.data.io.DefaultLoadingDialog;
import com.deanlib.ootb.utils.DLogUtils;
import com.deanlib.ootb.utils.VersionUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;
import org.xutils.ex.DbException;

/**
 * Application
 *
 * @anthor dean
 * @time 2018/4/18 下午5:48
 */

public class App extends MultiDexApplication {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        if (Constants.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
//        }

        OotbConfig.init(this, Constants.DEBUG);

        OotbConfig.setRequestServer(Constants.SERVICE_URL, new UserParam(), new UserResult(), new DefaultLoadingDialog());
        //通知
        PersistenceUtils persistenceUtils = new PersistenceUtils();
        String notity = persistenceUtils.getCache("notity");
        Constants.notify = "on".equals(notity);


        //友盟
        UMConfigure.init(this, "5af6acadb27b0a761e000306", "channel", UMConfigure.DEVICE_TYPE_PHONE, "44b9f56acba05f8a5e6859d41f1e886b");
        UMConfigure.setLogEnabled(Constants.DEBUG);

        //友盟推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                DLogUtils.d("友盟推送启动注册成功");
                DLogUtils.d("设备TOKEN:" + deviceToken);
                Constants.deviceToken = deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {
                DLogUtils.d("友盟推送启动注册失败");
                DLogUtils.d("失败信息：" + s + ">>>" + s1);
            }
        });
        //监听自定义消息
        PushAgent.getInstance(this).setMessageHandler(messageHandler);

        //控制错误抓取
        MobclickAgent.setCatchUncaughtExceptions(true);
        //场景类型设置接口
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        Constants.deviceToken = mPushAgent.getRegistrationId();

        DLogUtils.d("设备TOKEN:" + Constants.deviceToken);


        //设备ID
        Constants.deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Constants.versionName = VersionUtils.getAppVersionName();

        //用户
        UserManager.getInstance(this);
    }

    UmengMessageHandler messageHandler = new UmengMessageHandler() {
        @Override
        public void handleMessage(Context context, UMessage uMessage) {
            super.handleMessage(context, uMessage);
        }

        @Override
        public void dealWithNotificationMessage(Context context, UMessage uMessage) {
            super.dealWithNotificationMessage(context, uMessage);

        }

        @Override
        public void dealWithCustomMessage(final Context context, final UMessage uMessage) {
            if (Constants.notify) {
                //todo 通知栏通知 声音
                Intent intent = new Intent(getApplicationContext(),LaunchActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new NotificationCompat.Builder(getApplicationContext(),"cxgm").setContentTitle(uMessage.title)
                        .setContentText(uMessage.custom).setContentIntent(pendingIntent).build();
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(111,notification);
            }

            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    Message msg = new Message(uMessage);
                    DLogUtils.d("推送消息：" + msg);
                    try {
                        DB.getDbManager().save(msg);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            });

//                        // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
//                        boolean isClickOrDismissed = true;
//                        if(isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }
//                    Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();

        }
    };
}
