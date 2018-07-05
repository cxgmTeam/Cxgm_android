package com.cxgm.app.ui.view.common;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.Version;
import com.cxgm.app.data.io.common.VersionControlReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.FileUtils;
import com.deanlib.ootb.data.PersistenceUtils;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.AppUtils;
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

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Version version = (Version) msg.obj;
                    if (version!=null) {
                        new AlertDialog.Builder(SettingsActivity.this).setTitle(R.string.update_version)
                                .setMessage(R.string.new_version).setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppUtils.openThirdBrowser(SettingsActivity.this, version.getUrl());
                            }
                        }).setNegativeButton(R.string.not_update, null).show();
                    }
                    break;
            }
        }
    };

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
                FileUtils.deleteCacheAll();
                break;
            case R.id.layoutCheckVersion:
                loadCheckVersion();
                break;
            case R.id.tvAbout:
                ViewJump.toAbout(this);
                break;
            case R.id.tvLogout:
                UserManager.deleteUser();
                Constants.defaultUserAddress = null;
                ViewJump.toLogin(this);
                finish();
                break;
        }
    }

    private void loadCheckVersion(){
        new VersionControlReq(this).execute(new Request.RequestCallback<Version>() {
            @Override
            public void onSuccess(Version version) {
                //判断版本
                if (version!=null && Helper.str2Float(version.getVersionNum())> VersionUtils.getAppVersionCode()){
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1,version),500);
                }else {
                    ToastManager.sendToast(getString(R.string.last_version));
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastManager.sendToast(getString(R.string.last_version));
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                ToastManager.sendToast(getString(R.string.last_version));
            }

            @Override
            public void onFinished() {

            }
        });

    }
}
