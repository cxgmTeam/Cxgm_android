package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import com.baidu.location.BDLocation;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.entity.Version;
import com.cxgm.app.data.io.common.VersionControlReq;
import com.cxgm.app.data.io.order.AddressListReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.MapHelper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.AppUtils;
import com.deanlib.ootb.utils.VersionUtils;
import com.tbruyelle.rxpermissions.Permission;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 启动页
 *
 * @anthor dean
 * @time 2018/4/18 下午5:46
 */

public class LaunchActivity extends BaseActivity implements MapHelper.LocationCallback {


    String[] mPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    int mCount = 0;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Version version = (Version) msg.obj;
                    if (version!=null) {
                        new AlertDialog.Builder(LaunchActivity.this).setTitle(R.string.update_version)
                                .setMessage(R.string.new_version).setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               AppUtils.openThirdBrowser(LaunchActivity.this, version.getUrl());
                            }
                        }).setNegativeButton(R.string.not_update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                toMain();
                            }
                        }).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        new VersionControlReq(this).execute(new Request.RequestCallback<Version>() {
            @Override
            public void onSuccess(Version version) {
                //判断版本
                if (version!=null && Helper.str2Float(version.getVersionNum())> VersionUtils.getAppVersionCode()){
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1,version),500);
                }else {
                    toMain();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toMain();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                toMain();
            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void toMain() {

        mCount = 0;
        //请求权限
        PermissionManager.requstPermission(this, new Action1<Permission>() {
            @Override
            public void call(Permission permission) {

                mCount++;
                if (mCount >= mPermissions.length) {
                    //需要限定权限框询问结束时 打开定位打开Main
                    if (UserManager.isUserLogin()){
                        //优先用用户指定的默认地址
                        new AddressListReq(LaunchActivity.this).execute(new Request.RequestCallback<List<UserAddress>>() {
                            @Override
                            public void onSuccess(List<UserAddress> userAddresses) {
                                if (userAddresses!=null && userAddresses.size()>0){
                                    UserAddress temp  = userAddresses.get(0);
                                    for (UserAddress address : userAddresses){
                                        if (address.getIsDef() == 1){
                                            temp = address;
                                            break;
                                        }
                                    }
                                    //设置默认收货地址
                                    Constants.defaultUserAddress = temp;
                                    //我日
                                    /*new CheckAddressReq(LaunchActivity.this,temp.getLongitude(),temp.getDimension())
                                            .execute(new Request.RequestCallback<List<Shop>>() {
                                                @Override
                                                public void onSuccess(List<Shop> shops) {
                                                    if (shops!=null && shops.size()>0){
                                                        Constants.currentShopId = shops.get(0).getId();
                                                        Constants.setEnableDeliveryAddress(true);
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
                                                    //管他在不在，最后还是要进主页的
                                                    ViewJump.toMain(LaunchActivity.this);
                                                    finish();
                                                }
                                            });*/

                                    ViewJump.toMain(LaunchActivity.this);
                                    finish();
                                }else {
                                    //登录但没有添加过地址
                                    MapHelper mapHelper = new MapHelper(getApplicationContext(), LaunchActivity.this);
                                    mapHelper.startLocation();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                //为什么分开搞三个，而没有在onFinished中，是因为onSuccess里还有一个网络请求
                                MapHelper mapHelper = new MapHelper(getApplicationContext(), LaunchActivity.this);
                                mapHelper.startLocation();
                            }

                            @Override
                            public void onCancelled(Callback.CancelledException cex) {
                                MapHelper mapHelper = new MapHelper(getApplicationContext(), LaunchActivity.this);
                                mapHelper.startLocation();
                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }else {
                        MapHelper mapHelper = new MapHelper(getApplicationContext(), LaunchActivity.this);
                        mapHelper.startLocation();
                    }
                }
            }
        }, mPermissions);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation != null) {
            Constants.currentLocation = bdLocation;
            //定位功能 置Null currentUserLocation
            Constants.currentUserLocation = null;
            /*new CheckAddressReq(this, bdLocation.getLongitude() + "", bdLocation.getLatitude() + "")
                    .execute(false,new Request.RequestCallback<List<Shop>>() {

                @Override
                public void onSuccess(List<Shop> shops) {
                    if (shops != null && shops.size() > 0) {
                        Constants.currentShopId = shops.get(0).getId();
                        Constants.setEnableDeliveryAddress(true);
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

                    ViewJump.toMain(LaunchActivity.this);
                    finish();
                }
            });*/

            ViewJump.toMain(LaunchActivity.this);
            finish();

        }else {
            //定位失败
            ToastManager.sendToast(getString(R.string.location_fail));
            ViewJump.toMain(this);
            finish();
        }
    }
}
