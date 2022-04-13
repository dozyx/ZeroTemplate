package cn.dozyx.template

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

class DebugApplication : ZTApplication() {
    override fun initOnMainProcess() {
        super.initOnMainProcess()
//        initFlipper()
    }

    private fun initFlipper() {
        SoLoader.init(this, false)
        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))// Layout Inspector
            client.addPlugin(SharedPreferencesFlipperPlugin(this))
            client.start()
        }
    }
}