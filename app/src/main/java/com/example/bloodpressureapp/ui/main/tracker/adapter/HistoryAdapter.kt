package com.example.bloodpressureapp.ui.main.tracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.ItemHistoryBinding
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel

interface ItemHelper {
    fun onClickEdit(item: HistoryModel)
}

class HistoryAdapter(private val listener: ItemHelper) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var mList: MutableList<HistoryModel> = mutableListOf()
    private val diffCallback = DiffCallback(mList, mutableListOf())

    fun setData(list: MutableList<HistoryModel>) {
        diffCallback.newList = list
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList.clear()
        mList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        with(holder) {
            with(mList[position]) {
                binding.line.setBackgroundResource(
                    getColorFromStatus(
                        this.systolic,
                        this.diastolic
                    )
                )
                binding.tvSystolicValue.text = this.systolic.toString()
                binding.tvDiastolicValue.text = this.diastolic.toString()
                binding.tvTime.text = "${this.time}, ${this.date}"
                binding.tvStatus.text = this.status
                binding.tvPulseValue.text = "Pulse: ${this.pulse} BPM"
                binding.btnEdit.setOnClickListener {
                    listener.onClickEdit(this)
                }
                if (this.notes.isNotEmpty()) {
                    var notesString =""
                    this.notes.forEachIndexed { index, item ->
                        kotlin.run {
                            notesString += if (index < this.notes.size - 1) {
                                "#${item} "
                            } else {
                                "#${item}"
                            }
                        }

                    }
                    binding.tvNoteValue.text = notesString
                    binding.tvNoteValue.visibility = View.VISIBLE
                } else {
                    binding.tvNoteValue.visibility = View.GONE
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private fun getColorFromStatus(systolic: Int, diastolic: Int): Int {
        var resources = R.drawable.bg_cbp01_corner10


        if (systolic < 120 && diastolic < 80) {

            resources = R.drawable.bg_cbp01_corner10
        } else if (systolic in 120..129 && diastolic < 80) {

            resources = R.drawable.bg_cbp02_corner10
        } else if (systolic >= 180 || diastolic >= 120) {

            resources = R.drawable.bg_cbp05_corner10
        } else if (systolic >= 140 || diastolic >= 90) {

            resources = R.drawable.bg_cbp04_corner10
        } else if (systolic >= 130 || diastolic >= 80) {

            resources = R.drawable.bg_cbp03_corner10
        }
        return resources

    }
}