package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ShopAdapter extends BaseAdapter {

    public ShopAdapter() {

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
            convertView = View.inflate(parent.getContext(), R.layout.layout_shop_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(parent.getContext()).load("")
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(128,0, RoundedCornersTransformation.CornerType.TOP)))
                .into(holder.imgShopCover);

        return null;
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
