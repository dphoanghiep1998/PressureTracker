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
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.databinding.DialogAddNoteFinalBinding
import com.example.bloodpressureapp.dialog.BackPressDialogCallBack
import com.example.bloodpressureapp.dialog.DialogCallBack

class AddNoteFinal : DialogFragment() {
    private lateinit var binding: DialogAddNoteFinalBinding

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
        binding = DialogAddNoteFinalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initButton()
    }

    private fun initButton() {
        binding.btnSave.setOnClickListener {

        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.root.setOnClickListener {
            dismiss()
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