package com.cxgm.app.ui.view.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
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
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.PsfwTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.UserPoiInfo;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.common.FindAllPsfwReq;
import com.cxgm.app.ui.adapter.PoiAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.MapHelper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DLogUtils;
import com.deanlib.ootb.utils.DeviceUtils;
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

public class MapLocationActivity extends BaseActivity implements MapHelper.LocationCallback, OnGetPoiSearchResultListener {

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
    ListView lvAddr;
    @BindView(R.id.tvNoResult)
    TextView tvNoResult;
    @BindView(R.id.imgMarket)
    ImageView imgMarket;

    double mLongitude;
    double mLatitude;
    BaiduMap mBaiduMap;
    PoiSearch mPoiSearch;
    GeoCoder mGeoCoder;
    String mTempCity;
    //PoiInfo mSearchInfo; //搜索出来的地址，应该加入到poi list的第一个
    PoiAdapter mPoiAdapter;
    List<UserPoiInfo> mPoiList;
    float mZoomLevel = 15;
    Overlay mPointOverlay;
    List<Path> mPathList;
    final int mPathBaseNum = 100000;
    Point mCenterPoint;

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

        mLongitude = getIntent().getDoubleExtra("longitude", -1);
        mLatitude = getIntent().getDoubleExtra("latitude", -1);

        init();
        initMap();
        loadData();

    }

    private void initMap() {
        mBaiduMap = mapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                mZoomLevel = mapStatus.zoom;
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                mZoomLevel = mapStatus.zoom;
            }
        });
//        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                //标记定位点
//                drawLocationPoint(latLng.latitude, latLng.longitude);
//                //反向编码，以得到城市名，也可以得到POI信息
//                mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latLng.latitude, latLng.longitude)));
//            }
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                onMapClick(mapPoi.getPosition());
//                return true;
//            }
//        });


        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                //不用了
                //搜索时会调用到这里，但是这个结果里并没有poi，所以通过 反地理编码 可以得到POI 信息，
                //使用Poi 的searchInCity 有时并不能得到信息，所以改用了地理编码的方式，先得到搜索关键字的地址信息
                if (geoCodeResult!=null && geoCodeResult.getLocation()!=null){
                    //标记定位点
                    //缩放地图以定位点为中心
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                            .newMapStatus(new MapStatus.Builder()
                                    .target(geoCodeResult.getLocation())
                                    .zoom(mZoomLevel).build()));
                    //反向编码，以得到城市名，也可以得到POI信息
                    mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(geoCodeResult.getLocation()));
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult != null && reverseGeoCodeResult.getAddressDetail() != null) {
                    mTempCity = reverseGeoCodeResult.getAddressDetail().city;
//                    mSearchInfo = new PoiInfo();
//                    mSearchInfo.name = reverseGeoCodeResult.get
//                    mSearchInfo.address = reverseGeoCodeResult.getAddress();
//                    mSearchInfo.location = reverseGeoCodeResult.getLocation();
                    //获取POI检索结果
                    setPoiList(reverseGeoCodeResult.getPoiList(),false);
                }
            }
        });

        //定位 画两个标记 自身定位一个，用户选择地址一个（总是在地图的正中心）
        MapHelper mapHelper = new MapHelper(this, this);
        mapHelper.startLocation();

        if (mLongitude >= 0 || mLatitude >= 0) {
            //反向编码，以得到城市名，也可以得到POI信息
            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(mLatitude, mLongitude)));
        }

        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                int[] location = new int[2] ;
                imgMarket.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                //拿到地图中间点的坐标
                int x = DeviceUtils.getSreenWidth()/2;
                int y = location[1];
                mCenterPoint = new Point(x,y);
                LatLng latLng = mapView.getMap().getProjection().fromScreenLocation(mCenterPoint);
                //反向编码，以得到城市名，也可以得到POI信息
                mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latLng.latitude, latLng.longitude)));
            }
        });

        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if(motionEvent.getPointerCount()==1) {
                            LatLng latLng = mapView.getMap().getProjection().fromScreenLocation(mCenterPoint);
                            //反向编码，以得到城市名，也可以得到POI信息
                            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latLng.latitude, latLng.longitude)));
                            //缩放地图以定位点为中心
