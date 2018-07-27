package com.zerofate.template.justfortest

import android.app.Application
import android.util.Log
import com.dozeboy.android.core.utli.log.LoggerLogcatTree
import com.dozeboy.android.core.utli.log.RemoteLogTree
import com.orhanobut.logger.*
import com.dozeboy.android.core.utli.log.ZLog
import com.dozeboy.android.core.utli.number.PlusMinusController
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author Timon
 * @date 2017/11/13
 */

class MyApplication : Application(), Thread.UncaughtExceptionHandler {
    private var uncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    override fun onCreate() {
        super.onCreate()
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
//        ZLog.d("logger")
//
//        ZLog.json("""{"A":"PayOrder_V1","C":"0","D":{"callback_url":"http://192.168.21.243/cgi-bin/sppaynotify_bs.cgi","form":"<form name=punchout_form method=post action=https://openapi.alipaydev.com/gateway.do?sign=VpVlnDSvfrnC14nC3jOIwU14AZz1vayzvZ9o7EP%2FHes7u%2BxVlgGSZXxvTY8kJ5zOjiybL97G1kqRU9RiR4NhPTAu6dbcNJPHbzv1x%2BnKMTT9Osn3FxbvISOMuxx6s4sILZh5AOgyN6GeWy5nyfHTvZotqoYuWD10AEfJNwtiC7TbbpkpmrF%2BxGNNHkIw1ww6qYZo8Ih7Exg818nE2zpSVR2KUivlKBKVxGuTE2Pv2qOa6bSWD%2B70ABzpUIDY95N%2BYS8t%2FSL2FSdXxhiVGyMwoblB4QfqqCBBx9geYBGwgopoJpV76uZXJlSZc334rTxcfqHZQiXs0TZ3Ki%2FgErgbnQ%3D%3D&timestamp=2018-07-25+12%3A09%3A41&sign_type=RSA2&notify_url=http%3A%2F%2Ftestlepos.yeahka.com%3A43102%2Fcgi-bin%2Flepos_alipay_wap_pay_notify.cgi&charset=UTF-8&app_id=2016082600312055&method=alipay.trade.wap.pay&return_url=http%3A%2F%2Fpos.lepass.cn%2Fsp%2Falipay%2FwapPay_return_url.do&version=1.0&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json>\n<input type=hidden name=biz_content value={&quot;body&quot;:&quot;进件宝&quot;,&quot;out_trade_no&quot;:&quot;180725120941488001&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;进件宝&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;1.00&quot;}>\n<input type=submit value=立即支付 style=display:none >\n</form>\n<script>document.forms[0].submit();</script>","merchant_id":"6506703085","order_id":"180725000000012864","page_return_url":"http://pos.lepass.cn/unionscan/union/back","pay_order_id":"180725120941488001","td_code":""},"M":"正常,正常","O":"SP_BUSINESS"}""")
//        val list = listOf("周", "吴", "郑", "王")
//        ZLog.d(list)
        ZLog.d(PlusMinusController(15.555).value)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e(TAG, "uncaughtException: ", e)
        uncaughtExceptionHandler!!.uncaughtException(t, e)
    }

    companion object {
        private val TAG = "MyApplication"
    }
}
