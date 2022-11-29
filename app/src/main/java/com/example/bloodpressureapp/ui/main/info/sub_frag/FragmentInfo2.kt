package com.example.bloodpressureapp.ui.main.info.sub_frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.clickWithDebounce
import com.example.bloodpressureapp.databinding.LayoutInfo2Binding

class FragmentInfo2: Fragment() {
    private lateinit var binding: LayoutInfo2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LayoutInfo2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        changeBackPressCallBack()
    }

    private fun initView() {
        with(binding) {
            with(containerNormal) {
                imvImageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_normal
                    )
                )
                containerContent.setBackgroundResource(R.drawable.bg_cbp01_corner10)
                tvValueHigher.text = "< 120"
                tvSymbol.text = getString(R.string.and)
                tvValueLower.text = "< 80"
                tvStatus.text = getString(R.string.normal_blood_pressure)
            }
            with(containerElevated) {
                imvImageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_elevated
                    )
                )
                containerContent.setBackgroundResource(R.drawable.bg_cbp02_corner10)
                tvValueHigher.text = "120-129"
                tvSymbol.text = getString(R.string.and)
                tvValueLower.text = "< 80"
                tvStatus.text = getString(R.string.elevated_blood_pressure)
            }
            with(containerHigh1) {
                imvImageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_high
                    )
                )
                containerContent.setBackgroundResource(R.drawable.bg_cbp03_corner10)
                tvValueHigher.text = "130+"
                tvSymbol.text = getString(R.string.or)
                tvValueLower.text = "80+"
                tvStatus.text = getString(R.string.high_blood_pressure_stage_1)
            }
            with(containerHigh2) {
                imvImageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_high2
                    )
                )
                containerContent.setBackgroundResource(R.drawable.bg_cbp04_corner10)
                tvValueHigher.text = "140+"
                tvSymbol.text = getString(R.string.or)
                tvValueLower.text = "90+"
                tvStatus.text = getString(R.string.high_blood_pressure_stage_2)
            }
            with(containerDanger) {
                imvImageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_dangerously
                    )
                )
                containerContent.setBackgroundResource(R.drawable.bg_cbp05_corner10)
                tvValueHigher.text = "180+"
                tvSymbol.text = getString(R.string.and)
                tvValueLower.text = "90+"
                tvStatus.text = getString(R.string.dangerous_high_blood_pressure)
            }
        }

        initButton()
    }

    private fun initButton() {
        binding.btnBack.clickWithDebounce {
            findNavController().popBackStack()
        }
    }
    private fun changeBackPressCallBack() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}