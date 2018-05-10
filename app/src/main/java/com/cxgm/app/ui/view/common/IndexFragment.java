package com.cxgm.app.ui.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.io.common.CheckAddressReq;
import com.cxgm.app.data.io.goods.FindFirstCategoryReq;
import com.cxgm.app.ui.adapter.FirstCategoryAdapter;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.adapter.ShopAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.widget.GridViewForScrollView;
import com.deanlib.ootb.widget.HorizontalListView;
import com.kevin.loopview.AdLoopView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.loopview.internal.LoopData;
import com.kevin.loopview.utils.JsonTool;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 *
 * @anthor dean
 * @time 2018/4/18 下午5:46
 */

public class IndexFragment extends BaseFragment {

    @BindView(R.id.imgLocation)
    ImageView imgLocation;
    @BindView(R.id.etSearchWord)
    EditText etSearchWord;
    @BindView(R.id.imgTextClear)
    ImageView imgTextClear;
    @BindView(R.id.imgMessage)
    ImageView imgMessage;
    @BindView(R.id.layoutAppbar)
    View layoutAppbar;
    @BindView(R.id.layoutMessage)
    LinearLayout layoutMessage;

    @BindView(R.id.loopBanner)
    AdLoopView loopBanner;
    @BindView(R.id.lvShop)
    ListView lvShop;

    @BindView(R.id.layoutShopShow)
    LinearLayout layoutShopShow;
    @BindView(R.id.gvFirstCategory)
    GridViewForScrollView gvFirstCategory;
    @BindView(R.id.tvNewsContent)
    TextView tvNewsContent;
    @BindView(R.id.hlvRecommend)
    HorizontalListView hlvRecommend;
    @BindView(R.id.hlvNewGoods)
    HorizontalListView hlvNewGoods;
    @BindView(R.id.layoutGoodsShow)
    LinearLayout layoutGoodsShow;

    @BindView(R.id.imgAd1)
    ImageView imgAd1;
    @BindView(R.id.hlvAdGoods1)
    HorizontalListView hlvAdGoods1;
    @BindView(R.id.imgAd2)
    ImageView imgAd2;
    @BindView(R.id.hlvAdGoods2)
    HorizontalListView hlvAdGoods2;
    @BindView(R.id.imgAd3)
    ImageView imgAd3;
    @BindView(R.id.hlvAdGoods3)
    HorizontalListView hlvAdGoods3;
    @BindView(R.id.gvGoods)
    GridViewForScrollView gvGoods;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    Shop mShop;
    BDLocation mLocation;
    Unbinder unbinder;

    FirstCategoryAdapter mFCAdapter;
    List<ShopCategory> mFCList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mLocation = getArguments().getParcelable("location");
        }
        if (mLocation != null) {
            new CheckAddressReq(getActivity(), mLocation.getLongitude() + "", mLocation.getLatitude() + "").execute(new Request.RequestCallback<List<Shop>>() {

                @Override
                public void onSuccess(List<Shop> shops) {
                    if (shops!=null && shops.size()>0)
                        mShop = shops.get(0);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(Callback.CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    init();
                    loadData();
                }
            });
        }

    }

    private void init() {

        etSearchWord.setFocusable(false);
        etSearchWord.setKeyListener(null);

        loopBanner.setOnClickListener(new BaseLoopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
                loopBanner.getLoopData();
            }
        });

        if (mShop == null) {
            //无可配送商铺
            layoutShopShow.setVisibility(View.VISIBLE);
            layoutGoodsShow.setVisibility(View.GONE);

            showPopLocationInfo(getString(R.string.out_of_distribution));
            lvShop.setAdapter(new ShopAdapter());

        } else {
            layoutShopShow.setVisibility(View.GONE);
            layoutGoodsShow.setVisibility(View.VISIBLE);
            //First Category
            mFCList = new ArrayList<>();
            mFCAdapter = new FirstCategoryAdapter(mFCList);
            gvFirstCategory.setAdapter(mFCAdapter);

            gvGoods.setAdapter(new GoodsAdapter(2, 30));
        }

    }

    private void loadData() {
        LoopData loopData = JsonTool.toBean("", LoopData.class);
        loopBanner.refreshData(loopData);
        loopBanner.startAutoLoop();

        if (mShop == null){

        }else {
//            new FindFirstCategoryReq(getActivity(), mShop.getId()).execute(new Request.RequestCallback<List<ShopCategory>>() {
//                @Override
//                public void onSuccess(List<ShopCategory> list) {
//                    if (list!=null){
//                        mFCList.addAll(list);
//                        mFCAdapter.notifyDataSetChanged();
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//
//                }
//
//                @Override
//                public void onCancelled(Callback.CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
        }
    }

    private void showPopLocationInfo(String message) {
        PopupWindow popupWindow = new PopupWindow();
        View view = View.inflate(getActivity(), R.layout.layout_location_info, null);
        TextView tvContent = view.findViewById(R.id.tvContent);
        tvContent.setText(message);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(imgLocation, DensityUtil.dip2px(6),DensityUtil.dip2px(-6));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgLocation, R.id.etSearchWord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgLocation:
                ViewJump.toAddrList(getActivity(),this,mLocation);
                break;
            case R.id.etSearchWord:
                ViewJump.toSearch(getActivity());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK){
            switch (requestCode){
                case ViewJump.CODE_ADDR_LIST:
                    if (data!=null){
                        mLocation = data.getParcelableExtra("location");
                    }
                    break;
            }
        }
    }
}
