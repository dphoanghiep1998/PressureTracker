package com.example.bloodpressureapp.dialog.addnotedialog.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.ItemNoteBinding


interface ItemTouchListener {
    fun onTouchItem(listNote: MutableList<String>)
    fun onDeleteItem(note:String)
}

class NoteAdapter(private val listener: ItemTouchListener, private val showDelete: Boolean = false) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    private var noteList: MutableList<String> = mutableListOf()
    private var selectedNotes: MutableList<String> = mutableListOf()
    private val diffCallback = DiffCallback(noteList, mutableListOf())
    private val diffCallBackSelected = DiffCallback(selectedNotes, mutableListOf())
    fun setData(newList: MutableList<String>) {
        diffCallback.newList = newList.toMutableList()
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        noteList.clear()
        noteList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setSelectedNoteList(newList:MutableList<String>){
        diffCallBackSelected.newList = newList.toMutableList()
        val diffResult = DiffUtil.calculateDiff(diffCallBackSelected)
        selectedNotes.clear()
        selectedNotes.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder) {
            with(noteList[position]) {
                if (this in selectedNotes) {
                    binding.root.setBackgroundResource(R.drawable.bg_secondary02_corner50)
                } else {
                    binding.root.setBackgroundResource(R.drawable.bg_neutral01_corner50)
                }
                binding.tvNote.text = this

                if (showDelete) {
                    binding.btnClose.visibility = View.VISIBLE
                    binding.btnClose.setOnClickListener {
                        listener.onDeleteItem(this)
                    }
                    binding.root.setOnClickListener {
                    }

                } else {
                    binding.btnClose.visibility = View.GONE
                    binding.root.setOnClickListener {
                        if (this in selectedNotes) {
                            selectedNotes.remove(this)
                        } else {
                            selectedNotes.add(this)
                        }
                        listener.onTouchItem(selectedNotes)
                        notifyItemChanged(adapterPosition)
                    }
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

}