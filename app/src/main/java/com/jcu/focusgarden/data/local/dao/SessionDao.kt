package com.jcu.focusgarden.data.local.dao

import androidx.room.*
import com.jcu.focusgarden.data.local.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Session DAO (Data Access Object)
 * 提供 Session 数据的增删改查操作
 */
@Dao
interface SessionDao {
    
    /**
     * 插入新的专注会话
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity): Long
    
    /**
     * 更新专注会话
     */
    @Update
    suspend fun updateSession(session: SessionEntity)
    
    /**
     * 删除专注会话
     */
    @Delete
    suspend fun deleteSession(session: SessionEntity)
    
    /**
     * 获取所有会话（Flow 自动更新）
     */
    @Query("SELECT * FROM sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<SessionEntity>>
    
    /**
     * 根据日期获取会话
     */
    @Query("SELECT * FROM sessions WHERE date = :date ORDER BY startTime DESC")
    fun getSessionsByDate(date: String): Flow<List<SessionEntity>>
    
    /**
     * 获取某个日期范围内的会话
     */
    @Query("SELECT * FROM sessions WHERE date BETWEEN :startDate AND :endDate ORDER BY startTime DESC")
    fun getSessionsByDateRange(startDate: String, endDate: String): Flow<List<SessionEntity>>
    
    /**
     * 获取某个类别的所有会话
     */
    @Query("SELECT * FROM sessions WHERE category = :category ORDER BY startTime DESC")
    fun getSessionsByCategory(category: String): Flow<List<SessionEntity>>
    
    /**
     * 获取今日总专注时长（分钟）
     */
    @Query("SELECT SUM(duration) FROM sessions WHERE date = :date")
    suspend fun getTodayTotalDuration(date: String): Int?
    
    /**
     * 获取本周每天的专注时长
     */
    @Query("SELECT date, SUM(duration) as total FROM sessions WHERE date BETWEEN :startDate AND :endDate GROUP BY date ORDER BY date")
    suspend fun getWeeklyDurations(startDate: String, endDate: String): List<DailyDuration>
}

/**
 * 每日时长数据类
 */
data class DailyDuration(
    val date: String,
    val total: Int
)





