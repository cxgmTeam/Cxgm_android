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
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.cxgm.app.utils.ViewHelper;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DeviceUtils;

import org.xutils.common.Callback;
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
    Activity activity;

    public GoodsAdapter(Activity activity, List<ProductTransfer> mList, int numColumn, float margeDip){
        this.activity = activity;
        this.mList = mList;
        mItemHeight = mItemWidth = DeviceUtils.getSreenWidth()/numColumn - DensityUtil.dip2px(margeDip);
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
            view = View.inflate(viewGroup.getContext(), R.layout.layout_goods_item_1, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imgCover.getLayoutParams().width = mItemWidth;
        holder.imgCover.getLayoutParams().height = mItemHeight;
        Glide.with(view).load(mList.get(i).getImage())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(10,0))))
                .into(holder.imgCover);
        holder.tvTitle.setText(mList.get(i).getName());
        holder.tvMoney.setText(StringHelper.getRMBFormat(mList.get(i).getPrice()));

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.addOrUpdateShopCart(activity,mList.get(i),1);
            }
        });

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
