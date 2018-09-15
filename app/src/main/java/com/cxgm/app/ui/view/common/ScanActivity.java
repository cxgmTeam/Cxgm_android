package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.DLogUtils;
import com.tbruyelle.rxpermissions.Permission;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rx.functions.Action1;

/**
 * zxing实现
 * 用于android 8 及以上
 */
public class ScanActivity extends BaseActivity {

    @BindView(R.id.scanView)
    ZXingView scanView;

    Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        init();

    }

    private void init(){
        scanView.setType(BarcodeType.ONLY_QR_CODE,null);
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
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanView.stopSpot();
                scanView.startSpot();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        scanView.startCamera();
        scanView.startSpotAndShowRect();

        //部分国产手机只自动对焦一次后就不再继续对焦
        //代码开启3秒一次的重新对焦工作
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (scanView!=null){
                    scanView.stopSpot();
                    scanView.startSpot();
                }
            }
        },3000,3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.stopSpot();
        scanView.stopCamera();
        if (mTimer!=null){
            mTimer.purge();
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanView.onDestroy();
    }
}
