package com.zerofate.template;

import android.support.annotation.NonNull;

/**
 * @author Timon
 * @date 2017/12/11
 */

public class Test {
    public static void main(String[] args) {
        System.out.println(formatBankCard("6214835190421493"));
    }
    public static String formatBankCard(@NonNull String bankCard) {
        int len = bankCard.length();
        if (len < 9) {
            return bankCard;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len - 9; i++) {
            builder.append("*");
        }
        String starStr = builder.toString();
        return bankCard.substring(0, 5) + starStr + bankCard.substring(len - 4);
    }
}
