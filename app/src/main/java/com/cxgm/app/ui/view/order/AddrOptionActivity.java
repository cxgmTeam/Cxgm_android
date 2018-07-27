package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.io.order.AddressListReq;
import com.cxgm.app.ui.adapter.AddrAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

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

    List<UserAddress> mAddrList;
    AddrAdapter mAddrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_addr_option);
        ButterKnife.bind(this);

        init();
        loadData();

    }

    private void init() {
        tvTitle.setText(R.string.chioce_address);
        imgBack.setVisibility(View.VISIBLE);


        mAddrList = new ArrayList<>();
        mAddrAdapter = new AddrAdapter(this,mAddrList,true);
        lvAddr.setAdapter(mAddrAdapter);

        lvAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAddrList.get((int)id).isEnable) {
                    Intent data = new Intent();
                    data.putExtra("address", mAddrList.get((int) id));
                    setResult(RESULT_OK, data);
                    finish();
                }else {
                    ToastManager.sendToast(getString(R.string.adress_invalid));
                }
            }
        });

    }

    private void loadData(){
        new AddressListReq(this)
                .execute(new Request.RequestCallback<List<UserAddress>>() {
                    @Override
                    public void onSuccess(List<UserAddress> userAddresses) {
                        if (userAddresses!=null){
                            ViewHelper.filterAddress(AddrOptionActivity.this, userAddresses, Constants.currentShopId, new ViewHelper.OnActionListener() {
                                @Override
                                public void onSuccess() {
                                    mAddrList.addAll(userAddresses);
                                    mAddrAdapter.notifyDataSetChanged();
                                }
                            });

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

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @OnClick(R.id.tvNewAddr)
    public void onClickNewAddr() {
        if (!UserManager.isUserLogin()){
            ViewJump.toLogin(this);
            return;
        }
        ViewJump.toNewAddr(this);
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
            }
        }
    }
}
