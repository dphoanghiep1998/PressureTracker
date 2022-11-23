package com.example.bloodpressureapp.dialog.addnotedialog

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.databinding.DialogEditNoteBinding
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.ItemTouchListener
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.NoteAdapter
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.google.android.flexbox.*

class EditNoteFragmentDialog : DialogFragment(), AddNoteCallBack, ItemTouchListener {
    private lateinit var binding: DialogEditNoteBinding
    private lateinit var adapter: NoteAdapter

    private val viewModel: AppViewModel by activityViewModels()
    private var selectedNotes: HashSet<String> = hashSetOf()


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
        initRecycleView()
        observeNoteList()
    }

    private fun initRecycleView() {
        adapter = NoteAdapter(this, true)
        binding.rcvNotes.adapter = adapter
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        binding.rcvNotes.layoutManager = layoutManager
    }

    private fun initButton() {
        binding.btnAddNew.setOnClickListener {
            val addNoteFinal = AddNoteFinal(this)
            addNoteFinal.show(childFragmentManager, addNoteFinal.tag)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeNoteList() {
        viewModel.liveNoteList.observe(viewLifecycleOwner) {
            adapter.setData(it)
            AppSharePreference.INSTANCE.saveListNote(it)
        }
    }


    override fun onAddNote(note: String) {
        if(viewModel.liveNoteList.value!!.contains(note)){
            Toast.makeText(requireContext(),getString(R.string.dupplicate_note),Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.addNote(note)
    }

    override fun onTouchItem(listNote: List<String>) {
        //Do nothing in this fragment
    }

    override fun onDeleteItem(note: String) {
        viewModel.deleteNote(note)
    }

}