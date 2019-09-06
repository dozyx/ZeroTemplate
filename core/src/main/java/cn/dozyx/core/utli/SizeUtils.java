package cn.dozyx.core.utli;

import java.io.IOException;

public final class SizeUtils {

    public static int dp2sp(float dpValue) {
        return com.blankj.utilcode.util.SizeUtils.px2sp(
                com.blankj.utilcode.util.SizeUtils.dp2px(dpValue));
    }

    private SizeUtils() {
    }
}
