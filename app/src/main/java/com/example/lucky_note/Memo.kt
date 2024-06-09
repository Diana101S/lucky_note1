package com.example.lucky_note


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String
)