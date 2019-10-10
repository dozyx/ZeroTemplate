package cn.dozyx.template.base

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity

import cn.dozyx.template.R
import kotlinx.android.synthetic.main.activity_base_show_result.*

/**
 * 用于基本的按键-显示操作
 */
@Deprecated("使用 BaseTestActivity")
abstract class BaseShowResultActivity : AppCompatActivity(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_show_result)
        button.setOnClickListener { onButton1() }
        button2.setOnClickListener { onButton2() }
        button3.setOnClickListener { onButton3() }
        button4.setOnClickListener { onButton4() }
        button5.setOnClickListener { onButton5() }
        button6.setOnClickListener { onButton6() }
        val buttons = arrayOf<Button>(button, button2, button3, button4, button5, button6)
        var texts = getButtonText()
        for (i in texts.indices) {
            setButtonText(buttons[i], texts[i])
            buttons[i].visibility = View.VISIBLE
        }
    }

    open fun onButton1() {

    }

    open fun onButton2() {

    }

    open fun onButton3() {

    }

    open fun onButton4() {

    }

    open fun onButton5() {

    }

    open fun onButton6() {

    }

    protected fun setButtonText(button: Button, text: String) {
        button.text = text
    }

    fun setText(text: String?) {
        result_text!!.text = text
    }


    override fun clearResult() {
        setText("")
    }

    override fun appendResult(text: String) {
        result_text!!.text = result_text!!.text.toString() + "\n" + text
        Log.d(TAG, "appendResult: $text")
    }


    protected fun setImage(@DrawableRes drawable: Int) {
        result_image!!.setImageResource(drawable)
    }

    protected fun setImage(drawable: Drawable) {
        result_image!!.setImageDrawable(drawable)
    }

    companion object {
        private val TAG = "BaseShowResultActivity"
    }

    protected abstract fun getButtonText(): Array<String>
    override fun showResult(text: String?) {

    }
}
