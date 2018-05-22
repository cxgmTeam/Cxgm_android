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
    public ExpandableGoodsListAdapter(Activity activity,Map<String, List<ProductTransfer>> mProductMap){
        this.mProductMap = mProductMap;
        mActivity = activity;
        mKeyList = new ArrayList<>();
        Set<Map.Entry<String, List<ProductTransfer>>> entries = mProductMap.entrySet();
        for (Map.Entry<String,List<ProductTransfer>> entry : entries){
            mKeyList.add(entry.getKey());
        }
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
        TextView view1 = new TextView(viewGroup.getContext());
        view1.setText(mKeyList.get(i));
        return view1;
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
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.isUserLogin()){
                    ViewJump.toLogin(mActivity);
                    return;
                }
                ShopCart cart = new ShopCart(product.getId(),product.getGoodCode(),product.getName(),1,product.getPrice(), Constants.currentShop.getId());
                new AddCartReq(viewGroup.getContext(),cart).execute(false,new Request.RequestCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        ToastManager.sendToast(viewGroup.getContext().getString(R.string.added_shop_cart));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

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

        SubViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        }
    }
}
