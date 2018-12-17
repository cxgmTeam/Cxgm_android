package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Comment;
import com.cxgm.app.utils.StringHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 评论
 *
 * @anthor dean
 * @time 2018/12/17 6:21 PM
 */
public class CommentAdapter extends BaseAdapter {

    List<Comment> list;

    public CommentAdapter(List<Comment> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_comment_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(view).load("").apply(RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.default_head)
                .error(R.mipmap.default_head)).into(holder.imgCover);
        holder.tvUserName.setText(list.get(i).getUserName());
        holder.mrbScore.setRating(list.get(i).getScore());
        holder.tvTime.setText(StringHelper.cutTimeStr(list.get(i).getCreateTime()));
        holder.tvComment.setText(list.get(i).getContent());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.mrbScore)
        MaterialRatingBar mrbScore;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvComment)
        TextView tvComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
