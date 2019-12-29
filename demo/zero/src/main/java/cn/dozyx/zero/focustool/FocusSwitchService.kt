package cn.dozyx.zero.focustool

import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import timber.log.Timber

/**
 * 创建进入系统专注模式的快捷开关
 * @author dozyx
 * @date 2019-12-28
 */
@RequiresApi(Build.VERSION_CODES.N)
class FocusSwitchService : TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
    }

    override fun onClick() {
        super.onClick()
        startFocusActivity()
    }

    private fun startFocusActivity() {
        Timber.d("FocusSwitchService.startFocusActivity")
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.action = ACTION_FOCUS_ACTIVITY
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        startActivityAndCollapse(intent)
        // 需要在设置中允许后台弹出界面
    }

    companion object {
        const val ACTION_FOCUS_ACTIVITY = "miui.action.usagestas.MAIN"
    }
}