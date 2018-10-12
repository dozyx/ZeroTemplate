package com.zerofate.template

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dozeboy.android.core.utli.log.ZLog
import com.zerofate.template.base.BaseTestActivity

class DialogTestActivity : BaseTestActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addButton("显示对话框", Runnable {
            DialogFragmentTest.newInstance().show(supportFragmentManager, null)
        })
    }

    class DialogFragmentTest : androidx.fragment.app.DialogFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            ZLog.d("onCreate")
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.dialog_fragment_test, container, false)
        }

        override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
            ZLog.d("onGetLayoutInflater")
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
