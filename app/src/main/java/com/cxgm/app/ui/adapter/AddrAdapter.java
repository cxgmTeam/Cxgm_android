package com.cxgm.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.data.io.order.DeleteAddressReq;
import com.cxgm.app.utils.ToastManager;
import com.deanlib.ootb.data.io.Request;
import com.deanlib.ootb.utils.TextUtils;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收货地址列表适配
 *
 * @author dean
 * @time 2018/4/20 下午5:11
 */

public class AddrAdapter extends BaseAdapter {

    List<UserAddress> mList;
    public AddrAdapter(List<UserAddress> mList) {
        this.mList = mList;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_addr_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(mList.get(position).getRealName());
        holder.tvPhoneNumber.setText(TextUtils.hidePhoneNum(mList.get(position).getPhone()));
        holder.tvAddr.setText(mList.get(position).getAddress());
        //编辑，设为默认
        holder.cbDefault.setChecked(mList.get(position).getIdDef() == 1);
        holder.cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for (int i = 0;i<mList.size() ;i++){
                    mList.get(i).setIdDef(i == position?1:0);
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAddressReq(parent.getContext(),mList.get(position).getId())
                        .execute(new Request.RequestCallback<Integer>() {
                            @Override
                            public void onSuccess(Integer integer) {
                                if (integer!=0) {
                                    mList.remove(position);
                                    notifyDataSetChanged();
                                    ToastManager.sendToast(parent.getContext().getString(R.string.delete_successful));
                                }
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

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPhoneNumber)
        TextView tvPhoneNumber;
        @BindView(R.id.tvAddr)
        TextView tvAddr;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.cbDefault)
        CheckBox cbDefault;
        @BindView(R.id.tvEdit)
        TextView tvEdit;
        @BindView(R.id.tvDelete)
        TextView tvDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
