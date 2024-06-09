package com.example.lucky_note


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDao {
    @Query("SELECT content FROM memo_table LIMIT 1")
    fun getMemo1(): String?

    @Insert
    fun insertMemo(memo: Memo)
}