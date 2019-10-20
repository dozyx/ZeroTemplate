package cn.dozyx.template.demo.dragpanel

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

import cn.dozyx.core.base.BaseActivity
import cn.dozyx.template.R
import timber.log.Timber

/**
 * https://gist.github.com/deyanm/58cbd80cd9907cf520c7a06b567eefba
 * @author dozyx
 * @date 2019-10-20
 */
class DragPanelActivity : BaseActivity(), View.OnClickListener {
    private var mQueen: LinearLayout? = null
    private var mHidden: Button? = null
    private var accept: Button? = null
    private var decline: Button? = null
    private var mOuterLayout: DraggingPanel? = null // 支持 drag 的 view group
    private var mMainLayout: LinearLayout? = null// 支持 drag 布局的内容部分

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOuterLayout = findViewById(R.id.outer_layout)
        mMainLayout = findViewById(R.id.main_layout)
        mHidden = findViewById(R.id.hidden_button)
        accept = findViewById(R.id.button1)
        accept!!.setOnClickListener(this)
        decline = findViewById(R.id.button2)
        decline!!.setOnClickListener(this)
        mHidden!!.setOnClickListener(this)
        mQueen = findViewById(R.id.queen_button)
        mQueen!!.setOnClickListener(this)
        mMainLayout!!.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Timber.d("DragPanelActivity.onCreate onLayoutChange")
            if (mOuterLayout!!.isMoving) {
                v.top = oldTop
                v.bottom = oldBottom
                v.left = oldLeft
                v.right = oldRight
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_drag_panel
    }

    override fun onClick(v: View) {
        val b = v as Button
        val t = Toast.makeText(this, b.text.toString() + " clicked", Toast.LENGTH_SHORT)
        t.show()
    }
}
