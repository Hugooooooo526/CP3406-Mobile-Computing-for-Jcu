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
 * Timer ViewModel
 * 按照 TD 文档 MVVM 架构实现
 * 
 * Week 3-4: 基础结构
 * Week 5-6: 将实现完整的计时器逻辑和数据持久化
 */
class TimerViewModel(
    private val sessionRepository: SessionRepository,
    private val journalRepository: JournalRepository
) : ViewModel() {
    
    // Timer State
    private val _remainingSeconds = MutableStateFlow(25 * 60) // 25分钟
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()
    
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()
    
    private val _showReflectionDialog = MutableStateFlow(false)
    val showReflectionDialog: StateFlow<Boolean> = _showReflectionDialog.asStateFlow()
    
    /**
     * 开始/暂停计时器
     * Week 5-6 将实现真实的倒计时逻辑
     */
    fun toggleTimer() {
        _isRunning.value = !_isRunning.value
        
        // TODO: Week 5-6 - 实现 CountDownTimer 或 Flow-based timer
    }
    
    /**
     * 重置计时器
     */
    fun resetTimer() {
        _remainingSeconds.value = 25 * 60
        _isRunning.value = false
    }
    
    /**
     * 完成会话
     * Week 5-6 将保存到数据库
     */
    fun completeSession() {
        _showReflectionDialog.value = true
        _isRunning.value = false
        
        // TODO: Week 5-6 - 保存会话到数据库
    }
    
    /**
     * 保存反思记录
     */
    fun saveReflection(mood: String, note: String) {
        viewModelScope.launch {
            // TODO: Week 5-6 - 保存到数据库
            _showReflectionDialog.value = false
        }
    }
    
    /**
     * 跳过反思
     */
    fun skipReflection() {
        _showReflectionDialog.value = false
    }
}





