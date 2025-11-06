package com.jcu.focusgarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.data.repository.JournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Dashboard ViewModel
 * 按照 TD 文档 MVVM 架构实现
 * 
 * Week 3-4: 基础结构
 * Week 5-6: 将实现完整的业务逻辑
 */
class DashboardViewModel(
    private val sessionRepository: SessionRepository,
    private val journalRepository: JournalRepository
) : ViewModel() {
    
    // UI State
    private val _todayFocusMinutes = MutableStateFlow(0)
    val todayFocusMinutes: StateFlow<Int> = _todayFocusMinutes.asStateFlow()
    
    private val _currentStreak = MutableStateFlow(0)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()
    
    private val _weeklyData = MutableStateFlow(listOf(0, 0, 0, 0, 0, 0, 0))
    val weeklyData: StateFlow<List<Int>> = _weeklyData.asStateFlow()
    
    private val _academicPercentage = MutableStateFlow(0.6f)
    val academicPercentage: StateFlow<Float> = _academicPercentage.asStateFlow()
    
    init {
        // TODO: Week 5-6 - 从数据库加载真实数据
        loadDashboardData()
    }
    
    /**
     * 加载 Dashboard 数据
     * Week 5-6 将实现真实的数据加载逻辑
     */
    private fun loadDashboardData() {
        viewModelScope.launch {
            // TODO: 从 Repository 加载数据
            // 当前使用模拟数据（Week 3-4 静态 UI）
            _todayFocusMinutes.value = 75
            _currentStreak.value = 3
            _weeklyData.value = listOf(60, 45, 90, 120, 75, 30, 20)
            _academicPercentage.value = 0.6f
        }
    }
    
    /**
     * 刷新数据
     */
    fun refreshData() {
        loadDashboardData()
    }
}





