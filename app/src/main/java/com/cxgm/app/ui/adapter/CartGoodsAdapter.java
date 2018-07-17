package com.cxgm.app.ui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.StringHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车列表适配
 *
 * @anthor Dean
 * @time 2018/4/20 0020 22:55
 */
public class CartGoodsAdapter extends BaseAdapter {

    List<ShopCart> mList;
    OnShopCartActionListener mListener;
    public CartGoodsAdapter(List<ShopCart> mList,OnShopCartActionListener mListener) {
        this.mList = mList;
        this.mListener = mListener;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;

        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_cart_goods_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.layoutEditNum.setVisibility(View.VISIBLE);
        Glide.with(view).load(mList.get(i).getImageUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(holder.imgCover);
        holder.tvTitle.setText(mList.get(i).getGoodName());
        //规格
        holder.tvSpecification.setText(viewGroup.getContext()
                .getString(R.string.specification_,mList.get(i).getSpecifications()));
        holder.tvPrice.setText(StringHelper.getRMBFormat(mList.get(i).getPrice()));
        //原价
        if (mList.get(i).getOriginalPrice()> mList.get(i).getPrice()) {
            holder.tvOriginal.setText(StringHelper.getStrikeFormat(StringHelper.getRMBFormat(mList.get(i).getOriginalPrice())));
            holder.tvOriginal.setVisibility(View.VISIBLE);
        }else {
            holder.tvOriginal.setVisibility(View.GONE);
        }
        holder.tvNum.setText(mList.get(i).getGoodNum()+"");
        holder.tvSubtotal.setText(StringHelper.getRMBFormat(mList.get(i).getAmount()));
        //满减 你把展示满减的那个去掉吧 所有的地方都不展示
//        if (!TextUtils.isEmpty(mList.get(i).getCoupon())) {
//            holder.tvTag.setText(mList.get(i).getCoupon());
//        }else {
//            holder.tvTag.setText("");
//        }

        holder.imgSelect.setImageResource(mList.get(i).isChecked?R.mipmap.checked:R.mipmap.check);
        holder.layoutCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(i).isChecked = !mList.get(i).isChecked;
                holder.imgSelect.setImageResource(mList.get(i).isChecked?R.mipmap.checked:R.mipmap.check);
                if (mListener!=null)
                    mListener.onChangeCheck(i,mList.get(i).isChecked);
            }
        });

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.onUpdateGoods(mList.get(i),1);
            }
        });

        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null) {
                    mListener.onUpdateGoods(mList.get(i),-1);
                }
            }
        });

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.imgSelect)
        ImageView imgSelect;
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
        @BindView(R.id.tvTag)
        TextView tvTag;
        @BindView(R.id.tvSubtotal)
        TextView tvSubtotal;
        @BindView(R.id.layoutCheck)
        LinearLayout layoutCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }

    public interface OnShopCartActionListener{
        void onUpdateGoods(ShopCart cartGoods,int actionNum);
        void onChangeCheck(int postion,boolean isChecked);
    }
}
