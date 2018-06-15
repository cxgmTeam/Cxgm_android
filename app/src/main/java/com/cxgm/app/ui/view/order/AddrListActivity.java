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
import com.baidu.mapapi.search.core.PoiInfo;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.entity.UserPoiInfo;
import com.cxgm.app.data.io.order.AddressListReq;
import com.cxgm.app.ui.adapter.AddrAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.MapHelper;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.widget.ListViewForScrollView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.tvCurrentAddr)
    TextView tvCurrentAddr;
    @BindView(R.id.tvRelocation)
    TextView tvRelocation;

    boolean mUpdatedLocation = false;//地址更新
    List<UserAddress> mAddrList;
    AddrAdapter mAddrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_addr_list);
        ButterKnife.bind(this);

        init();
        loadData();
    }

    private void init() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.receiver_addr);

        if (Constants.getLocation(false) != null) {
            tvCurrentAddr.setText(Constants.getLocation(false).address);
        } else {
            location();
        }

        mAddrList = new ArrayList<>();
        mAddrAdapter = new AddrAdapter(this,mAddrList);
        lvAddr.setAdapter(mAddrAdapter);
        lvAddr.setFocusable(false);
    }

    private void loadData(){
        if (UserManager.isUserLogin()) {
            new AddressListReq(this)
                    .execute(new Request.RequestCallback<List<UserAddress>>() {
                        @Override
                        public void onSuccess(List<UserAddress> userAddresses) {
                            if (userAddresses != null) {
                                mAddrList.addAll(userAddresses);
                                mAddrAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(Callback.CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {
                            if (mAddrList.size() == 0) {
                                tvNoAddr.setVisibility(View.VISIBLE);
                            } else {
                                tvNoAddr.setVisibility(View.GONE);
                            }
                        }
                    });
        }
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
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(this);
                    return;
                }
                ViewJump.toNewAddr(this);
                break;
            case R.id.tvRelocation:
                tvCurrentAddr.setText("");
                location();
                break;
        }
    }

    private void location(){
        mUpdatedLocation = true;
        MapHelper helper = new MapHelper(this,this);
        helper.startLocation();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation!=null) {
            Constants.currentLocation = bdLocation;
            //重新定位功能 置Null currentUserLocation
            Constants.currentUserLocation = null;
            tvCurrentAddr.setText(Constants.getLocation(false).address);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ViewJump.CODE_NEW_ADDRESS:
                    mAddrList.clear();
                    loadData();
                    break;
                case ViewJump.CODE_MAP_LOCATION:
                    if (data!=null){
                        PoiInfo mPoiInfo = data.getParcelableExtra("poiInfo");
                        if (mPoiInfo!=null){
                            tvCurrentAddr.setText(mPoiInfo.address);
                            mUpdatedLocation = true;
                            //设置用户指定的位置
                            Constants.currentUserLocation = mPoiInfo;
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void finish() {
        if (mUpdatedLocation)
            setResult(RESULT_OK);
        super.finish();
    }

}
