package com.gauravnadar.notes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gauravnadar.notes.database.NoteDao

class NoteViewModelFactory(private val dataSource: NoteDao,
                           private val application: Application
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }


}























