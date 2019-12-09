package cn.dozyx.core.widget.webview;

import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

/**
 * @author dozyx
 * @date 2019-12-02
 */
public class ZWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return shouldOverrideUrlLoadingCompat(view, url);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoadingCompat(view, request.getUrl().toString());
    }


    private boolean shouldOverrideUrlLoadingCompat(WebView view, String url) {
        view.loadUrl(url);
        // 安全设置setAllowFileAccess(true)就需要，禁止 file 协议加载 JavaScript
        if (url.startsWith("file://")) {
            view.getSettings().setJavaScriptEnabled(false);
        } else {
            view.getSettings().setJavaScriptEnabled(true);
        }
        return true;
    }
}
