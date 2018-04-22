package com.cxgm.app.ui.adapter;


import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二级分类subclassify适配
 *
 * @anthor Dean
 * @time 2018/4/22 0022 16:52
 */

public class GoodsSecondClassifyAdapter extends BaseExpandableListAdapter {
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        SubViewHolder holder;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_item_2, null);
            holder = new SubViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SubViewHolder) view.getTag();
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class SubViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvSubTitle)
        TextView tvSubTitle;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvOriginal)
        TextView tvOriginal;
        @BindView(R.id.imgAdd)
        ImageView imgAdd;

        SubViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }
}
