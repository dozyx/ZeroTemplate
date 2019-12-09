package cn.dozyx.core.widget.webview;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author dozyx
 * @date 2019-12-02
 */
public class WebViewUtil {

    public static void init(WebView webView) {
        init(webView, WebConfig.getDefault());
    }

    public static void init(WebView webView, WebConfig config) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(config.jsEnable);

        // 自适应屏幕
        settings.setUseWideViewPort(true);
        // 缩放至屏幕大小
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        // 是否允许在 webview 中访问文件
        settings.setAllowFileAccess(true);
        // 是否允许通过 file url 加载的 js 代码读取其他的本地文件
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);

        settings.setDomStorageEnabled(true);
    }


}
