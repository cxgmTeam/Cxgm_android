package com.cxgm.app.ui.view.goods;

import android.content.Intent;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.io.goods.FindProductBycodeReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DLogUtils;
import com.deanlib.ootb.utils.PopupUtils;

import org.xutils.common.Callback;

import cn.simonlee.xcodescanner.core.CameraScanner;
import cn.simonlee.xcodescanner.core.GraphicDecoder;
import cn.simonlee.xcodescanner.core.OldCameraScanner;
import cn.simonlee.xcodescanner.core.ZBarDecoder;
import cn.simonlee.xcodescanner.view.AdjustTextureView;

/**
 * 扫描条形码
 * zbar实现
 * 用于android 8 以下的版本
 */
public class ScanBarCodeActivity extends BaseActivity  implements CameraScanner.CameraListener
        , TextureView.SurfaceTextureListener, GraphicDecoder.DecodeListener, View.OnClickListener
        ,ViewHelper.OnShopCartUpdateListener{

    private AdjustTextureView mTextureView;
    private View mScannerFrameView;

    private CameraScanner mCameraScanner;
    protected GraphicDecoder mGraphicDecoder;

    protected String TAG = "XCodeScanner";
//    private Button mButton_Flash;
    private int[] mCodeType;

    private ImageView imgBack;
    private TextView tvTitle;
    private  TextView tvAction1;
    private LinearLayout layoutToShopCart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bar_code);

        mTextureView = findViewById(R.id.textureview);
        mTextureView.setSurfaceTextureListener(this);

        mScannerFrameView = findViewById(R.id.scannerframe);

//        mButton_Flash = findViewById(R.id.btn_flash);
//        mButton_Flash.setOnClickListener(this);

        Intent intent = getIntent();
        mCodeType = intent.getIntArrayExtra("codeType");

        /*
         * 注意，SDK21的设备是可以使用NewCameraScanner的，但是可能存在对新API支持不够的情况，比如红米Note3（双网通Android5.0.2）
         * 开发者可自行配置使用规则，比如针对某设备型号过滤，或者针对某SDK版本过滤
         * */
//        if (intent.getBooleanExtra("newAPI", false) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mCameraScanner = new NewCameraScanner(this);
//        } else {
            mCameraScanner = new OldCameraScanner(this);
//        }

        imgBack = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvAction1 = findViewById(R.id.tvAction1);
        layoutToShopCart = findViewById(R.id.layoutToShopCart);

        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(this);
        tvTitle.setText(R.string.scan_bar_code);
        tvAction1.setVisibility(View.VISIBLE);
        tvAction1.setText(R.string.manually_input);
        tvAction1.setOnClickListener(this);
        layoutToShopCart.setOnClickListener(this);

        ViewHelper.addOnShopCartUpdateListener(this);
    }

    @Override
    protected void onRestart() {
        if (mTextureView.isAvailable()) {
            //部分机型转到后台不会走onSurfaceTextureDestroyed()，因此isAvailable()一直为true，转到前台后不会再调用onSurfaceTextureAvailable()
            //因此需要手动开启相机
            mCameraScanner.setPreviewTexture(mTextureView.getSurfaceTexture());
            mCameraScanner.setPreviewSize(mTextureView.getWidth(), mTextureView.getHeight());
            mCameraScanner.openCamera(this.getApplicationContext());
        }
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mCameraScanner.closeCamera();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mCameraScanner.setGraphicDecoder(null);
        if (mGraphicDecoder != null) {
            mGraphicDecoder.setDecodeListener(null);
            mGraphicDecoder.detach();
        }
        mCameraScanner.detach();
        super.onDestroy();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, getClass().getName() + ".onSurfaceTextureAvailable() width = " + width + " , height = " + height);
        mCameraScanner.setPreviewTexture(surface);
        mCameraScanner.setPreviewSize(width, height);
        mCameraScanner.openCamera(this);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, getClass().getName() + ".onSurfaceTextureSizeChanged() width = " + width + " , height = " + height);
        // TODO 当View大小发生变化时，要进行调整。
