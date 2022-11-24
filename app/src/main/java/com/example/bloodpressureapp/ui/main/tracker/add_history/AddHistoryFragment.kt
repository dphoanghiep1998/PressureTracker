package com.example.bloodpressureapp.ui.main.tracker.add_history

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.DateTimeUtils
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.getDrawable
import com.example.bloodpressureapp.databinding.FragmentAddHistoryBinding
import com.example.bloodpressureapp.dialog.DialogCallBack
import com.example.bloodpressureapp.dialog.addnotedialog.ConfirmDialogCallBack
import com.example.bloodpressureapp.dialog.addnotedialog.DeleteConfirmDialog
import com.example.bloodpressureapp.dialog.blood_pressure.BloodPressureTypeDialog
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.github.mikephil.charting.animation.Easing
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddHistoryFragment : DialogFragment(), ConfirmDialogCallBack {
    private lateinit var binding: FragmentAddHistoryBinding
    private val viewModel: AppViewModel by activityViewModels()
    private var historyModel = HistoryModel()
    private var selectedNoteList = arrayListOf<String>()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = ConstraintLayout(requireContext())
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(getColor(R.color.neutral_02)))

        dialog.window!!.setLayout(
            (requireContext().resources.displayMetrics.widthPixels),
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return dialog
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle()
        initView()
        observeBackStack()


    }


    private fun getDataFromBundle() {
        val bundle: Bundle? = this.arguments
        if (bundle != null) {
            val model: Parcelable? = bundle.getParcelable(Constant.KEY_HISTORY_MODEL)
            if (model != null) {
                historyModel = model as HistoryModel
                binding.tvTitle.text = getText(R.string.edit)
                binding.btnDelete.visibility = View.VISIBLE
                selectedNoteList.clear()
                selectedNoteList.addAll(model.notes)
            } else {
                binding.tvTitle.text = getText(R.string.new_record)
                binding.btnDelete.visibility = View.GONE
            }
        } else {
            val calendar = Calendar.getInstance()
            val time = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
            val date = DateTimeUtils.getDateConverted(Date(System.currentTimeMillis())).toString()
            historyModel.time = time
            historyModel.date = date
            selectedNoteList.clear()
        }

    }


    private fun initView() {
        setStatus(historyModel.systolic, historyModel.diastolic)
        initContentView()
        initPicker()
        initButton()
    }


    private fun initContentView() {
        binding.tvTimeValue.text = historyModel.time
        binding.tvDateTimeValue.text = historyModel.date
    }

    private fun initButton() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNote.setOnClickListener {
            val bundle = Bundle()
            bundle.putStringArrayList(Constant.KEY_SELECTED_NOTE_LIST, selectedNoteList)
            findNavController().navigate(R.id.action_addHistoryFragment_to_addNoteDialog,bundle)
        }
        binding.containerCalendar.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.show(requireActivity().supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                historyModel.date = DateTimeUtils.getDateConverted(Date(it)).toString()
                binding.tvDateTimeValue.text = DateTimeUtils.getDateConverted(Date(it))
            }
        }

        binding.containerTimer.setOnClickListener {
            val calendar = Calendar.getInstance()
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTheme(R.style.TimePicker)
                .build()
            picker.show(requireActivity().supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                historyModel.time = "${picker.hour}:${picker.minute}"
                binding.tvTimeValue.text = "${picker.hour}:${picker.minute}"
            }

        }
        binding.btnInfo.setOnClickListener {
            val bloodPressureDialog = BloodPressureTypeDialog(requireContext())
            bloodPressureDialog.show()
        }

        binding.btnSave.setOnClickListener {
            viewModel.insertHistory(historyModel)
            findNavController().popBackStack()
        }
        binding.btnDelete.setOnClickListener {
            val deleteConfirmDialog = DeleteConfirmDialog(this)
            deleteConfirmDialog.show(childFragmentManager, deleteConfirmDialog.tag)
        }
    }


    private fun setStatus(systolic: Int, diastolic: Int) {
        var status = getString(R.string.normal_blood_pressure)
        var condition = getString(R.string.condition_normal)
        var color = getColor(R.color.cbp_01)
        var imageDrawable = getDrawable(R.drawable.ic_face_normal)
        if (systolic < 120 && diastolic < 80) {
            status = getString(R.string.normal_blood_pressure)
            condition = getString(R.string.condition_normal)
            imageDrawable = getDrawable(R.drawable.ic_face_normal)
            color = getColor(R.color.cbp_01)
        } else if (systolic in 120..129 && diastolic < 80) {
            status = getString(R.string.elevated_blood_pressure)
            condition = getString(R.string.condition_elevated)
            imageDrawable = getDrawable(R.drawable.ic_face_elevated)
            color = getColor(R.color.cbp_02)
        } else if (systolic >= 180 || diastolic >= 120) {
            status = getString(R.string.dangerous_high_blood_pressure)
            condition = getString(R.string.condition_dangerously)
            imageDrawable = getDrawable(R.drawable.ic_face_dangerously)
            color = getColor(R.color.cbp_05)
        } else if (systolic in 140..179 || diastolic in 90..119) {
            status = getString(R.string.high_blood_pressure_stage_2)
            condition = getString(R.string.condition_high_2)
            imageDrawable = getDrawable(R.drawable.ic_face_high2)
            color = getColor(R.color.cbp_04)
        } else if (systolic in 130..139 || diastolic in 80..89) {
            status = getString(R.string.high_blood_pressure_stage_1)
            condition = getString(R.string.condition_high_1)
            imageDrawable = getDrawable(R.drawable.ic_face_high)

            color = getColor(R.color.cbp_03)
        }
        binding.tvStatus.text = status
        binding.tvCondition.text = condition
        binding.containerInfoContent.setBackgroundColor(color)
        binding.imvStatus.setImageDrawable(imageDrawable)
        historyModel.status = status
    }

    private fun initPicker() {
        with(binding.systolicPicker) {
            this.maxValue = 300
            this.minValue = 20
            this.value = historyModel.systolic
            this.wrapSelectorWheel = false
            this.setOnValueChangedListener { _, _, new ->
                historyModel.systolic = new
                setStatus(historyModel.systolic, historyModel.diastolic)

            }
        }
        with(binding.diastolicPicker) {
            this.maxValue = 300
            this.minValue = 20
            this.value = historyModel.diastolic
            this.wrapSelectorWheel = false
            this.setOnValueChangedListener { _, _, new ->
                historyModel.diastolic = new
                setStatus(historyModel.systolic, historyModel.diastolic)
            }
        }
        with(binding.pulsePicker) {
            this.maxValue = 200
            this.minValue = 20
            this.value = historyModel.pulse
            this.wrapSelectorWheel = false
            this.setOnValueChangedListener { _, _, new ->
                historyModel.pulse = new
            }
        }
    }

    private fun observeBackStack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MutableList<String>>(
            Constant.KEY_SELECTED_NOTE_LIST
        )?.observe(viewLifecycleOwner) { it ->
            selectedNoteList.clear()
            selectedNoteList.addAll(it)
            historyModel.notes.clear()
            historyModel.notes.addAll(it)
        }
    }


    override fun onPositiveClicked() {
        viewModel.deleteHistory(historyModel)
        findNavController().popBackStack()
    }


}