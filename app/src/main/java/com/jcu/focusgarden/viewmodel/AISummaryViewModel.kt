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
 * AI Summary ViewModel
 * 按照 TD 文档 MVVM 架构实现
 * 
 * Week 3-4: 基础结构
 * Week 9: 将实现 AI 总结生成逻辑（本地算法）
 */
class AISummaryViewModel(
    private val sessionRepository: SessionRepository,
    private val journalRepository: JournalRepository
) : ViewModel() {
    
    // Summary State
    private val _totalFocusTime = MutableStateFlow(540)
    val totalFocusTime: StateFlow<Int> = _totalFocusTime.asStateFlow()
    
    private val _averagePerDay = MutableStateFlow(77)
    val averagePerDay: StateFlow<Int> = _averagePerDay.asStateFlow()
    
    private val _longestStreak = MutableStateFlow(5)
    val longestStreak: StateFlow<Int> = _longestStreak.asStateFlow()
    
    private val _peakDay = MutableStateFlow("Wednesday")
    val peakDay: StateFlow<String> = _peakDay.asStateFlow()
    
    private val _productivityTrend = MutableStateFlow(listOf(45f, 60f, 75f, 90f, 85f, 70f, 55f))
    val productivityTrend: StateFlow<List<Float>> = _productivityTrend.asStateFlow()
    
    private val _recommendations = MutableStateFlow<List<String>>(emptyList())
    val recommendations: StateFlow<List<String>> = _recommendations.asStateFlow()
    
    init {
        // TODO: Week 9 - 从数据库加载并生成 AI 总结
        generateSummary()
    }
    
    /**
     * 生成周总结
     * Week 9 将实现真实的 AI 总结算法（参考 TD 文档第 6 节）
     */
    private fun generateSummary() {
        viewModelScope.launch {
            // 模拟数据（Week 3-4 静态 UI）
            _totalFocusTime.value = 540
            _averagePerDay.value = 77
            _longestStreak.value = 5
            _peakDay.value = "Wednesday"
            _productivityTrend.value = listOf(45f, 60f, 75f, 90f, 85f, 70f, 55f)
            
            // 模拟建议
            _recommendations.value = listOf(
                "Schedule morning sessions for better focus",
                "Try shorter breaks on Wednesday",
                "Maintain weekend rest balance"
            )
        }
    }
    
    /**
     * 生成月度报告
     */
    fun generateMonthlyReport() {
        viewModelScope.launch {
            // TODO: Week 9 - 实现月度报告生成（PDF 导出）
        }
    }
    
    /**
     * 刷新总结
     */
    fun refreshSummary() {
        generateSummary()
    }
}





