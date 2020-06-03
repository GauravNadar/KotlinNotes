package com.gauravnadar.notes


import androidx.room.Room
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gauravnadar.notes.database.Note
import com.gauravnadar.notes.database.NoteDao
import com.gauravnadar.notes.database.NoteDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {
//
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase
//
    @Before
    fun createDb() {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
//                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        noteDao = db.noteDao
    }
//
    @After
    @Throws(IOException::class)
        fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNotes() {
        val note = Note()
        noteDao.addNote(note)
        val single_note = noteDao.get(3)
       assertEquals(single_note?.id, null)
    }
}