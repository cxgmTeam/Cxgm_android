package com.cxgm.app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;

import java.util.List;


public class CategoryPageAdapter extends PagerAdapter {

        List<ShopCategory> list;

        Activity context;

        public CategoryPageAdapter(Activity context, List<ShopCategory> list) {

            this.list = list;

            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size()/4+(list.size()%4!=0?1:0);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {

            LinearLayout layoutView = new LinearLayout(context);
            layoutView.setGravity(Gravity.CENTER);

            for (int i = position * 4; i < 4 * (position + 1) && i<list.size(); i++) {

                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_first_category_item, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                layoutParams.weight = 1.0f;
                view.setLayoutParams(layoutParams);
                ImageView imgCover = view.findViewById(R.id.imgCover);
                TextView tvName = view.findViewById(R.id.tvName);
                Glide.with(context).load(list.get(i).getImageUrl())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                        .into(imgCover);
                tvName.setText(list.get(i).getName());

                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    //分类点击事件
                    ViewJump.toGoodsSecondClassify(context,list.get(finalI));
                    }
                });

                layoutView.addView(view);

            }

            container.addView(layoutView);

            return layoutView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }