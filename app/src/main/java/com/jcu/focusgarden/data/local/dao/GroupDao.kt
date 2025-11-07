package com.jcu.focusgarden.data.local.dao

import androidx.room.*
import com.jcu.focusgarden.data.local.entity.GroupEntity
import kotlinx.coroutines.flow.Flow

/**
 * Group DAO (Data Access Object)
 * 提供 Heist Group 数据的增删改查操作
 */
@Dao
interface GroupDao {
    
    /**
     * 插入新的小组
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupEntity)
    
    /**
     * 更新小组信息
     */
    @Update
    suspend fun updateGroup(group: GroupEntity)
    
    /**
     * 删除小组
     */
    @Delete
    suspend fun deleteGroup(group: GroupEntity)
    
    /**
     * 获取所有小组
     */
    @Query("SELECT * FROM groups ORDER BY streak DESC")
    fun getAllGroups(): Flow<List<GroupEntity>>
    
    /**
     * 根据 ID 获取小组
     */
    @Query("SELECT * FROM groups WHERE groupId = :groupId")
    suspend fun getGroupById(groupId: String): GroupEntity?
    
    /**
     * 根据 ID 获取小组（Flow）
     */
    @Query("SELECT * FROM groups WHERE groupId = :groupId")
    fun getGroupByIdFlow(groupId: String): Flow<GroupEntity?>
}






