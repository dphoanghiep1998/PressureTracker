package com.example.bloodpressureapp.dialog.blood_pressure

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.DialogBloodPressureTypeBinding


class PermissionDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: DialogBloodPressureTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogBloodPressureTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        window?.setLayout(
            (context.resources.displayMetrics.widthPixels - 16f).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setBackgroundDrawableResource(R.color.transparent)

        initContent()


    }

    private fun initContent() {
        with(binding.containerNormal) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_normal
                )
            )
            tvValueHigher.text = getString(R.string.less_120)
            tvSymbol.text = getString(R.string.and)
            tvValueLower.text = getString(R.string.less_80)
            tvStatus.text = getString(R.string.normal_blood_pressure)
        }
        with(binding.containerNormal) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_elevated
                )
            )
            tvValueHigher.text = getString(R.string.in_120_to_190)
            tvSymbol.text = getString(R.string.and)
            tvValueLower.text = getString(R.string.less_80)
            tvStatus.text = getString(R.string.elevated_blood_pressure)
        }
        with(binding.containerNormal) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_high
                )
            )
            tvValueHigher.text = getString(R.string.more_130)
            tvSymbol.text = getString(R.string.or)
            tvValueLower.text = getString(R.string.more_80)
            tvStatus.text = getString(R.string.high_blood_pressure_stage_1)
        }
        with(binding.containerNormal) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_high2
                )
            )
            tvValueHigher.text = getString(R.string.more_140)
            tvSymbol.text = getString(R.string.or)
            tvValueLower.text = getString(R.string.more_90)
            tvStatus.text = getString(R.string.high_blood_pressure_stage_2)
        }
        with(binding.containerNormal) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_dangerously
                )
            )
            tvValueHigher.text = getString(R.string.more_180)
            tvSymbol.text = getString(R.string.and)
            tvValueLower.text = getString(R.string.more_90)
            tvStatus.text = getString(R.string.dangerous_high_blood_pressure)
        }

    }

    private fun getString(id: Int): String {
        return getString(id)
    }
}