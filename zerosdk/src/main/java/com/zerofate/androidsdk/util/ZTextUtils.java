package com.zerofate.androidsdk.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * isXXXValid() - 检查文本的合法性
 *
 * @author dozeboy
 * @date 2018/1/5
 */

public class ZTextUtils {
    /**
     * 判断文本长度是否符合要求
     */
    public static boolean isLengthValid(String text, int minLen, int maxLen) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (minLen != -1 && text.length() < minLen) {
            return false;
        }
        if (maxLen != -1 && text.length() > maxLen) {
            return false;
        }
        return true;
    }

    public static void filterChineseAndLetter(EditText editText) {
        editText.setFilters(new InputFilter[]{new ChineseLetterFilter()});
    }

    public static class ChineseLetterFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                int dstart,
                int dend) {
            Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
            Matcher m = p.matcher(source.toString());
            if (!m.matches()) {
                return "";
            }
            return null;
        }
    }
}
