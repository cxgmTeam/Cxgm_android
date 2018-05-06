package com.cxgm.app.utils;

import android.widget.Toast;

import com.deanlib.ootb.OotbConfig;

public class ToastManager {

    public static void sendToast(String msg){
        Toast.makeText(OotbConfig.mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
