package com.example.bloodpressureapp.ui.main.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.FragmentTrackerBinding

class FragmentTracker : Fragment() {
    private lateinit var binding: FragmentTrackerBinding

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
    }

    private fun initView() {
        setupContainerInfo()
        initButton()
    }

    private fun initButton() {
        binding.floatingActionButton.setOnClickListener {

            navigateToPage(R.id.action_fragmentTracker_to_addHistoryFragment)
        }
    }

    private fun setupContainerInfo(){
        with(binding.containerInfoSystolic){
            this.tvTitle.text = getString(R.string.systolic)
            this.containerContent.setBackgroundColor(getColor(R.color.cbp_03))
        }
        with(binding.containerInfoDiastolic){
            this.tvTitle.text = getString(R.string.diastolic)
            this.containerContent.setBackgroundColor(getColor(R.color.cbp_01))
        }
        with(binding.containerInfoPulse){
            this.tvTitle.text = getString(R.string.pulse)
            this.containerContent.setBackgroundColor(getColor(R.color.secondary_05))
        }
    }


}