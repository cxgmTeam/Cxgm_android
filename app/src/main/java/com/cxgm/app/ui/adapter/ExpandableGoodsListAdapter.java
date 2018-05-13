package com.cxgm.app.ui.adapter;


import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二级分类subclassify适配
 *
 * @anthor Dean
 * @time 2018/4/22 0022 16:52
 */

public class ExpandableGoodsListAdapter extends BaseExpandableListAdapter {
    List<String> mKeyList;
    Map<String,List<ProductTransfer>> mProductMap;
    public ExpandableGoodsListAdapter(Map<String,List<ProductTransfer>> mProductMap){
        this.mProductMap = mProductMap;
        mKeyList = new ArrayList<>();
        Set<Map.Entry<String, List<ProductTransfer>>> entries = mProductMap.entrySet();
        for (Map.Entry<String,List<ProductTransfer>> entry : entries){
            mKeyList.add(entry.getKey());
        }
    }
    @Override
    public int getGroupCount() {
        return mProductMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mProductMap.get(mKeyList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mProductMap.get(mKeyList.get(i));
    }

    @Override
    public Object getChild(int i, int i1) {
        return mProductMap.get(mKeyList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView view1 = new TextView(viewGroup.getContext());
        view1.setText(mKeyList.get(i));
        return view1;
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
        Glide.with(view).load(mProductMap.get(mKeyList.get(i)).get(i1).getImage())
                .into(holder.imgCover);
        holder.tvTitle.setText(mProductMap.get(mKeyList.get(i)).get(i1).getFullName());
        holder.tvPrice.setText(StringHelper.getRMBFormat(mProductMap.get(mKeyList.get(i)).get(i1).getPrice()));

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
