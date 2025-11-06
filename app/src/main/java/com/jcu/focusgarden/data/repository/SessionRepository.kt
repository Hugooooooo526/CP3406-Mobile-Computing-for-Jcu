package com.jcu.focusgarden.data.repository

import com.jcu.focusgarden.data.local.dao.SessionDao
import com.jcu.focusgarden.data.local.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Session Repository
 * 按照 TD 文档 MVVM 架构实现
 * 
 * 提供 Session 数据的业务逻辑层抽象
 * Week 5-6 将用于 MVP 开发
 */
class SessionRepository(
    private val sessionDao: SessionDao
) {
    
    /**
     * 获取所有会话
     */
    fun getAllSessions(): Flow<List<SessionEntity>> {
        return sessionDao.getAllSessions()
    }
    
    /**
     * 根据日期获取会话
     */
    fun getSessionsByDate(date: String): Flow<List<SessionEntity>> {
        return sessionDao.getSessionsByDate(date)
    }
    
    /**
     * 获取某个日期范围内的会话
     */
    fun getSessionsByDateRange(startDate: String, endDate: String): Flow<List<SessionEntity>> {
        return sessionDao.getSessionsByDateRange(startDate, endDate)
    }
    
    /**
     * 插入新会话
     */
    suspend fun insertSession(session: SessionEntity): Long {
        return sessionDao.insertSession(session)
    }
    
    /**
     * 更新会话
     */
    suspend fun updateSession(session: SessionEntity) {
        sessionDao.updateSession(session)
    }
    
    /**
     * 删除会话
     */
    suspend fun deleteSession(session: SessionEntity) {
        sessionDao.deleteSession(session)
    }
    
    /**
     * 获取今日总专注时长
     */
    suspend fun getTodayTotalDuration(date: String): Int {
        return sessionDao.getTodayTotalDuration(date) ?: 0
    }
}





