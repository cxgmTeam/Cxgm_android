package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.deanlib.ootb.utils.DeviceUtils;

import org.xutils.common.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsAdapter extends BaseAdapter {

    int mItemWidth;
    int mItemHeight;

    public GoodsAdapter(int numColumn,float margeDip){
        mItemHeight = mItemWidth = (int) (DensityUtil.px2dip(DeviceUtils.getSreenWidth())/numColumn - margeDip);
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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_item_1, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(viewGroup.getContext()).load("")
                .apply(new RequestOptions().override(mItemWidth,mItemHeight))
                .into(holder.imgCover);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvMoney)
        TextView tvMoney;
        @BindView(R.id.imgAdd)
        ImageView imgAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
