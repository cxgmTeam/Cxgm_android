package com.cxgm.app.ui.view.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Comment;
import com.cxgm.app.data.entity.Shop;
import com.cxgm.app.data.entity.base.PageInfo;
import com.cxgm.app.data.io.common.AddCommentReq;
import com.cxgm.app.data.io.common.FindCommentReq;
import com.cxgm.app.data.io.common.ShopDetailReq;
import com.cxgm.app.ui.adapter.CommentAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.PopupUtils;
import com.deanlib.ootb.widget.ListViewForScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgShopCover)
    ImageView imgShopCover;
    @BindView(R.id.tvShopName)
    TextView tvShopName;
    @BindView(R.id.tvShopInfo)
    TextView tvShopInfo;
    @BindView(R.id.tvShopScore)
    TextView tvShopScore;
    @BindView(R.id.mrbShopScore)
    MaterialRatingBar mrbShopScore;
    @BindView(R.id.tvScoreInfo)
    TextView tvScoreInfo;
    @BindView(R.id.mrbUserScore)
    MaterialRatingBar mrbUserScore;
    @BindView(R.id.tvUserScore)
    TextView tvUserScore;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.tvCommitComment)
    TextView tvCommitComment;
    @BindView(R.id.layoutUserComment)
    LinearLayout layoutUserComment;
    @BindView(R.id.tvCommentTotal)
    TextView tvCommentTotal;
    @BindView(R.id.lvConments)
    ListViewForScrollView lvConments;
    @BindView(R.id.layoutComments)
    RelativeLayout layoutComments;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    int mShopId;
    boolean isUserComment = false;

    int mPage = 1;
    List<Comment> mCommentList;
    CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        mShopId = getIntent().getIntExtra("shopId",0);
        isUserComment = getIntent().getBooleanExtra("isUserComment",false);
        init();
        loadShopInfo();
        loadData();
    }

    private void init(){
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.comment);

        lvConments.setAdapter(mCommentAdapter = new CommentAdapter(mCommentList = new ArrayList<>()));

        srl.setEnableLoadMore(!isUserComment);
        srl.setEnableRefresh(!isUserComment);
        if (isUserComment){
            layoutUserComment.setVisibility(View.VISIBLE);
            layoutComments.setVisibility(View.GONE);
            mrbUserScore.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    tvUserScore.setText(((int)rating)+".0分");
                }
            });
        }else {
            layoutUserComment.setVisibility(View.GONE);
            layoutComments.setVisibility(View.VISIBLE);
            srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mPage++;
                    loadData();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mPage = 1;
                    mCommentList.clear();
                    loadData();
                }
            });
        }

    }

    private void loadShopInfo(){
        new ShopDetailReq(this,mShopId).execute(new Request.RequestCallback<Shop>() {
            @Override
            public void onSuccess(Shop shop) {
                if (shop != null){
                    Glide.with(CommentActivity.this).load(shop.getImageUrl())
                            .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.icon_round)
                            .error(R.mipmap.icon_round)).into(imgShopCover);
                    tvShopName.setText(shop.getShopName());
                    tvShopInfo.setText(getString(R.string.shop_info_3_,shop.getMonthSales(),shop.getDistance()+""));
                    tvShopScore.setText(((int)shop.getScore())+".0");
                    mrbShopScore.setRating(shop.getScore());
                    //todo 评分下边有句话

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

    private void loadData(){
        new FindCommentReq(this,mShopId,mPage,10).execute(new Request.RequestCallback<PageInfo<Comment>>() {
            @Override
            public void onSuccess(PageInfo<Comment> commentPageInfo) {
                if (commentPageInfo!=null && commentPageInfo.getList()!=null && commentPageInfo.getList().size()>0){
                    tvCommentTotal.setText(getString(R.string._scores,commentPageInfo.getTotal()));
                    mCommentList.addAll(commentPageInfo.getList());
                    mCommentAdapter.notifyDataSetChanged();
                }else {
                    tvCommentTotal.setText(getString(R.string._scores,0));
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
                srl.finishLoadMore();
                srl.finishRefresh();
            }
        });
    }

    @OnClick({R.id.imgBack, R.id.tvCommitComment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvCommitComment:
                //提交评论
                if (UserManager.isUserLogin()) {
                    Comment comment = new Comment(etComment.getText().toString().trim(),
                            (int) mrbUserScore.getRating(), mShopId,UserManager.user.getUserId(),UserManager.user.getUserName());
                    new AddCommentReq(this,comment).execute(new Request.RequestCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            //评论成功 跳到列表页
                            isUserComment = false;
                            init();
                            loadData();
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
                }else {
                    ViewJump.toLogin(this);
                }
                break;
        }
    }
}
