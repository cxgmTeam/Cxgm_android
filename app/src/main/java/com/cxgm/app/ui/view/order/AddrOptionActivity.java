package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.adapter.AddrAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收货地址选择
 *
 * @anthor Dean
 * @time 2018/4/21 0021 1:06
 */
public class AddrOptionActivity extends BaseActivity {


    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.lvAddr)
    ListView lvAddr;
    @BindView(R.id.tvNewAddr)
    TextView tvNewAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_addr_option);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tvTitle.setText(R.string.goods_order_list);
        imgBack.setVisibility(View.VISIBLE);

        lvAddr.setAdapter(new AddrAdapter());
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.tvNewAddr)
    public void onClickNewAddr() {
        ViewJump.toNewAddr(this);
    }
}
