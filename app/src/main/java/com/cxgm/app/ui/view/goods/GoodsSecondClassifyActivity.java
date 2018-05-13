package com.cxgm.app.ui.view.goods;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.goods.FindProductByCategoryReq;
import com.cxgm.app.data.io.goods.FindSecondCategoryReq;
import com.cxgm.app.data.io.goods.FindThirdCategoryReq;
import com.cxgm.app.ui.adapter.ExpandableGoodsListAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * 二级分类
 *
 * @anthor Dean
 * @time 2018/4/22 0022 16:13
 */
public class GoodsSecondClassifyActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.imgAction2)
    ImageView imgAction2;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.tabClassify)
    VerticalTabLayout tabClassify;
    @BindView(R.id.tabSubClassify)
    TabLayout tabSubClassify;
    @BindView(R.id.lvGoods)
    ExpandableListView lvGoods;

    ShopCategory mFirstCategory;
//    List<ShopCategory> mSCList;
    List<ShopCategory> mTCList;
    Map<String,List<ProductTransfer>> mProductMap;

    ExpandableGoodsListAdapter mEGLAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_second_classify);
        ButterKnife.bind(this);

        mFirstCategory = (ShopCategory) getIntent().getSerializableExtra("category");
        if (mFirstCategory != null) {
            init();
            loadData();
        }
    }

    private void init() {
        tvTitle.setText(mFirstCategory.getName());
        imgBack.setVisibility(View.VISIBLE);
        imgAction1.setImageResource(R.mipmap.search3);
        imgAction1.setVisibility(View.VISIBLE);
        imgAction2.setImageResource(R.mipmap.shop_cart3);
        imgAction2.setVisibility(View.VISIBLE);

        //三级分类
        mTCList = new ArrayList<>();
        //商品列表
        mProductMap = new LinkedHashMap<>();
        mEGLAdapter = new ExpandableGoodsListAdapter(mProductMap);
        lvGoods.setAdapter(mEGLAdapter);
        //全部展开
        for (int i = 0; i < mEGLAdapter.getGroupCount(); i++) {
            lvGoods.expandGroup(i);
        }
        //拦截组点击事件，防止点击收起组
        lvGoods.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        lvGoods.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ViewJump.toGoodsDetail(GoodsSecondClassifyActivity.this);
                return true;
            }
        });
    }

    private void loadData() {

        //二级分类
        new FindSecondCategoryReq(this, Constants.currentShop.getId(), mFirstCategory.getId())
                .execute(new Request.RequestCallback<List<ShopCategory>>() {
                    @Override
                    public void onSuccess(final List<ShopCategory> shopCategories) {
                        if (shopCategories != null) {
                            tabClassify.setTabAdapter(new TabAdapter() {
                                @Override
                                public int getCount() {
                                    return shopCategories.size();
                                }

                                @Override
                                public ITabView.TabBadge getBadge(int position) {
                                    return null;
                                }

                                @Override
                                public ITabView.TabIcon getIcon(int position) {
                                    return null;
                                }

                                @Override
                                public ITabView.TabTitle getTitle(int position) {
                                    return new ITabView.TabTitle.Builder()
                                            .setContent(shopCategories.get(position).getName()).build();
                                }

                                @Override
                                public int getBackground(int position) {
                                    return 0;
                                }
                            });
                            tabClassify.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabView tab, int position) {
                                    loadSubTabs(shopCategories.get(position).getId());
                                }

                                @Override
                                public void onTabReselected(TabView tab, int position) {

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

    /**
     * 三级分类
     * @param tabId 二级分类ID
     */
    private void loadSubTabs(final int tabId) {
        new FindThirdCategoryReq(this, Constants.currentShop.getId(), tabId)
                .execute(new Request.RequestCallback<List<ShopCategory>>() {
                    @Override
                    public void onSuccess(List<ShopCategory> shopCategories) {
                        if (shopCategories != null) {
                            mTCList.addAll(shopCategories);
                            for (ShopCategory category : mTCList) {
                                tabSubClassify.addTab(tabSubClassify.newTab()
                                        .setText(category.getName()).setTag(category));
                            }
                            tabSubClassify.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    ShopCategory category = (ShopCategory) tab.getTag();
                                    //锚点定位
                                    for (int i = 0;i<mTCList.size();i++){
                                        if (category.getId() == mTCList.get(i).getId()){
                                            lvGoods.setSelectedGroup(i);
                                            break;
                                        }
                                    }

                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });

                            loadGoodsList(tabId);
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

    /**
     * 商品列表
     * @param tabId 二级分类ID
     */
    private void loadGoodsList(int tabId) {
        new FindProductByCategoryReq(this, Constants.currentShop.getId(),tabId)
                .execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
                    @Override
                    public void onSuccess(PageInfo<ProductTransfer> productTransferPageInfo) {
                        if (productTransferPageInfo != null && productTransferPageInfo.getList() != null) {
                            for (ShopCategory category : mTCList) {
                                //调整顺序到与 mTCList 一致
                                List<ProductTransfer> list = mProductMap.get(category.getName());
                                if (list == null) {
                                    list = new ArrayList<>();
                                    mProductMap.put(category.getName(), list);
                                }
                                for (ProductTransfer product : productTransferPageInfo.getList()) {
                                    if (product.getProductCategoryThirdId() == category.getId()) {
                                        list.add(product);
                                    }
                                }
                            }
                            mEGLAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.imgBack, R.id.imgAction1, R.id.imgAction2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAction1:
                break;
            case R.id.imgAction2:
                break;
        }
    }

}
