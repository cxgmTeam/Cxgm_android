package com.cxgm.app.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.CouponDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券列表适配
 *
 * @anthor Dean
 * @time 2018/4/24 0024 0:07
 */
public class CouponAdapter extends BaseAdapter {
    List<CouponDetail> mList;
    public CouponAdapter(List<CouponDetail> mList){
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_coupon_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvValue.setText(mList.get(i).getPriceExpression());
        holder.tvCondition.setText(viewGroup.getContext().getString(R.string._full_reduction,mList.get(i).getMaximumPrice()));
        holder.tvName.setText(mList.get(i).getName());
        holder.tvIndate.setText(mList.get(i).getBeginDate() + "-"+ mList.get(i).getEndDate());

//        holder.tvUse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        if (!TextUtils.isEmpty(mList.get(i).getIntroduction())){
            holder.tvRulesContent.setVisibility(View.VISIBLE);
            holder.tvRulesContent.setText(mList.get(i).getIntroduction());
        }else {
            holder.tvRulesContent.setVisibility(View.GONE);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvCurrency)
        TextView tvCurrency;
        @BindView(R.id.tvValue)
        TextView tvValue;
        @BindView(R.id.tvCondition)
        TextView tvCondition;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvIndate)
        TextView tvIndate;
        @BindView(R.id.tvUse)
        TextView tvUse;
        @BindView(R.id.view1)
        View view1;
        @BindView(R.id.tvRules)
        TextView tvRules;
        @BindView(R.id.imgShowRules)
        ImageView imgShowRules;
        @BindView(R.id.tvRulesContent)
        TextView tvRulesContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
