package com.cxgm.app.ui.view.common;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.utils.VersionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 *
 * @author dean
 * @time 2018/6/28 下午5:30
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvAgreement)
    TextView tvAgreement;
    @BindView(R.id.tvPolicy)
    TextView tvPolicy;
    @BindView(R.id.tvBusinessInfo)
    TextView tvBusinessInfo;
    @BindView(R.id.tvInsideEntrance)
    TextView tvInsideEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        tvTitle.setText(R.string.about_me);
        imgBack.setVisibility(View.VISIBLE);
        tvVersion.setText(String.format(getString(R.string.version_v_), VersionUtils.getAppVersionName()));
    }

    @OnClick({R.id.imgBack, R.id.tvAgreement, R.id.tvPolicy, R.id.tvBusinessInfo, R.id.tvInsideEntrance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvAgreement:
                //TODO 协议
                break;
            case R.id.tvPolicy:
                //todo 政策
                break;
            case R.id.tvBusinessInfo:
                //todo 商铺信息
                break;
            case R.id.tvInsideEntrance:
                //http://47.104.226.173/
                ViewJump.toWebView(this,"http://47.104.226.173/");
                break;
        }
    }
}
