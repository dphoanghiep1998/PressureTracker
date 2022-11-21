package com.example.bloodpressureapp.ui.onboard.child_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodpressureapp.databinding.FragmentPressureBinding

class FragmentPressure :Fragment() {
    private lateinit var binding: FragmentPressureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPressureBinding.inflate(layoutInflater)
        return binding.root
    }
}