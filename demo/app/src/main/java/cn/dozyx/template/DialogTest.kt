package cn.dozyx.template

import android.app.Dialog
import android.app.Presentation
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import cn.dozyx.core.utli.log.LogUtil
import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.databinding.DialogBinding
import cn.dozyx.template.databinding.DialogBottomSheetFullScreenBinding
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.PermissionUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.dialog.*
import timber.log.Timber

/**
 * [Dialog.cancel] 会同时触发 onCancel 和 onDismiss
 * [Dialog.dismiss] 只会触发 onDismiss
 */
class DialogTest : BaseTestActivity(), DialogInterface.OnCancelListener,
    DialogInterface.OnDismissListener {
    override fun initActions() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarColor(android.R.color.transparent)
            .navigationBarDarkIcon(true)
            .init()
        super.onCreate(savedInstanceState)
        addButton("Dark", Runnable {
            delegate.localNightMode =
                if (delegate.localNightMode == MODE_NIGHT_YES) MODE_NIGHT_NO else MODE_NIGHT_YES
        })
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
            builder.setTitle("标题").setMessage("内容")
                .setNegativeButton("取消") { dialog, _ -> dialog?.cancel() }
                .setPositiveButton("确定") { dialog, _ -> dialog?.dismiss() }
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

        addButton("圆角", Runnable {
            val dialog = Dialog(this, R.style.no_frame_dialog)
            dialog.setContentView(R.layout.dialog_corner)
            dialog.show()
        })

        addButton("显示在第三方上面", Runnable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            Handler().postDelayed({
                val intent = Intent(this, DialogActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }, 500)
        })

        addButton("显示在第三方上面2", Runnable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            val dialog = Dialog(this, R.style.no_frame_dialog)
            dialog.setContentView(R.layout.dialog_corner)
            dialog.window?.setType(WindowManager.LayoutParams.TYPE_TOAST)
            dialog.show()
        })

        addButton("显示在第三方上面3", Runnable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
            val view = LayoutInflater.from(this).inflate(R.layout.activity_dialog, null)
            show(getSystemService(WINDOW_SERVICE) as WindowManager, view, createWindowLayoutParam())
        })

        addButton("bottom sheet", Runnable {
            val dialogFragment = object : BottomSheetDialog(this, R.style.ZeroBottomSheetDialog) {
                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(R.layout.dialog)
//                    window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    btn_hello.setOnClickListener {
                        startActivity(IntentUtils.getDialIntent("12345"))
                    }
                }

                override fun onStop() {
//                    window?.setWindowAnimations(-1)
                    super.onStop()
                }
            }
            dialogFragment.show()
        })

        addButton("bottom sheet fragment", Runnable {
//            MyBottomSheetDialogFragment().show(supportFragmentManager, "bottom_sheet")
            val fragment = MyBottomSheetDialogFragment()
            supportFragmentManager.beginTransaction().add(fragment, "1111").commitNow()
               fragment.onCreateDialog(null).also {

               }.show()
        })

        addButton("bottom sheet full", Runnable {
            val dialog = object : BottomSheetDialog(this, R.style.ZeroBottomSheetDialog) {
            }
            dialog.show()
        })
    }

    fun createWindowLayoutParam(): WindowManager.LayoutParams {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.END or Gravity.BOTTOM
        layoutParams.flags = layoutParams.flags or 32
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (Build.VERSION.SDK_INT < 19) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        } else if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this)) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST
        }
        layoutParams.packageName = packageName
        return layoutParams
    }

    fun show(
        windowManager: WindowManager?, view: View?,
        layoutParams: WindowManager.LayoutParams?
    ): Presentation? {
        if (windowManager != null && view != null && layoutParams != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                if (PermissionUtils.isGrantedDrawOverlays()) {
                    /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        layoutParams.type = 2037 // WindowManager.LayoutParams.TYPE_PRESENTATION
                    } else*/ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
                    }
                    return showWindow(windowManager, view, layoutParams)
                } else if (view.context.applicationInfo.targetSdkVersion >= Build.VERSION_CODES.O
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        layoutParams.type = 2037
                        return showWindow(windowManager, view, layoutParams)
                    }
                }
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1 || layoutParams.type != WindowManager.LayoutParams.TYPE_TOAST) {
                if (view.parent == null) {
                    return showWindow(windowManager, view, layoutParams)
                }
            } else if (Settings.canDrawOverlays(view.context)) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
                return showWindow(windowManager, view, layoutParams)
            } else {
                layoutParams.type = 2037
                return showWindow(windowManager, view, layoutParams)
            }
        }
        return null
    }

    private fun showWindow(
        windowManager: WindowManager,
        view: View?,
        layoutParams: WindowManager.LayoutParams
    ): Presentation? {
        if (view != null && view.parent == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && layoutParams.type == 2037) {
                // 采用 presenter 方式
                var presentation: Presentation? = null
                try {
                    presentation = Presentation(
                        view.context,
                        windowManager.defaultDisplay, R.style.no_frame_dialog
                    )
                    presentation.setContentView(view)
                    if (presentation.window != null) {
                        presentation.window!!.attributes = layoutParams
                    }
                    val finalPresentation: Presentation = presentation
                    view.postDelayed(Runnable { finalPresentation.show() }, 100)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return presentation
            } else {
                windowManager.addView(view, layoutParams)
            }
        }
        return null
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

    override fun onCancel(dialog: DialogInterface?) {
        Timber.d("DialogTestActivity.onCancel")
    }

    override fun onDismiss(dialog: DialogInterface?) {
        Timber.d("DialogTestActivity.onDismiss")
    }
}

class TopDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.full_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

class MyBottomSheetDialogFragment : AppCompatDialogFragment() {
    private lateinit var binding: DialogBinding


    override fun getTheme(): Int {
        return R.style.ZeroBottomSheetDialog
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBinding.inflate(inflater, container, false)
//        showsDialog = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHello.setOnClickListener {
            startActivity(IntentUtils.getDialIntent("12345"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {
            override fun show() {
                super.show()
            }
        }.apply {
//            dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun onStart() {
        super.onStart()
//        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.apply {
//            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}
