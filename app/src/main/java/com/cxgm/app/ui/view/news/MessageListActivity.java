package com.cxgm.app.ui.view.news;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.adapter.MessageAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 消息列表
 *
 * @author dean
 * @time 2018/5/30 上午9:57
 */
public class MessageListActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvMessage)
    ListView lvMessage;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tvTitle.setText(R.string.message);
        imgBack.setVisibility(View.VISIBLE);
        lvMessage.setAdapter(new MessageAdapter());
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    private void loadData(){
        //TODO 消息
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }
}
