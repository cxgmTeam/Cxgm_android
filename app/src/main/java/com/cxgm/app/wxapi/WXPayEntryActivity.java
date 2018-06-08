package com.cxgm.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cxgm.app.app.Constants;
import com.cxgm.app.data.event.PayEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity : ";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
//
        api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        LogUtil.d(TAG + "onNewIntent");

        setIntent(intent);
        api.handleIntent(intent, this);

    }

    @Override
    public void onReq(BaseReq req) {

        LogUtil.d(TAG + "onReq");
    }

    @Override
    public void onResp(BaseResp resp) {

        LogUtil.d(TAG+ "onResp type:"+resp.getType()+" errcode:"+resp.errCode+" errstr:"+resp.errStr);
        //Toast.makeText(this, TAG+ "onResp type:"+resp.getType()+" errcode:"+resp.errCode+" errstr:"+resp.errStr, Toast.LENGTH_LONG).show();

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){

            switch (resp.errCode){
                case 0:
                    Toast.makeText(this, "支付成功",Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this, "用户取消",Toast.LENGTH_SHORT).show();
                    break;
            }

            PayEvent event = new PayEvent(PayEvent.PAY_TYPE_WECHAT,resp.errCode==0?PayEvent.STATUS_SUCCESS:PayEvent.STATUS_FAIL);
            event.obj = resp;
            EventBus.getDefault().post(event);

            finish();

        }

//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
    }
}
