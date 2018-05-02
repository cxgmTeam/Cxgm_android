package com.cxgm.app.ui.view.user;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;
import com.deanlib.ootb.widget.ListViewForScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 邀请有礼
 *
 * @anthor Dean
 * @time 2018/5/2 0002 20:05
 */
public class InviteActivity extends BaseActivity {

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
    @BindView(R.id.tvInvite)
    TextView tvInvite;
    @BindView(R.id.layout1)
    CardView layout1;
    @BindView(R.id.tvInviteFriendNum)
    TextView tvInviteFriendNum;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.tvActivityTag)
    TextView tvActivityTag;
    @BindView(R.id.lvInvite)
    ListViewForScrollView lvInvite;
    @BindView(R.id.layoutList)
    CardView layoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        tvTitle.setText(R.string.invite_gift);
        imgBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imgBack, R.id.tvInvite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvInvite:
                break;
        }
    }
}
