package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.ui.adapter.OrderGoodsListAdatpter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品清单 订单中
 *
 * @anthor Dean
 * @time 2018/4/21 0021 0:11
 */
public class GoodsListActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.lvGoodsOrder)
    ListView lvGoodsOrder;

    ArrayList<OrderProduct> mOrderProductList;
    OrderGoodsListAdatpter mOrderProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_goods_order_list);
        ButterKnife.bind(this);
        mOrderProductList = getIntent().getParcelableArrayListExtra("products");
        init();
    }

    private void init(){
        tvTitle.setText(R.string.goods_order_list);
        imgBack.setVisibility(View.VISIBLE);

        if (mOrderProductList!=null){
            mOrderProductAdapter = new OrderGoodsListAdatpter(mOrderProductList);
            lvGoodsOrder.setAdapter(mOrderProductAdapter);
            lvGoodsOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ViewJump.toGoodsDetail(GoodsListActivity.this,mOrderProductList.get((int)id).getProductId());
                }
            });
        }
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        finish();
    }
}
