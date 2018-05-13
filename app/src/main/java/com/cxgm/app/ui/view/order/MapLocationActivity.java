package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.PsfwTransfer;
import com.cxgm.app.data.io.common.FindAllPsfwReq;
import com.cxgm.app.ui.adapter.PoiAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.MapHelper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.widget.ListViewForScrollView;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图定位
 *
 * @author dean
 * @time 2018/4/20 上午10:19
 */

public class MapLocationActivity extends BaseActivity implements MapHelper.LocationCallback ,OnGetPoiSearchResultListener{

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

    double mLongitude;
    double mLatitude;
    BaiduMap mBaiduMap;
    PoiSearch mPoiSearch;
    GeoCoder mGeoCoder;
    String mTempCity;
    PoiAdapter mPoiAdapter;
    List<PoiAddrInfo> mPoiList;

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

        mBaiduMap = mapView.getMap();
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult!=null){
//                    reverseGeoCodeResult.getPoiList()
                    mTempCity = reverseGeoCodeResult.getAddressDetail().city;
                }
            }
        });

        mLongitude = getIntent().getDoubleExtra("longitude", -1);
        mLatitude = getIntent().getDoubleExtra("latitude", -1);

        init();

        if (mLongitude <0 || mLatitude <0){
            //没有经纬度信息时直接定位
            MapHelper mapHelper = new MapHelper(this,this);
            mapHelper.startLocation();
        }else {
            //标记定位点
            drawLocationPoint(mLatitude,mLongitude);
            //反向编码，以得到城市名，也可以得到POI信息
            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(mLatitude,mLongitude)));
        }

        loadData();

    }

    private void init(){
        etSearchWord.setHint(R.string.map_search_tag);
        //搜索
        etSearchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String keyword = etSearchWord.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword)){
                        loadPoi(mTempCity,keyword);
                    }
                }
                return true;
            }
        });

        mPoiList = new ArrayList<>();
        mPoiAdapter = new PoiAdapter(mPoiList);
        lvAddr.setAdapter(mPoiAdapter);
    }

    private void loadData(){
        if (Constants.currentShop!=null) {
            new FindAllPsfwReq(this,Constants.currentShop.getId())
                    .execute(new Request.RequestCallback<List<PsfwTransfer>>() {
                        @Override
                        public void onSuccess(List<PsfwTransfer> psfwTransfers) {
                            if (psfwTransfers!=null){
                                for (PsfwTransfer psfw:psfwTransfers){
                                    drawPsfw(psfw.getPsfw());
                                }
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

    private void drawPsfw(String psfw){
        if (!TextUtils.isEmpty(psfw)){
            String[] lnglats = psfw.split(",");
            List<LatLng> pts = new ArrayList<>();
            for (String s : lnglats){
                String[] lnglat = s.split("_");
                if (lnglat.length == 2){
                    pts.add(new LatLng(Double.parseDouble(lnglat[1]),Double.parseDouble(lnglat[0])));
                }
            }

            //构建用户绘制多边形的Option对象
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(pts)
                    .stroke(new Stroke(5, 0xAA00FF00))
                    .fillColor(0xAAFFFF00);

            //在地图上添加多边形Option，用于显示
            mBaiduMap.addOverlay(polygonOption);
        }
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation!=null){
            Constants.currentLocation = bdLocation;
            drawLocationPoint(bdLocation.getLatitude(),bdLocation.getLongitude());
        }
    }

    private void drawLocationPoint(double latitude,double longitude){
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

// 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                //.accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(latitude)
                .longitude(longitude).build();

// 设置定位数据
        mBaiduMap.setMyLocationData(locData);

// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//        mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_geo);
//        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
//        mBaiduMap.setMyLocationConfiguration(config);

// 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mPoiSearch.destroy();
        mGeoCoder.destroy();
    }

    /**
     * 搜索
     * @param city
     * @param keyword
     */
    private void loadPoi(String city,String keyword){
        if (TextUtils.isEmpty(city))
            city = Constants.currentLocation.getCity();
        mPoiList.clear();
        mPoiSearch.searchInCity(new PoiCitySearchOption()
            .city(city).keyword(keyword).pageNum(20)
        );
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        //获取POI检索结果
        if (poiResult!=null && poiResult.getAllAddr()!=null){
            mPoiList.addAll(poiResult.getAllAddr());
            mPoiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        //获取Place详情页检索结果
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
