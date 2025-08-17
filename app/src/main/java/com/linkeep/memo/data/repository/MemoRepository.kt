package com.linkeep.memo.data.repository

import com.linkeep.memo.data.db.MemoDao
import com.linkeep.memo.data.model.Memo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoRepository @Inject constructor(
    private val memoDao: MemoDao
) {
    fun getAllMemos(): Flow<List<Memo>> = memoDao.getAllMemos()

    suspend fun getMemoById(id: Long): Memo? = memoDao.getMemoById(id)

    suspend fun insertMemo(memo: Memo): Long = memoDao.insertMemo(memo)

    suspend fun updateMemo(memo: Memo) = memoDao.updateMemo(memo)

    suspend fun deleteMemo(memo: Memo) = memoDao.deleteMemo(memo)

    fun getMemosByCategory(category: String): Flow<List<Memo>> = 
        memoDao.getMemosByCategory(category)
} 