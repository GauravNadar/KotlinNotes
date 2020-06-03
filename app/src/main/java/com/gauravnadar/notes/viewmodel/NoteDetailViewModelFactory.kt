package com.gauravnadar.notes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gauravnadar.notes.database.NoteDao

class NoteDetailViewModelFactory( private val noteId: Int, private val dataSource: NoteDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(NoteDetailViewModel::class.java)){
            return NoteDetailViewModel(noteId, dataSource) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }


}