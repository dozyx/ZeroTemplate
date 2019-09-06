package cn.dozyx.core.context

import android.content.Context
import android.content.ContextWrapper

/**
 */
class CustomContextWrapper(base: Context) : ContextWrapper(base) {
    companion object {
        /**
         * 由 app 控制字体大小，而不是跟随系统
         */
        fun wrap(context: Context, fontScale: Float): ContextWrapper {
            val config = context.resources.configuration
            var newContext = context
            if (config.fontScale != fontScale) {
                config.fontScale = fontScale
                // createConfigurationContext 在 API17 引入
                newContext = context.createConfigurationContext(config)
            }
            return CustomContextWrapper(newContext)
        }
    }
}
