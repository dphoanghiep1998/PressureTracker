package com.example.bloodpressureapp.dialog.blood_pressure

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.DialogBloodPressureTypeBinding


class BloodPressureTypeDialog(
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
        initButton()

    }

    private fun initButton() {
        binding.btnGotIt.setOnClickListener {
            dismiss()
        }
    }

    private fun initContent() {
        with(binding.containerNormal) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_normal
                )
            )
            containerContent.setBackgroundResource(R.drawable.bg_cbp01_corner10)
            tvValueHigher.text = context.getString(R.string.less_120)
            tvSymbol.text = context.getString(R.string.and)
            tvValueLower.text = context.getString(R.string.less_80)
            tvStatus.text = context.getString(R.string.normal_blood_pressure)
        }
        with(binding.containerElevated) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_elevated
                )
            )
            containerContent.setBackgroundResource(R.drawable.bg_cbp02_corner10)
            tvValueHigher.text = context.getString(R.string.in_120_to_129)
            tvSymbol.text = context.getString(R.string.and)
            tvValueLower.text = context.getString(R.string.less_80)
            tvStatus.text = context.getString(R.string.elevated_blood_pressure)
        }
        with(binding.containerHigh1) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_high
                )
            )
            containerContent.setBackgroundResource(R.drawable.bg_cbp03_corner10)
            tvValueHigher.text = context.getString(R.string.more_130)
            tvSymbol.text = context.getString(R.string.or)
            tvValueLower.text = context.getString(R.string.more_80)
            tvStatus.text = context.getString(R.string.high_blood_pressure_stage_1)
        }
        with(binding.containerHigh2) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_high2
                )
            )
            containerContent.setBackgroundResource(R.drawable.bg_cbp04_corner10)
            tvValueHigher.text = context.getString(R.string.more_140)
            tvSymbol.text = context.getString(R.string.or)
            tvValueLower.text = context.getString(R.string.more_90)
            tvStatus.text = context.getString(R.string.high_blood_pressure_stage_2)
        }
        with(binding.containerDanger) {
            imvImageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_face_dangerously
                )
            )
            containerContent.setBackgroundResource(R.drawable.bg_cbp05_corner10)
            tvValueHigher.text = context.getString(R.string.more_180)
            tvSymbol.text = context.getString(R.string.and)
            tvValueLower.text = context.getString(R.string.more_90)
            tvStatus.text = context.getString(R.string.dangerous_high_blood_pressure)
        }

    }


}