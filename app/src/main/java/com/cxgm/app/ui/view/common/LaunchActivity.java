package com.cxgm.app.ui.view.common;

import android.os.Bundle;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.io.common.VersionControlReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.VersionUtils;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 启动页
 *
 * @anthor dean
 * @time 2018/4/18 下午5:46
 */

public class LaunchActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        new VersionControlReq(this, VersionUtils.getAppVersionCode() + "").execute(new Request.RequestCallback() {
            @Override
            public void onSuccess(Object o) {
                toMain();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toMain();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void toMain() {
        ViewJump.toMain(this);
        finish();
    }
}
