package com.jcu.focusgarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.data.repository.JournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Dashboard ViewModel
 * 按照 TD 文档 MVVM 架构实现
 * 
 * Week 3-4: 基础结构
 * Week 5-6: ✅ Phase E - 实现完整的数据加载逻辑
 *   - E1: 今日专注时长
 *   - E2: Streak 计算
 *   - E3: 本周数据（7天柱状图）
 *   - E4: 学术/个人占比（饼图）
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
        loadDashboardData()
    }
    
    /**
     * 加载 Dashboard 所有数据
     * Week 5-6: ✅ Phase E 实现
     */
    private fun loadDashboardData() {
        viewModelScope.launch {
            loadTodayFocusTime()
            calculateStreak()
            loadWeeklyData()
            loadWorkloadBalance()
        }
    }
    
    /**
     * E1: 加载今日专注时长
     */
    private suspend fun loadTodayFocusTime() {
        val today = getTodayDateString()
        val totalMinutes = sessionRepository.getTodayTotalDuration(today)
        _todayFocusMinutes.value = totalMinutes
    }
    
    /**
     * E2: 计算连续打卡天数（Streak）
     * 算法：从今天开始往前推，检查连续的日期
     */
    private suspend fun calculateStreak() {
        val allDates = sessionRepository.getAllDistinctDates()
        if (allDates.isEmpty()) {
            _currentStreak.value = 0
            return
        }
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var streak = 0
        val calendar = Calendar.getInstance()
        
        // 从今天开始检查
        var checkDate = getTodayDateString()
        
        while (allDates.contains(checkDate)) {
            streak++
            // 往前推一天
            calendar.time = dateFormat.parse(checkDate) ?: break
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            checkDate = dateFormat.format(calendar.time)
        }
        
        _currentStreak.value = streak
    }
    
    /**
     * E3: 加载本周数据（7天柱状图）
     */
    private suspend fun loadWeeklyData() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val today = calendar.time
        
        // 获取本周的开始日期（周一）和结束日期（周日）
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val startDate = dateFormat.format(calendar.time)
        
        calendar.add(Calendar.DAY_OF_YEAR, 6)
        val endDate = dateFormat.format(calendar.time)
        
        // 查询本周数据
        val weeklyDurations = sessionRepository.getWeeklyDurations(startDate, endDate)
        
        // 构建完整的7天数据（包括0的日期）
        val weekData = mutableListOf<Int>()
        calendar.time = dateFormat.parse(startDate) ?: today
        
        for (i in 0..6) {
            val dateStr = dateFormat.format(calendar.time)
            val duration = weeklyDurations.find { it.date == dateStr }?.total ?: 0
            weekData.add(duration)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        
        _weeklyData.value = weekData
    }
    
    /**
     * E4: 加载学术/个人占比（饼图）
     */
    private suspend fun loadWorkloadBalance() {
        val categoryDurations = sessionRepository.getCategoryDurations()
        
        val academicTime = categoryDurations.find { it.category == "Academic" }?.total ?: 0
        val personalTime = categoryDurations.find { it.category == "Personal" }?.total ?: 0
        val totalTime = academicTime + personalTime
        
        if (totalTime > 0) {
            _academicPercentage.value = academicTime.toFloat() / totalTime
        } else {
            _academicPercentage.value = 0.5f // 默认50%
        }
    }
    
    /**
     * 刷新数据
     */
    fun refreshData() {
        loadDashboardData()
    }
    
    /**
     * 获取今日日期字符串（yyyy-MM-dd）
     */
    private fun getTodayDateString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}





