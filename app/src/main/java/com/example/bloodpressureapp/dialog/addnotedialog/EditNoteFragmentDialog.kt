package com.example.bloodpressureapp.dialog.addnotedialog

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.LayoutDirection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.toDp
import com.example.bloodpressureapp.databinding.DialogEditNoteBinding
import com.example.bloodpressureapp.dialog.BackPressDialogCallBack
import com.example.bloodpressureapp.dialog.DialogCallBack
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.google.android.material.chip.Chip

class EditNoteFragmentDialog : DialogFragment() {
    private lateinit var binding: DialogEditNoteBinding
    private val viewModel: AppViewModel by activityViewModels()
    private var selectedNotes: HashSet<String> = hashSetOf()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = ConstraintLayout(requireContext())
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialog = DialogCallBack(requireContext(), callback)
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEditNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initButton()
        initChipGroup()
    }

    private fun initButton() {
        binding.btnAddNew.setOnClickListener {

        }
    }

    private fun initChipGroup() {
        val noteList = requireActivity().resources.getStringArray(R.array.noteList)
        noteList.forEach { note ->
            kotlin.run {
                val chip = Chip(requireContext())
                chip.setChipBackgroundColorResource(R.color.neutral_01)
                chip.setTextColor(getColor(R.color.neutral_04))
                chip.chipMinHeight = toDp(40).toFloat()
                chip.text = note
                chip.layoutDirection = View.LAYOUT_DIRECTION_RTL
                chip.chipIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_exit_circle_red)
                chip.setTextAppearance(R.style.ChipTextStyle)
                chip.setOnClickListener {
                    if (note in selectedNotes) {
                        selectedNotes.remove(note)
                        chip.setChipBackgroundColorResource(R.color.neutral_01)
                    } else {
                        selectedNotes.add(note)
                        chip.setChipBackgroundColorResource(R.color.secondary_02)
                    }
                }
                binding.chipGroup.addView(chip)
            }
        }
    }


    private val callback = object : BackPressDialogCallBack {
        override fun shouldInterceptBackPress(): Boolean {
//            return viewMoDel.flagChangeBack.value!!
            return true
        }

        override fun onBackPressIntercepted() {
//            binding.containerEdit.visibility = View.GONE
//            binding.containerShowUrl.visibility = View.VISIBLE
//            hideKeyboard()
//            viewMoDel.flagChangeBack.postValue(false)
        }

    }

}