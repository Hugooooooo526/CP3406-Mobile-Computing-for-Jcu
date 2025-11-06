package com.jcu.focusgarden.data.repository

import com.jcu.focusgarden.data.local.dao.JournalDao
import com.jcu.focusgarden.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

/**
 * Journal Repository
 * 按照 TD 文档 MVVM 架构实现
 * 
 * 提供 Journal 数据的业务逻辑层抽象
 */
class JournalRepository(
    private val journalDao: JournalDao
) {
    
    /**
     * 获取所有反思记录
     */
    fun getAllJournals(): Flow<List<JournalEntity>> {
        return journalDao.getAllJournals()
    }
    
    /**
     * 根据日期获取反思记录
     */
    fun getJournalsByDate(date: String): Flow<List<JournalEntity>> {
        return journalDao.getJournalsByDate(date)
    }
    
    /**
     * 根据会话 ID 获取反思记录
     */
    suspend fun getJournalBySessionId(sessionId: Int): JournalEntity? {
        return journalDao.getJournalBySessionId(sessionId)
    }
    
    /**
     * 插入新反思记录
     */
    suspend fun insertJournal(journal: JournalEntity): Long {
        return journalDao.insertJournal(journal)
    }
    
    /**
     * 更新反思记录
     */
    suspend fun updateJournal(journal: JournalEntity) {
        journalDao.updateJournal(journal)
    }
    
    /**
     * 删除反思记录
     */
    suspend fun deleteJournal(journal: JournalEntity) {
        journalDao.deleteJournal(journal)
    }
    
    /**
     * 获取最近的心情趋势
     */
    suspend fun getRecentMoods(limit: Int = 7): List<String> {
        return journalDao.getRecentMoods(limit)
    }
}





