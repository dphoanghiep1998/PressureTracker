package com.example.bloodpressureapp.ui.main.tracker.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bloodpressureapp.ui.main.tracker.model.HistoryModel

class DiffCallback(
    private val oldList: List<HistoryModel>,
    var newList: List<HistoryModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}