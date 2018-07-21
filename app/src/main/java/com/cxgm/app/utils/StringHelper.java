package com.cxgm.app.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
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

    public static String getWeight(float weight){
        //按g换算
        if (weight >= 1000) {
            return (weight/1000)+"kg";
        }else {
            return weight+"g";
        }
    }

    public static String getSpecification(String weight,String unit){
//        return StringHelper.getWeight(weight)+"/"+unit;
        if ("kg".equalsIgnoreCase(unit)){
            return weight+unit;
        }
        String str = "/";
        if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(unit))
            str = "";
        return weight+str+unit;
    }
    
}
