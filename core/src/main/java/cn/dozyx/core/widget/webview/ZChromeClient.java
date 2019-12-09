package cn.dozyx.core.widget.webview;

import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;


/**
 * @author dozyx
 * @date 2019-12-02
 */
public class ZChromeClient extends WebChromeClient {
    public static final int DELAY_MILLIS_HIDE_PROGRESS = 300;
    private Handler handler;
    private ProgressBar progressBar;

    public ZChromeClient() {
        handler = new Handler();
    }

    public ZChromeClient(ProgressBar progressBar) {
        this();
        this.progressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (progressBar == null) {
            return;
        }
        boolean finished = newProgress == 100;
        if (finished) {
            progressBar.setProgress(100);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar.setProgress(0);
                }
            }, DELAY_MILLIS_HIDE_PROGRESS);
        } else {
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setProgress(newProgress);
        }

    }
}
