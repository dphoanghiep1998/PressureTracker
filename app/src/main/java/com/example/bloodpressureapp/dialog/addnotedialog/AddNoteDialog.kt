package com.example.bloodpressureapp.dialog.addnotedialog

import android.app.Dialog
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.DialogAddNoteBinding
import com.example.bloodpressureapp.dialog.BackPressDialogCallBack
import com.example.bloodpressureapp.dialog.DialogCallBack
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.ItemTouchListener
import com.example.bloodpressureapp.dialog.addnotedialog.adapter.NoteAdapter
import com.example.bloodpressureapp.viewmodel.AppViewModel

class AddNoteDialog : DialogFragment(), ItemTouchListener {
    private lateinit var binding: DialogAddNoteBinding
    private lateinit var adapter: NoteAdapter
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

    }

    private fun initView() {

//        initChipGroup()
        initRecycleView()
    }

    private fun initRecycleView() {
        adapter = NoteAdapter(this)
        val mList = requireContext().resources.getStringArray(R.array.noteList)
        adapter.setData(mList.toList())
        binding.rcvNotes.adapter = adapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvNotes.layoutManager = gridLayoutManager

    }

//    private fun initChipGroup() {
//        adapter = NoteAdapter(this)
//        val noteList = requireActivity().resources.getStringArray(R.array.noteList)
//        noteList.forEach { note ->
//            kotlin.run {
//                val chip = Chip(requireContext())
//                chip.setChipBackgroundColorResource(R.color.neutral_01)
//                chip.setTextColor(getColor(R.color.neutral_04))
//                chip.chipMinHeight = toDp(40).toFloat()
////                chip.setPadding(toDp(16), toDp(10), toDp(16), toDp(10))
//                chip.text = note
//                chip.setTextAppearance(R.style.ChipTextStyle)
//                chip.setOnClickListener {
//                    if (note in selectedNotes) {
//                        selectedNotes.remove(note)
//                        chip.setChipBackgroundColorResource(R.color.neutral_01)
//                    } else {
//                        selectedNotes.add(note)
//                        chip.setChipBackgroundColorResource(R.color.secondary_02)
//                    }
//                }
//                binding.chipGroup.addView(chip)
//            }
//        }
//    }

    private fun toDp(sizeInDp: Int): Int {
        val scale: Float = requireContext().resources.displayMetrics.density
        return (sizeInDp * scale + 0.5f).toInt()
    }

    private fun getNavBarHeight(): Int {
        val resources: Resources = requireContext().resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
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

    private fun getColor(resId: Int): Int {
        return ContextCompat.getColor(requireContext(), resId)
    }

    override fun onTouchItem(listNote: List<String>) {

    }
}