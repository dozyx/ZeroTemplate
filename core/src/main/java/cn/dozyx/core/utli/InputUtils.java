package cn.dozyx.core.utli;

import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ToastUtils;

import cn.dozyx.zerofate.android.core.R;

public final class InputUtils {

    public static void setEmptyTip(EditText editText, @StringRes int tip) {
        setEmptyTip(editText, editText.getResources().getString(tip));
    }

    public static void setEmptyTip(EditText editText, String tip) {
        editText.setTag(R.id.tag_input_empty_tip, tip);
    }

    private static String getEmptyTip(EditText editText) {
        return (String) editText.getTag(R.id.tag_input_empty_tip);
    }

    public static boolean notEmpty(EditText editText, boolean emptyToast) {
        if (TextUtils.isEmpty(editText.getText())) {
            if (emptyToast) {
                ToastUtils.showShort(getEmptyTip(editText));
            }
            return false;
        }
        return true;
    }
}
