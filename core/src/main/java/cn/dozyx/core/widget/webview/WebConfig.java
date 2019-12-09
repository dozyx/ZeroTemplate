package cn.dozyx.core.widget.webview;

import android.webkit.WebChromeClient;

/**
 * @author dozyx
 */
public class WebConfig {
    /**
     * 是否需要访问文件
     */
    public boolean fileAccess;
    /**
     * 是否需要允许执行 js 脚本
     */
    public boolean jsEnable;

    public WebChromeClient client;


    public static WebConfig getDefault() {
        WebConfig webConfig = new WebConfig();
        webConfig.fileAccess = false;
        webConfig.jsEnable = false;
        webConfig.client = new WebChromeClient();
        return webConfig;
    }

}
