package com.gauravnadar.notes.util

import androidx.recyclerview.widget.RecyclerView
import com.gauravnadar.notes.database.Note
import com.gauravnadar.notes.databinding.SingleNoteItemBinding

class NotesViewHolder(val binding: SingleNoteItemBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(
        item: Note,
        clickListener: NoteListener
    ) {

        binding.note = item
        binding.nDate.text = item.date
        binding.nTime.text = item.time
        binding.nTitle.text = item.title

         binding.clicklistener = clickListener
    }

}