//                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory
//                                    .newMapStatus(new MapStatus.Builder()
//                                            .target(latLng)
//                                            .zoom(mZoomLevel).build()));

                        }

                        break;
                }

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    private void init() {
        etSearchWord.setHint(R.string.map_search_tag);
        //搜索
        etSearchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = etSearchWord.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword)) {
//                        View view = getWindow().peekDecorView();
//                        if (view != null) {
//                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                        }
                        DeviceUtils.hideKeyboard(MapLocationActivity.this);
//                        loadPoi(mTempCity, keyword);
                        loadPoi("北京", keyword);
                    }
                }
                return true;
            }
        });

        mPathList = new ArrayList<>();

        mPoiList = new ArrayList<>();
        mPoiAdapter = new PoiAdapter(mPoiList);
        lvAddr.setAdapter(mPoiAdapter);
        lvAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < mPoiList.size(); i++) {
                    mPoiList.get(i).isChecked = i == id;
                }
                mPoiAdapter.notifyDataSetChanged();
                /** 改成选一个地址直接就返回 不再需要定位
                 //标记定位点
                 drawLocationPoint(mPoiList.get((int) id).location.latitude, mPoiList.get((int) id).location.longitude);
                 //反向编码，以得到城市名，也可以得到POI信息 开启后，得到新的结果会重置这个list,看上去就是跳来跳去
                 mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(mPoiList.get((int) id).location.latitude, mPoiList.get((int) id).location.longitude)));
                 **/
                finish();

            }
        });
    }

    private void loadData() {
        //用定位地址区分是否显示全部店铺的区域，还是指定店铺的区域
        if (Constants.currentLocation!=null){
            new CheckAddressReq(this,Constants.currentLocation.getLongitude()+"",Constants.currentLocation.getLatitude()+"")
                    .execute(new Request.RequestCallback<List<Shop>>() {
                        @Override
                        public void onSuccess(List<Shop> shops) {
                            Shop temp = null;
                            if (shops!=null && shops.size()>0){
                                temp = shops.get(0);
                            }
                            new FindAllPsfwReq(MapLocationActivity.this, temp != null ? temp.getId() : 0)
                                    .execute(mPsfwCallback);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            new FindAllPsfwReq(MapLocationActivity.this).execute(mPsfwCallback);
                        }

                        @Override
                        public void onCancelled(Callback.CancelledException cex) {
                            new FindAllPsfwReq(MapLocationActivity.this).execute(mPsfwCallback);
                        }

                        @Override
                        public void onFinished() {

                        }
                    });
        }else {
            new FindAllPsfwReq(this).execute(mPsfwCallback);
        }


    }

    Request.RequestCallback mPsfwCallback = new Request.RequestCallback<List<PsfwTransfer>>() {
        @Override
        public void onSuccess(List<PsfwTransfer> psfwTransfers) {
            if (psfwTransfers != null) {
                mPathList.clear();
                for (PsfwTransfer psfw : psfwTransfers) {
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
    };

    private void drawPsfw(String psfw) {
        if (!TextUtils.isEmpty(psfw)) {
            String[] lnglats = psfw.split(",");
            List<LatLng> pts = new ArrayList<>();
            Path path = new Path();
            for (String s : lnglats) {
                String[] lnglat = s.split("_");
                if (lnglat.length == 2) {
                    pts.add(new LatLng(Double.parseDouble(lnglat[1]), Double.parseDouble(lnglat[0])));
                    if (path.isEmpty()) {
                        path.moveTo(Float.parseFloat(lnglat[1]) * mPathBaseNum, Float.parseFloat(lnglat[0]) * mPathBaseNum);
                    } else {
                        path.lineTo(Float.parseFloat(lnglat[1]) * mPathBaseNum, Float.parseFloat(lnglat[0]) * mPathBaseNum);
                    }
                }
            }
            path.close();
            mPathList.add(path);

            //构建用户绘制多边形的Option对象
            OverlayOptions polygonOption = new PolygonOptions()
                    .points(pts)
//                    .stroke(new Stroke(5, 0xAA6A006C))
                    .fillColor(0xAAB9F6F5);
            //绘制虚线
            OverlayOptions ooPolyline = new PolylineOptions().width(5)
                    .color(0xAA6A006C).points(pts).dottedLine(true);

            //在地图上添加多边形Option，用于显示
            mBaiduMap.addOverlay(polygonOption);
            mBaiduMap.addOverlay(ooPolyline);
        }
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation != null) {
            Constants.currentLocation = bdLocation;
            mTempCity = bdLocation.getCity();
            drawLocationPoint(bdLocation.getLatitude(), bdLocation.getLongitude());
            //反向编码，以得到城市名，也可以得到POI信息
            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())));
        }
    }

    /**
     * 绘制Point
     *
     * @param latitude
     * @param longitude
     */
    private void drawLocationPoint(double latitude, double longitude) {
        //先画个圆
        LatLng llCircle = new LatLng(latitude, longitude);
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x554AAEF3)
                .center(llCircle).stroke(new Stroke(2, 0xAA4AAEF3)).radius(300);
        //设置颜色和透明度，均使用16进制显示，0xAARRGGBB，如 0xAA000000 其中AA是透明度，000000为颜色
        mBaiduMap.addOverlay(ooCircle);


        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.location3);

        /* 取消定位图层的原因是定位图层的标记自定义功能缺少锚点操作，改用覆盖物方式画标记
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
        //      .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(latitude)
                .longitude(longitude).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mBaiduMap.setMyLocationConfiguration(config);

        // 当不需要定位图层时关闭定位图层
        //mBaiduMap.setMyLocationEnabled(false);
        */

        //覆盖物方式画标记点
        if (mPointOverlay != null)
            mPointOverlay.remove();
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(mCurrentMarker)
                .anchor(mCurrentMarker.getBitmap().getWidth() / 2, mCurrentMarker.getBitmap().getHeight());

        //在地图上添加Marker，并显示
        mPointOverlay = mBaiduMap.addOverlay(option);

        //缩放地图以定位点为中心
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                .newMapStatus(new MapStatus.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(mZoomLevel).build()));
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
     * 搜索POI
     *
     * @param city
     * @param keyword
     */
    private void loadPoi(String city, String keyword) {
        if (TextUtils.isEmpty(city))
            city = Constants.getLocation(false).city;
        //市内方式
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(city).keyword(keyword)
        );
        //附近方式
