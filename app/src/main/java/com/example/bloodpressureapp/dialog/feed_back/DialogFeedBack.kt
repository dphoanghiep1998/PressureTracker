package com.example.bloodpressureapp.dialog.feed_back

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
import com.example.bloodpressureapp.databinding.DialogFeedBackBinding

interface FeedBackCallBack {
    fun onFeedBack(message: String)
}

class DialogFeedBack(private val callback: FeedBackCallBack) : DialogFragment() {
    private lateinit var binding: DialogFeedBackBinding

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
        binding = DialogFeedBackBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnSend.setOnClickListener {
            if (binding.edtNote.text.isNotBlank()) {
                callback.onFeedBack(binding.edtNote.text.toString())
            }
        }
        binding.root.setOnClickListener {
            dismiss()
        }
    }
}