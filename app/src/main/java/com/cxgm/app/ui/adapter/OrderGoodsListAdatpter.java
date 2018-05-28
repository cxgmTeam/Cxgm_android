package com.cxgm.app.ui.adapter;

import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.StringHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单 商品清单列表适配
 *
 * @anthor Dean
 * @time 2018/4/21 0021 0:50
 */
public class OrderGoodsListAdatpter extends BaseAdapter {

    List<OrderProduct> mList;
    public OrderGoodsListAdatpter(List<OrderProduct> mList) {
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_order_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(view).load(mList.get(i).getProductUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)).into(holder.imgCover);
        holder.tvTitle.setText(mList.get(i).getProductName());
//        holder.tvSpecification.setText(viewGroup.getContext().getString(R.string.specification_
//                ,StringHelper.getSpecification(Helper.str2Float(mList.get(i).getWeight()),mList.get(i).getUnit())));
        holder.tvSpecification.setText(mList.get(i).getSpecifications());
        holder.tvPrice.setText(StringHelper.getRMBFormat(mList.get(i).getPrice()));

        if (mList.get(i).getPrice()!= mList.get(i).getOriginalPrice()) {
            holder.tvOriginal.setText(StringHelper.getStrikeFormat(StringHelper.getRMBFormat(mList.get(i).getOriginalPrice())));
            holder.tvOriginal.setVisibility(View.VISIBLE);
        }else holder.tvOriginal.setVisibility(View.GONE);

        holder.tvCount.setText("x"+mList.get(i).getProductNum());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvSpecification)
        TextView tvSpecification;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvOriginal)
        TextView tvOriginal;
        @BindView(R.id.tvMinus)
        TextView tvMinus;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.tvPlus)
        TextView tvPlus;
        @BindView(R.id.layoutEditNum)
        LinearLayout layoutEditNum;
        @BindView(R.id.tvCount)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }
}
