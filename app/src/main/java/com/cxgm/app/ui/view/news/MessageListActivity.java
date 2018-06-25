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
import com.cxgm.app.data.entity.Message;
import com.cxgm.app.ui.adapter.MessageAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.Helper;
import com.deanlib.ootb.data.db.DB;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

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

    int mPageNum = 1;
    List<Message> mMessageList;
    MessageAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);

        init();
        mPageNum = 1;
        loadData();
    }

    private void init() {
        tvTitle.setText(R.string.message);
        imgBack.setVisibility(View.VISIBLE);
        lvMessage.setAdapter(mMessageAdapter = new MessageAdapter(mMessageList = new ArrayList<>()));
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageNum++;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = 1;
                mMessageList.clear();
                loadData();
            }
        });
    }

    private void loadData(){
        //消息
        try {
            List<Message> all = DB.getDbManager().selector(Message.class).offset((mPageNum - 1) * 10).limit(10).orderBy("id",true).findAll();
            if (all!=null && all.size()>0){
                mMessageList.addAll(all);
                mMessageAdapter.notifyDataSetChanged();
            }else {
                mPageNum = Helper.getUnsuccessPageNum(mPageNum);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        srl.finishRefresh();
        srl.finishLoadMore();
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }
}
