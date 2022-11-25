package com.example.bloodpressureapp.dialog.rate_us

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
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.getColor
import com.example.bloodpressureapp.common.utils.getDrawable
import com.example.bloodpressureapp.databinding.DialogRateUsBinding
import com.example.bloodpressureapp.viewmodel.AppViewModel

interface RateCallBack {
    fun rateOnStore()
}

class DialogRateUs(private val callBack: RateCallBack) : DialogFragment() {
    private lateinit var binding: DialogRateUsBinding
    private val viewModel: AppViewModel by activityViewModels()
    private var star = 0
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
        binding = DialogRateUsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        initFirst()
        initButton()

    }

    private fun initButton() {
        binding.btnRate.setOnClickListener {
            if (star == 0) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_rate),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (star < 5) {
                viewModel.userActionRate = true
                dismiss()
            } else if (star == 5) {
                callBack.rateOnStore()
                dismiss()
            }
        }

        binding.btnLater.setOnClickListener {
            viewModel.userActionRate = true
            dismiss()
        }

    }

    private fun initFirst() {
        binding.root.setOnClickListener {
            dismiss()
        }
        binding.containerMain.setOnClickListener(null)

        val groupImageStatus = listOf(
            R.drawable.ic_status_1,
            R.drawable.ic_status_2,
            R.drawable.ic_status_3,
            R.drawable.ic_status_4,
            R.drawable.ic_status_5,
        )
        val groupStar =
            listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)

        val textExpressive = listOf(
            R.string.expressive_bad,
            R.string.expressive_bad_a_little,
            R.string.expressive_normal,
            R.string.expressive_good,
            R.string.expressive_best
        )
        groupStar.forEachIndexed { index, item ->
            kotlin.run {
                item.setOnClickListener {
                    star = index + 1
                    binding.btnLater.visibility = View.GONE
                    binding.tvStatus2.text = getString(textExpressive[index])
                    binding.imvStatus.setImageResource(groupImageStatus[index])
                    when (star) {
                        in 1..3 -> {
                            binding.tvStatus1.text = getString(R.string.oh_no)
                            binding.btnRate.text = getString(R.string.rate_us_button)
                        }
                        4 -> {
                            binding.tvStatus1.text = getString(R.string.so_amazing)
                            binding.btnRate.text = getString(R.string.rate_us_button)
                        }
                        else -> {
                            binding.tvStatus1.text = getString(R.string.we_like_you_too)
                            binding.btnRate.text = getString(R.string.rate_on_google_play)
                        }
                    }
                    binding.tvStatus1.visibility = View.VISIBLE

                    val subStar = groupStar.slice(0..index)
                    if (index < groupStar.size - 1) {
                        val subStarInActive = groupStar.slice(index + 1..4)
                        subStarInActive.forEachIndexed { _, item ->
                            kotlin.run {
                                if (item.id != R.id.star_5) {
                                    item.setImageDrawable(
                                        getDrawable(
                                            R.drawable.ic_start_inactive
                                        )
                                    )
                                } else {
                                    item.setImageDrawable(
                                        getDrawable(
                                            R.drawable.ic_last_star_inactive
                                        )
                                    )
                                }

                            }
                        }
                    }
                    subStar.forEachIndexed { index, item ->
                        kotlin.run {
                            if (index == 4) {
                                item.setImageDrawable(
                                    getDrawable(
                                        R.drawable.ic_last_star_active
                                    )
                                )
                            } else {
                                item.setImageDrawable(
                                    getDrawable(
                                        R.drawable.ic_star_active
                                    )
                                )
                            }

                        }
                    }
                }
            }
        }
    }

}