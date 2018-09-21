package com.dozeboy.android.core.utli.money

import java.math.BigDecimal
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @author dozeboy
 * @date 2018/8/11
 */

class MoneyCalculator(
        initialAmount: Double = 0.0,
        private val scale: Int = 2,
        private val step: Double = 1.0,
        val max: Double = 100.0,
        val min: Double = 0.0, val autoToEdge: Boolean = true, val format: String = "#.##"
) {
    var amount: BigDecimal = BigDecimal.valueOf(initialAmount).setScale(scale)
        set(value) {
            if (checkIsInRange(value)) {
                listener?.onValueChanged(amount.toDouble(), value.toDouble())
                field = value
            } else {
                if (autoToEdge) {
                    listener?.onValueChanged(amount.toDouble(), value.toDouble())
                    field = BigDecimal(maxOf(min, minOf(max, value.toDouble()))).setScale(scale)
                }
            }
        }
    var listener: ValueChangedListener? = null

    fun add(increment: Double) {
        val temp = amount.add(BigDecimal(increment))
        if (checkIsInRange(temp)) {
            listener?.onValueChanged(amount.toDouble(), temp.toDouble())
            amount = temp
        }
    }

    fun minus(decrement: Double) {
        val temp = amount.minus(BigDecimal(decrement))
        if (checkIsInRange(temp)) {
            listener?.onValueChanged(amount.toDouble(), temp.toDouble())
            amount = temp
        }
    }

    fun stepUp() {
        add(step)
    }

    fun stepDown() {
        minus(step)
    }

    fun setAmount(newAmount: Double) {
        amount = BigDecimal.valueOf(newAmount).setScale(2)
    }

    fun getFormattedAmount() = DecimalFormat(format).format(amount)

    private fun checkIsInRange(value: BigDecimal): Boolean {
        when {
            value.compareTo(BigDecimal.valueOf(max)) == 1 -> {
                listener?.overMax(max, value.toDouble())
                false
            }
            value.compareTo(BigDecimal.valueOf(min)) == -1 -> {
                listener?.belowMin(min, value.toDouble())
                false
            }
        }
        return true
    }


    interface ValueChangedListener {
        fun onValueChanged(old: Double, new: Double)
        fun overMax(max: Double, attempt: Double)
        fun belowMin(min: Double, attempt: Double)
    }


}