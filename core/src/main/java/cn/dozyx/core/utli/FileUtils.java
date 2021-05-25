package cn.dozyx.core.utli;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.io.File;

public class FileUtils {
    @Nullable
    public static File getUniqueNewFile(File file) {
        if (!file.exists()) {
            return file;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < 9999; i++) {
            String suffix = "_" + i;
            String filePath = file.getAbsolutePath();
            String ext = com.blankj.utilcode.util.FileUtils.getFileExtension(filePath);
            int suffixPos = filePath.length();
            if (!TextUtils.isEmpty(ext)) {
                suffixPos = filePath.length() - ext.length() - 1;
            }
            if (builder.length() > 0) {
                builder.delete(0, builder.length());
            }
            builder.append(filePath, 0, suffixPos).append(suffix);
            if (suffixPos != filePath.length()) {
                builder.append(filePath, suffixPos, filePath.length());
            }
            if (!com.blankj.utilcode.util.FileUtils.isFileExists(builder.toString())) {
                return new File(builder.toString());
            }
        }
        return null;
    }
}
