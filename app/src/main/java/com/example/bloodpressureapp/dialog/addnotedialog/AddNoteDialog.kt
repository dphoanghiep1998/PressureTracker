package com.example.bloodpressureapp.dialog.addnotedialog

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import com.example.bloodpressureapp.common.utils.clickWithDebounce
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.databinding.DialogAddNoteBinding
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.ItemTouchListener
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.NoteAdapter
import com.example.bloodpressureapp.viewmodel.AppViewModel
import com.google.android.flexbox.*


class AddNoteDialog :
    DialogFragment(), ItemTouchListener {
    private lateinit var binding: DialogAddNoteBinding
    private lateinit var adapter: NoteAdapter
    private val viewModel: AppViewModel by activityViewModels()
    private var notes: ArrayList<String> = arrayListOf()
    private var selectedNotes: ArrayList<String> = arrayListOf()

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
        initView()
        observeBackStack()
        getDataFromBundle()

    }

    private fun initView() {
        notes = AppSharePreference.INSTANCE.getListNote(
            arrayListOf()
        )
        Log.d("TAG", "initView: "+notes)
        initButton()
        initRecycleView()
    }

    private fun initButton() {
        binding.containerMain.clickWithDebounce { }
        binding.root.clickWithDebounce {
            dismiss()
        }
        binding.btnEditAdd.clickWithDebounce {
            val bundle = Bundle()
            bundle.putStringArrayList(Constant.KEY_NOTE_LIST, notes)
            findNavController().navigate(
                R.id.action_addNoteDialog_to_editNoteFragmentDialog,
                bundle
            )
        }
        binding.btnClose.clickWithDebounce {
            dismiss()
        }
        binding.btnSave.clickWithDebounce {
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
        adapter.setData(notes)

    }


    private fun observeBackStack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MutableList<String>>(
            Constant.KEY_NOTE_LIST
        )?.observe(viewLifecycleOwner) { it ->
            notes.clear()
            notes.addAll(it)
            adapter.setData(it)
        }
    }

    private fun getDataFromBundle() {
        val bundle: Bundle? = this.arguments
        if (bundle != null) {
            val selectedList: ArrayList<String>? =
                bundle.getStringArrayList(Constant.KEY_SELECTED_NOTE_LIST)
            selectedList?.let {
                selectedNotes.clear()
                selectedNotes.addAll(it)
                adapter.setSelectedNoteList(it)
            }
        }
    }

    override fun onTouchItem(listNote: MutableList<String>) {
        selectedNotes.clear()
        selectedNotes.addAll(listNote)
    }

    override fun onDeleteItem(note: String) {
        //Do nothing in this fragment
    }


}