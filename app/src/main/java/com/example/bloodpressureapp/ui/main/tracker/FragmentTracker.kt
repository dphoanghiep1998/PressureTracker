package com.example.bloodpressureapp.ui.main.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.clickWithDebounce
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.FragmentTrackerBinding
import com.example.bloodpressureapp.ui.main.tracker.adapter.HistoryAdapter
import com.example.bloodpressureapp.ui.main.tracker.adapter.ItemHelper
import com.example.bloodpressureapp.ui.main.tracker.chart_helper.BarChartCustomRender
import com.example.bloodpressureapp.ui.main.tracker.chart_helper.ValueBarFormatter
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


enum class FilterType {
    MAX, MIN, AVERAGE, LASTEST
}

class FragmentTracker : Fragment(), ItemHelper {
    private lateinit var binding: FragmentTrackerBinding
    private lateinit var adapter: HistoryAdapter
    private var rollvalue = 1073741824
    private val viewModel: AppViewModel by activityViewModels()
    private var filterType = FilterType.MAX

    //calculator
    private var tmpMaxSystolic = 0
    private var tmpMinSystolic = 301
    private var tmpAverageSystolic = 0

    private var tmpMaxDiastolic = 0
    private var tmpMinDiastolic = 301
    private var tmpAverageDiastolic = 0

    private var tmpMaxPulse = 0
    private var tmpMinPulse = 301
    private var tmpAveragePulse = 0

