package com.cxgm.app.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ViewHelper;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 商品列表
 *
 * @author dean
 * @time 2018/6/6 下午2:13
 */
public class GoodsRecyclerViewAdapter extends RecyclerView.Adapter<GoodsRecyclerViewAdapter.GoodsViewHolder> {

    List<ProductTransfer> mList;
    Activity activity;
    OnItemClickListener mListener;
    public GoodsRecyclerViewAdapter(Activity activity, List<ProductTransfer> mList){
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GoodsViewHolder holder = new GoodsViewHolder(View.inflate(parent.getContext(),R.layout.layout_goods_item_1,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
        holder.imgCover.getLayoutParams().width = holder.imgCover.getLayoutParams().height = DensityUtil.dip2px(106);
        Glide.with(activity).load(mList.get(position).getImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,0))
                .error(R.mipmap.default_img).placeholder(R.mipmap.default_img))
                .into(holder.imgCover);
        holder.tvTitle.setText(mList.get(position).getName());
        holder.tvMoney.setText(StringHelper.getRMBFormat(mList.get(position).getPrice()));

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.addOrUpdateShopCart(activity,mList.get(position),1);
            }
        });
        holder.imgCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvMoney)
        TextView tvMoney;
        @BindView(R.id.imgAdd)
        ImageView imgAdd;


        public GoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
