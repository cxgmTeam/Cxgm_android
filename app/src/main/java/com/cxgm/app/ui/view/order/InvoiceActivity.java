package com.cxgm.app.ui.view.order;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cxgm.app.R;
import com.cxgm.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发票
 *
 * @anthor Dean
 * @time 2018/4/23 0023 21:36
 */
public class InvoiceActivity extends BaseActivity {

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgAction1)
    ImageView imgAction1;
    @BindView(R.id.imgAction2)
    ImageView imgAction2;
    @BindView(R.id.tvAction1)
    TextView tvAction1;
    @BindView(R.id.rbCommonInvoice)
    RadioButton rbCommonInvoice;
    @BindView(R.id.rbEInvoice)
    RadioButton rbEInvoice;
    @BindView(R.id.rgInvoiceType)
    RadioGroup rgInvoiceType;
    @BindView(R.id.tvInvoiceReceiver)
    TextView tvInvoiceReceiver;
    @BindView(R.id.rbGoodsDetail)
    RadioButton rbGoodsDetail;
    @BindView(R.id.rgInvoiceContent)
    RadioGroup rgInvoiceContent;
    @BindView(R.id.rbPerson)
    RadioButton rbPerson;
    @BindView(R.id.rbCompany)
    RadioButton rbCompany;
    @BindView(R.id.rgInvoiceHead)
    RadioGroup rgInvoiceHead;
    @BindView(R.id.etInvoiceHead)
    EditText etInvoiceHead;
    @BindView(R.id.etInvoiceNum)
    EditText etInvoiceNum;
    @BindView(R.id.tvSave)
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_invoice);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        tvTitle.setText(R.string.invoice);
        imgBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.imgBack, R.id.tvSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvSave:
                break;
        }
    }
}
