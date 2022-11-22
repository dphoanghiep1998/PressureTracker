package com.example.bloodpressureapp.ui.main.tracker.add_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.FragmentAddHistoryBinding
import com.example.bloodpressureapp.dialog.addnotedialog.AddNoteDialog
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class AddHistoryFragment : Fragment() {
    private lateinit var binding: FragmentAddHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initPicker()
        initButton()
    }

    private fun initButton() {
        binding.btnNote.setOnClickListener {
//            val addNoteDialog = AddNoteDialog()
//            addNoteDialog.show(childFragmentManager,"")
            navigateToPage(R.id.action_addHistoryFragment_to_addNoteDialog)

        }
        binding.containerCalendar.setOnClickListener {

        }
        binding.containerTimer.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(10)
                    .setNegativeButtonText(getText(R.string.cancel))
                    .setPositiveButtonText(getText(R.string.ok))
                    .setTitleText("Select Appointment time")
                    .build()

            picker.show(childFragmentManager, "");
        }
    }

    private fun initPicker() {
        with(binding.systolicPicker) {
            this.maxValue = 300
            this.minValue = 20
            this.value = 50
            this.wrapSelectorWheel = false
        }
        with(binding.diastolicPicker) {
            this.maxValue = 300
            this.minValue = 20
            this.value = 50
            this.wrapSelectorWheel = false
        }
        with(binding.pulsePicker) {
            this.maxValue = 200
            this.minValue = 20
            this.value = 20
            this.wrapSelectorWheel = false
        }

    }

}