package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.DeliveryTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 配送时间
 *
 * @anthor Dean
 * @time 2018/8/2 0002 21:46
 */
public class TimeAdapter extends BaseAdapter {

    List<DeliveryTime> mTimeList;

    public TimeAdapter(List<DeliveryTime> timeList) {
        this.mTimeList = timeList;
    }

    @Override
    public int getCount() {
        return mTimeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTimeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_time_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTime.setText(mTimeList.get(position).getTime());
        if (mTimeList.get(position).isChecked){
            holder.imgTime.setVisibility(View.VISIBLE);
        }else {
            holder.imgTime.setVisibility(View.GONE);
        }
        
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.imgTime)
        ImageView imgTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
