package com.cxgm.app.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;

import java.math.BigDecimal;

public class StringHelper {

    public static String getRMBFormat(float money){
        BigDecimal decimal = new BigDecimal(money);
        return getRMBFormat(decimal.setScale(2,BigDecimal.ROUND_DOWN).toString());
    }

    public static String getRMBFormat(String money){
        return "¥ " + money;
    }

    public static SpannableString getStrikeFormat(String str){
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new StrikethroughSpan(),0,str.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
