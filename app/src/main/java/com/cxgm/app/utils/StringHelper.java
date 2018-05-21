package com.cxgm.app.utils;

import java.math.BigDecimal;

public class StringHelper {

    public static String getRMBFormat(float money){
        BigDecimal decimal = new BigDecimal(money);
        return getRMBFormat(decimal.setScale(2,BigDecimal.ROUND_DOWN).toString());
    }

    public static String getRMBFormat(String money){
        return "¥ " + money;
    }

}
