package cn.dozyx.template.performance

import android.view.Choreographer
import timber.log.Timber

/**
 * @author dozyx
 * @date 2020/3/8
 */
class FPSFrameCallback : Choreographer.FrameCallback {
    private var lastFrameTimeNanos: Long = 0
    override fun doFrame(frameTimeNanos: Long) {

        if (lastFrameTimeNanos == 0L) {
            lastFrameTimeNanos = frameTimeNanos
            Choreographer.getInstance().postFrameCallback(this)
            return
        }
        val jitterNanos = frameTimeNanos - lastFrameTimeNanos
        if (jitterNanos >= FRAME_INTERVAL_IN_NANO) {
            val skippedFrames = jitterNanos / FRAME_INTERVAL_IN_NANO
            if (skippedFrames > 30) {
                Timber.d(
                    "Skipped $skippedFrames frames!  The application may be doing too much work on its main thread."
                )
            }
        }
        lastFrameTimeNanos = frameTimeNanos
        //注册下一帧回调
        Choreographer.getInstance().postFrameCallback(this)
    }

    companion object {
        private const val TAG = "FPS_TEST"
        private const val FRAME_INTERVAL_IN_NANO = (1000000000 / 60.0).toLong()
    }
}