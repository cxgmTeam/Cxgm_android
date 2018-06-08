package com.cxgm.app.ui.view.common;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.io.common.VersionControlReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.PersistenceUtils;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.VersionUtils;

import org.xutils.common.Callback;

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
    @BindView(R.id.cbNotifySwitch)
    CheckBox cbNotifySwitch;

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

        if (UserManager.isUserLogin()){
            tvLogout.setVisibility(View.VISIBLE);
        }else {
            tvLogout.setVisibility(View.GONE);
        }

        //Version
        tvVersion.setText(VersionUtils.getAppVersionName());
        cbNotifySwitch.setChecked(Constants.notify);
        cbNotifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PersistenceUtils persistenceUtils = new PersistenceUtils();
                persistenceUtils.setCache("notify",isChecked==true?"on":"off");
                Constants.notify = isChecked;
            }
        });
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
                ViewJump.toLogin(this);
                finish();
                break;
        }
    }

    private void loadCheckVersion(){
        new VersionControlReq(this,VersionUtils.getAppVersionCode()+"")
                .execute(new Request.RequestCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        //TODO 判断
//                        tvVersionTag.setText(R.string.last_version);
//                        tvVersionTag.setText(R.string.new_version);
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
    }
}
