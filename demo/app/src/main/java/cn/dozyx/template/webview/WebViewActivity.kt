package cn.dozyx.template.webview

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.core.widget.webview.WebConfig
import cn.dozyx.core.widget.webview.WebViewUtil
import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_drag_panel.*
import kotlinx.android.synthetic.main.activity_web_view.*
import timber.log.Timber
import java.net.URLDecoder
import java.net.URLEncoder

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val webView = object : WebView(this) {
            override fun loadUrl(url: String?) {
                super.loadUrl(url)
                Timber.d("loadUrl: $url")
            }

            override fun postUrl(url: String?, postData: ByteArray?) {
                super.postUrl(url, postData)
                Timber.d("postUrl: $url")
            }


        }
        val config = WebConfig.getDefault()
        config.jsEnable = true
        WebViewUtil.init(webView, config)
        WebView.setWebContentsDebuggingEnabled(true);
        val settings = webView.settings
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.allowFileAccessFromFileURLs = true
        settings.allowUniversalAccessFromFileURLs = true
        settings.databaseEnabled = true
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        fl_web.addView(webView)

        configWebViewClient(webView)
        configWebChromeClient(webView)
        val header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
//        val url = "http://www.mobiuhome.com/redirect?ad_s=https%3a%2f%2flarkgame.com?utm_source=sp_settings"
        val url = "https://www.instagram.com/zapeebrasil/"
        webView.loadUrl(url)

        val data = ""
        val sendData = URLEncoder.encode(data, "utf-8")
        val recData = URLDecoder.decode(sendData, "utf-8")
        Timber.d("onCreate: $sendData + & + $recData")
        btn_back.setOnClickListener {
            if (webView.canGoBack()){
                webView.goBack()
            } else{
                finish()
            }
        }
        btn_user_agent.setOnClickListener {
            Timber.d("WebSettings.getDefaultUserAgent %s", WebSettings.getDefaultUserAgent(this))
            Timber.d("new WebView() %s", WebView(this).settings.userAgentString)
        }
    }

    private fun configWebChromeClient(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onRequestFocus(view: WebView?) {
                super.onRequestFocus(view)
                Timber.d("onRequestFocus: ")
            }

            override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
            ): Boolean {
                Timber.d( "onJsAlert: ")
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsPrompt(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    defaultValue: String?,
                    result: JsPromptResult?
            ): Boolean {
                Timber.d( "onJsPrompt: ")
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                Timber.d("onShowCustomView: ")
            }

            override fun onGeolocationPermissionsShowPrompt(
                    origin: String?,
                    callback: GeolocationPermissions.Callback?
            ) {
                super.onGeolocationPermissionsShowPrompt(origin, callback)
                Timber.d( "onGeolocationPermissionsShowPrompt: ")
            }

            override fun onPermissionRequest(request: PermissionRequest?) {
                super.onPermissionRequest(request)
                Timber.d("onPermissionRequest: ")
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                return super.onConsoleMessage(consoleMessage)
                Timber.d( "onConsoleMessage: ")
            }

            override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                super.onPermissionRequestCanceled(request)
                Timber.d( "onPermissionRequestCanceled: ")
            }

            // 通知 client 显示一个文件选择器
            override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
            ): Boolean {
                Timber.d("onShowFileChooser: ")
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            override fun onReceivedTouchIconUrl(
                    view: WebView?,
                    url: String?,
                    precomposed: Boolean
            ) {
                Timber.d( "onReceivedTouchIconUrl: ")
                super.onReceivedTouchIconUrl(view, url, precomposed)
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
                Timber.d( "onReceivedIcon: ")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Timber.d( "onReceivedTitle: ")
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Timber.d( "onProgressChanged: ")
            }

            override fun onJsConfirm(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
            ): Boolean {
                Timber.d("onJsConfirm: ")
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt()
                Timber.d( "onGeolocationPermissionsHidePrompt: ")
            }

            override fun onJsBeforeUnload(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
            ): Boolean {
                Timber.d("onJsBeforeUnload: ")
                return super.onJsBeforeUnload(view, url, message, result)
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                Timber.d("onHideCustomView: ")
            }

            override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
            ): Boolean {
                Timber.d("onCreateWindow: ")
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }

            override fun onCloseWindow(window: WebView?) {
                super.onCloseWindow(window)
                Timber.d("onCloseWindow: ")
            }
        }
    }

    private fun configWebViewClient(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Timber.d("onPageFinished: ")
            }


            /*override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse {
                Timber.d("shouldInterceptRequest: "  + request?.url)
                return super.shouldInterceptRequest(view, request)
            }*/


            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                Timber.d("shouldOverrideKeyEvent: ")
                return super.shouldOverrideKeyEvent(view, event)
            }

            override fun onSafeBrowsingHit(
                    view: WebView?,
                    request: WebResourceRequest?,
                    threatType: Int,
                    callback: SafeBrowsingResponse?
            ) {
                Timber.d("onSafeBrowsingHit: ")
                super.onSafeBrowsingHit(view, request, threatType, callback)
            }

            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                super.doUpdateVisitedHistory(view, url, isReload)
                Timber.d("doUpdateVisitedHistory: ")
            }

            override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d(
                            TAG,
                            "onReceivedError: " + request?.url.toString() + " & " + error?.description
                    )
                }
                super.onReceivedError(view, request, error)
            }

            override fun onRenderProcessGone(
                    view: WebView?,
                    detail: RenderProcessGoneDetail?
            ): Boolean {
                Timber.d("onRenderProcessGone: ")
                return super.onRenderProcessGone(view, detail)
            }

            override fun onReceivedLoginRequest(
                    view: WebView?,
                    realm: String?,
                    account: String?,
                    args: String?
            ) {
                Timber.d("onReceivedLoginRequest: ")
                super.onReceivedLoginRequest(view, realm, account, args)
            }

            override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
            ) {
                Timber.d("onReceivedHttpError: ")
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Timber.d("onPageStarted: $url")
                super.onPageStarted(view, url, favicon)
            }

            override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                super.onScaleChanged(view, oldScale, newScale)
                Timber.d("onScaleChanged: ")
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
            ): Boolean {
                Timber.d("shouldOverrideUrlLoading: ${request?.url}")
//                view?.loadUrl(request.url.toString())
                return false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Timber.d("shouldOverrideUrlLoading: deprecated $url")
//                view?.loadUrl(url)
                return false
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                Timber.d("onPageCommitVisible: ")
            }

            override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
                super.onUnhandledKeyEvent(view, event)
                Timber.d("onUnhandledKeyEvent: ")
            }

            override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
                super.onReceivedClientCertRequest(view, request)
                Timber.d("onReceivedClientCertRequest: ")
            }

            override fun onReceivedHttpAuthRequest(
                    view: WebView?,
                    handler: HttpAuthHandler?,
                    host: String?,
                    realm: String?
            ) {
                Timber.d("onReceivedHttpAuthRequest: ")
                super.onReceivedHttpAuthRequest(view, handler, host, realm)
            }

            override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
            ) {
                Timber.d("onReceivedSslError: ")
                super.onReceivedSslError(view, handler, error)
            }

            override fun onFormResubmission(
                    view: WebView?,
                    dontResend: Message?,
                    resend: Message?
            ) {
                Timber.d("onFormResubmission: ")
                super.onFormResubmission(view, dontResend, resend)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Timber.d("onLoadResource: ")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private val TAG = "WebViewActivity"
    }
}
