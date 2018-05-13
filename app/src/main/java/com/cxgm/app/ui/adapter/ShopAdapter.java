package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Shop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 店铺列表适配
 *
 * @anthor Dean
 * @time 2018/4/19 0019 22:08
 */

public class ShopAdapter extends BaseAdapter {

    List<Shop> mList;
    public ShopAdapter(List<Shop> mList) {
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
            convertView = View.inflate(parent.getContext(), R.layout.layout_shop_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(parent.getContext()).load(mList.get(position).getImageUrl())
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(128,0, RoundedCornersTransformation.CornerType.TOP))
                .placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(holder.imgShopCover);
        holder.tvShopName.setText(mList.get(position).getShopName());
        holder.tvShopAdds.setText(mList.get(position).getShopAddress());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvShopName)
        TextView tvShopName;
        @BindView(R.id.tvShopAdds)
        TextView tvShopAdds;
        @BindView(R.id.imgShopCover)
        ImageView imgShopCover;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
