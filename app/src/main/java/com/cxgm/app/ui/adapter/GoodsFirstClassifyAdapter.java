package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.ShopCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一级分类适配
 *
 * @anthor Dean
 * @time 2018/4/20 0020 23:39
 */
public class GoodsFirstClassifyAdapter extends BaseAdapter {
    List<ShopCategory> mList;
    public GoodsFirstClassifyAdapter(List<ShopCategory> mList) {
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_classify_1_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvName.setText(mList.get(i).getName());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvName)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
