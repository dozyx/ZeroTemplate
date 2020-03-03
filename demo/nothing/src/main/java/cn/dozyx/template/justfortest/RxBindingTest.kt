package cn.dozyx.template.justfortest

import android.os.Bundle
import cn.dozyx.core.base.BaseActivity
import cn.dozyx.core.rx.RxViewUtils
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_binding.*

/**
 * @author dozyx
 * @date 2020/3/3
 */
class RxBindingTest : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_rx_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 扩展函数
        btn1.clicks().subscribe(object : Observer<Unit> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Unit) {
                ToastUtils.showShort("onClick")
            }

            override fun onError(e: Throwable) {
            }
        })

        RxViewUtils.enable(btn2, edit1, edit2)

    }
}