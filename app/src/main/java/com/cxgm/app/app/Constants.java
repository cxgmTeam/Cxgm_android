package com.cxgm.app.app;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.entity.UserPoiInfo;

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

    public static final String WECHAT_APP_ID = "wxd2f7d73babd9de68";
    public static final String BAIDU_AK = "KevuKZ68Ny3gszdU1h4Y7Rc2ytTY9BPq";

    public static BDLocation currentLocation;//当前定位
    public static PoiInfo currentUserLocation;//用户在地图上点选的位置
    public static UserAddress defaultUserAddress;//默认收货地址
    public static Shop currentShop;//当前商铺
    private static boolean enableDeliveryAddress = false;//当前地址可配送
    public static boolean updatedAddress = false;//标记位，标记地址发生变化 三个变量控制地址 过于复杂,好多地方需要放到onresume等方法即时更新，设置标记位以减少重复加载次数

    public static boolean notify = true;//是否接收通知
    public static float postage = 10f;//邮费

    //获得地址  定位或用户点选
    //isReferenceUserAddress 参考收货地址
    public static PoiInfo getLocation(boolean isReferenceUserAddress){
        if (isReferenceUserAddress && currentUserLocation==null && defaultUserAddress!=null) {
            PoiInfo info = new UserPoiInfo(defaultUserAddress);
            return info;
        }else if (currentUserLocation==null && currentLocation!=null) {
            PoiInfo info = new UserPoiInfo(currentLocation);
            return info;
        }else return currentUserLocation;
    }

    /**
     * 当前地址 是否可配送
     * @return
     */
    public static boolean getEnableDeliveryAddress(){
        return enableDeliveryAddress;
    }
    public static void setEnableDeliveryAddress(boolean enable){
        enableDeliveryAddress =  enable;
        updatedAddress = true;
    }

}
