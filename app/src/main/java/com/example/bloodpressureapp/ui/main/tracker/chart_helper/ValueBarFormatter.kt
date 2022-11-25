package com.example.bloodpressureapp.ui.main.tracker.chart_helper

import com.github.mikephil.charting.formatter.ValueFormatter


class ValueBarFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
    }
}