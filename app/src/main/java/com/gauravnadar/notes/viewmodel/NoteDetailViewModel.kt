package com.gauravnadar.notes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gauravnadar.notes.database.Note
import com.gauravnadar.notes.database.NoteDao
import kotlinx.coroutines.*

class NoteDetailViewModel(private val noteId: Int=0, dataSource: NoteDao): ViewModel() {

    val database = dataSource
    private val viewModelJob = Job()
    val update_flag = MutableLiveData<Int>()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val note_detail = database.get(noteId)

    fun updateNote(){

        uiScope.launch {
            val updatedNote = Note(id=noteId, title = note_detail.value!!.title, note = note_detail.value!!.note)
            update(updatedNote)
            update_flag.value = 1
        }
    }


    private suspend fun update(note: Note){
        withContext(Dispatchers.IO){
            database.update(note)
        }
    }
    

}