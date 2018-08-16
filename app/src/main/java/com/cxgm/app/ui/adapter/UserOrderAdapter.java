package com.cxgm.app.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.cxgm.app.data.io.order.ReturnMoneyReq;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.utils.Helper;
import com.cxgm.app.utils.StringHelper;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.DeviceUtils;
import com.jakewharton.rxbinding2.view.RxView;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的订单列表适配
 *
 * @anthor Dean
 * @time 2018/4/24 0024 22:20
 */
public class UserOrderAdapter extends BaseAdapter {

    Activity mActivity;
    List<Order> mList;
    public UserOrderAdapter(Activity mActivity,List<Order> mList){
        this.mActivity = mActivity;
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
            //布局有出入 UI是四个图片
            View itemView = View.inflate(viewGroup.getContext(),R.layout.layout_3goods_1info,null);
            holder.layoutContainer.addView(itemView);
            ImageView imgView1 = itemView.findViewById(R.id.imgView1);
            ImageView imgView2 = itemView.findViewById(R.id.imgView2);
            ImageView imgView3 = itemView.findViewById(R.id.imgView3);
            TextView tvView = itemView.findViewById(R.id.tvView);
            int width = (DeviceUtils.getSreenWidth()-2* DensityUtil.dip2px(15) - 3*DensityUtil.dip2px(10))/4;
//            tvView.getLayoutParams().width = width;
            imgView1.getLayoutParams().width = width;
            imgView1.getLayoutParams().height = width;
            imgView2.getLayoutParams().width = width;
            imgView2.getLayoutParams().height = width;
            imgView3.getLayoutParams().width = width;
            imgView3.getLayoutParams().height = width;
            tvView.getLayoutParams().width = width;
            tvView.getLayoutParams().height = width;
            tvView.setText(R.string.click_open);
            RxView.clicks(tvView).throttleFirst(2, TimeUnit.SECONDS).subscribe(o->{
                ViewJump.toGoodsList(mActivity, (ArrayList<OrderProduct>) mList.get(i).getProductDetails());});
            int number = mList.get(i).getProductDetails().size();
            number = number>3?3:number;
            switch (number){
                case 3:
                    Glide.with(view).load(mList.get(i).getProductDetails().get(2).getProductUrl())
                            .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                            )
                            .into(imgView3);
                case 2:
                    Glide.with(view).load(mList.get(i).getProductDetails().get(1).getProductUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                )
                        .into(imgView2);
                    Glide.with(view).load(mList.get(i).getProductDetails().get(0).getProductUrl())
                            .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img)
                                    )
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
                    StringHelper.getSpecification(product.getWeight(),product.getUnit())));
            tvPrice.setText(StringHelper.getRMBFormat(product.getPrice()));

        }

        holder.tvCountNum.setText(viewGroup.getContext().getString(R.string._count,mList.get(i).getProductDetails().size()));
        holder.tvTotal.setText(StringHelper.getRMBFormat(mList.get(i).getOrderAmount()));
        switch (mList.get(i).getStatus()){
            case Order.STATUS_TO_BE_PAID:
                //待付款
                holder.tvOrderState.setText(R.string.unpaid);
                holder.tvOrderAction.setText(R.string.to_pay);
                holder.tvOrderAction.setVisibility(View.VISIBLE);
                break;
            case Order.STATUS_DISTRIBUTION:
            case Order.STATUS_DISTRIBUTION2:
            case Order.STATUS_DISTRIBUTION3:
                //待配送
                holder.tvOrderState.setText(R.string.distribution);
                holder.tvOrderAction.setText(R.string.apply_for_returning);
                holder.tvOrderAction.setVisibility(View.GONE);
                break;
            case Order.STATUS_DISTRIBUTING:
                //配送中
                holder.tvOrderState.setText(R.string.distributing);
                holder.tvOrderAction.setText(R.string.apply_for_returning);
                holder.tvOrderAction.setVisibility(View.GONE);
                break;
            case Order.STATUS_COMPLETE:
                //已完成
                holder.tvOrderState.setText(R.string.complete);
                holder.tvOrderAction.setText(R.string.buy_again);
                holder.tvOrderAction.setVisibility(View.VISIBLE);
                break;
            case Order.STATUS_WAIT_REFUND:
                //待退款
                holder.tvOrderState.setText(R.string.wait_refund);
                holder.tvOrderAction.setVisibility(View.GONE);
                break;
            case Order.STATUS_REFUND:
                //已退款
                holder.tvOrderState.setText(R.string.refunded);
                holder.tvOrderAction.setVisibility(View.GONE);
                break;
            case Order.STATUS_CANCEL:
                //已取消
                holder.tvOrderState.setText(R.string.canceled);
                holder.tvOrderAction.setText(R.string.buy_again);
                holder.tvOrderAction.setVisibility(View.VISIBLE);
                break;
            case Order.STATUS_SYSTEM_CANCEL:
                //已取消
                holder.tvOrderState.setText(R.string.canceled);
                holder.tvOrderAction.setText(R.string.buy_again);
                holder.tvOrderAction.setVisibility(View.VISIBLE);
                break;
            default:
                holder.tvOrderState.setText("");
                holder.tvOrderAction.setVisibility(View.GONE);
                break;
        }

        holder.tvOrderAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去干啥
                switch (mList.get(i).getStatus()){
                    case Order.STATUS_TO_BE_PAID:
                        //待付款 去支付
                        ViewJump.toOrderPay(mActivity,mList.get(i).getId(),mList.get(i).getOrderAmount());
                        break;
                    case Order.STATUS_DISTRIBUTION:
                    case Order.STATUS_DISTRIBUTION2:
                    case Order.STATUS_DISTRIBUTION3:
                        //待配送 申请退货
                    case Order.STATUS_DISTRIBUTING:
                        //配送中 申请退货
                        //提示框
                        new AlertDialog.Builder(mActivity).setTitle(R.string.hint)
                                .setMessage(R.string.refund_tag2).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new ReturnMoneyReq(mActivity,mList.get(i).getId())
                                        .execute(new Request.RequestCallback<Integer>() {
                                            @Override
                                            public void onSuccess(Integer integer) {
                                                ToastManager.sendToast(mActivity.getString(R.string.refund_tag));
                                                mList.get(i).setStatus(Order.STATUS_WAIT_REFUND);
                                                notifyDataSetChanged();
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
                        }).setNegativeButton(R.string.cancel,null).show();

                        break;
                    case Order.STATUS_COMPLETE:
                        //已完成 再次购买
                    case Order.STATUS_CANCEL:
                        //已取消 再次购买
                    case Order.STATUS_SYSTEM_CANCEL:
                        //已取消 再次购买
                        ViewJump.toVerifyOrder(mActivity, (ArrayList<OrderProduct>) mList.get(i).getProductDetails());
                        break;
                    case Order.STATUS_REFUND:
                        //退款 没有按钮
                        break;
                    default:

                        break;
                }
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
        @BindView(R.id.tvCountNum)
        TextView tvCountNum;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvOrderAction)
        TextView tvOrderAction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