//        mTextureView.setImageFrameMatrix();
//        mCameraScanner.setPreviewSize(width, height);
//        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.d(TAG, getClass().getName() + ".onSurfaceTextureDestroyed()");
        return true;
    }

    @Override// 每有一帧画面，都会回调一次此方法
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void openCameraSuccess(int frameWidth, int frameHeight, int frameDegree) {
        Log.d(TAG, getClass().getName() + ".openCameraSuccess() frameWidth = " + frameWidth + " , frameHeight = " + frameHeight + " , frameDegree = " + frameDegree);
        mTextureView.setImageFrameMatrix(frameWidth, frameHeight, frameDegree);
        if (mGraphicDecoder == null) {
            mGraphicDecoder = new DebugZBarDecoder(this, mCodeType);//使用带参构造方法可指定条码识别的格式
        }
        //该区域坐标为相对于父容器的左上角顶点。
        //TODO 应考虑TextureView与ScannerFrameView的Margin与padding的情况
        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
        mCameraScanner.setGraphicDecoder(mGraphicDecoder);
    }

    @Override
    public void openCameraError() {
        PopupUtils.sendToast(getString(R.string.scan_error));
    }

    @Override
    public void noCameraPermission() {
        PopupUtils.sendToast("没有权限");
        finish();
    }

    @Override
    public void cameraDisconnected() {
        PopupUtils.sendToast("断开了连接");
    }

    @Override
    public void cameraBrightnessChanged(int brightness) {
//        if (brightness <= 50) {
//            mButton_Flash.setVisibility(View.VISIBLE);
//        } else if (!mCameraScanner.isFlashOpened()) {
//            mButton_Flash.setVisibility(View.GONE);
//        }
        Log.d(TAG, getClass().getName() + ".cameraBrightnessChanged() brightness = " + brightness);
    }

    int mCount = 0;
    String mResult = null;

    @Override
    public void decodeComplete(String result, int type, int quality, int requestCode) {
        if (result == null) return;
        if (result.equals(mResult)) {
            if (++mCount > 3) {//连续四次相同则显示结果（主要过滤脏数据，也可以根据条码类型自定义规则）
                mCameraScanner.closeCamera();
//                if (quality < 10) {
//                    PopupUtils.sendToast("[类型" + type + "/精度00" + quality + "]" + result);
//                } else if (quality < 100) {
//                    PopupUtils.sendToast("[类型" + type + "/精度0" + quality + "]" + result);
//                } else {
//                    PopupUtils.sendToast("[类型" + type + "/精度" + quality + "]" + result);
//                }
//                DLogUtils.d("扫码："+result);
//                Intent data = new Intent();
//                data.putExtra("result",result);
//                setResult(RESULT_OK,data);
//                finish();
                //查询对应商品
                new FindProductBycodeReq(this, Constants.currentShopId,result).execute(new Request.RequestCallback<ProductTransfer>() {
                    @Override
                    public void onSuccess(ProductTransfer productTransfer) {
                        //直接放到购物车中
                        if (productTransfer!=null){
                            ViewHelper.addOrUpdateShopCart(ScanBarCodeActivity.this,productTransfer,1);
                        }else {
                            PopupUtils.sendToast(R.string.goods_not_found);
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
                        mCameraScanner.openCamera(ScanBarCodeActivity.this);
                    }
                });
            }
        } else {
            mCount = 1;
            mResult = result;
        }
        Log.d(TAG, getClass().getName() + ".decodeComplete() -> " + mResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.base_toolbar_navigation: {
//                onBackPressed();
//                break;
//            }
//            case R.id.btn_flash: {
//                if (v.isSelected()) {
//                    ((Button) v).setText("打开闪光灯");
//                    v.setSelected(false);
//                    mCameraScanner.closeFlash();
//                } else {
//                    ((Button) v).setText("关闭闪光灯");
//                    v.setSelected(true);
//                    mCameraScanner.openFlash();
//                }
//                break;
//            }
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvAction1:
                //手动输入
                ViewJump.toManuallyInputBarCode(this);
                break;
            case R.id.layoutToShopCart:
                //去购物车 无效了 因为红点
                ViewJump.toShopCart(this);
                break;
        }
    }

    @Override
    public void onUpdate(int num) {
        ViewHelper.drawShopCartNum(this, layoutToShopCart, num, true);
    }

    /**
     * debug模式，只是加入了一个FPS的Log
     *
     * @author Simon Lee
     * @e-mail jmlixiaomeng@163.com
     * @github https://github.com/Simon-Leeeeeeeee/XCodeScanner
     */
     class DebugZBarDecoder extends ZBarDecoder {

        private Handler mHandler;
        private int FPS_Preview;
        private int FPS_Decode;

        public DebugZBarDecoder(DecodeListener listener, int[] symbolTypeArray) {
            super(listener, symbolTypeArray);
            this.mHandler = new Handler(this);
            mHandler.sendEmptyMessageDelayed(1991, 1000);
        }

        @Override
        public synchronized void decode(byte[] frameData, int width, int height, RectF clipRectRatio) {
            FPS_Preview++;
            super.decode(frameData, width, height, clipRectRatio);
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_DECODE_COMPLETE: {
                    FPS_Decode++;
                    break;
                }
                case 1991: {
                    mHandler.sendEmptyMessageDelayed(1991, 1000);
                    Log.d(TAG, getClass().getName() + ".handleMessage() 预览FPS：" + FPS_Preview + " , 解码FPS：" + FPS_Decode);
                    break;
                }
            }
            return super.handleMessage(msg);
        }

        @Override
        public void detach() {
            super.detach();
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
        }

    }
}
