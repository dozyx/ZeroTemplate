package com.zerofate.template.view.chart

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.ViewPortHandler
import com.zerofate.template.R
import java.util.*

class MpChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mp_chart)

        // 修改描述的文字，默认显示在右下角
        chart.description.text = "单位:万元"
        chart.description.textSize = 12f
//        chart.description.textAlign = Paint.Align.LEFT
        // 是否允许缩放
        chart.setScaleEnabled(false)
        // 柱状图是否可触摸
//        chart.setTouchEnabled(false)
        // 隐藏左轴标注
//        chart.axisLeft.setDrawLabels(false)
        // 禁用左轴
//        chart.axisLeft.isEnabled = false
//        chart.axisLeft.isGranularityEnabled = false
        // 是否显示左轴网格线
//        chart.axisLeft.setDrawGridLines(false)
//        chart.axisLeft.setDrawZeroLine(false)
        // 是否显示轴线，即左侧第一条竖着的线
//        chart.axisLeft.setDrawAxisLine(false)
        // 设置左轴从 0 开始
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.setLabelCount(6, true)
        // 隐藏右轴标注
        chart.axisRight.setDrawLabels(false)
        // 禁用右轴
        chart.axisRight.isEnabled = false
        // 隐藏图例
        chart.legend.isEnabled = false
        // 不显示网格，只影响竖线部分
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.textSize = 11f
        chart.xAxis.textColor = ActivityCompat.getColor(this, android.R.color.holo_green_dark)
        chart.xAxis.valueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return "${value.toInt()}日"
            }
        }


        val values = ArrayList<BarEntry>(7)
        values.add(BarEntry(0f, 0.0058f))
        values.add(BarEntry(1f, 0.0058f))
        values.add(BarEntry(2f, 0.0058f))
        values.add(BarEntry(3f, 0.0058f))
        values.add(BarEntry(4f, 0.0058f))
        values.add(BarEntry(5f, 0.0058f))
        values.add(BarEntry(6f, 0.0058f))
        // BarEntry 的两个参数分别为 x 和 y 坐标
        // BarEntry 的数量也是 x 轴同时显示的 bar 的数量

        // label 为图例的说明
        val barDataSet = BarDataSet(values, "22222")
        // bar 颜色
        barDataSet.color = Color.parseColor("#ff8a17")
        // bar 值的大小
        barDataSet.valueTextSize = 11f

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet)

        val barData = BarData(dataSets)
        // bar 宽，为百分比
        barData.barWidth = 0.3f
        // 格式化 value
        barData.setValueFormatter(object : IValueFormatter {
            override fun getFormattedValue(
                    value: Float,
                    entry: Entry?,
                    dataSetIndex: Int,
                    viewPortHandler: ViewPortHandler?
            ): String {
                return String.format("%.2f", value)
            }
        })
        chart.data = barData

        chart.marker = BarTouchView(this)
        chart.highlightValue(14f, 0)
    }

    class BarTouchView(context: Context) : MarkerView(context, android.R.layout.simple_list_item_1) {
        private val textValue: TextView

        init {
            textValue = findViewById(android.R.id.text1)
            textValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            textValue.setTextColor(Color.parseColor("#ff7e00"))
        }

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            textValue.text = String.format(Locale.getDefault(), "%.2f", e!!.y)
            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
    }
}
