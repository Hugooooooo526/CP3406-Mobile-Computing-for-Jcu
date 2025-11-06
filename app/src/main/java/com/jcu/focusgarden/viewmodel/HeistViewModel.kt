package com.jcu.focusgarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcu.focusgarden.data.repository.GroupRepository
import com.jcu.focusgarden.ui.screens.MemberProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Heist Group ViewModel
 * 按照 TD 文档 MVVM 架构实现
 * 
 * Week 3-4: 基础结构
 * Week 7-8: 将实现小组功能和数据同步
 */
class HeistViewModel(
    private val groupRepository: GroupRepository
) : ViewModel() {
    
    // Group State
    private val _groupGoal = MutableStateFlow("Complete 8 Pomodoros Today")
    val groupGoal: StateFlow<String> = _groupGoal.asStateFlow()
    
    private val _groupStreak = MutableStateFlow(3)
    val groupStreak: StateFlow<Int> = _groupStreak.asStateFlow()
    
    private val _members = MutableStateFlow<List<MemberProgress>>(emptyList())
    val members: StateFlow<List<MemberProgress>> = _members.asStateFlow()
    
    init {
        // TODO: Week 7-8 - 从数据库加载小组数据
        loadGroupData()
    }
    
    /**
     * 加载小组数据
     * Week 7-8 将从数据库加载真实数据
     */
    private fun loadGroupData() {
        viewModelScope.launch {
            // 模拟数据（Week 3-4 静态 UI）
            _members.value = listOf(
                MemberProgress("Alex", 60, 0.6f, "AM"),
                MemberProgress("Sara", 45, 0.45f, "SM"),
                MemberProgress("John", 90, 0.9f, "JD"),
                MemberProgress("Emma", 75, 0.75f, "EW")
            )
        }
    }
    
    /**
     * 邀请新成员
     */
    fun inviteMember(memberName: String) {
        viewModelScope.launch {
            // TODO: Week 7-8 - 实现邀请逻辑
        }
    }
    
    /**
     * 给成员点赞
     */
    fun thumbsUpMember(memberName: String) {
        viewModelScope.launch {
            // TODO: Week 7-8 - 实现点赞逻辑
        }
    }
    
    /**
     * 刷新小组数据
     */
    fun refreshGroupData() {
        loadGroupData()
    }
}





