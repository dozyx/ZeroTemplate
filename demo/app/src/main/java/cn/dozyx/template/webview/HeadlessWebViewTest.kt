package cn.dozyx.template.webview

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import com.acsbendi.requestinspectorwebview.RequestInspectorWebViewClient
import com.acsbendi.requestinspectorwebview.WebViewRequest
import kotlinx.android.synthetic.main.web_view_headless.btn_load_video
import kotlinx.android.synthetic.main.web_view_headless.btn_mock_click
import kotlinx.android.synthetic.main.web_view_headless.btn_mock_mute
import kotlinx.android.synthetic.main.web_view_headless.btn_start
import kotlinx.android.synthetic.main.web_view_headless.fl_container
import okio.buffer
import okio.source
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


class HeadlessWebViewTest : BaseActivity() {

    /**
     * 修改请在 interceptheader.html 文件进行，然后压缩后复制过来。压缩网站：https://htmlcompressor.com/compressor/
     * 代码来源：https://github.com/KonstantinSchubert/request_data_webviewclient/blob/master/writeintercept/src/main/assets/interceptheader.html
     */
    private val INSPECTOR_JS =
        "var requestUrl=null;XMLHttpRequest.prototype._open=XMLHttpRequest.prototype.open;XMLHttpRequest.prototype.open=function(f,c,d,a,b){requestUrl=c;const e=d===undefined?true:d;this._open(f,c,e,a,b)};XMLHttpRequest.prototype._send=XMLHttpRequest.prototype.send;XMLHttpRequest.prototype.send=function(a){YouTubePlayerBridge.customAjax(requestUrl,a);requestUrl=null;this._send(a)};"
    private lateinit var webView: WebView
    private val UI_HANDLER = Handler(Looper.getMainLooper())
    private val UNEMBED_VIDEO_URL = "https://www.youtube.com/embed/cJsFPeCBUeM"
    private val EMBED_VIDEO_URL = "https://www.youtube.com/embed/BAgPCaQ7lTU"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
        startIntercept(UNEMBED_VIDEO_URL)
        btn_start.setOnClickListener {
            startIntercept(EMBED_VIDEO_URL)
        }
        btn_load_video.setOnClickListener {
            webView.loadUrl("javascript:loadVideo('" + "78ASlB64rA4" + "', " + 0 + ")")
        }

        btn_mock_click.setOnClickListener {

            // 播放
            webView.evaluateJavascript("document.querySelector('#movie_player').click();", null)
            // 静音。放播放的前面或者后面都没有用，等实际开始播放之后才有效。
//            webView.evaluateJavascript("Array.from(document.querySelectorAll('video, audio')).forEach(el => el.muted = true);", null)
        }

