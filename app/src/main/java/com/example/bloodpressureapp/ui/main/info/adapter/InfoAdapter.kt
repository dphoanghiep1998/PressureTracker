package com.example.bloodpressureapp.ui.main.info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressureapp.databinding.LayoutInfoBinding
import com.example.bloodpressureapp.ui.main.info.DataInfoModel

interface ItemTouchListener {
    fun onClickItem(position: Int)
}

class InfoAdapter(
    private val list: MutableList<DataInfoModel>,
    private val listener: ItemTouchListener
) :
    RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(val binding: LayoutInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = LayoutInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.imvIcon.setImageResource(this.imgId)
                binding.tvTitle.text = this.title
                binding.tvTitle.setTextColor(this.color)
                binding.root.setOnClickListener {
                    listener.onClickItem(adapterPosition)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}