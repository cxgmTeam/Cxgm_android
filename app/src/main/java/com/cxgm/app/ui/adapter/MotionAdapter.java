package com.cxgm.app.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Motion;
import com.cxgm.app.ui.view.ViewJump;
import com.cxgm.app.ui.widget.SpaceItemDecoration;
import com.deanlib.ootb.widget.HorizontalListView;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运营
 *
 * @anthor Dean
 * @time 2018/5/23 0023 22:55
 */
public class MotionAdapter extends BaseAdapter {
    List<Motion> mList;
    Activity activity;
    public MotionAdapter(Activity activity,List<Motion> mList) {
        this.mList = mList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_motion_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(convertView).load(mList.get(position).getImageUrl())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                .into(holder.imgBanner);
//        holder.imgBanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if (mList.get(position).getProductList() != null) {

            GoodsRecyclerViewAdapter adapter = new GoodsRecyclerViewAdapter(activity,mList.get(position).getProductList());
            holder.hlvAdGoods.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.hlvAdGoods.setLayoutManager(layoutManager);
            holder.hlvAdGoods.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(1.5f)));
            adapter.setOnItemClickListener(new GoodsRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ViewJump.toGoodsDetail(activity,mList.get(position).getProductList().get(position).getId());
                }
            });

        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imgBanner)
        ImageView imgBanner;
        @BindView(R.id.hlvAdGoods)
        RecyclerView hlvAdGoods;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
