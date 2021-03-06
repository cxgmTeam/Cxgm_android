package com.cxgm.app.utils;


import android.text.TextUtils;

import com.cxgm.app.data.entity.Postage;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static int getUnsuccessPageNum(int pageNum){
        return pageNum>1?--pageNum:1;
    }

    public static int getPositiveMinusNum(int num){
        return num<1?0:--num;
    };

    public static float moneyAdd(float x,float y){
        return BigDecimal.valueOf(x).add(BigDecimal.valueOf(y)).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    public static float moneySubtract(float x,float y){
        return BigDecimal.valueOf(x).subtract(BigDecimal.valueOf(y)).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    public static float moneyMultiply(float x,float y){
        return BigDecimal.valueOf(x).multiply(BigDecimal.valueOf(y)).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
    }
    public static float moneyDivide(float x,float y){
        return BigDecimal.valueOf(x).divide(BigDecimal.valueOf(y)).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 计算折扣
     * @param amount
     * @param expression
     * @return
     */
    public static float calculateDiscounted(float amount,String expression){
        Pattern pattern = Pattern.compile("([+\\-*\\/])(\\d+)");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()){
            String option = matcher.group(1);
            if ("+".equals(option)){
                return moneyAdd(amount,Float.parseFloat(matcher.group(2)));
            }else if ("-".equals(option)){
                return moneySubtract(amount,Float.parseFloat(matcher.group(2)));
            }else if ("*".equals(option)){
                return moneyMultiply(amount,Float.parseFloat(matcher.group(2)));
            }else if ("/".equals(option)){
                return moneyDivide(amount,Float.parseFloat(matcher.group(2)));
            }
        }

        return amount;
    }

    public static float str2Float(String str){
        if (str!=null) {
            if (Pattern.matches("\\d+\\.?\\d*", str)) {
                return Float.parseFloat(str);
            }
        }
        return 0;
    }

    public static String longData2shortData(String data){
        if (TextUtils.isEmpty(data)){
            return "";
        }
        if (data.length()<10){
            return data;
        }
        return data.substring(0,10);
    }

    public static float calculatePostage(float totalAmount, Postage postage){
        if (postage!=null){
            if (totalAmount>=postage.getSatisfyMoney()){
                return 0;
            }else {
                return postage.getReduceMoney();
            }
        }
        return 7f;
    }
}
