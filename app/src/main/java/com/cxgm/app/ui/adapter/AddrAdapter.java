package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.UserAddress;
import com.deanlib.ootb.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收货地址列表适配
 *
 * @author dean
 * @time 2018/4/20 下午5:11
 */

public class AddrAdapter extends BaseAdapter {

    List<UserAddress> mList;
    public AddrAdapter(List<UserAddress> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

        holder.tvName.setText(mList.get(position).getRealName());
        holder.tvPhoneNumber.setText(TextUtils.hidePhoneNum(mList.get(position).getPhone()));
        holder.tvAddr.setText(mList.get(position).getAddress());
        //TODO 编辑，设为默认

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
