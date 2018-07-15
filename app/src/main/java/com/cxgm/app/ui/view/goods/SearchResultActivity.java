package com.cxgm.app.ui.view.goods;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.goods.SearchReq;
import com.cxgm.app.ui.adapter.GoodsAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索结果
 *
 * @anthor Dean
 * @time 2018/4/19 0019 22:50
 */

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.etSearchWord)
    EditText etSearchWord;
    @BindView(R.id.imgTextClear)
    ImageView imgTextClear;
    @BindView(R.id.gvGoods)
    GridView gvGoods;
    @BindView(R.id.imgShopCar)
    ImageView imgShopCar;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.layoutSearchEmpty)
    LinearLayout layoutSearchEmpty;

    String mKeyword;
    List<ProductTransfer> mProductList;
    GoodsAdapter mProductAdapter;
    int mPageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        mKeyword = getIntent().getStringExtra("keyword");
        init();
        loadData(mKeyword);
    }

    private void init() {
        etSearchWord.setText(mKeyword);
        mProductList = new ArrayList<>();
        mProductAdapter = new GoodsAdapter(this, mProductList, 2, 30);
        gvGoods.setAdapter(mProductAdapter);
        gvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewJump.toGoodsDetail(SearchResultActivity.this, mProductList.get((int) id).getId());
            }
        });
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageNum++;
                loadData(mKeyword);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = 1;
                mProductList.clear();
                loadData(mKeyword);
            }
        });
        //键盘监听
        etSearchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    loadData(etSearchWord.getText().toString());
                }
                return true;
            }
        });
    }

    private void loadData(String keyword) {
        if (Constants.currentShop != null) {
            keyword = keyword.trim();
            if (TextUtils.isEmpty(keyword)) {
                ToastManager.sendToast(getString(R.string.keyword_is_empty));
            } else {
                new SearchReq(this, Constants.currentShop.getId(), keyword).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
                    @Override
                    public void onSuccess(PageInfo<ProductTransfer> productTransferPageInfo) {
                        if (productTransferPageInfo != null && productTransferPageInfo.getList() != null) {
                            mProductList.addAll(productTransferPageInfo.getList());
                            mProductAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        mPageNum = Helper.getUnsuccessPageNum(mPageNum);
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        mPageNum = Helper.getUnsuccessPageNum(mPageNum);
                    }

                    @Override
                    public void onFinished() {
                        if (mProductList.size() > 0) {
                            srl.setVisibility(View.VISIBLE);
                            layoutSearchEmpty.setVisibility(View.GONE);
                        } else {
                            //没有搜索到结果
                            srl.setVisibility(View.GONE);
                            layoutSearchEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
    }


    @OnClick({R.id.imgBack, R.id.imgShopCar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgShopCar:
                ViewJump.toMain(this,R.id.rbShopCart);
                break;
        }
    }
}
