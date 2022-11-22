package com.example.bloodpressureapp.dialog.addnotedialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.databinding.ItemNoteBinding


interface ItemTouchListener {
    fun onTouchItem(listNote: List<String>)
}

class NoteAdapter(private val listener: ItemTouchListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    private var noteList: List<String> = mutableListOf()
    private var selectedNotes: HashSet<String> = hashSetOf()

    fun setData(list: List<String>) {
        this.noteList = list
        notifyDataSetChanged()
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

                binding.root.setOnClickListener {
                    if (this in selectedNotes) {
                        selectedNotes.remove(this)
                    } else {
                        selectedNotes.add(this)
                    }
                    notifyItemChanged(position)
                    listener.onTouchItem(selectedNotes.toList())


                }
            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}