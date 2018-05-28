package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送时间
 *
 * @author dean
 * @time 2018/5/28 上午10:10
 */
public class DeliveryTimeDialogActivity extends BaseActivity {


    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.tvTime1)
    TextView tvTime1;
    @BindView(R.id.cbTime1)
    CheckBox cbTime1;
    @BindView(R.id.layoutTime1)
    LinearLayout layoutTime1;
    @BindView(R.id.tvTime2)
    TextView tvTime2;
    @BindView(R.id.cbTiem2)
    CheckBox cbTiem2;
    @BindView(R.id.layoutTime2)
    LinearLayout layoutTime2;

    public static final String[] TIMES = {"09:00-18:00","18:00-22:00"};

    int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_delivery_time_dialog);
        ButterKnife.bind(this);
        mPosition = getIntent().getIntExtra("position",0);
        init();
    }

    private void init() {
        tvTime1.setText(TIMES[0]);
        tvTime2.setText(TIMES[1]);
        if (mPosition==0){
            layoutTime1.performClick();
        }else if (mPosition == 1){
            layoutTime2.performClick();
        }
    }


    @OnClick({R.id.imgClose, R.id.layoutTime1, R.id.layoutTime2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                finish();
                break;
            case R.id.layoutTime1:
                tvTime2.setTextColor(getResources().getColor(R.color.textBlack));
                cbTiem2.setChecked(false);
                tvTime1.setTextColor(getResources().getColor(R.color.textGreen));
                cbTime1.setChecked(true);
                mPosition = 0;
                break;
            case R.id.layoutTime2:
                tvTime1.setTextColor(getResources().getColor(R.color.textBlack));
                cbTime1.setChecked(false);
                tvTime2.setTextColor(getResources().getColor(R.color.textGreen));
                cbTiem2.setChecked(true);
                mPosition = 1;
                break;
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("position",mPosition);
        setResult(RESULT_OK,data);
        super.finish();
    }
}
