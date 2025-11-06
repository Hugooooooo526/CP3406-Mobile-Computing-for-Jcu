package com.jcu.focusgarden.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jcu.focusgarden.data.local.converter.StringListConverter

/**
 * Group Entity
 * 按照 TD 文档第 5 节定义
 * 
 * 存储 Heist Group 的信息（3-5人小组）
 */
@Entity(tableName = "groups")
@TypeConverters(StringListConverter::class)
data class GroupEntity(
    @PrimaryKey
    val groupId: String,
    
    /**
     * 小组名称
     */
    val name: String,
    
    /**
     * 成员列表（用户名或 ID）
     */
    val members: List<String>,
    
    /**
     * 连续天数
     */
    val streak: Int,
    
    /**
     * 小组目标描述
     */
    val goal: String
)





