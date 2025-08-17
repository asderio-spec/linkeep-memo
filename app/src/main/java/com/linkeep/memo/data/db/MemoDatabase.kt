package com.linkeep.memo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linkeep.memo.data.model.Memo

@Database(entities = [Memo::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
} 