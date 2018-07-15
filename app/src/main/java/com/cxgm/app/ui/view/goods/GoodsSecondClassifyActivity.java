package com.cxgm.app.ui.view.goods;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.goods.FindProductByCategoryReq;
import com.cxgm.app.data.io.goods.FindSecondCategoryReq;
import com.cxgm.app.data.io.goods.FindThirdCategoryReq;
import com.cxgm.app.data.io.order.ShopCartListReq;
import com.cxgm.app.ui.adapter.ExpandableGoodsListAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class GoodsSecondClassifyActivity extends BaseActivity implements ExpandableGoodsListAdapter.OnShopCartActionListener,ViewHelper.OnShopCartUpdateListener{

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
    @BindView(R.id.layoutEmpty)
    LinearLayout layoutEmpty;

    ShopCategory mFirstCategory;
//    List<ShopCategory> mSCList;
    List<ShopCategory> mTCList;
    Map<String,List<ProductTransfer>> mProductMap;

    ExpandableGoodsListAdapter mEGLAdapter;
//    Badge mShopCartBadge;
    int mShopCartNum = 0; //种类数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_second_classify);
        ButterKnife.bind(this);
        ViewHelper.addOnShopCartUpdateListener(this);
        mFirstCategory = (ShopCategory) getIntent().getSerializableExtra("category");
        if (mFirstCategory != null) {
            init();
            loadData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewHelper.updateShopCart(this);
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
        mEGLAdapter = new ExpandableGoodsListAdapter(this,mProductMap,this);
        lvGoods.setAdapter(mEGLAdapter);

        lvGoods.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ViewJump.toGoodsDetail(GoodsSecondClassifyActivity.this,mProductMap.get(mEGLAdapter.getKeyList().get(groupPosition)).get(childPosition).getId());
                return true;
            }
        });
    }

    private void loadData() {
        if (Constants.currentShop==null){
            ToastManager.sendToast(getString(R.string.choice_shop));
            finish();
        }else {
            //二级分类
            new FindSecondCategoryReq(this, Constants.currentShop.getId(), mFirstCategory.getId())
                    .execute(new Request.RequestCallback<List<ShopCategory>>() {
                        @Override
                        public void onSuccess(final List<ShopCategory> shopCategories) {
                            if (shopCategories != null && shopCategories.size() > 0) {
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
                                                .setContent(shopCategories.get(position).getName())
                                                .setTextColor(getResources().getColor(R.color.textBlack)
                                                        , getResources().getColor(R.color.textBlackTint)).build();
                                    }

                                    @Override
                                    public int getBackground(int position) {
                                        return 0;
                                    }
                                });
                                tabClassify.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabView tab, int position) {
                                        for (int i = 0; i < tabClassify.getTabCount(); i++) {
                                            if (i != position)
                                                tabClassify.getTabAt(i).setBackgroundColor(0);
                                        }
                                        tab.getTabView().setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                        loadSubTabs(shopCategories.get(position).getId());
                                    }

                                    @Override
                                    public void onTabReselected(TabView tab, int position) {

                                    }
                                });

                                tabClassify.setTabSelected(0, true);
                                //上边的 callListener 不管用，所以有了下面这两行代码
                                tabClassify.getTabAt(0).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                loadSubTabs(shopCategories.get(0).getId());
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


    /**
     * 三级分类
     * @param tabId 二级分类ID
     */
    private void loadSubTabs(final int tabId) {
        mTCList.clear();
        tabSubClassify.removeAllTabs();
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
                        loadGoodsList(tabId);
                    }
                });
    }

    /**
     * 商品列表
     * @param tabId 二级分类ID
     */
    private void loadGoodsList(int tabId) {
        //mProductMap.clear();
        new FindProductByCategoryReq(this, Constants.currentShop.getId(),tabId)
                .execute(new Request.RequestCallback<List<ProductTransfer>>() {
                    @Override
                    public void onSuccess(List<ProductTransfer> productTransferPageInfo) {
                        mProductMap.clear();
                        if (productTransferPageInfo != null ) {
                            for (ShopCategory category : mTCList) {
                                //调整顺序到与 mTCList 一致
                                List<ProductTransfer> list = mProductMap.get(category.getName());
                                if (list == null) {
                                    list = new ArrayList<>();
                                    mProductMap.put(category.getName(), list);
                                }
                                for (ProductTransfer product : productTransferPageInfo) {
                                    if (product.getProductCategoryThirdId() == category.getId()) {
                                        list.add(product);
                                    }
                                }
                            }

                            //其他
                            List<ProductTransfer> otherList = new ArrayList<>();
                            for (ProductTransfer product : productTransferPageInfo){
                                if (product.getProductCategoryThirdId()==0){
                                    otherList.add(product);
                                }
                            }
                            mProductMap.put("",otherList);

                            //清除无商品的三级分类，不显示
                            Iterator<Map.Entry<String, List<ProductTransfer>>> iterator = mProductMap.entrySet().iterator();
                            while (iterator.hasNext()){

                                if (iterator.next().getValue().size() == 0 ){
                                    iterator.remove();
                                }
                            }

                            mEGLAdapter.notifyDataSetChanged();

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
                            layoutEmpty.setVisibility(View.GONE);
                            lvGoods.setVisibility(View.VISIBLE);
                        }else {
                            layoutEmpty.setVisibility(View.VISIBLE);
                            lvGoods.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println(111);
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        System.out.println(111);
                    }

                    @Override
                    public void onFinished() {
                        System.out.println(111);
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
                //搜索
                ViewJump.toSearch(this);
                break;
            case R.id.imgAction2:
                //购物车 由于使用 qbadgeview 这里的点击事件无效了
                ViewJump.toMain(this,R.id.rbShopCart);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ViewJump.CODE_GOODS_DETAIL:
                    //更新数量
                    if (data!=null){
                        ProductTransfer product = (ProductTransfer) data.getSerializableExtra("product");
                        if (product!=null){
                            List<ProductTransfer> list = mProductMap.get(product.getProductCategoryThirdName());
                            if (list!=null) {
                                for (ProductTransfer p : list){
                                    if (p.getId() == product.getId()){
                                        p.setShopCartNum(product.getShopCartNum());
                                        mEGLAdapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

    //    private void drawShopCartNum(int num){
//        //商品种类，不是商品数量
//        if (mShopCartBadge == null) {
//            mShopCartBadge = new QBadgeView(GoodsSecondClassifyActivity.this)
//                    .bindTarget(imgAction2).setBadgeTextSize(8, true);
//            RxView.clicks(imgAction2).throttleFirst(2, TimeUnit.SECONDS)
//                    .subscribe(o->{ViewJump.toMain(this,R.id.rbShopCart);});
//        }
//        if (num>0)
//            mShopCartBadge.setBadgeNumber(num);
//        else mShopCartBadge.hide(true);
//    }


    @Override
    public void onAddGoods(ProductTransfer product) {
        if (product!=null && product.getShopCartNum() == 1){
            //从无到有
//            mShopCartNum ++;
//            ViewHelper.drawShopCartNum(GoodsSecondClassifyActivity.this,imgAction2,mShopCartNum);
            ViewHelper.updateShopCart(this);
        }

    }

    @Override
    public void onMinusGoods(ProductTransfer product) {
        if (product!=null && product.getShopCartNum() == 0){
            //从有到无
//            mShopCartNum --;
//            if (mShopCartNum<0)
//                mShopCartNum = 0;
//            ViewHelper.drawShopCartNum(GoodsSecondClassifyActivity.this,imgAction2,mShopCartNum);
            ViewHelper.updateShopCart(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewHelper.removeShopCartUpdateListener(this);
    }

    @Override
    public void onUpdate(int num) {
        mShopCartNum = num;
        ViewHelper.drawShopCartNum(GoodsSecondClassifyActivity.this,imgAction2,mShopCartNum,true);

    }
}
