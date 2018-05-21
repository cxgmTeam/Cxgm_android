package com.cxgm.app.ui.view.order;

import android.content.Intent;
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
import com.cxgm.app.data.entity.Invoice;
import com.cxgm.app.data.entity.UserAddress;
import com.cxgm.app.ui.base.BaseActivity;
import com.cxgm.app.utils.ToastManager;
import com.cxgm.app.utils.UserManager;
import com.deanlib.ootb.utils.TextUtils;

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

    UserAddress mUserAddress;
    Invoice mInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_invoice);
        ButterKnife.bind(this);

        mUserAddress = (UserAddress) getIntent().getSerializableExtra("address");

        init();
    }

    private void init() {
        tvTitle.setText(R.string.invoice);
        imgBack.setVisibility(View.VISIBLE);
        if (mUserAddress!=null) {
            mInvoice = new Invoice();
            //收票人信息
            mInvoice.setPhone(mUserAddress.getPhone());
            tvInvoiceReceiver.setText(TextUtils.hidePhoneNum(mUserAddress.getPhone()));

            //发票类型
            rgInvoiceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbCommonInvoice:
                            mInvoice.setType("0");
                            break;
                        case R.id.rbEInvoice:
                            mInvoice.setType("1");
                            break;
                    }
                }
            });
            rbEInvoice.setChecked(true);

            //发票抬头
            rgInvoiceHead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbPerson:
                            etInvoiceHead.setVisibility(View.GONE);
                            etInvoiceNum.setVisibility(View.GONE);
                            mInvoice.setCompanyName(null);
                            mInvoice.setDutyParagraph(null);
                            break;
                        case R.id.rbCompany:
                            etInvoiceHead.setVisibility(View.VISIBLE);
                            etInvoiceNum.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            });
            rbPerson.setChecked(true);

            //发票内容
            rgInvoiceContent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    mInvoice.setContent(((RadioButton)group.findViewById(checkedId)).getText().toString());
                }
            });
            rbGoodsDetail.setChecked(true);
        }
    }

    @OnClick({R.id.imgBack, R.id.tvSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvSave:
                //实体传回
                if (rbCommonInvoice.isChecked()){
                    String invoiceHead = etInvoiceHead.getText().toString().trim();
                    if (android.text.TextUtils.isEmpty(invoiceHead)){
                        ToastManager.sendToast(getString(R.string.empty_invoice_head));
                        return;
                    }
                    String invoiceNum = etInvoiceNum.getText().toString().trim();
                    if (android.text.TextUtils.isEmpty(invoiceNum)){
                        ToastManager.sendToast(getString(R.string.empty_invoice_num));
                        return;
                    }
                    mInvoice.setCompanyName(invoiceHead);
                    mInvoice.setDutyParagraph(invoiceNum);
                }
                Intent data = new Intent();
                data.putExtra("invoice",mInvoice);
                setResult(RESULT_OK,data);
                finish();
                break;
        }
    }
}
