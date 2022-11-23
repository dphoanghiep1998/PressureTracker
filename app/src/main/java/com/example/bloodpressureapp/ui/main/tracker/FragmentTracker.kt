package com.example.bloodpressureapp.ui.main.tracker

import android.os.Bundle
import android.util.Log
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

class FragmentTracker : Fragment(), ItemHelper {
    private lateinit var binding: FragmentTrackerBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel: AppViewModel by activityViewModels()

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
    }

    private fun observeListHistory() {
        viewModel.getAllHistory().observe(viewLifecycleOwner) {
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
        adapter = HistoryAdapter(requireContext(),this)
        val linearLayoutManager = LinearLayoutManager(requireContext())
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

    override fun onClickEdit(item: HistoryModel) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.KEY_HISTORY_MODEL, item)
        findNavController().navigate(R.id.action_fragmentTracker_to_addHistoryFragment, bundle)
    }


}