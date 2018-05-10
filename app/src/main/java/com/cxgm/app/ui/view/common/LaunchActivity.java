package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.cxgm.app.R;
import com.cxgm.app.data.io.common.VersionControlReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.MapHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.VersionUtils;
import com.tbruyelle.rxpermissions.Permission;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 启动页
 *
 * @anthor dean
 * @time 2018/4/18 下午5:46
 */

public class LaunchActivity extends BaseActivity implements MapHelper.LocationCallback {

    @BindView(R.id.textView)
    TextView textView;

    String[] mPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        new VersionControlReq(this, VersionUtils.getAppVersionCode() + "").execute(new Request.RequestCallback() {
            @Override
            public void onSuccess(Object o) {
                //TODO 判断版本
                toMain();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toMain();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

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

                if (permission.granted) {


                }
                mCount++;
                if (mCount >= mPermissions.length){
                    //需要限定权限框询问结束时 打开定位打开Main
                    MapHelper mapHelper = new MapHelper(getApplicationContext(),LaunchActivity.this);
                    mapHelper.startLocation();
                }
            }
        }, mPermissions);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        ViewJump.toMain(this,bdLocation);
        finish();
    }
}
