package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增地址
 *
 * @anthor dean
 * @time 2018/4/19 下午2:21
 */

public class NewAddrActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etConsignee)
    EditText etConsignee;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.tvDistrict)
    TextView tvDistrict;
    @BindView(R.id.etNumber)
    EditText etNumber;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.tvSave)
    TextView tvSave;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        }
        setContentView(R.layout.activity_new_addr);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tvTitle.setText(R.string.new_addr);
        imgBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layoutDistrict)
    public void onClickDistrict() {
        ViewJump.toMapLocation(this);
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }
}
