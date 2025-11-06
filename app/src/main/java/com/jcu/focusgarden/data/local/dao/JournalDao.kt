package com.jcu.focusgarden.data.local.dao

import androidx.room.*
import com.jcu.focusgarden.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

/**
 * Journal DAO (Data Access Object)
 * 提供 Journal 数据的增删改查操作
 */
@Dao
interface JournalDao {
    
    /**
     * 插入新的反思记录
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalEntity): Long
    
    /**
     * 更新反思记录
     */
    @Update
    suspend fun updateJournal(journal: JournalEntity)
    
    /**
     * 删除反思记录
     */
    @Delete
    suspend fun deleteJournal(journal: JournalEntity)
    
    /**
     * 获取所有反思记录
     */
    @Query("SELECT * FROM journals ORDER BY date DESC")
    fun getAllJournals(): Flow<List<JournalEntity>>
    
    /**
     * 根据会话 ID 获取反思记录
     */
    @Query("SELECT * FROM journals WHERE sessionId = :sessionId")
    suspend fun getJournalBySessionId(sessionId: Int): JournalEntity?
    
    /**
     * 根据日期获取反思记录
     */
    @Query("SELECT * FROM journals WHERE date = :date ORDER BY id DESC")
    fun getJournalsByDate(date: String): Flow<List<JournalEntity>>
    
    /**
     * 获取某个日期范围内的反思记录
     */
    @Query("SELECT * FROM journals WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getJournalsByDateRange(startDate: String, endDate: String): Flow<List<JournalEntity>>
    
    /**
     * 获取最近的心情趋势
     */
    @Query("SELECT mood FROM journals ORDER BY date DESC LIMIT :limit")
    suspend fun getRecentMoods(limit: Int = 7): List<String>
}





