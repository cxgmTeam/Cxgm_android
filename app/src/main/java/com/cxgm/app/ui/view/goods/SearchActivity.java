package com.cxgm.app.ui.view.goods;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.goods.FindHotProductReq;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索
 *
 * @anthor Dean
 * @time 2018/4/19 0019 21:48
 */

public class SearchActivity extends BaseActivity {

    @BindView(R.id.etSearchWord)
    EditText etSearchWord;
    @BindView(R.id.imgTextClear)
    ImageView imgTextClear;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tflWord)
    TagFlowLayout tflWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        init();
        loadData();
    }

    private void init(){
        etSearchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    doSearch(etSearchWord.getText().toString());
                }
                return true;
            }
        });
    }

    private void loadData(){
        if (Constants.currentShop!=null) {
            new FindHotProductReq(this, Constants.currentShop.getId(), 1, 8).execute(new Request.RequestCallback<PageInfo<ProductTransfer>>() {
                @Override
                public void onSuccess(final PageInfo<ProductTransfer> productTransferPageInfo) {
                    if (productTransferPageInfo != null) {

                        tflWord.setAdapter(new TagAdapter<ProductTransfer>(productTransferPageInfo.getList()) {

                            @Override
                            public View getView(FlowLayout parent, int position, ProductTransfer productTransfer) {
                                TextView view = (TextView) View.inflate(parent.getContext(), R.layout.layout_word_tag, null);
                                view.setText(productTransfer.getName());
                                return view;
                            }
                        });

                        tflWord.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                            @Override
                            public boolean onTagClick(View view, int position, FlowLayout parent) {
                                etSearchWord.setText(productTransferPageInfo.getList().get(position).getName());
                                doSearch(productTransferPageInfo.getList().get(position).getName());
                                return true;
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
    }

    @OnClick(R.id.tvCancel)
    public void onClickCancel() {
        finish();
    }

    private void doSearch(String keyword){
        //搜索
        keyword = keyword.trim();
        if (TextUtils.isEmpty(keyword)){
            ToastManager.sendToast(getString(R.string.keyword_is_empty));
        }else {
            ViewJump.toSearchResult(this,keyword);
        }
    }
}
