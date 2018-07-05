package com.cxgm.app.ui.view.user;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.User;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.order.AddressListReq;
import com.cxgm.app.data.io.user.LoginReq;
import com.cxgm.app.data.io.user.SendSMSReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.view.common.LaunchActivity;
import com.cxgm.app.utils.MapHelper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.ValidateUtils;

import org.xutils.common.Callback;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 *
 * @anthor Dean
 * @time 2018/5/2 0002 22:55
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.imgAction2)
    ImageView imgAction2;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.etPhoneNum)
    EditText etPhoneNum;
    @BindView(R.id.tvGetCheckCode)
    TextView tvGetCheckCode;
    @BindView(R.id.etCheckCode)
    EditText etCheckCode;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvProtocol)
    TextView tvProtocol;

    String mCheckCode;

    CountDownTimer mDownTimer = new CountDownTimer(60 * 1000,1000) {
        @Override
        public void onTick(long l) {
            enabledBtn(tvGetCheckCode,false);
            tvGetCheckCode.setText(getString(R.string.get_check_code_again_,l/1000));
        }

        @Override
        public void onFinish() {
            enabledBtn(tvGetCheckCode,true);
            tvGetCheckCode.setText(R.string.get_check_code_again);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        tvTitle.setText(R.string.login);
        imgBack.setVisibility(View.VISIBLE);

        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enabledBtn(tvGetCheckCode,ValidateUtils.isMobileNum(editable.toString()));
            }
        });
        etCheckCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enabledBtn(tvLogin,ValidateUtils.isMobileNum(etPhoneNum.getText().toString()) && !TextUtils.isEmpty(editable) && editable.length() >0);
            }
        });
    }

    @OnClick({R.id.imgBack, R.id.tvGetCheckCode, R.id.tvLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvGetCheckCode:
                new SendSMSReq(this,etPhoneNum.getText().toString()).execute(new Request.RequestCallback<String>(){

                    @Override
                    public void onSuccess(String s) {
                        ToastManager.sendToast(getString(R.string.check_code_sent));
                        mCheckCode = s;
                        mDownTimer.start();
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
                break;
            case R.id.tvLogin:
                if (!Constants.DEBUG) {
                    if (!ValidateUtils.isMobileNum(etPhoneNum.getText().toString())) {
                        ToastManager.sendToast(getString(R.string.phone_num_invalid));
                        return;
                    }
                    if (!etCheckCode.getText().toString().equals(mCheckCode)) {
                        ToastManager.sendToast(getString(R.string.check_code_invalid));
                        return;
                    }
                }

                new LoginReq(this,etPhoneNum.getText().toString(),etCheckCode.getText().toString()).execute(new Request.RequestCallback<User>() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null){
                            ToastManager.sendToast(getString(R.string.login_successful));
                            UserManager.saveUser(user);
                            for (UserManager.OnUserActionListener listener:UserManager.mListenerList){
                                listener.onLogin(user);
                            }
                            ViewHelper.updateShopCart(getApplicationContext());
                            new AddressListReq(LoginActivity.this).execute(new Request.RequestCallback<List<UserAddress>>() {
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

                                        new CheckAddressReq(LoginActivity.this,temp.getLongitude(),temp.getDimension())
                                                .execute(new Request.RequestCallback<List<Shop>>() {
                                                    @Override
                                                    public void onSuccess(List<Shop> shops) {
                                                        if (shops!=null && shops.size()>0){
                                                            Constants.currentShop = shops.get(0);
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
                                                        Request.dismissDialog();
                                                        finish();
                                                    }
                                                });
                                    }else {
                                        //登录但没有添加过地址
                                        Request.dismissDialog();
                                        finish();
                                    }
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    //为什么分开搞三个，而没有在onFinished中，是因为onSuccess里还有一个网络请求
                                    Request.dismissDialog();
                                    finish();
                                }

                                @Override
                                public void onCancelled(Callback.CancelledException cex) {
                                    Request.dismissDialog();
                                    finish();
                                }

                                @Override
                                public void onFinished() {

                                }
                            });

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
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownTimer.cancel();
    }

    private void enabledBtn(View view, boolean enabled){
        if (enabled){
            view.setBackgroundResource(R.drawable.shape_tc_tran_green);
        }else {
            view.setBackgroundResource(R.drawable.shape_tc_tran_gray_2);
        }
        view.setEnabled(enabled);
    }

}
