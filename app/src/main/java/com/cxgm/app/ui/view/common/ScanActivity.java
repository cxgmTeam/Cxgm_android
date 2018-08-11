package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.DLogUtils;
import com.tbruyelle.rxpermissions.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rx.functions.Action1;

public class ScanActivity extends BaseActivity {

    @BindView(R.id.scanView)
    ZXingView scanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        PermissionManager.requstPermission(this, new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted){
                    init();
                }else {
                    finish();
                }
            }
        }, Manifest.permission.CAMERA);

    }

    private void init(){
        scanView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                DLogUtils.d("扫码："+result);
                Intent data = new Intent();
                data.putExtra("result",result);
                setResult(RESULT_OK,data);
                finish();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                ToastManager.sendToast(getString(R.string.scan_error));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        scanView.startCamera();
        scanView.startSpotAndShowRect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.stopSpot();
        scanView.stopCamera();
    }
}
