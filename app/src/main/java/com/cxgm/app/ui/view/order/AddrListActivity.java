package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.adapter.AddrAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.widget.ListViewForScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收货地址 列表
 *
 * @author dean
 * @time 2018/4/20 下午1:49
 */

public class AddrListActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.layoutReposition)
    LinearLayout layoutReposition;
    @BindView(R.id.layoutSurrounding)
    LinearLayout layoutSurrounding;
    @BindView(R.id.lvAddr)
    ListViewForScrollView lvAddr;
    @BindView(R.id.tvNewAddr)
    TextView tvNewAddr;
    @BindView(R.id.tvNoAddr)
    TextView tvNoAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_addr_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.receiver_addr);

        lvAddr.setAdapter(new AddrAdapter());
    }

    @OnClick(R.id.tvNewAddr)
    public void onClickNewAddr() {
        ViewJump.toNewAddr(this);
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.layoutSurrounding)
    public void onClickSurrounding() {
        ViewJump.toMapLocation(this);
    }
}
