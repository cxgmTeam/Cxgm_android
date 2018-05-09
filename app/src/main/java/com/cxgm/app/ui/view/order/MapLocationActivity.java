package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.TextureMapView;
import com.cxgm.app.R;
import com.cxgm.app.ui.adapter.PoiAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.deanlib.ootb.widget.ListViewForScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图定位
 *
 * @author dean
 * @time 2018/4/20 上午10:19
 */

public class MapLocationActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.etSearchWord)
    EditText etSearchWord;
    @BindView(R.id.imgTextClear)
    ImageView imgTextClear;
    @BindView(R.id.layoutInput)
    LinearLayout layoutInput;
    @BindView(R.id.mapView)
    TextureMapView mapView;
    @BindView(R.id.lvAddr)
    ListViewForScrollView lvAddr;
    @BindView(R.id.tvNoResult)
    TextView tvNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_location);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        etSearchWord.setHint(R.string.map_search_tag);

        lvAddr.setAdapter(new PoiAdapter());
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }
}
