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
import com.cxgm.app.ui.adapter.GoodsSecondClassifyAdapter;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

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

    GoodsSecondClassifyAdapter mGSCAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_second_classify);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //TODO tvTitle.setText();
        imgBack.setVisibility(View.VISIBLE);
        imgAction1.setImageResource(R.mipmap.search3);
        imgAction1.setVisibility(View.VISIBLE);
        imgAction2.setImageResource(R.mipmap.shop_cart3);
        imgAction2.setVisibility(View.VISIBLE);

        tabClassify.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return 0;
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
                return null;
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });

        mGSCAdapter = new GoodsSecondClassifyAdapter();
        lvGoods.setAdapter(mGSCAdapter);
        //全部展开
        for (int i = 0;i< mGSCAdapter.getGroupCount();i++){
            lvGoods.expandGroup(i);
        }
        lvGoods.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
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
