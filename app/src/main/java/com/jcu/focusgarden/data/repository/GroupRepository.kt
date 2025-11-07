package com.jcu.focusgarden.data.repository

import com.jcu.focusgarden.data.local.dao.GroupDao
import com.jcu.focusgarden.data.local.entity.GroupEntity
import kotlinx.coroutines.flow.Flow

/**
 * Group Repository
 * 按照 TD 文档 MVVM 架构实现
 * 
 * 提供 Heist Group 数据的业务逻辑层抽象
 */
class GroupRepository(
    private val groupDao: GroupDao
) {
    
    /**
     * 获取所有小组
     */
    fun getAllGroups(): Flow<List<GroupEntity>> {
        return groupDao.getAllGroups()
    }
    
    /**
     * 根据 ID 获取小组
     */
    suspend fun getGroupById(groupId: String): GroupEntity? {
        return groupDao.getGroupById(groupId)
    }
    
    /**
     * 根据 ID 获取小组（Flow）
     */
    fun getGroupByIdFlow(groupId: String): Flow<GroupEntity?> {
        return groupDao.getGroupByIdFlow(groupId)
    }
    
    /**
     * 插入新小组
     */
    suspend fun insertGroup(group: GroupEntity) {
        groupDao.insertGroup(group)
    }
    
    /**
     * 更新小组信息
     */
    suspend fun updateGroup(group: GroupEntity) {
        groupDao.updateGroup(group)
    }
    
    /**
     * 删除小组
     */
    suspend fun deleteGroup(group: GroupEntity) {
        groupDao.deleteGroup(group)
    }
}






