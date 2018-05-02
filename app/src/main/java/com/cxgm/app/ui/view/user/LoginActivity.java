package com.cxgm.app.ui.view.user;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.deanlib.ootb.utils.ValidateUtils;

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
                enabledBtn(tvLogin,ValidateUtils.isMobileNum(etPhoneNum.getText().toString()) && !TextUtils.isEmpty(editable));
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
                break;
            case R.id.tvLogin:
                break;
        }
    }

    private void enabledBtn(View view,boolean enabled){
        if (enabled){
            view.setBackgroundResource(R.drawable.shape_tc_tran_green);
        }else {
            view.setBackgroundResource(R.drawable.shape_tc_tran_gray_2);
        }
        view.setEnabled(enabled);
    }
}
