package com.zerofate.template

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import java.net.URLDecoder
import java.net.URLEncoder

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = object :WebView(this){
            override fun loadUrl(url: String?) {
                super.loadUrl(url)
                Log.d(TAG,"loadUrl: $url")
            }

            override fun postUrl(url: String?, postData: ByteArray?) {
                super.postUrl(url, postData)
                Log.d(TAG,"postUrl: $url")
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
//        webView.loadUrl(HTML_URL)
//        webView.loadData(HTML_STRING, "text/html", "UTF-8")
        webView.loadDataWithBaseURL(null, HTML_STRING, "text/html", "UTF-8",null)
        Log.d(TAG,"onCreate: decode test: encode " + URLEncoder.encode("+","utf-8"))
        Log.d(TAG,"onCreate: decode test: decode " + URLDecoder.decode("<form name=punchout_form method=post action=https://openapi.alipaydev.com/gateway.do?sign=GcnsVY6ZplPAPYPLxBIA4SyYTxHVfDQncmsI%2BkU9yYQU7xQHQ7l%2Fsoe3wdd5I%2FaT5kYIue4wJexGVb2%2B8cEu8ENGBwRKSjQSzZb38e8vq8HWolZDQqHyCXH7me2JUkXO6apK6Ix2mD3V8cxTRDYNupsgapth%2FRlU%2BCXfQD2hhfaQRCJkAD6PsfPQOZfseGAhE1lJPHGQqexXIm151M8ai8ZVYgFzYLs2UD3ykALA%2FyDr7ox%2FkKCdqxkKg19oHcjtgbosjbqR%2Fcab72hR8oIp9NsFl4Y6dCrTFDq1%2BYTlRZXcTeUZBxEHeszdzinaZI699lcwvYcGEEMKaX9skxgUJQ%3D%3D&timestamp=2018-07-25+09%3A20%3A07&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json>\\n\n" +
                "    <input type=hidden name=biz_content value={&quot;body&quot;:&quot;进件宝&quot;,&quot;out_trade_no&quot;:&quot;1807250920014803&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;进件宝&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;0.50&quot;}>\\n\n" +
                "    <input type=submit value=立即支付 style=display:none>\\n</form>\\n\n" +
                "<script>document.forms[0].submit();</script>","utf-8"))
        Log.d(TAG,"onCreate: decode test: decode " + URLDecoder.decode(URLEncoder.encode("%2B","utf-8"),"utf-8"))

        val data = ""
        val sendData = URLEncoder.encode(data,"utf-8")
        val recData = URLDecoder.decode(sendData,"utf-8")
        Log.d(TAG,"onCreate: $sendData + & + $recData")
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
        private val HTML_STRING ="""<form name=punchout_form method=post action=https://openapi.alipaydev.com/gateway.do?sign=GcnsVY6ZplPAPYPLxBIA4SyYTxHVfDQncmsI%2BkU9yYQU7xQHQ7l%2Fsoe3wdd5I%2FaT5kYIue4wJexGVb2%2B8cEu8ENGBwRKSjQSzZb38e8vq8HWolZDQqHyCXH7me2JUkXO6apK6Ix2mD3V8cxTRDYNupsgapth%2FRlU%2BCXfQD2hhfaQRCJkAD6PsfPQOZfseGAhE1lJPHGQqexXIm151M8ai8ZVYgFzYLs2UD3ykALA%2FyDr7ox%2FkKCdqxkKg19oHcjtgbosjbqR%2Fcab72hR8oIp9NsFl4Y6dCrTFDq1%2BYTlRZXcTeUZBxEHeszdzinaZI699lcwvYcGEEMKaX9skxgUJQ%3D%3D&timestamp=2018-07-25+09%3A20%3A07&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json>\n<input type=hidden name=biz_content value={&quot;body&quot;:&quot;进件宝&quot;,&quot;out_trade_no&quot;:&quot;1807250920014803&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;进件宝&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;0.50&quot;}>\n<input type=submit value=立即支付 style=display:none >\n</form>\n<script>document.forms[0].submit();</script>"""
        private val HTML_STRING_NOT_AUTO="<form name=punchout_form method=post action=https://openapi.alipaydev.com/gateway.do?sign=cZxmp9uVAaMDJZq5gSRmKdHJl9kbIuwBzuXK6Tmd3d36BvpeLupEpp36rHyd%2BAIqeSh36AFudETrC5OeXfCNyZf2lvYDUR%2BK17wqxDdrzjZ2g1k%2FpBsjIf88X6FoETlV1%2FqffbE0RaEVoQSOZ9ngRMl%2F6FHX%2BBnvddExY4kWBdF%2FUdW6lPP24PmW%2Bb0vZhGHHcwBnRVvWwU%2Fx%2BtbveE8PTXn7mSZIDIFMEScuOd5GOt8VDmkOO904q%2FGd4D3QKfMS45d0Nx%2Bzr76Pt90qgTyGldPALVVsqZqAPDWM6%2F8bBUaLabmHc0TPefx5FPUWk6epCjoAfFudIMzniTcYBpypw%3D%3D&timestamp=2018-07-24+16%3A09%3A42&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do%3Fsp_id%253D10010864%2526order_id%253D180724000000019864&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json>\\n\n" +
                "    <input type=hidden name=biz_content value={&quot;body&quot;:&quot;进件宝&quot;,&quot;out_trade_no&quot;:&quot;1807241609414591&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;进件宝&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;0.50&quot;}>\\n\n" +
                "    <input type=submit value=立即支付>\\n</form>\\n"
        private val HTML_STRING2= """<form name="punchout_form" method="get" action="https://openapi.alipaydev.com/gateway.do?charset=UTF-8&method=alipay.trade.wap.pay&sign=tvTwuqzWfkwlwop0mStG7b9fAwR8wLGNpelevrn4elDseESHoCtKGnJ3iJEaFdK%2BrUo6wT%2ByPZFe1D1pYukYs3sHV2CjFpcmSOzrN5ZUALyEtSyRSSFNni9oO%2F3pC9iea1MDs70A8XAvlrVhcQIqqQ9vuliyzvzSvgpqXwvCIS78Thn4Hdd6FyUg0ReUYxtV%2BYKaecMnM6Tj8j0c5V%2FHvbRmlOLCB7J2%2BZiTqboTLQPJQ1%2F%2FOpAFX4bHwQoQcCd%2FZL9bcT25kSXDgtfUAUUVcgY2igBIbLnPyAq4OyPU3GxSERFPUQ%2FWTpM78IA8L1dplFCdevsAJeyWz1ghxbbacw%3D%3D&return_url=http%3A%2F%2F192.168.7.46%3A80%2Falipay.trade.wap.pay-java-utf-8%2Falipay%2Freturn_url.do&notify_url=127.0.0.1%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&version=1.0&app_id=2016082600312055&sign_type=RSA2&timestamp=2018-03-29+15%3A24%3A23&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json">
    <input type="hidden" name="biz_content" value="{&quot;body&quot;:&quot;lin0.01&quot;,&quot;out_trade_no&quot;:&quot;1803291524208343&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;lin&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;0.01&quot;}">
    <input type="submit" value="立即支付" >
    </form>"""
        private val HTML_URL = "https://openapi.alipaydev.com/gateway.do?sign=Ge4PVZtmzqWYBYZPnSKMBVxCo15%2B4KxyrmWTU4hlIapPWI2sX%2BjRwQ3jP2cA8ZMuUMsFRFwhorOToR9GOknvGH%2FTa0D6NUvmWyoRIAbL76CfUs17hzr3pAi5STbV%2B7NHFOHAp3wfHHWNc4ZmQ%2BQgKJwTBBJlovjypVL4DVAC1%2FeBMmEFCeEU7lpb4A7a5c2tb1Fhk9%2FUAN0c1TVdP1oOPKZG0hz001R%2FntdHWIYEWUiCD%2Bd1bShuUCprwyUVcS1CwpolYqe9iWDOtYRs7jZ3%2F5YL0sshTzu2x2zuFgCur65RijxKdNGaYmRK%2FmsGkbTeWSx%2BbLf7iCEs4glJdz4t7Q%3D%3D&timestamp=2018-07-24+09%3A16%3A14&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do%3Fsp_id%253D10010864&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json&biz_content={\"body\":\"进件宝\",\"out_trade_no\":\"1807240916114516\",\"product_code\":\"QUICK_WAP_WAY\",\"subject\":\"进件宝\",\"timeout_express\":\"2m\",\"total_amount\":\"0.50\"}"
        private val HTML_STRING_OLD = """<form name=punchout_form method=post action=https://openapi.alipaydev.com/gateway.do?sign=df9HKcjac3Ow5%2BeBP8erWpsPiAbG8AS9Ml3%2BrXqHUR%2BUzMNgAOln44RqhQcu4TozgD2YOqAc6Q09aPFUUcHny4ZFtcBfS4%2B4GnxNKqzIg06diiqlQePDNt1GLHXEO3sGtXyCCQnwUdh1sNg38YE9MH2ZiEwW9jbvXfbkWc7jll0dqP%2BLC2r3RP8J8umxtF3e4ZW%2BpfNUcopQMDEkZekY3pOlqTRGGleDxY2G7NRVFNv81QRptd0S3UIDg4%2BBEziAJ3MfpHI2CHwnpWtEfh8FFE04mCWjAfI9%2Fl8NgGbDQOLLYm8z83Pt%2FoxHuaSsIbPW0MhNhRzYy9Ta1MW68Iz9hQ%3D%3D&timestamp=2018-07-24+17%3A31%3A41&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json>\n
    <input type=hidden name=biz_content value={&quot;body&quot;:&quot;aaa&quot;,&quot;out_trade_no&quot;:&quot;1807241731414661&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;aaa&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;1.00&quot;}>\n
    <input type=submit value=立即支付 style=display:none>\n</form>\n
<script>document.forms[0].submit();</script>"""
    }
}
