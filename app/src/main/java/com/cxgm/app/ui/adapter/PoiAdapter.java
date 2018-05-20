package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.UserPoiInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地图定位地址列表适配
 *
 * @anthor Dean
 * @time 2018/4/20 0020 23:55
 */
public class PoiAdapter extends BaseAdapter {

    List<UserPoiInfo> mList;
    public PoiAdapter(List<UserPoiInfo> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_poi_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvName.setText(mList.get(i).name);
        holder.tvAddr.setText(mList.get(i).address);
        if (mList.get(i).isChecked)
            holder.imgPosition.setVisibility(View.VISIBLE);
        else
            holder.imgPosition.setVisibility(View.INVISIBLE);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAddr)
        TextView tvAddr;
        @BindView(R.id.imgPosition)
        ImageView imgPosition;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
