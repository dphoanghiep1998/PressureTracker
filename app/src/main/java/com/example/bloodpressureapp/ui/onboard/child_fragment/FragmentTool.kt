package com.example.bloodpressureapp.ui.onboard.child_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodpressureapp.databinding.FragmentToolBinding

class FragmentTool:Fragment() {
    private lateinit var binding: FragmentToolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToolBinding.inflate(layoutInflater)
        return binding.root
    }

}