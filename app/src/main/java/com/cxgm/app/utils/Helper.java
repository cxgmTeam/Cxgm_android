package com.cxgm.app.utils;


import java.math.BigDecimal;

public class Helper {

    public static int getUnsuccessPageNum(int pageNum){
        return pageNum>1?--pageNum:1;
    }

    public static int getPositiveMinusNum(int num){
        return num<1?0:--num;
    };

    public static float moneyAdd(float x,float y){
        return BigDecimal.valueOf(x).add(BigDecimal.valueOf(y)).setScale(2).floatValue();
    }
    public static float moneySubtract(float x,float y){
        return BigDecimal.valueOf(x).subtract(BigDecimal.valueOf(y)).setScale(2).floatValue();
    }
    public static float moneyMultiply(float x,float y){
        return BigDecimal.valueOf(x).multiply(BigDecimal.valueOf(y)).setScale(2).floatValue();
    }
    public static float moneyDivide(float x,float y){
        return BigDecimal.valueOf(x).divide(BigDecimal.valueOf(y)).setScale(2).floatValue();
    }
}
