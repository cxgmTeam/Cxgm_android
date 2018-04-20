package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cxgm.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收货地址列表适配
 *
 * @author dean
 * @time 2018/4/20 下午5:11
 */

public class AddrAdapter extends BaseAdapter {

    public AddrAdapter() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_addr_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPhoneNumber)
        TextView tvPhoneNumber;
        @BindView(R.id.tvAddr)
        TextView tvAddr;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.cbDefault)
        CheckBox cbDefault;
        @BindView(R.id.tvEdit)
        TextView tvEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
