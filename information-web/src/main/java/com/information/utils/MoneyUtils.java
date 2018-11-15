package com.information.utils;


import org.codehaus.plexus.util.StringUtils;

import java.math.BigDecimal;

public class MoneyUtils {

    public static BigDecimal StringToDecimal(String amount){
        if(StringUtils.isBlank(amount)) return null;
        BigDecimal rs = new BigDecimal(amount);
        rs = rs.divide(new BigDecimal(100));
        rs = rs.setScale(2,BigDecimal.ROUND_DOWN);
        return rs;
    }

    public static String DecimalToString(BigDecimal amount){
        if(amount == null) return "";
        BigDecimal rs = amount.multiply(new BigDecimal(100));
        rs = rs.setScale(0,BigDecimal.ROUND_DOWN);
        return rs.toString();
    }
    
    public static String DecimalToStringWithYUAN(BigDecimal amount){
        if(amount == null) return "";
        return amount.toPlainString();
    }

}
