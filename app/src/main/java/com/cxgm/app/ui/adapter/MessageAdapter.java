package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxgm.app.R;
import com.cxgm.app.data.entity.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息列表适配
 *
 * @author dean
 * @time 2018/5/30 上午9:57
 */
public class MessageAdapter extends BaseAdapter {

    List<Message> mMessageList;
    public MessageAdapter(List<Message> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_message_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(mMessageList.get(position).getTitle());
        holder.tvContent.setText(mMessageList.get(position).getContent());
        holder.tvTime.setText(mMessageList.get(position).getDate());
        int coverResId = R.mipmap.notification;
        switch (mMessageList.get(position).getTitle()){
            case Message.TYPE_PROMOTION:
                coverResId = R.mipmap.promotion;
                break;
            case Message.TYPE_SERVICE:
                coverResId = R.mipmap.service2;
                break;
            case Message.TYPE_NOTIFICATION:
                coverResId = R.mipmap.notification;
                break;
            case Message.TYPE_NEWS:
                coverResId = R.mipmap.news;
                break;
        }
        Glide.with(convertView).load(coverResId).into(holder.imgCover);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imgCover)
        ImageView imgCover;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvTime)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
