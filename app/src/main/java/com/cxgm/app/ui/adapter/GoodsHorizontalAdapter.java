package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.utils.StringHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 横向 商品列表
 *
 * @author dean
 * @time 2018/5/11 上午11:21
 */

public class GoodsHorizontalAdapter extends BaseAdapter {

    List<ProductTransfer> mList;

    public GoodsHorizontalAdapter(List<ProductTransfer> mList) {
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
            convertView = View.inflate(parent.getContext(), R.layout.layout_goods_item_1, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(convertView).load(mList.get(position).getImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,0)))
                .into(holder.imgCover);
        holder.tvTitle.setText(mList.get(position).getName());
        holder.tvMoney.setText(StringHelper.getRMBFormat(mList.get(position).getPrice()));

        return convertView;
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
