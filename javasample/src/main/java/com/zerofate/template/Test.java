package com.zerofate.template;


import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author dozeboy
 * @date 2017/12/11
 */

public class Test {
    public static void main(String[] args) {
        String value = "-1.955";
        BigDecimal num;
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_UP);// 正数往正无穷取舍，负数往负无穷取舍
        System.out.println("BigDecimal.ROUND_UP -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_DOWN);// 向零取舍
        System.out.println("BigDecimal.ROUND_DOWN -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_CEILING);// ceiling 天花板，向正无穷大取舍
        System.out.println("BigDecimal.ROUND_CEILING -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_FLOOR);// 向负无穷大取舍
        System.out.println("BigDecimal.ROUND_FLOOR -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_HALF_UP);// 向最接近的进行取舍，四舍五入
        System.out.println("BigDecimal.ROUND_HALF_UP -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_HALF_DOWN);// 向最接近的进行取舍，五将舍弃
        System.out.println("BigDecimal.ROUND_HALF_DOWN -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_HALF_EVEN);// 向相邻的偶数取舍
        System.out.println("BigDecimal.ROUND_HALF_EVEN -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        System.out.println("BigDecimal.ROUND_UNNECESSARY -> " + num);
        num = new BigDecimal(value);
        num = num.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        System.out.println("BigDecimal.ROUND_HALF_EVEN -> " + num);
    }


}
