package cn.dozyx.core.rx

import android.annotation.SuppressLint
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable

/**
 * @author dozyx
 * @date 2020/3/3
 */
object RxViewUtils {
    /**
     * 存在空输入 EditText 时，禁用 Button。Button 初始状态为禁用。
     */
    @SuppressLint("CheckResult")
    fun enable(button: Button, vararg editTexts: EditText) {
        if (editTexts.isEmpty()) {
            return
        }
        button.isEnabled = false
        val textChangeObservables: Array<Observable<*>?> = arrayOfNulls(editTexts.size)

        editTexts.forEachIndexed { index, editText ->
            textChangeObservables[index] = editText.textChanges()
        }
        Observable.combineLatest(
            textChangeObservables
        ) { t ->
            t.all { s ->
                TextUtils.isEmpty(s.toString()).not()
            }
        }
            .subscribe { enable -> button.isEnabled = enable }
    }
}