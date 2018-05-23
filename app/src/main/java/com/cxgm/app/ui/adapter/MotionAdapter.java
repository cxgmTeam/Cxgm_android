package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Motion;
import com.deanlib.ootb.widget.HorizontalListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运营
 *
 * @anthor Dean
 * @time 2018/5/23 0023 22:55
 */
public class MotionAdapter extends BaseAdapter {
    List<Motion> mList;

    public MotionAdapter(List<Motion> mList) {
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
            convertView = View.inflate(parent.getContext(), R.layout.layout_motion_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(convertView).load(mList.get(position).getImageUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(holder.imgAd);
        holder.hlvAdGoods.setAdapter(new GoodsHorizontalAdapter(mList.get(position).getProductList()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imgAd)
        ImageView imgAd;
        @BindView(R.id.hlvAdGoods)
        HorizontalListView hlvAdGoods;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
