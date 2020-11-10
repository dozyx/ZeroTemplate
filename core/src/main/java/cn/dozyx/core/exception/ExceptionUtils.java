package cn.dozyx.core.exception;

import androidx.annotation.Nullable;

public class ExceptionUtils {

    private static final int CAUSE_DEEP_COUNT = 3;

    @Nullable
    public static String getCauseChain(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        StringBuilder builder = null;
        int currentDeep = 0;
        Throwable cause;
        while ((cause = throwable.getCause()) != null && currentDeep++ < CAUSE_DEEP_COUNT) {
            if (builder == null) {
                builder = new StringBuilder();
            }
            builder.append("{");
            builder.append(cause);
            builder.append("}");

            throwable = cause;
        }
        if (builder != null) {
            return builder.toString();
        }
        return null;
    }

}
