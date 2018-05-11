package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.cxgm.app.R;
import com.cxgm.app.ui.adapter.AddrAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.MapHelper;
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

public class AddrListActivity extends BaseActivity implements MapHelper.LocationCallback{

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

    BDLocation mLocation;
    @BindView(R.id.tvCurrentAddr)
    TextView tvCurrentAddr;
    @BindView(R.id.tvRelocation)
    TextView tvRelocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_addr_list);
        ButterKnife.bind(this);

        mLocation = getIntent().getParcelableExtra("location");
        init();

        if (mLocation != null) {
            tvCurrentAddr.setText(mLocation.getStreet() + mLocation.getBuildingName());
        } else {
            location();
        }
    }

    private void init() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.receiver_addr);

        lvAddr.setAdapter(new AddrAdapter());
    }

    @OnClick({R.id.imgBack, R.id.layoutSurrounding, R.id.tvNewAddr,R.id.tvRelocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.layoutSurrounding:
                ViewJump.toMapLocation(this);
                break;
            case R.id.tvNewAddr:
                ViewJump.toNewAddr(this);
                break;
            case R.id.tvRelocation:
                location();
                break;
        }
    }

    private void location(){
        MapHelper helper = new MapHelper(this,this);
        helper.startLocation();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation!=null) {
            mLocation = bdLocation;
            tvCurrentAddr.setText(mLocation.getDistrict() + mLocation.getStreet() + mLocation.getLocationDescribe());
        }
    }

    @Override
    public void finish() {
        if (mLocation!=null) {
            Intent intent = new Intent();
            intent.putExtra("location", mLocation);
            setResult(RESULT_OK, intent);
        }
        super.finish();
    }

}
