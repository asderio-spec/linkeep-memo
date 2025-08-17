package com.linkeep.memo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val link: String? = null,
    val category: String,
    val createdAt: Long = System.currentTimeMillis()
) 