package cn.dozyx.template.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import cn.dozyx.template.R
import cn.dozyx.template.base.BaseShowResultActivity
import com.blankj.utilcode.util.ActivityUtils

open class ActivityLifeCycleTest : BaseShowResultActivity() {
    private var state = 0

    override fun getButtonText(): Array<String> {
        return arrayOf("启动", "清空", "Dialog", "延迟启动")
    }

    override fun onButton1() {
        startActivity(Intent(this, ActivityLifeCycleTest::class.java))
    }

    override fun onButton2() {
        setText("")
    }

    override fun onButton3() {
        val builder = AlertDialog.Builder(this, R.style.TranslucentDialog)
        builder.setMessage("哈哈哈").show()
    }

    override fun onButton4() {
        Handler().postDelayed({ ActivityUtils.startActivity(ActivityLifeCycleTest::class.java) }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appendResult("onCreate -> savedInstanceState ：${savedInstanceState == null} ")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        appendResult("onPostCreate -> savedInstanceState ：${savedInstanceState == null} ")
    }

    override fun onStart() {
        super.onStart()
        appendResult("onStart ->")
    }

    override fun onRestart() {
        super.onRestart()
        appendResult("onRestart ->")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        appendResult("onNewIntent ->")
    }

    override fun onResume() {
        appendResult("onResume() -> called before super.onResume")
        super.onResume()
        appendResult("onResume() -> called after super.onResume")
    }

    override fun onPause() {
        appendResult("onPause() -> called before super.onPause")
        super.onPause()
        appendResult("onPause() -> called after super.onPause")
    }

    override fun onStop() {
        appendResult("onStop ->")
        super.onStop()
    }

    override fun onDestroy() {
        appendResult("onDestroy ->")
        super.onDestroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // onRestoreInstanceState 在 Activity "确实" 被系统销毁时才会调用，所以它与 onSaveInstanceState 并不是成对的
        // 即，onSaveInstanceState 是可能被销毁的一种防范措施，而 onRestoreInstanceState 是事件发生后的补救措施
        super.onRestoreInstanceState(savedInstanceState)
        appendResult("onRestoreInstanceState -> savedInstanceState") // bundle 不可能为 null
    }

    override fun onSaveInstanceState(outState: Bundle) { // 在系统认为该 Activity 可能被 kill 掉时调用，通常是在 onPause 之后，onStop 之前
// 以下几种情形都会调用：home 键、电源键、任务界面选择其他程序、该Activity启动另一Activity、旋转屏幕（未设置configchange）
// 一开始我用这个 Activity 测试屏幕旋转时，没看到 save 方法的调用，后面才反应过来，save 是发生在了被销毁的 Activity。。。
// 系统会自动为 Activity 中的 View 的状态进行保存，前提是该 View 指定了唯一 id，其实它的实现也是调用了 View
// 的onSaveInstanceState 方法。
// 不过我在测试过程中旋转屏幕时发现，TextView 的文本没有被保存，初略看了下 TextView 的 onSaveInstanceState 实现，也不是很明白，
// 经过搜索发现的确会有这种情况，也不知道是不是个别设备或 SDK 问题，如果希望自动保存，可以设置 TextView的属性 android:freezesText="true"
// TODO: 分析下 TextView 的 onSaveInstanceState 实现
        outState.putInt("state", state)
        super.onSaveInstanceState(outState)
        appendResult("onSaveInstanceState ->")
    }
}