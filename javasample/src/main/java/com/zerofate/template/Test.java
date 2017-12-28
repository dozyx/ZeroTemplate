package com.zerofate.template;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author dozeboy
 * @date 2017/12/11
 */

public class Test {
    public static void main(String[] args) {
        Calendar now = GregorianCalendar.getInstance();
        System.out.println(now.get(Calendar.DAY_OF_WEEK));
        System.out.println(now.get(Calendar.DAY_OF_MONTH));
        System.out.println(now.get(Calendar.DAY_OF_YEAR));
    }
}
