package com.example.bloodpressureapp.ui.main.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.FragmentTrackerBinding
import com.example.bloodpressureapp.ui.main.tracker.adapter.HistoryAdapter
import com.example.bloodpressureapp.ui.main.tracker.adapter.ItemHelper
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarEntry

enum class FilterType {
    MAX, MIN, AVERAGE
}

class FragmentTracker : Fragment(), ItemHelper {
    private lateinit var binding: FragmentTrackerBinding
    private lateinit var adapter: HistoryAdapter
    private var rollvalue = 1073741823
    private val viewModel: AppViewModel by activityViewModels()
    private var filterType = FilterType.MAX

    //calculator
    private var tmpMaxSystolic = 0
    private var tmpMinSystolic = Int.MAX_VALUE
    private var tmpAverageSystolic = 0

    private var tmpMaxDiastolic = 0
    private var tmpMinDiastolic = Int.MAX_VALUE
    private var tmpAverageDiastolic = 0

    private var tmpMaxPulse = 0
    private var tmpMinPulse = Int.MAX_VALUE
    private var tmpAveragePulse = 0

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
    }

    private fun initView() {
        setupContainerInfo()
        initButton()
        initRcv()
        initRolling()
        initChart()
    }
    private fun initChart() {
        binding.barChart.setTouchEnabled(false)
        binding.barChart.isClickable = false
        binding.barChart.isDoubleTapToZoomEnabled = false
        binding.barChart.isDoubleTapToZoomEnabled = false

        binding.barChart.setDrawBorders(false)
        binding.barChart.setDrawGridBackground(false)

        binding.barChart.description.isEnabled = false
//        binding.barChart.legend.isEnabled = false

//        binding.barChart.axisLeft.setDrawGridLines(false)
//        binding.barChart.axisLeft.setDrawLabels(false)
//        binding.barChart.axisLeft.setDrawAxisLine(false)

//        binding.barChart.xAxis.setDrawGridLines(false)
//        binding.barChart.xAxis.setDrawLabels(false)
//        binding.barChart.xAxis.setDrawAxisLine(false)

        binding.barChart.axisRight.setDrawGridLines(false)
        binding.barChart.axisRight.setDrawLabels(false)
        binding.barChart.axisRight.setDrawAxisLine(false)
        binding.barChart.axisLeft.axisMinimum = 0f
        binding.barChart.animateY(1000, Easing.EaseOutBack);
        binding.barChart.setNoDataText("")

        var barChart = ArrayList<BarEntry>()

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
        binding.btnBack.setOnClickListener {
            rollvalue += 1
            if (rollvalue == Int.MAX_VALUE) {
                rollvalue = 1073741824
            }
            when (rollvalue % 3) {
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
            }
            observeFilterType(filterType)

        }
        binding.btnNext.setOnClickListener {
            rollvalue -= 1
            if (rollvalue == 0) {
                rollvalue = 1073741823
            }
            when (rollvalue % 3) {
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
            }
            observeFilterType(filterType)

        }
        binding.tvRollValue.text
    }

    private fun observeListHistory() {
        viewModel.getAllHistory().observe(viewLifecycleOwner) {
            calculateValue(it)
            adapter.setData(it.toMutableList())
        }
    }

    private fun initButton() {
        binding.floatingActionButton.setOnClickListener {
            navigateToPage(R.id.action_fragmentTracker_to_addHistoryFragment)
        }
        binding.btnHistory.setOnClickListener {
            navigateToPage(R.id.action_fragmentTracker_to_fragmentHistory)
        }
    }

    private fun initRcv() {
        adapter = HistoryAdapter(requireContext(), this)

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

        }
    }

    override fun onClickEdit(item: HistoryModel) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.KEY_HISTORY_MODEL, item)
        findNavController().navigate(R.id.action_fragmentTracker_to_addHistoryFragment, bundle)
    }

    private fun calculateValue(list: List<HistoryModel>) {
        if (list.isNotEmpty()) {
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
            tmpAverageDiastolic /= list.size
            tmpAveragePulse /= list.size
            tmpAverageSystolic /= list.size

            if (tmpMaxSystolic > 300) {
                tmpMaxSystolic = 0
            }
            if (tmpMaxDiastolic > 300) {
                tmpMaxDiastolic = 0
            }
            if (tmpMaxPulse > 300) {
                tmpMaxPulse = 0
            }
            observeFilterType(filterType)

        }
    }


}