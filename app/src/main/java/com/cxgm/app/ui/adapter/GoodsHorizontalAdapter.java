package com.cxgm.app.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.io.order.AddCartReq;
import com.cxgm.app.data.io.order.UpdateCartReq;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 横向 商品列表
 * @see GoodsRecyclerViewAdapter
 * @author dean
 * @time 2018/5/11 上午11:21
 */
@Deprecated
public class GoodsHorizontalAdapter extends BaseAdapter {

    List<ProductTransfer> mList;
    Activity activity;
    public GoodsHorizontalAdapter(Activity activity,List<ProductTransfer> mList) {
        this.mList = mList;
        this.activity = activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.addOrUpdateShopCart(activity,mList.get(position),1);
            }
        });

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
