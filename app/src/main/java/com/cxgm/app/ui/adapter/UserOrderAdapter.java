package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单列表适配
 *
 * @anthor Dean
 * @time 2018/4/24 0024 22:20
 */
public class UserOrderAdapter extends BaseAdapter {
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_user_order_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvOrderNum)
        TextView tvOrderNum;
        @BindView(R.id.tvOrderState)
        TextView tvOrderState;
        @BindView(R.id.layoutContainer)
        LinearLayout layoutContainer;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvOrderAction)
        TextView tvOrderAction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
