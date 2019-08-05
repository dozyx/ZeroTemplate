package cn.dozyx.template

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
import java.net.URLDecoder
import java.net.URLEncoder

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = object : WebView(this) {
            override fun loadUrl(url: String?) {
                super.loadUrl(url)
                Log.d(TAG, "loadUrl: $url")
            }

            override fun postUrl(url: String?, postData: ByteArray?) {
                super.postUrl(url, postData)
                Log.d(TAG, "postUrl: $url")
            }


        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.allowFileAccess = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        setContentView(webView)

        configWebViewClient(webView)
        configWebChromeClient(webView)
        val header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
        webView.loadUrl("www.baidu.com")

        val data = ""
        val sendData = URLEncoder.encode(data, "utf-8")
        val recData = URLDecoder.decode(sendData, "utf-8")
        Log.d(TAG, "onCreate: $sendData + & + $recData")
    }

    private fun configWebChromeClient(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onRequestFocus(view: WebView?) {
                super.onRequestFocus(view)
                Log.d(TAG, "onRequestFocus: ")
            }

            override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
            ): Boolean {
                Log.d(TAG, "onJsAlert: ")
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsPrompt(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    defaultValue: String?,
                    result: JsPromptResult?
            ): Boolean {
                Log.d(TAG, "onJsPrompt: ")
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                Log.d(TAG, "onShowCustomView: ")
            }

            override fun onGeolocationPermissionsShowPrompt(
                    origin: String?,
                    callback: GeolocationPermissions.Callback?
            ) {
                super.onGeolocationPermissionsShowPrompt(origin, callback)
                Log.d(TAG, "onGeolocationPermissionsShowPrompt: ")
            }

            override fun onPermissionRequest(request: PermissionRequest?) {
                super.onPermissionRequest(request)
                Log.d(TAG, "onPermissionRequest: ")
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                return super.onConsoleMessage(consoleMessage)
                Log.d(TAG, "onConsoleMessage: ")
            }

            override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                super.onPermissionRequestCanceled(request)
                Log.d(TAG, "onPermissionRequestCanceled: ")
            }

            override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
            ): Boolean {
                Log.d(TAG, "onShowFileChooser: ")
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            override fun onReceivedTouchIconUrl(
                    view: WebView?,
                    url: String?,
                    precomposed: Boolean
            ) {
                Log.d(TAG, "onReceivedTouchIconUrl: ")
                super.onReceivedTouchIconUrl(view, url, precomposed)
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
                Log.d(TAG, "onReceivedIcon: ")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Log.d(TAG, "onReceivedTitle: ")
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d(TAG, "onProgressChanged: ")
            }

            override fun onJsConfirm(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
            ): Boolean {
                Log.d(TAG, "onJsConfirm: ")
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt()
                Log.d(TAG, "onGeolocationPermissionsHidePrompt: ")
            }

            override fun onJsBeforeUnload(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
            ): Boolean {
                Log.d(TAG, "onJsBeforeUnload: ")
                return super.onJsBeforeUnload(view, url, message, result)
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                Log.d(TAG, "onHideCustomView: ")
            }

            override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
            ): Boolean {
                Log.d(TAG, "onCreateWindow: ")
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }

            override fun onCloseWindow(window: WebView?) {
                super.onCloseWindow(window)
                Log.d(TAG, "onCloseWindow: ")
            }
        }
    }

    private fun configWebViewClient(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "onPageFinished: ")
            }


            /*override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse {
                Log.d(TAG, "shouldInterceptRequest: "  + request?.url)
                return super.shouldInterceptRequest(view, request)
            }*/


            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                Log.d(TAG, "shouldOverrideKeyEvent: ")
                return super.shouldOverrideKeyEvent(view, event)
            }

            override fun onSafeBrowsingHit(
                    view: WebView?,
                    request: WebResourceRequest?,
                    threatType: Int,
                    callback: SafeBrowsingResponse?
            ) {
                Log.d(TAG, "onSafeBrowsingHit: ")
                super.onSafeBrowsingHit(view, request, threatType, callback)
            }

            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                super.doUpdateVisitedHistory(view, url, isReload)
                Log.d(TAG, "doUpdateVisitedHistory: ")
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
                Log.d(TAG, "onRenderProcessGone: ")
                return super.onRenderProcessGone(view, detail)
            }

            override fun onReceivedLoginRequest(
                    view: WebView?,
                    realm: String?,
                    account: String?,
                    args: String?
            ) {
                Log.d(TAG, "onReceivedLoginRequest: ")
                super.onReceivedLoginRequest(view, realm, account, args)
            }

            override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
            ) {
                Log.d(TAG, "onReceivedHttpError: ")
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.d(TAG, "onPageStarted: $url")
                super.onPageStarted(view, url, favicon)
            }

            override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                super.onScaleChanged(view, oldScale, newScale)
                Log.d(TAG, "onScaleChanged: ")
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
            ): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading: ${request?.url}")
//                view?.loadUrl(request.url.toString())
                return false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading: deprecated $url")
//                view?.loadUrl(url)
                return false
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                Log.d(TAG, "onPageCommitVisible: ")
            }

            override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
                super.onUnhandledKeyEvent(view, event)
                Log.d(TAG, "onUnhandledKeyEvent: ")
            }

            override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
                super.onReceivedClientCertRequest(view, request)
                Log.d(TAG, "onReceivedClientCertRequest: ")
            }

            override fun onReceivedHttpAuthRequest(
                    view: WebView?,
                    handler: HttpAuthHandler?,
                    host: String?,
                    realm: String?
            ) {
                Log.d(TAG, "onReceivedHttpAuthRequest: ")
                super.onReceivedHttpAuthRequest(view, handler, host, realm)
            }

            override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
            ) {
                Log.d(TAG, "onReceivedSslError: ")
                super.onReceivedSslError(view, handler, error)
            }

            override fun onFormResubmission(
                    view: WebView?,
                    dontResend: Message?,
                    resend: Message?
            ) {
                Log.d(TAG, "onFormResubmission: ")
                super.onFormResubmission(view, dontResend, resend)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.d(TAG, "onLoadResource: ")
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
