package cn.dozyx.template.permission

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cn.dozyx.core.base.BaseFragment
import cn.dozyx.core.base.BaseSingleFragmentActivity
import cn.dozyx.template.R
import com.blankj.utilcode.util.ToastUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_rx_permissions.*

class RxPermissionsActivity : BaseSingleFragmentActivity() {
    override fun getFragment(startIntent: Intent): Fragment {
        return RxPermissionFragment()
    }

}

class RxPermissionFragment : BaseFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_request.setOnClickListener {
            val permissions = RxPermissions(this)
            permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA).subscribe { aBoolean ->
                ToastUtils.showShort("请求结果：$aBoolean")
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_rx_permissions
    }
}
