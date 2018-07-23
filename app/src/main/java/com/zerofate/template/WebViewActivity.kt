package com.zerofate.template

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.webkit.*

import butterknife.BindView
import butterknife.ButterKnife

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = WebView(this)
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
//        webView.loadUrl("https://www.baidu.com")
        webView.loadData(HTML_STRING, "text/html", "UTF-8")
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
                Log.d(TAG, "shouldInterceptRequest: ")
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
                    Log.d(TAG, "onReceivedError: " + request?.url.toString() + " & " + error?.description)
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
                super.onPageStarted(view, url, favicon)
                Log.d(TAG, "onPageStarted: ")
            }

            override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                super.onScaleChanged(view, oldScale, newScale)
                Log.d(TAG, "onScaleChanged: ")
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d(TAG, "shouldOverrideUrlLoading: ")
//                view?.loadUrl(request.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d(TAG,"shouldOverrideUrlLoading: deprecated")
                view?.loadUrl(url)
                return true
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
        private val HTML_STRING =
            """
<form name=punchout_form method=post action=https://openapi.alipaydev.com/gateway.do?sign=cIqIninjIjY99oWn5RPS%2FT3fjDXI8MfIiLlfd5nEHCLD4XaT4dLTll0aUMNJtoKmNMgg6GY9KkLMEIUHgrfhWdqLfvVorNluwu6ezkLs%2FWacFC2McGd5mQNXTYb%2FKkMRCxUANm89aO25gbh3iCb59GcRT9YiWvtdO7drLh7unUw3lS0unETSD7xFVG3fQOkEzbMfrdg2pAQ1oW1BFfkJ131yfzONb2eCEqRVKmnpJ15LIEhmdm6tgogiMnom7vR7qkE20oTcBLboippewNnIy1k0R4ZUjY3RzQeZwjuZlcr1nfNYUpQJWc6NvHWhqDQO%2F8i2NzCmy8VNF%2FDqmIlB2A%3D%3D&timestamp=2018-07-20+15%3A44%3A45&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do%3Fsp_id%253D10000121&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json>
<input type=hidden name=biz_content value={&quot;body&quot;:&quot;进件宝&quot;,&quot;out_trade_no&quot;:&quot;1807201544414192&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;进件宝&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;0.50&quot;}>
<input type=submit value=立即支付 style=display:none>
</form>
<script>document.forms[0].submit();</script>"""
    }
}
