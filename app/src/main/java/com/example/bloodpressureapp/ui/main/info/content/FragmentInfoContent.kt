package com.example.bloodpressureapp.ui.main.info.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.databinding.FragmentInfoContentBinding


class FragmentInfoContent : Fragment() {
    private lateinit var binding: FragmentInfoContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoContentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle()
        initView()
    }

    private fun initView() {
        initContent()
        initButton()
    }

    private fun initButton() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getDataFromBundle() {
        val bundle = this.arguments
        bundle?.let {
            val index = bundle.getInt(Constant.KEY_INFORMATION)
            showLayout(index)
        }
    }

    private fun showLayout(index: Int) {
        val groupLayout = listOf(
            binding.layout1,
            binding.layout2,
            binding.layout3,
            binding.layout4,
            binding.layout5,
            binding.layout6,
            binding.layout7
        )
        groupLayout.forEachIndexed { vIndex, item ->
            if (index == vIndex) {
                item.root.visibility = View.VISIBLE
            } else {
                item.root.visibility = View.GONE
            }
        }
    }

    private fun initContent() {
        with(binding.layout2) {
            with(containerNormal) {
                imvImageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_face_normal
                    )
                )
                containerContent.setBackgroundResource(R.drawable.bg_cbp01_corner10)
                tvValueHigher.text = getString(R.string.less_120)
                tvSymbol.text = getString(R.string.and)
                tvValueLower.text = getString(R.string.less_80)
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
                tvValueHigher.text = getString(R.string.in_120_to_129)
                tvSymbol.text = getString(R.string.and)
                tvValueLower.text = getString(R.string.less_80)
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
                tvValueHigher.text = getString(R.string.more_130)
                tvSymbol.text = getString(R.string.or)
                tvValueLower.text = getString(R.string.more_80)
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
                tvValueHigher.text = getString(R.string.more_140)
                tvSymbol.text = getString(R.string.or)
                tvValueLower.text = getString(R.string.more_90)
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
                tvValueHigher.text = getString(R.string.more_180)
                tvSymbol.text = getString(R.string.and)
                tvValueLower.text = getString(R.string.more_90)
                tvStatus.text = getString(R.string.dangerous_high_blood_pressure)
            }
        }


    }


}