//        LatLng latLng = mapView.getMap().getProjection().fromScreenLocation(mCenterPoint);
//        mPoiSearch.searchNearby(
//                new PoiNearbySearchOption()
//                .keyword(keyword).sortType(PoiSortType.distance_from_near_to_far)
//                        .location(latLng).radius(10000)
//        );
        //地理编码方式
//        mGeoCoder.geocode(new GeoCodeOption().city(city).address(keyword));
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        //获取POI检索结果
        if (poiResult != null)
            setPoiList(poiResult.getAllPoi(),true);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        //获取Place详情页检索结果
        if (poiDetailResult!=null){

        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    private void setPoiList(List<PoiInfo> poiInfos,boolean isCenter) {
        if (poiInfos != null) {
            mPoiList.clear();
            for (int i = 0; i < poiInfos.size(); i++) {
                UserPoiInfo info = new UserPoiInfo(poiInfos.get(i));
                if (i == 0) {
                    info.isChecked = true;
                    if (isCenter){
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                                .newMapStatus(new MapStatus.Builder()
                                        .target(info.location)
                                        .zoom(mZoomLevel).build()));
                        //drawLocationPoint(info.location.latitude, info.location.longitude);
                    }

                }
                mPoiList.add(info);
            }
            mPoiAdapter.notifyDataSetChanged();
            tvNoResult.setVisibility(View.GONE);
            lvAddr.setVisibility(View.VISIBLE);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            lvAddr.setVisibility(View.GONE);
        }
    }

//    /**
//     * 检查点是否包含在配送区
//     * 该方法不管用，也不知道是哪里出了问题
//     * 二了一个晚上，早上醒来才想起来，checkAddress接口不就是干这个的嘛
//     * @param latitude
//     * @param longitude
//     * @return
//     */
//    private boolean checkPointEnable(double latitude,double longitude){
//        boolean b = false;
//        latitude *=mPathBaseNum;
//        longitude *=mPathBaseNum;
//        for (Path path : mPathList){
//            RectF bounds = new RectF();
//            path.computeBounds(bounds,true);
//            Region region = new Region();
//            region.setPath(path,new Region((int)bounds.left,(int)bounds.top,(int)bounds.right,(int)bounds.bottom));
//            b = region.contains((int)latitude,(int)longitude);
//            if (b)
//                break;
//        }
//        return b;
//    }

    @Override
    public void finish() {
        for (UserPoiInfo info : mPoiList) {
            DLogUtils.d(info.toString());
            if (info.isChecked) {
//                if (checkPointEnable(info.location.latitude,info.location.longitude)) {
                Intent data = new Intent();
                data.putExtra("poiInfo", info);
                setResult(RESULT_OK, data);
//                }else {
//                    ToastManager.sendToast(getString(R.string.addr_cannot_delivery));
//                }
                break;
            }
        }
        super.finish();
    }
}
