package com.example.bloodpressureapp.ui.main.tracker.chart_helper

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler


class BarChartCustomRender(
    chart: BarDataProvider?,
    animator: ChartAnimator?,
    viewPortHandler: ViewPortHandler?,
) : BarChartRenderer(chart, animator, viewPortHandler) {
    private val textPaint = Paint(1)

    init {


        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 30f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = 0xFF525969.toInt()



    }

    override fun drawValue(c: Canvas?, valueText: String?, x: Float, y: Float, color: Int) {
        c?.drawText(valueText.toString(), x, y, textPaint)
    }

    override fun drawHighlighted(c: Canvas?, indices: Array<out Highlight>?) {
        super.drawHighlighted(c, indices)

    }

}