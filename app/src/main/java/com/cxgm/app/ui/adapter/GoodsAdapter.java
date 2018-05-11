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
import com.deanlib.ootb.utils.DeviceUtils;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 竖向商品列表适配
 *
 * @anthor Dean
 * @time 2018/4/19 0019 21:54
 */
public class GoodsAdapter extends BaseAdapter {

    int mItemWidth;
    int mItemHeight;
    List<ProductTransfer> mList;

    public GoodsAdapter( List<ProductTransfer> mList,int numColumn,float margeDip){
        this.mList = mList;
        mItemHeight = mItemWidth = (int) (DensityUtil.px2dip(DeviceUtils.getSreenWidth())/numColumn - margeDip);
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_item_1, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(view).load(mList.get(i).getImage())
                .apply(new RequestOptions().override(mItemWidth,mItemHeight)
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,0))))
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
