package cn.dozyx.core.utli.number

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @author dozeboy
 * @date 2018/7/27
 */
class PlusMinusController(
        value: Double = 50.0,
        val step: Double = 1.0,
        val max: Double = 100.0,
        val min: Double = 0.0,
        val reserveBit: Int = 2,
        val format: String = "#.##"
) {
    var value = value
        get() = BigDecimal.valueOf(field).setScale(reserveBit, RoundingMode.HALF_UP).toDouble()

    lateinit var listener: ValueChangeListener


    fun increase() {
        val tempValue = value + step
        if (tempValue.compareTo(max) == 1) {
            listener?.onAboveMax(max)
            return
        }
        if (tempValue.compareTo(min) == -1) {
            listener?.onBelowMin(min)
            return
        }
        value = tempValue
    }

    fun decrease() {
        val tempValue = value - step
        if (tempValue.compareTo(max) == 1) {
            listener?.onAboveMax(max)
            return
        }
        if (tempValue.compareTo(min) == -1) {
            listener?.onBelowMin(min)
            return
        }
        value = tempValue
    }

    fun plus(num: Float) {
        val tempValue = value + num
        if (tempValue.compareTo(max) == 1) {
            listener?.onAboveMax(max)
            return
        }
        if (tempValue.compareTo(min) == -1) {
            listener?.onBelowMin(min)
            return
        }
        value = tempValue
    }

    fun minus(num: Float) {
        val tempValue = value + num
        if (tempValue.compareTo(max) == 1) {
            listener?.onAboveMax(max)
            return
        }
        if (tempValue.compareTo(min) == -1) {
            listener?.onBelowMin(min)
            return
        }
        value = tempValue
    }

    fun getStringValue(): String {
        val bitCount = format.substring(format.indexOf('.') + 1).length
        return DecimalFormat(format).format(
                BigDecimal(value).setScale(bitCount, RoundingMode.HALF_UP)
        )
    }

    interface ValueChangeListener {
        fun onChange(controller: PlusMinusController)
        fun onAboveMax(max: Double)
        fun onBelowMin(min: Double)
    }

}