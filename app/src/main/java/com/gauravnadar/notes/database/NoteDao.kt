package com.gauravnadar.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao{

    @Insert
    fun addNote(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * from notes_table WHERE id = :id")
    fun get(id: Int): LiveData<Note>

    @Query("DELETE FROM notes_table")
    fun clear()

    @Delete
    fun deleteSingleNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

}