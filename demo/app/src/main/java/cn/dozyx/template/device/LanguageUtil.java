package cn.dozyx.template.device;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import java.util.Locale;

/**
 * https://blog.csdn.net/u012527560/article/details/108816692
 */
class LanguageUtil {

    // 下面是多语言切换方法
    public static Context attachBaseContext(Context context, Integer language) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context, language);
        } else {
            applyLanguage(context, language);
            return context;
        }
    }

    public static void applyLanguage(Context context, Integer newLanguage) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSupportLanguage(newLanguage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DisplayMetrics dm = resources.getDisplayMetrics();
            // apply locale
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, dm);
        } else {
            // updateConfiguration
            DisplayMetrics dm = resources.getDisplayMetrics();
            configuration.locale = locale;
            resources.updateConfiguration(configuration, dm);
        }
    }

    private static Locale getSupportLanguage(Integer newLanguage) {
        return new Locale("pt");
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context, Integer language) {
        Resources resources = context.getResources();
        final Configuration configuration = resources.getConfiguration();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        Locale locale;
        if (language < 0) {
            // 如果没有指定语言使用系统首选语言
            locale = getSystemPreferredLanguage();
        } else {
            // 指定了语言使用指定语言，没有则使用首选语言
            locale = getSupportLanguage(language);
        }
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, dm);
        return context;
    }

    /**
     * 获取系统首选语言
     *
     * @return Locale
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Locale getSystemPreferredLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
