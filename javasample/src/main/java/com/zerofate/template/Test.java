package com.zerofate.template;


import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author dozeboy
 * @date 2017/12/11
 */

public class Test {
    public static void main(String[] args) {
        BigDecimal num = new BigDecimal("1.9315");
        num = num.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(isNumeric("+11.11133E9"));
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static class Goods {
        float price;

        public Goods(float price) {
            this.price = price;
        }
    }

}
