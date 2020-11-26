package cn.dozyx.core.log

import java.util.*

class Sampler @JvmOverloads constructor(sampleRate: Int = DEFAULT_SAMPLE_RATE) {
    private val enable: Boolean

    init {
        val random = Random()
        enable = random.nextInt(sampleRate) == 0
    }

    fun enable(): Boolean {
        return enable
    }

    companion object {
        val SHARE_DEFAULT_SAMPLER = Sampler()
        private const val DEFAULT_SAMPLE_RATE = 32
    }
}