    private var lastestSystolic = 0
    private var lastestDiaStolic = 0
    private var lastestPulse = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeListHistory()
        observeListHistoryD()
    }

    private fun observeListHistoryD() {
        viewModel.getAllHistory().observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                lastestSystolic = it[0].systolic
                lastestDiaStolic = it[0].diastolic
                lastestPulse = it[0].pulse
            }
            adapter.setData(it.subList(0, 3).toMutableList())
        }

        viewModel.getAllHistoryAcs().observe(viewLifecycleOwner) {
            getDataValue(it)
        }
    }

    private fun initView() {
        setupContainerInfo()
        initButton()
        initRcv()
        initRolling()
        initChart()
    }

    private fun initChart() {

        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(mutableListOf())
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        binding.barChart.isDragEnabled = true
//        binding.barChart.setTouchEnabled(false)
        binding.barChart.isClickable = false
        binding.barChart.isDoubleTapToZoomEnabled = false
        binding.barChart.xAxis.setDrawGridLines(false)
        binding.barChart.xAxis.setDrawAxisLine(false)
        binding.barChart.description.isEnabled = false
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.renderer = BarChartCustomRender(
            binding.barChart,
            binding.barChart.animator,
            binding.barChart.viewPortHandler,
        )


        val barData = BarData()

        binding.barChart.data = barData
        binding.barChart.invalidate()


    }


    private fun getDataValue(list: List<HistoryModel>) {
        val barSpace = 0.05f
        val groupSpace = 0.3f
        val xLabel = ArrayList<String>()
        val dataSystolic = ArrayList<BarEntry>()
        val dataDiastolic = ArrayList<BarEntry>()
        if (list.isNotEmpty()) {


            list.forEachIndexed { index, item ->
                dataSystolic.add(BarEntry(index.toFloat(), item.systolic.toFloat()))
                dataDiastolic.add(BarEntry(index.toFloat(), item.diastolic.toFloat()))
                xLabel.add(item.date)
            }
        }
        val barDataSet1 = BarDataSet(dataSystolic, getString(R.string.systolic))
        val barDataSet2 = BarDataSet(dataDiastolic, getString(R.string.diastolic))

        barDataSet1.setGradientColor(
            getColor(R.color.gradient_systolic_end),
            getColor(R.color.gradient_systolic_start)
        )
        barDataSet2.setGradientColor(
            getColor(R.color.gradient_diastolic_end),
            getColor(R.color.gradient_diastolic_start)
        )
        barDataSet1.valueFormatter = ValueBarFormatter()
        barDataSet2.valueFormatter = ValueBarFormatter()

        barDataSet1.color = getColor(R.color.gradient_systolic_start)
        barDataSet2.color = getColor(R.color.gradient_diastolic_start)


        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(xLabel)
        xAxis.textColor = getColor(R.color.neutral_03)
        xAxis.labelCount = list.size
        xAxis.setCenterAxisLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.lexend_daca_regular_400)

        binding.barChart.axisLeft.textSize = 12f
        binding.barChart.axisLeft.textColor = getColor(R.color.neutral_04)
        binding.barChart.axisLeft.typeface = typeface
        binding.barChart.legend.textSize = 12f
        binding.barChart.legend.textColor = getColor(R.color.neutral_04)
        binding.barChart.legend.form = Legend.LegendForm.CIRCLE

        binding.barChart.renderer = BarChartCustomRender(
            binding.barChart,
            binding.barChart.animator,
            binding.barChart.viewPortHandler,
        )
        val barData = BarData()

        barData.barWidth = .325f
        barData.addDataSet(barDataSet1)
        barData.addDataSet(barDataSet2)
        binding.barChart.data = barData
        binding.barChart.setScaleMinima(1f, 1f)

        binding.barChart.axisLeft.axisMinimum = 0f
        binding.barChart.axisLeft.axisMaximum = 300f

        binding.barChart.xAxis.axisMinimum = 0f
        binding.barChart.xAxis.axisMaximum =
            0f + binding.barChart.barData.getGroupWidth(groupSpace, barSpace) * list.size
        binding.barChart.groupBars(0f, groupSpace, barSpace)
        binding.barChart.notifyDataSetChanged()
        binding.barChart.invalidate()

        binding.barChart.setVisibleXRangeMaximum(3.5f)


    }


    private fun resetValue() {
        tmpMaxSystolic = 0
        tmpMinSystolic = Int.MAX_VALUE
        tmpAverageSystolic = 0

        tmpMaxDiastolic = 0
        tmpMinDiastolic = Int.MAX_VALUE
        tmpAverageDiastolic = 0

        tmpMaxPulse = 0
        tmpMinPulse = Int.MAX_VALUE
        tmpAveragePulse = 0
    }

    private fun initRolling() {
        filterType = FilterType.MAX
        binding.tvRollValue.text = getString(R.string.max)
        observeFilterType(filterType)
        binding.btnBack.clickWithDebounce {
            rollvalue += 1
            if (rollvalue == Int.MAX_VALUE) {
                rollvalue = 1073741823
            }
            when (rollvalue % 4) {
                0 -> {
                    binding.tvRollValue.text = getString(R.string.max)
                    filterType = FilterType.MAX
                }
                1 -> {
                    binding.tvRollValue.text = getString(R.string.average)
                    filterType = FilterType.AVERAGE

                }
                2 -> {
                    binding.tvRollValue.text = getString(R.string.min)
                    filterType = FilterType.MIN

                }
                3 -> {
                    binding.tvRollValue.text = getString(R.string.latest)
                    filterType = FilterType.LASTEST
                }
            }
            observeFilterType(filterType)

        }
        binding.btnNext.clickWithDebounce {
            rollvalue -= 1
            if (rollvalue == 0) {
                rollvalue = 1073741824
            }
            when (rollvalue % 4) {
                0 -> {
                    binding.tvRollValue.text = getString(R.string.max)
                    filterType = FilterType.MAX

                }
                1 -> {
                    binding.tvRollValue.text = getString(R.string.average)
                    filterType = FilterType.AVERAGE

                }
                2 -> {
                    binding.tvRollValue.text = getString(R.string.min)
                    filterType = FilterType.MIN
                }
                3 -> {
                    binding.tvRollValue.text = getString(R.string.latest)
                    filterType = FilterType.LASTEST
                }
            }
            observeFilterType(filterType)

        }
        binding.tvRollValue.text
    }

    private fun observeListHistory() {
        viewModel.getAllHistoryDesc().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.containerAllHistory.visibility = View.GONE
            } else {
                binding.containerAllHistory.visibility = View.VISIBLE

            }
            calculateValue(it)
        }

        viewModel.getAllHistoryAcs().observe(viewLifecycleOwner) {
            getDataValue(it)
        }
    }

    private fun initButton() {
        binding.floatingActionButton.clickWithDebounce(1000L) {
            navigateToPage(R.id.action_fragmentMain_to_addHistoryFragment)
        }
        binding.btnHistory.clickWithDebounce {
            navigateToPage(R.id.action_fragmentMain_to_fragmentHistory)
        }
        binding.containerAllHistory.clickWithDebounce {
            navigateToPage(R.id.action_fragmentMain_to_fragmentHistory)
        }
    }

    private fun initRcv() {
        adapter = HistoryAdapter(this)

        val linearLayoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically() = false
        }
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rcvHistory.adapter = adapter
        binding.rcvHistory.layoutManager = linearLayoutManager
    }

    private fun setupContainerInfo() {
        with(binding.containerInfoSystolic) {
            this.tvTitle.text = getString(R.string.systolic)
            this.containerContent.setBackgroundColor(getColor(R.color.cbp_03))
        }
        with(binding.containerInfoDiastolic) {
            this.tvTitle.text = getString(R.string.diastolic)
            this.containerContent.setBackgroundColor(getColor(R.color.cbp_01))
        }
        with(binding.containerInfoPulse) {
            this.tvTitle.text = getString(R.string.pulse)
            this.containerContent.setBackgroundColor(getColor(R.color.secondary_05))
        }
    }

    private fun observeFilterType(filterType: FilterType) {
        when (filterType) {
            FilterType.MAX -> {
                binding.containerInfoPulse.tvValue.text = tmpMaxPulse.toString()
                binding.containerInfoDiastolic.tvValue.text =
                    tmpMaxDiastolic.toString()
                binding.containerInfoSystolic.tvValue.text = tmpMaxSystolic.toString()
            }
            FilterType.MIN -> {
                binding.containerInfoPulse.tvValue.text = tmpMinPulse.toString()
                binding.containerInfoDiastolic.tvValue.text =
                    tmpMinDiastolic.toString()
                binding.containerInfoSystolic.tvValue.text = tmpMinSystolic.toString()
            }
            FilterType.AVERAGE -> {
                binding.containerInfoPulse.tvValue.text = tmpAveragePulse.toString()
                binding.containerInfoDiastolic.tvValue.text =
                    tmpAverageDiastolic.toString()
                binding.containerInfoSystolic.tvValue.text = tmpAverageSystolic.toString()
            }
            else -> {
                binding.containerInfoPulse.tvValue.text = lastestSystolic.toString()
                binding.containerInfoDiastolic.tvValue.text =
                    lastestDiaStolic.toString()
                binding.containerInfoSystolic.tvValue.text = lastestPulse.toString()
            }

        }
    }

    override fun onClickEdit(item: HistoryModel) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.KEY_HISTORY_MODEL, item)
        findNavController().navigate(R.id.action_fragmentMain_to_addHistoryFragment, bundle)
    }

    private fun calculateValue(list: List<HistoryModel>) {
        resetValue()
        list.forEach {
            if (it.systolic > tmpMaxSystolic) {
                tmpMaxSystolic = it.systolic
            }
            if (it.systolic < tmpMinSystolic) {
                tmpMinSystolic = it.systolic
            }
            if (it.diastolic > tmpMaxDiastolic) {
                tmpMaxDiastolic = it.diastolic
            }
            if (it.diastolic < tmpMinDiastolic) {
                tmpMinDiastolic = it.diastolic
            }
            if (it.pulse > tmpMaxPulse) {
                tmpMaxPulse = it.pulse
            }
            if (it.pulse < tmpMinPulse) {
                tmpMinPulse = it.pulse
            }
            tmpAverageDiastolic += it.diastolic
            tmpAveragePulse += it.pulse
            tmpAverageSystolic += it.systolic
        }
        if (list.isNotEmpty()) {

            tmpAverageDiastolic /= list.size
            tmpAveragePulse /= list.size
            tmpAverageSystolic /= list.size
        }

        if (tmpMinSystolic > 300) {
            tmpMinSystolic = 0
        }
        if (tmpMinDiastolic > 300) {
            tmpMinDiastolic = 0
        }
        if (tmpMinPulse > 300) {
            tmpMinPulse = 0
        }
        observeFilterType(filterType)


    }


}