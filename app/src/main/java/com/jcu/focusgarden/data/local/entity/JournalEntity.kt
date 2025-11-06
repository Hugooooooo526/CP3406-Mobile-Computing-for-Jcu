package com.jcu.focusgarden.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

/**
 * Journal Entity
 * 按照 TD 文档第 5 节定义
 * 
 * 存储每次专注会话后的反思记录
 */
@Entity(
    tableName = "journals",
    foreignKeys = [
        ForeignKey(
            entity = SessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JournalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /**
     * 关联的会话 ID
     */
    val sessionId: Int,
    
    /**
     * 心情（emoji 字符串：😀, 🙂, 😐, 🙁）
     */
    val mood: String,
    
    /**
     * 学习笔记
     */
    val note: String,
    
    /**
     * 日期（格式：yyyy-MM-dd）
     */
    val date: String
)





