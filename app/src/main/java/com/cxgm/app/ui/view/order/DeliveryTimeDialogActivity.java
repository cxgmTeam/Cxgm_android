package com.cxgm.app.ui.view.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.data.entity.DeliveryTime;
import com.cxgm.app.ui.adapter.TimeAdapter;
import com.cxgm.app.ui.base.BaseActivity;
import com.deanlib.ootb.utils.FormatUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    @BindView(R.id.lvTimes)
    ListView lvTimes;
    @BindView(R.id.tvDay)
    TextView tvDay;

//    public static final String[] TIMES = {"09:00-18:00", "18:00-22:00"};

    int mPosition = 0;
    String mDate;
    List<DeliveryTime> mTimeList;
    TimeAdapter mTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_delivery_time_dialog);
        ButterKnife.bind(this);
        mDate = getIntent().getStringExtra("date");
        init();
    }

    private void init() {
        mTimeList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int initHour = currentHour;
        tvDay.setText(R.string.today);
        long offTime = 0;
        if (currentHour<9){
            //当日
            initHour = 9;
        }else if (currentHour>=20){
            //次日
            tvDay.setText(R.string.tomorrow);
            offTime = 1000 * 60 * 60 * 24;
            initHour = 9;
        }else {
            initHour++;
        }

        String day = FormatUtils.convertDateTimestampToString(calendar.getTimeInMillis()+offTime,"yyyy年M月d日");

        for (int i = initHour;i<21;i++){
            String time = i+":00-"+(i+1)+":00";
            mTimeList.add(new DeliveryTime(time,day + " "+time,offTime==0));
        }

        if (mTimeList.size()>0) {
            for (int i = 0;i<mTimeList.size();i++){
                if (mTimeList.get(i).getDate().equals(mDate)){
                    mPosition = i;
                    break;
                }
            }

            mTimeList.get(mPosition).isChecked = true;
        }

        lvTimes.setAdapter(mTimeAdapter = new TimeAdapter(mTimeList));
        lvTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0 ;i<mTimeList.size();i++){
                    mTimeList.get(i).isChecked = i == id;
                }
                mTimeAdapter.notifyDataSetChanged();
                mPosition = (int) id;
            }
        });
    }


    @OnClick({R.id.imgClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        if (mTimeList.size()>0)
            data.putExtra("deliveryTime", mTimeList.get(mPosition));
        setResult(RESULT_OK, data);
        super.finish();
    }



}
