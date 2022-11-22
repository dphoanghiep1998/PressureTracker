package com.example.bloodpressureapp.ui.main.tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressureapp.databinding.ItemHistoryBinding
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var mList: List<HistoryModel> = mutableListOf()
    fun setData(list: MutableList<HistoryModel>) {
        this.mList = list
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
                binding.tvSystolicValue.text = this.systolic.toString()
                binding.tvDiastolicValue.text = this.diastolic.toString()
                binding.tvTime.text = this.time.toString()
                binding.tvStatus.text = this.status
                binding.tvPulseValue.text = this.pulse.toString()

            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}