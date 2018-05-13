package com.cxgm.app.ui.view.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.app.Constants;
import com.cxgm.app.data.entity.ShopCategory;
import com.cxgm.app.data.io.goods.FindFirstCategoryReq;
import com.cxgm.app.ui.adapter.FirstCategoryAdapter;
import com.cxgm.app.ui.adapter.GoodsFirstClassifyAdapter;
import com.cxgm.app.ui.base.BaseFragment;
import com.cxgm.app.ui.view.ViewJump;
import com.deanlib.ootb.data.io.Request;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 一级分类
 *
 * @anthor Dean
 * @time 2018/4/20 0020 22:59
 */
public class GoodsFirstClassifyFragment extends BaseFragment {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.gvClassify)
    GridView gvClassify;
    Unbinder unbinder;

    GoodsFirstClassifyAdapter mFCAdapter;
    List<ShopCategory> mFCList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_first_classify, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        loadData();
    }

    private void init(){
        tvTitle.setText(R.string.classify);
        imgAction1.setImageResource(R.mipmap.search3);
        imgAction1.setVisibility(View.VISIBLE);

        mFCList = new ArrayList<>();
        mFCAdapter = new GoodsFirstClassifyAdapter(mFCList);

        gvClassify.setAdapter(mFCAdapter);
        gvClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewJump.toGoodsSecondClassify(getActivity(),mFCList.get((int)id));
            }
        });
    }

    private void loadData(){
        if (Constants.currentShop == null){
            //TODO
        }else {
            new FindFirstCategoryReq(getActivity(), Constants.currentShop.getId()).execute(new Request.RequestCallback<List<ShopCategory>>() {
                @Override
                public void onSuccess(List<ShopCategory> list) {
                    if (list != null) {
                        mFCList.addAll(list);
                        mFCAdapter.notifyDataSetChanged();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
