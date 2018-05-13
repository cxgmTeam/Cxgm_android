package com.cxgm.app.ui.adapter;

import android.graphics.Paint;
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
        //TODO 规格
//        holder.tvSpecification.setText(viewGroup.getContext()
//                .getString(R.string.specification_,mList.get(i).get));
        holder.tvPrice.setText(StringHelper.getRMBFormat(mList.get(i).getPrice()));
        holder.tvNum.setText(mList.get(i).getGoodNum());
        holder.tvSubtotal.setText(StringHelper.getRMBFormat(mList.get(i).getAmount()));
        //TODO 满减
//        holder.tvTag.setText(mList.get(i).get);

        holder.cbSelect.setChecked(mList.get(i).isChecked);
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mListener!=null)
                    mListener.onChangeCheck(i,isChecked);
            }
        });

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(i).setGoodNum(mList.get(i).getGoodNum()+1);
                holder.tvNum.setText(mList.get(i).getGoodNum());
                mList.get(i).setAmount(Helper.moneyMultiply(mList.get(i).getPrice(),mList.get(i).getGoodNum()));
                holder.tvSubtotal.setText(StringHelper.getRMBFormat(mList.get(i).getAmount()));
                if (mListener!=null)
                    mListener.onUpdateGoods(mList.get(i));
            }
        });

        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.get(i).setGoodNum(Helper.getPositiveMinusNum(mList.get(i).getGoodNum()));
                if (mList.get(i).getGoodNum()== 0){
                    //删除
                    mList.remove(i);
                    notifyDataSetChanged();
                    if (mListener!=null)
                        mListener.onDeleteGoods(mList.get(i).getId()+"");
                }else {
                    holder.tvNum.setText(mList.get(i).getGoodNum());
                    mList.get(i).setAmount(Helper.moneyMultiply(mList.get(i).getPrice(),mList.get(i).getGoodNum()));
                    holder.tvSubtotal.setText(StringHelper.getRMBFormat(mList.get(i).getAmount()));
                    if (mListener!=null)
                        mListener.onUpdateGoods(mList.get(i));
                }
            }
        });

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.cbSelect)
        CheckBox cbSelect;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }

    public interface OnShopCartActionListener{
        void onDeleteGoods(String ids);
        void onUpdateGoods(ShopCart cartGoods);
        void onChangeCheck(int postion,boolean isChecked);
    }
}
