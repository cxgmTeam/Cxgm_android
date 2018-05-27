package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Order;
import com.cxgm.app.data.entity.OrderProduct;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.StringHelper;
import com.deanlib.ootb.utils.DeviceUtils;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单列表适配
 *
 * @anthor Dean
 * @time 2018/4/24 0024 22:20
 */
public class UserOrderAdapter extends BaseAdapter {

    List<Order> mList;
    public UserOrderAdapter(List<Order> mList){
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_user_order_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvOrderNum.setText(mList.get(i).getOrderNum());
        holder.layoutContainer.removeAllViews();
        if (mList.get(i).getProductDetails().size()>1){
            View itemView = View.inflate(viewGroup.getContext(),R.layout.layout_3goods_1info,null);
            holder.layoutContainer.addView(itemView);
            ImageView imgView1 = itemView.findViewById(R.id.imgView1);
            ImageView imgView2 = itemView.findViewById(R.id.imgView2);
            ImageView imgView3 = itemView.findViewById(R.id.imgView3);
            TextView tvView = itemView.findViewById(R.id.tvView);
            int width = (DeviceUtils.getSreenWidth()-2* DensityUtil.dip2px(15) - 3*DensityUtil.dip2px(10))/4;
            tvView.getLayoutParams().width = width;
            tvView.getLayoutParams().height = width;
            int number = mList.get(i).getProductDetails().size();
            number = number>3?3:number;
            switch (number){
                case 3:
                    Glide.with(view).load(mList.get(i).getProductDetails().get(2).getProductUrl())
                            .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                            .override(width,width))
                            .into(imgView3);
                case 2:
                    Glide.with(view).load(mList.get(i).getProductDetails().get(1).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                .override(width,width))
                        .into(imgView2);
                    Glide.with(view).load(mList.get(i).getProductDetails().get(0).getProductUrl())
                            .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                    .override(width,width))
                            .into(imgView1);
            }

        }else {
            View itemView = View.inflate(viewGroup.getContext(),R.layout.layout_goods_order_item,null);
            holder.layoutContainer.addView(itemView);
            TextView tvCount = itemView.findViewById(R.id.tvCount);
            ImageView imgCover = itemView.findViewById(R.id.imgCover);
            TextView tvTitle = itemView.findViewById(R.id.tvTitle);
            TextView tvSpecification = itemView.findViewById(R.id.tvSpecification);
            TextView tvPrice = itemView.findViewById(R.id.tvPrice);
            TextView tvOriginal = itemView.findViewById(R.id.tvOriginal);
            OrderProduct product = mList.get(i).getProductDetails().get(0);
            Glide.with(view).load(product.getProductUrl())
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                    .into(imgCover);
            tvCount.setText("x"+product.getProductNum());
            tvTitle.setText(product.getProductName());
            tvSpecification.setText(viewGroup.getContext().getString(R.string.specification_,
                    StringHelper.getSpecification(Helper.str2Float(product.getWeight()),product.getUnit())));
            tvPrice.setText(StringHelper.getRMBFormat(product.getPrice()));

        }

        holder.tvCount.setText(viewGroup.getContext().getString(R.string._count,mList.get(i).getProductDetails().size()));
        holder.tvTotal.setText(StringHelper.getRMBFormat(mList.get(i).getOrderAmount()));
        holder.tvOrderAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 去干啥
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvOrderNum)
        TextView tvOrderNum;
        @BindView(R.id.tvOrderState)
        TextView tvOrderState;
        @BindView(R.id.layoutContainer)
        LinearLayout layoutContainer;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvOrderAction)
        TextView tvOrderAction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
