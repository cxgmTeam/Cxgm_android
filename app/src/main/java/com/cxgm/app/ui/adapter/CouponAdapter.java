package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券列表适配
 *
 * @anthor Dean
 * @time 2018/4/24 0024 0:07
 */
public class CouponAdapter extends BaseAdapter {
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_coupon_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvCurrency)
        TextView tvCurrency;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.tvCondition)
        TextView tvCondition;
        @BindView(R.id.tvIndate)
        TextView tvIndate;
        @BindView(R.id.tvUse)
        TextView tvUse;
        @BindView(R.id.view1)
        View view1;
        @BindView(R.id.tvRules)
        TextView tvRules;
        @BindView(R.id.imgShowRules)
        ImageView imgShowRules;
        @BindView(R.id.tvRulesContent)
        TextView tvRulesContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}