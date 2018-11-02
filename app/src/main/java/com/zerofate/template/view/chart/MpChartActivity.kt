package com.zerofate.template.view.chart

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.zerofate.template.R
import kotlinx.android.synthetic.main.activity_mp_chart.*

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
        chart.setTouchEnabled(false)
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
        chart.axisLeft.setLabelCount(5, false)
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
        chart.xAxis.valueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return "${value}日"
            }
        }


        val values = ArrayList<BarEntry>(7)
        values.add(BarEntry(1f, 1.58f))
        values.add(BarEntry(2f, 1.58f))
        values.add(BarEntry(3f, 3.69f))
        values.add(BarEntry(4f, 1.94f))
        values.add(BarEntry(5f, 1.43f))
        values.add(BarEntry(6f, 4.56f))
        values.add(BarEntry(7f, 3.05f))
        // BarEntry 的两个参数分别为 x 和 y 坐标
        // BarEntry 的数量也是 x 轴同时显示的 bar 的数量

        val barDataSet = BarDataSet(values, "22222")

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(barDataSet)

        val barData = BarData(dataSets)
        chart.data = barData
    }
}
