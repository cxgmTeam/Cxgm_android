package com.cxgm.app.app;

import com.baidu.location.BDLocation;
import com.cxgm.app.data.entity.Shop;

/**
 * 常量
 * Created by dean on 2017/7/8.
 */

public class Constants {

    public static final boolean DEBUG = true;
//    public static final long offMaxDate = ((long) 1000) * 3600 * 24 * 365 * 100;

    public static final String SERVICE_URL = "http://47.104.226.173";
    public static final String PORT7 = ":41207/";
    public static final String PORT2 = ":41202/";
    public static final String PORT3 = ":41203/";


    //文件夹目录
    public static String baseDir = "cxgm";
    public static String tempDir = baseDir + "/temp";
    public static String imageDir = baseDir + "/images";
    public static String configDir = baseDir + "/config";
    public static String cacheDir = baseDir + "/cache";
    public static String downloadDir = baseDir + "/download";
    public static String logDir = baseDir + "/log";

    public static String deviceId;
    public static String versionName;
    public static String deviceToken;

//    public static String WECHAT_APP_ID = "wx614763e83a977f41";
//    public static String BAIDU_AK = "Kuu8NxsbIOHBtkgTYXX8vfNqqsBuPZ9U";

    public static BDLocation currentLocation;//当前定位
    public static Shop currentShop;//当前商铺
    public static boolean checkAddress = false;//当前地址可配送

    public static boolean notify = true;//是否接收通知
    public static float postage = 10f;//邮费

}
