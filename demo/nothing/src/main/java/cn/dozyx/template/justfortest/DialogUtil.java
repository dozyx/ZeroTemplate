package cn.dozyx.template.justfortest;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author dozeboy
 * @date 2018/3/9
 */

public class DialogUtil {
    private static ProgressDialog sProgressDialog;

    public static void showDialog(Context context) {
        sProgressDialog = new ProgressDialog(context);
        sProgressDialog.show();
    }
}
