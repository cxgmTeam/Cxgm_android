package com.cxgm.app.ui.adapter;

import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车列表适配
 *
 * @anthor Dean
 * @time 2018/4/20 0020 22:55
 */
public class CartGoodsAdapter extends BaseAdapter {

    public CartGoodsAdapter() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_cart_goods_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.layoutEditNum.setVisibility(View.VISIBLE);

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.cbSelect)
        CheckBox cbSelect;
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvSpecification)
        TextView tvSpecification;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvOriginal)
        TextView tvOriginal;
        @BindView(R.id.tvMinus)
        TextView tvMinus;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.tvPlus)
        TextView tvPlus;
        @BindView(R.id.layoutEditNum)
        LinearLayout layoutEditNum;
        @BindView(R.id.tvTag)
        TextView tvTag;
        @BindView(R.id.tvSubtotal)
        TextView tvSubtotal;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }
}
