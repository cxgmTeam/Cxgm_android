package com.cxgm.app.ui.view.common;

import android.Manifest;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.deanlib.ootb.manager.PermissionManager;
import com.tbruyelle.rxpermissions.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * 骨架
 *
 * @anthor dean
 * @time 2018/4/18 下午5:47
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.layout_container)
    FrameLayout layoutContainer;
    @BindView(R.id.rbIndex)
    RadioButton rbIndex;
    @BindView(R.id.rbGoods)
    RadioButton rbGoods;
    @BindView(R.id.rbShopcar)
    RadioButton rbShopcar;
    @BindView(R.id.rbUser)
    RadioButton rbUser;
    @BindView(R.id.imgLocation)
    ImageView imgLocation;
    @BindView(R.id.etSearchWord)
    EditText etSearchWord;
    @BindView(R.id.imgTextClear)
    ImageView imgTextClear;
    @BindView(R.id.imgMessage)
    ImageView imgMessage;
    @BindView(R.id.layoutMenu)
    RadioGroup layoutMenu;

    IndexFragment mIndexFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

        //请求权限
        PermissionManager.requstPermission(this, new Action1<Permission>() {
            @Override
            public void call(Permission permission) {

                if (permission.granted){



                }

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void init() {

        mIndexFragment = new IndexFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.layout_container,mIndexFragment)
                .hide(mIndexFragment).commit();

        layoutMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeView(checkedId);
            }
        });

        rbIndex.setChecked(true);
    }

    private void loadData() {

    }

    private void changeView(int checkedId) {
        switch (checkedId){

            case R.id.rbIndex:
                getSupportFragmentManager().beginTransaction().show(mIndexFragment).commit();
                break;
        }
    }
}
