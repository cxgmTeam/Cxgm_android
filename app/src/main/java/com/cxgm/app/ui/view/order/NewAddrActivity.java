package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.io.order.AddAddressReq;
import com.cxgm.app.data.io.order.AddressListReq;
import com.cxgm.app.data.io.order.UpdateAddressReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.ValidateUtils;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增地址 或 编辑地址
 *
 * @anthor dean
 * @time 2018/4/19 下午2:21
 */

public class NewAddrActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etConsignee)
    EditText etConsignee;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.tvDistrict)
    TextView tvDistrict;
    @BindView(R.id.etNumber)
    EditText etNumber;
    @BindView(R.id.etRemark)
    EditText etRemark;
    @BindView(R.id.tvSave)
    TextView tvSave;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;

    UserAddress mAddress;
    boolean mIsEdit;
    PoiInfo mPoiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        }
        setContentView(R.layout.activity_new_addr);
        ButterKnife.bind(this);

        mAddress = (UserAddress) getIntent().getSerializableExtra("address");
        mIsEdit = mAddress != null;

        init();
        loadData();
    }

    private void init() {
        if (mIsEdit){
            tvTitle.setText(R.string.edit_addr);
            etConsignee.setText(mAddress.getRealName());
            etPhoneNumber.setText(mAddress.getPhone());
            tvDistrict.setText(mAddress.getArea());
            etNumber.setText(mAddress.getAddress());
            etRemark.setText(mAddress.getRemarks());
        }else
            tvTitle.setText(R.string.new_addr);
        imgBack.setVisibility(View.VISIBLE);
    }

    private void loadData(){
        //如果不是编辑，新添加，可能是第一次添加地址，如果是该用户的第一个地址，应自动设置为默认地址
        if (!mIsEdit) {
            new AddressListReq(this)
                    .execute(new Request.RequestCallback<List<UserAddress>>() {
                        @Override
                        public void onSuccess(List<UserAddress> userAddresses) {
                            if (userAddresses == null || userAddresses.size() == 0) {
                                if (mAddress == null) {
                                    mAddress = new UserAddress();
                                }
                                mAddress.setIsDef(1);//设置地址为默认地址
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

                        }
                    });
        }
    }

    @OnClick({R.id.imgBack, R.id.layoutDistrict, R.id.tvSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.layoutDistrict:
                ViewJump.toMapLocation(this);
                break;
            case R.id.tvSave:
                save();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ViewJump.CODE_MAP_LOCATION:
                    if (data!=null){
                        mPoiInfo = data.getParcelableExtra("poiInfo");
                        if (mPoiInfo!=null){
                            tvDistrict.setText(mPoiInfo.address);
                        }
                    }
                    break;
            }
        }
    }

    private void save(){
        String consignee = etConsignee.getText().toString().trim();
        if (TextUtils.isEmpty(consignee)){
            ToastManager.sendToast(getString(R.string.consignee_empty));
            return;
        }
        String phoneNum = etPhoneNumber.getText().toString().trim();
        if (!ValidateUtils.isMobileNum(phoneNum)){
            ToastManager.sendToast(getString(R.string.invaild_phone_num));
            return;
        }
        String district = tvDistrict.getText().toString();
        if (TextUtils.isEmpty(district)){
            ToastManager.sendToast(getString(R.string.delivery_address_empty));
            return;
        }
        String number = etNumber.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();

        if (mAddress==null){
            mAddress = new UserAddress();
        }
        mAddress.setRealName(consignee);
        mAddress.setPhone(phoneNum);
        //Q:area address 与UI不符  没有remark备注信息
        //A：area存成小区大厦 address存成门牌号  先不用管这个备注
        mAddress.setArea(district);
        mAddress.setAddress(number);
        //经纬度信息
        if (mPoiInfo != null && mPoiInfo.location!=null) {
            mAddress.setLongitude(mPoiInfo.location.longitude + "");
            mAddress.setDimension(mPoiInfo.location.latitude + "");
        }
        mAddress.setRemarks(remark);

        //检查范围
        /*new CheckAddressReq(this,mAddress.getLongitude()+"",mAddress.getDimension()+"").execute(new Request.RequestCallback<List<Shop>>() {
            @Override
            public void onSuccess(List<Shop> shops) {
                if (shops!=null && shops.size()>0) {
                    if (mIsEdit) {
                        //约定编辑传Null 以区分设置默认功能
                        mAddress.setIsDef(null);
                        new UpdateAddressReq(NewAddrActivity.this, mAddress).execute(callback);
                    } else {
                        new AddAddressReq(NewAddrActivity.this, mAddress).execute(callback);
                    }
                }else {
                    ToastManager.sendToast(getString(R.string.addr_cannot_delivery));
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

            }
        });*/

        if (mIsEdit) {
            //约定编辑传Null 以区分设置默认功能
            mAddress.setIsDef(null);
            new UpdateAddressReq(NewAddrActivity.this, mAddress).execute(callback);
        } else {
            new AddAddressReq(NewAddrActivity.this, mAddress).execute(callback);
        }

    }

    Request.RequestCallback callback = new Request.RequestCallback<Integer>() {
        @Override
        public void onSuccess(Integer integer) {
            ToastManager.sendToast(getString(R.string.save_successful));
            setResult(RESULT_OK);
            finish();
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
    };
}