        btn_mock_mute.setOnClickListener {
            webView.evaluateJavascript(
                "Array.from(document.querySelectorAll('video, audio')).forEach(el => el.muted = true);",
                null
            )
        }
    }

    private fun initWebView() {
        webView = WebView(this)
        fl_container.addView(
            webView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        WebView.setWebContentsDebuggingEnabled(true)

        webView.settings.apply {
            javaScriptEnabled = true
//            mediaPlaybackRequiresUserGesture = false
//            cacheMode = WebSettings.LOAD_DEFAULT
        }
        webView.addJavascriptInterface(YouTubePlayerBridge(), "YouTubePlayerBridge")
//        webView.loadUrl("about:blank")
    }

    private fun startIntercept(url: String) {
//        interceptByLib()
        interceptCustom()
        webView.loadUrl(url)
//        webView.loadUrl("https://www.youtube.com/embed/78ASlB64rA4?autoplay=1") // 能触发 v1/player，接口里也有 poToken，但还是受到 LOGIN_REQUIRED 限制。

//        webView.loadDataWithBaseURL("https://www.youtube.com", getPlayHtml(), "text/html", "utf-8", null)
    }

    private fun interceptCustom() {
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                webView.evaluateJavascript("javascript: \n$INSPECTOR_JS", null)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url!!.contains("https://www.youtube.com/embed/")) {
                    // 触发播放，延迟避免被识别为机器人
                    UI_HANDLER.postDelayed(
                        {
                            view!!.evaluateJavascript(
                                "document.querySelector('#movie_player').click();",
                                null
                            )
                        },
                        1000
                    )
                }
            }

            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                if (isPlaybackUrl(request.url) || isLogUrl(request.url)) {
                    // 禁止播放
                    // /youtubei/v1/player 拦截后 5s
                    return createInvalidResponse();
                }
                return super.shouldInterceptRequest(view, request)
            }
        }
    }

    private fun isLogUrl(uri: Uri): Boolean {
        return !TextUtils.isEmpty(uri.path) && uri.path!!.endsWith("log_event")
    }

    private fun isPlaybackUrl(uri: Uri): Boolean {
        if (uri.path?.contains("/youtubei/v1/player") == true) {
            return true
        }
        if (uri.host?.contains("googlevideo.com") == true) {
            return true
        }
        if (uri.getQueryParameter("itag")?.isNotEmpty() == true) {
            return true
        }
        if (uri.getQueryParameter("mime")?.contains("video") == true
            || uri.getQueryParameter("mime")?.contains("audio") == true
        ) {
            return true
        }
        return false
    }

    private fun interceptByLib() {
        webView.webViewClient = object : RequestInspectorWebViewClient(webView) {

            override fun shouldInterceptRequest(
                view: WebView,
                webViewRequest: WebViewRequest
            ): WebResourceResponse? {
                if (webViewRequest.url.contains("/youtubei/v1/player")) {
                    return createInvalidResponse()
                }

                return super.shouldInterceptRequest(view, webViewRequest)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //                webView.performClick()
                //                webView.loadUrl("https://www.youtube.com/embed/78ASlB64rA4?autoplay=1")
                //                webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
                /*runOnUiThread {
                    webView.evaluateJavascript(
                        "document.querySelector('#movie_player').click();"
                    ) { Log.d(TAG, "onReceiveValue: ") }
                }*/
            }
        }
    }

    private fun createInvalidResponse(): WebResourceResponse {
        val responseBody = "Not Found"
        val inputStream: InputStream = ByteArrayInputStream(
            responseBody.toByteArray(
                StandardCharsets.UTF_8
            )
        )
        return WebResourceResponse(
            "text/plain",
            "UTF-8",
            404,
            "Not Found",
            null,
            inputStream
        )
    }

    private fun getPlayHtml(): String {
//        return PLAYER_HTML
        return getResources()
            .openRawResource(R.raw.youtube_player)
            .source()
            .buffer()
            .readString(Charset.forName("UTF-8"));
    }

    override fun getLayoutId() = R.layout.web_view_headless

    inner class YouTubePlayerBridge {

        @JavascriptInterface
        fun onReady() {
        }

        @JavascriptInterface
        fun onStateChange(state: String?) {
        }

        @JavascriptInterface
        fun onPlaybackQualityChange(quality: String?) {
        }

        /**
         * The default playback rate is 1, which indicates that the video is playing at normal speed. Playback rates may include values like 0.25, 0.5, 1, 1.5, and 2.
         * <br></br><br></br>TODO add constants
         * @param rate 0.25, 0.5, 1, 1.5, 2
         */
        @JavascriptInterface
        fun onPlaybackRateChange(rate: String) {
        }

        @JavascriptInterface
        fun onError(error: String?) {
        }

        @JavascriptInterface
        fun onApiChange() {
        }

        @JavascriptInterface
        fun currentSeconds(seconds: String) {
        }

        @JavascriptInterface
        fun onVideoTitle(videoTitle: String?) {
        }

        @JavascriptInterface
        fun onVideoId(videoId: String?) {
        }

        @JavascriptInterface
        fun onVideoDuration(seconds: String?) {
        }

        @JavascriptInterface
        fun onLog(message: String?) {
        }

        @JavascriptInterface
        fun customAjax(url: String, body: String?) {
            Log.d(TAG, "customAjax $url: $body")
            if (url.contains("/api/stats/qoe")){
                UI_HANDLER.post {
                    webView.destroy()
                    initWebView()
                    startIntercept(EMBED_VIDEO_URL)
                }
            }
        }
    }

    companion object {
        private const val TAG = "HeadlessWebViewTest"
    }
}