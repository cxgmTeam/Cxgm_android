package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.order.ShopCartListReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.view.goods.GoodsSecondClassifyActivity;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.manager.PermissionManager;
import com.deanlib.ootb.utils.DLogUtils;
import com.tbruyelle.rxpermissions.Permission;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.common.Callback;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class WebViewActivity extends BaseActivity implements ViewHelper.OnShopCartUpdateListener {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    String mUrl;
    @BindView(R.id.imgAction2)
    ImageView imgAction2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        ViewHelper.addOnShopCartUpdateListener(this);
        mUrl = getIntent().getStringExtra("url");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewHelper.updateShopCart(this);
    }

    private void init() {
        tvTitle.setText(R.string.app_name);
        imgBack.setVisibility(View.VISIBLE);
        imgAction2.setVisibility(View.VISIBLE);
        imgAction2.setImageResource(R.mipmap.shop_cart3);

        if (!TextUtils.isEmpty(mUrl)) {
            if (!mUrl.startsWith("file:///") && !Pattern.matches("https?:\\/\\/.+", mUrl)) {
                mUrl = "http://" + mUrl;
            }
            webview.setWebChromeClient(webChromeClient);
            webview.setWebViewClient(webViewClient);

            WebView.setWebContentsDebuggingEnabled(Constants.DEBUG);
            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new CxgmJS(this), "cxgm");

            //Uncaught TypeError: Cannot read property ‘getItem’ of null”
            webSettings.setDomStorageEnabled(true);//允许浏览器保存doom原型，这样js就可以调用这个方法了

            /**
             * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
             * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
             * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
             * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
             */
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
            //Mozilla/5.0 (Linux; Android 7.0; AOSP on HammerHead Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.91 Mobile Safari/537.36
            //Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Mobile Safari/537.36
//            webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Mobile Safari/537.36");
            //支持屏幕缩放
//            webSettings.setSupportZoom(true);
//            webSettings.setBuiltInZoomControls(true);

            //不显示webview缩放按钮
//        webSettings.setDisplayZoomControls(false);

            webview.loadUrl(mUrl);
            //使用webview显示html代码
//        webView.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
//                "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html" , "utf-8", null);
            //加载asset文件夹下html
//        webView.loadUrl("file:///android_asset/test.html");
//            webview.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端
        }
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked(View view) {
        finish();
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成

            String title = view.getTitle();
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setText(R.string.app_name);
            }
            pbLoading.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            DLogUtils.d("拦截url:" + url);

            if (url.startsWith("tel://")) {
                String url1 = url;
                PermissionManager.requstPermission(WebViewActivity.this, new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {

                            String phoneNum = url1.substring(6);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + phoneNum));
                            startActivity(intent);
                        }
                    }
                }, Manifest.permission.CALL_PHONE);

                return true;
            } else if (url.contains("ps-xq.html")) {
                if (Constants.currentLocation != null)
                    url += "&long=" + Constants.currentLocation.getLongitude() + "&lat=" + Constants.currentLocation.getLatitude();
//                url+="&long=116.384756&lat=39.960647";
                DLogUtils.d("拼接url:" + url);
            }
//            if(url.equals("http://www.google.com/")){
//                Toast.makeText(MainActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
//                return true;//表示我已经处理过了
//            }
            view.loadUrl(url);//只要return false，其实不加这一句也可以继续访问网页，但是需要加这一句，是因为上面url做了修改，如果不用loadUrl，url修改是无效的。
            return true;
        }
    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton(R.string.ok, null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            DLogUtils.i("网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            pbLoading.setProgress(newProgress);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
            geolocationPermissionsCallback.invoke(s, true, true);
            super.onGeolocationPermissionsShowPrompt(s, geolocationPermissionsCallback);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        DLogUtils.i("是否有上一个页面:" + webview.canGoBack());
        if (webview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webview.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onUpdate(int num) {
        ViewHelper.drawShopCartNum(this,imgAction2,num,true);
    }

    public class CxgmJS {
        //JS调用android的方法

        Activity activity;

        public CxgmJS(Activity activity) {
            this.activity = activity;
        }

        /**
         * 商品详情
         *
         * @return
         */
        @JavascriptInterface
        public void toGoodsDetail(String productId, String shopId) {
            DLogUtils.d("productId:" + productId + " shopid:" + shopId);
            ViewJump.toGoodsDetail(activity, Integer.valueOf(shopId), Integer.valueOf(productId));
        }

        ProductTransfer product = null;

        /**
         * 加购物车
         *
         * @param goodCode
         * @param goodName
         * @param shopId
         * @param productId
         */
        @JavascriptInterface
        public void addOrUpdateShopCart(String productId, String shopId, String goodName, String goodCode, String price) {
            if (UserManager.isUserLogin() && !"0".equals(shopId)) {
                Constants.currentShopId = Integer.valueOf(shopId);
                product = new ProductTransfer();
                product.setId(Integer.valueOf(productId));
                product.setGoodCode(goodCode);
                product.setName(goodName);
                product.setPrice(Float.valueOf(price));
//                product.setProductCategoryId(Integer.valueOf(categoryId));
                new ShopCartListReq(activity, Constants.currentShopId)
                        .execute(false, new Request.RequestCallback<PageInfo<ShopCart>>() {
                            @Override
                            public void onSuccess(PageInfo<ShopCart> pageInfo) {
                                if (pageInfo != null && pageInfo.getList() != null) {
                                    for (ShopCart shopCart : pageInfo.getList()) {
                                        if (shopCart.getGoodCode().equals(goodCode)
                                                && shopCart.getProductId() == Integer.valueOf(productId)) {
                                            product.setShopCartId(shopCart.getId());
                                            product.setShopCartNum(shopCart.getGoodNum());
                                        }
                                    }
                                }

                                ViewHelper.addOrUpdateShopCart(activity, product, 1);
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
            } else {
                ViewJump.toLogin(activity);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webview.destroy();
        webview = null;

        ViewHelper.removeShopCartUpdateListener(this);
    }

}
