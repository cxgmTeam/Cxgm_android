package com.cxgm.app.ui.adapter;


import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ProductTransfer;
import com.cxgm.app.data.entity.ShopCart;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.io.order.AddCartReq;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二级分类subclassify适配
 *
 * @anthor Dean
 * @time 2018/4/22 0022 16:52
 */

public class ExpandableGoodsListAdapter extends BaseExpandableListAdapter {
    List<String> mKeyList;
    Map<String,List<ProductTransfer>> mProductMap;
    Activity mActivity;
    OnShopCartActionListener mListener;
    public ExpandableGoodsListAdapter(Activity activity,Map<String, List<ProductTransfer>> mProductMap,OnShopCartActionListener listener){
        this.mProductMap = mProductMap;
        mActivity = activity;
        this.mListener = listener;
        mKeyList = new ArrayList<>();
        Set<Map.Entry<String, List<ProductTransfer>>> entries = mProductMap.entrySet();
        for (Map.Entry<String,List<ProductTransfer>> entry : entries){
            mKeyList.add(entry.getKey());
        }
    }

    public List<String> getKeyList(){
        return mKeyList;
    }

    @Override
    public void notifyDataSetChanged() {
        mKeyList.clear();
        Set<Map.Entry<String, List<ProductTransfer>>> entries = mProductMap.entrySet();
        for (Map.Entry<String,List<ProductTransfer>> entry : entries){
            mKeyList.add(entry.getKey());
        }

        super.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mProductMap.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mProductMap.get(mKeyList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mProductMap.get(mKeyList.get(i));
    }

    @Override
    public Object getChild(int i, int i1) {
        return mProductMap.get(mKeyList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = View.inflate(viewGroup.getContext(),R.layout.layout_third_title_item,null);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(mKeyList.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, final ViewGroup viewGroup) {

        final SubViewHolder holder;
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_item_2, null);
            holder = new SubViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SubViewHolder) view.getTag();
        }
        final ProductTransfer product = mProductMap.get(mKeyList.get(i)).get(i1);
        Glide.with(view).load(product.getImage())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(holder.imgCover);
        holder.tvTitle.setText(product.getName());
        holder.tvPrice.setText(StringHelper.getRMBFormat(product.getPrice()));
        updateActionView(holder,product);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.addOrUpdateShopCart(mActivity,product,1,new ViewHelper.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        updateActionView(holder,product);
                        if (mListener!=null)
                            mListener.onAddGoods(product);
                    }
                });
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.addOrUpdateShopCart(mActivity,product,-1,new ViewHelper.OnActionListener() {
                    @Override
                    public void onSuccess() {
                        updateActionView(holder,product);
                        if (mListener!=null)
                            mListener.onMinusGoods(product);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class SubViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvSubTitle)
        TextView tvSubTitle;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvOriginal)
        TextView tvOriginal;
        @BindView(R.id.imgAdd)
        ImageView imgAdd;
        @BindView(R.id.imgMinus)
        ImageView imgMinus;
        @BindView(R.id.tvNum)
        TextView tvNum;

        SubViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }

    private void updateActionView(SubViewHolder holder,ProductTransfer product){
        holder.tvNum.setText(product.getShopCartNum()+"");
        if (product.getShopCartNum()>0){
            //展示可调节
            holder.imgMinus.setVisibility(View.VISIBLE);
            holder.tvNum.setVisibility(View.VISIBLE);
        }else {
            holder.imgMinus.setVisibility(View.GONE);
            holder.tvNum.setVisibility(View.GONE);
        }
    }

    public interface OnShopCartActionListener{
        void onAddGoods(ProductTransfer product);
        void onMinusGoods(ProductTransfer product);
    }
}
