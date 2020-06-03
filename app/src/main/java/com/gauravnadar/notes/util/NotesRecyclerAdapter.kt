

package com.gauravnadar.notes.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.gauravnadar.notes.database.Note
import com.gauravnadar.notes.databinding.SingleNoteItemBinding

class NotesRecyclerAdapter(val clickListener: NoteListener) :
    ListAdapter<Note, NotesViewHolder>(NotesDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SingleNoteItemBinding.inflate(layoutInflater, parent, false)
        return NotesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.bind(getItem(position)!!, clickListener!!)

    }

    fun getNoteAt(position: Int): Note? {
        return getItem(position)
    }

}


class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}


class NoteListener(val clickListener: (noteId: Int) -> Unit) {
    fun onClick(note: Note) = clickListener(note.id)
}