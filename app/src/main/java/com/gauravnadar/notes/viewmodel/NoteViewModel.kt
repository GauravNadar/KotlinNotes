package com.gauravnadar.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gauravnadar.notes.database.Note
import com.gauravnadar.notes.database.NoteDao
import kotlinx.coroutines.*

class NoteViewModel(val database: NoteDao,  application: Application): AndroidViewModel(application) {

    val title = MutableLiveData<String>()
    val note = MutableLiveData<String>()

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = database.getAllNotes()

    val _noteAdded = MutableLiveData<Boolean>()

    //Add Note to Database
    fun addNoteToDatabase() {

        onNoteEntered()

        uiScope.launch {
            val newNote = Note(title = title.value.toString(), note = note.value.toString())
            insert(newNote)
            _noteAdded.value = false
            _navigateToHomeFragment.value = 1
        }
    }

    private suspend fun insert(note: Note) {

        withContext(Dispatchers.IO) {
            database.addNote(note)
        } }


    //Delete a single note
    fun deleteSingleNote(note: Note) {
        uiScope.launch {
            deleteSingle(note)
        }
    }

    private suspend fun deleteSingle(note:Note)
    {
        withContext(Dispatchers.IO) {
            database.deleteSingleNote(note)
        }
    }


    //Delete all Notes
    private suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun deletefull(){
        uiScope.launch {
            deleteAll()
        }
    }





    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }


    private val _navigateToDetailFragment = MutableLiveData<Int>()
    val navigateToDetailFragment get() = _navigateToDetailFragment

    private val _navigateToHomeFragment = MutableLiveData<Int>()
    val navigateToHomeFragment get() = _navigateToHomeFragment


    fun onNoteClicked(id: Int){

        _navigateToDetailFragment.value = id
    }

    fun onDetailFragmentNavigated(){

        _navigateToDetailFragment.value = null
    }

    fun onNoteEntered(){

    }

    fun onHomeScreen(){
        _navigateToHomeFragment.value = null
    }
}