package com.jcu.focusgarden.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Session Entity
 * 按照 TD 文档第 5 节定义
 * 
 * 存储每次专注会话的记录
 */
@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * 类别：Academic（学术）或 Personal（个人）
     */
    val category: String,
    
    /**
     * 持续时间（分钟）
     */
    val duration: Int,
    
    /**
     * 开始时间（时间戳）
     */
    val startTime: Long,
    
    /**
     * 结束时间（时间戳）
     */
    val endTime: Long,
    
    /**
     * 日期（格式：yyyy-MM-dd）
     */
    val date: String
)





