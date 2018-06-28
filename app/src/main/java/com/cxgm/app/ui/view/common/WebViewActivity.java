package com.cxgm.app.ui.view.common;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;

    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("url");
        init();
    }

    private void init(){
        tvTitle.setText(R.string.inside_entrance);
        imgBack.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(mUrl)) {
            webview.getSettings().setJavaScriptEnabled(true);

            webview.loadUrl(mUrl);
        }
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }
}
