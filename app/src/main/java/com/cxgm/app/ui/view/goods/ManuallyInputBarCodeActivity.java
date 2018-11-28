package com.cxgm.app.ui.view.goods;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.io.goods.FindProductBycodeReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.PopupUtils;
import com.tbruyelle.rxpermissions.Permission;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 手动输入条形码
 *
 * @anthor dean
 * @time 2018/11/28 9:53 AM
 */
public class ManuallyInputBarCodeActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.etBarCode)
    EditText etBarCode;
    @BindView(R.id.tvOK)
    TextView tvOK;

    ProductTransfer mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_input_bar_code);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.manually_input);
        tvAction1.setText(R.string.switch_code);
        tvAction1.setVisibility(View.VISIBLE);
        etBarCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String result = etBarCode.getText().toString().trim();
                    if (!TextUtils.isEmpty(result)) {
                        //查询对应商品
                        new FindProductBycodeReq(ManuallyInputBarCodeActivity.this, Constants.currentShopId,result ).execute(new Request.RequestCallback<ProductTransfer>() {
                            @Override
                            public void onSuccess(ProductTransfer productTransfer) {
                                //放到 mProduct 中
                                if (productTransfer != null) {
                                    mProduct = productTransfer;
                                } else {
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

                            }
                        });
                    }else {
                        PopupUtils.sendToast(R.string.empty_bar_code);
                    }
                }
                return true;
            }
        });
//        etBarCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    @OnClick({R.id.imgBack, R.id.tvAction1, R.id.tvOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvAction1:
                //切换
                PermissionManager.requstPermission(this, new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted){
                            ViewJump.toScanBarCode(ManuallyInputBarCodeActivity.this);
                        }
                    }
                }, Manifest.permission.CAMERA);
                break;
            case R.id.tvOK:
                if (mProduct!=null) {
                    //加购物车
                    //根据查询到的商品加购物车
                    ViewHelper.addOrUpdateShopCart(this, mProduct, 1);
                }else {
                    PopupUtils.sendToast(R.string.goods_not_found);
                }
                break;
        }
    }
}
