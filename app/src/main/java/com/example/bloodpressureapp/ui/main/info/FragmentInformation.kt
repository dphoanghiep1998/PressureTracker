package com.example.bloodpressureapp.ui.main.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodpressureapp.databinding.FragmentInfomationBinding


class FragmentInformation : Fragment() {

    private lateinit var binding: FragmentInfomationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfomationBinding.inflate(layoutInflater)
        return binding.root
    }

}