package cn.dozyx.template

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import cn.dozyx.core.utli.log.LogUtil
import cn.dozyx.template.base.BaseTestActivity

class DialogTestActivity : BaseTestActivity() {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("显示对话框", Runnable {
            DialogFragmentTest.newInstance().show(supportFragmentManager, null)
        })
        addButton("top对话框", Runnable {
            TopDialog().show(supportFragmentManager, null)
        })
        addButton("constraint对话框", Runnable {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_constraint)
            dialog.show()
        })

        addButton("constraint style对话框", Runnable {
            val dialog = Dialog(this, R.style.Dialog)
            dialog.setContentView(R.layout.dialog_constraint)
            dialog.show()
        })
        addButton("Alert Dialog", Runnable {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("标题").setMessage("内容").setNegativeButton("取消", null).setPositiveButton("确定", null)
            val dialog = builder.show()
            dialog.window!!
            window.attributes
        })

        addButton("自定义", Runnable {
            val dialog = object : AppCompatDialog(this, R.style.CustomDialog) {
                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    val attributes = window!!.attributes
//                    attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
//                    window.attributes = attributes
                }
            }
            dialog.setContentView(R.layout.dialog_custom)
            val window = dialog.window!!
            dialog.show()
        })
        addButton("自定义2", Runnable {
            val dialog = Dialog(this, R.style.no_frame_dialog)
            // inflate 出来的 view 的 layout_params 无效了
            val content = LayoutInflater.from(this).inflate(R.layout.dialog_custom2, null)
            dialog.setContentView(content)
//            dialog.setContentView(content, content.layoutParams)
            dialog.show()
        })
    }

    class DialogFragmentTest : DialogFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            LogUtil.d("onCreate")
        }

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.dialog_fragment_test, container, false)
        }

        override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
            LogUtil.d("onGetLayoutInflater")
            val inflater = super.onGetLayoutInflater(savedInstanceState)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                dialog.window.setBackgroundDrawable(resources.getDrawable(android.R.color.transparent,null))
            }
            return inflater
        }

        companion object {
            fun newInstance() = DialogFragmentTest()
        }
    }
}

class TopDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.full_dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_top, container, false)
    }

    override fun onStart() {
        super.onStart()
//        dialog.window.setGravity(Gravity.TOP)
//        val attributes = dialog.window.attributes
//        attributes.width = 1080
//        dialog.window.attributes = attributes
    }
}
