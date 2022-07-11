package cn.dozyx.template.keeplive

import android.content.Context
import android.os.Build

object DaemonAgent {
    fun onAppCreate(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            RebirthJobService.init(context);
        }
    }
}