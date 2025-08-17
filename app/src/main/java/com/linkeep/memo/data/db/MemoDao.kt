package com.linkeep.memo.data.db

import androidx.room.*
import com.linkeep.memo.data.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM memos ORDER BY createdAt DESC")
    fun getAllMemos(): Flow<List<Memo>>

    @Query("SELECT * FROM memos WHERE id = :id")
    suspend fun getMemoById(id: Long): Memo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo): Long

    @Update
    suspend fun updateMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("SELECT * FROM memos WHERE category = :category ORDER BY createdAt DESC")
    fun getMemosByCategory(category: String): Flow<List<Memo>>
} 