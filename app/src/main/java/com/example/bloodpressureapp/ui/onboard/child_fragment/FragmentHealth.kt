package com.example.bloodpressureapp.ui.onboard.child_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodpressureapp.databinding.FragmentHealthBinding

class FragmentHealth :Fragment(){
    private lateinit var binding: FragmentHealthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthBinding.inflate(layoutInflater)
        return binding.root
    }
}