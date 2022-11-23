package com.example.bloodpressureapp.dialog.addnotedialog

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.Constant
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.navigateToPage
import com.example.bloodpressureapp.databinding.DialogAddNoteBinding
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.ItemTouchListener
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.NoteAdapter
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.google.android.flexbox.*

class AddNoteDialog : DialogFragment(), ItemTouchListener {
    private lateinit var binding: DialogAddNoteBinding
    private lateinit var adapter: NoteAdapter
    private val viewModel: AppViewModel by activityViewModels()
    private var selectedNotes: List<String> = listOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = ConstraintLayout(requireContext())
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(getColor(R.color.transparent)))
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
        binding = DialogAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        initView()

    }

    private fun initValue() {
        selectedNotes = listOf()
    }

    private fun initView() {

//        initChipGroup()
        initButton()
        initRecycleView()
    }

    private fun initButton() {
        binding.root.setOnClickListener {
            dismiss()
        }
        binding.btnEditAdd.setOnClickListener {
            navigateToPage(R.id.action_addNoteDialog_to_editNoteFragmentDialog)
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                Constant.KEY_SELECTED_NOTE_LIST,
                selectedNotes
            )
            findNavController().popBackStack()
        }
    }

    private fun initRecycleView() {
        adapter = NoteAdapter(this)
        binding.rcvNotes.adapter = adapter
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        binding.rcvNotes.layoutManager = layoutManager
        val noteList = requireActivity().resources.getStringArray(R.array.noteList)
        adapter.setData(noteList.toList())

    }


    override fun onTouchItem(listNote: List<String>) {
        selectedNotes = listNote
    }

    override fun onDeleteItem(note: String) {
        //Do nothing in this fragment
    }

}