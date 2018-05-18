package com.cxgm.app.ui.view.common;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 *
 * @anthor Dean
 * @time 2018/5/16 0016 21:50
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvClearCache)
    TextView tvClearCache;
    @BindView(R.id.tvVersionTag)
    TextView tvVersionTag;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.layoutCheckVersion)
    LinearLayout layoutCheckVersion;
    @BindView(R.id.tvAbout)
    TextView tvAbout;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        tvTitle.setText(R.string.settings);
        imgBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imgBack, R.id.tvClearCache, R.id.layoutCheckVersion, R.id.tvAbout, R.id.tvLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvClearCache:
                break;
            case R.id.layoutCheckVersion:
                break;
            case R.id.tvAbout:
                break;
            case R.id.tvLogout:
                UserManager.deleteUser();
                break;
        }
    }
}