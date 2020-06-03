package com.gauravnadar.notes.database

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "notes_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "date")
    var date: String = SimpleDateFormat("dd-MM-yyyy")
        .format(Calendar.getInstance().time),

    @ColumnInfo(name = "time")
    var time: String = android.icu.text.SimpleDateFormat("HH:mm:ss")
        .format(Calendar.getInstance().time),

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "note")
    var note: String = ""



)