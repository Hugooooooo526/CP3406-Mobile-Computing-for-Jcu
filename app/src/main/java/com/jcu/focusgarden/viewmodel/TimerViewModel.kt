package com.jcu.focusgarden.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcu.focusgarden.data.local.entity.SessionEntity
import com.jcu.focusgarden.data.local.entity.JournalEntity
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.data.repository.JournalRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Timer ViewModel
 * 按照 TD 文档 MVVM 架构实现
 * 
 * Week 3-4: 基础结构
 * Week 5-6: ✅ 实现完整的计时器逻辑和数据持久化
 * 
 * 实现决策：
 * - 使用 Kotlin Flow + delay 实现倒计时（前台）
 * - 不使用 Foreground Service（保持简单）
 * - Category 在 Journal 时选择
 * 
 * @author Hugo Cui (14706438)
 * @since Week 5-6
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
    
    // Countdown Job
    private var timerJob: Job? = null
    
    // Session tracking
    private var sessionStartTime: Long = 0L
    private var currentSessionId: Long = -1L
    
    /**
     * 开始/暂停计时器
     * Week 5-6: ✅ 实现完整的倒计时逻辑
     */
    fun toggleTimer() {
        if (_isRunning.value) {
            // 暂停
            _isRunning.value = false
            pauseTimer()
        } else {
            // 开始或恢复
            if (sessionStartTime == 0L) {
                // 第一次开始，记录开始时间
                sessionStartTime = System.currentTimeMillis()
            }
            _isRunning.value = true
            startCountdown()
        }
    }
    
    /**
     * 启动倒计时
     * 使用 Kotlin Flow + delay 实现（前台计时）
     */
    private fun startCountdown() {
        timerJob?.cancel() // 取消之前的 Job
        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0 && _isRunning.value) {
                delay(1000L) // 每秒更新
                _remainingSeconds.value -= 1
            }
            // 时间到，触发完成
            if (_remainingSeconds.value == 0) {
                onTimerComplete()
            }
        }
    }
    
    /**
     * 暂停计时器
     */
    private fun pauseTimer() {
        timerJob?.cancel()
    }
    
    /**
     * 重置计时器
     */
    fun resetTimer() {
        timerJob?.cancel()
        _remainingSeconds.value = 25 * 60
        _isRunning.value = false
        sessionStartTime = 0L
    }
    
    /**
     * 计时器完成回调
     * 自动显示反思对话框
     */
    private fun onTimerComplete() {
        _isRunning.value = false
        _showReflectionDialog.value = true
    }
    
    /**
     * 完成会话（手动触发）
     * Week 5-6: ✅ 保存到数据库（Phase B）
     */
    fun completeSession() {
        _showReflectionDialog.value = true
        _isRunning.value = false
        pauseTimer()
        
        // TODO: Phase B - 保存会话到数据库
    }
    
    /**
     * 保存反思记录
     * Week 5-6: ✅ Phase B + Phase D - 保存到数据库
     * 
     * @param category 专注类别（Academic/Personal）
     * @param mood 情绪标签
     * @param note 学习笔记
     */
    fun saveReflection(category: String, mood: String, note: String) {
        viewModelScope.launch {
            // Phase B: 保存 Session
            val endTime = System.currentTimeMillis()
            val actualDuration = ((endTime - sessionStartTime) / 1000 / 60).toInt() // 分钟
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date(sessionStartTime))
            
            val session = SessionEntity(
                category = category,
                duration = actualDuration,
                startTime = sessionStartTime,
                endTime = endTime,
                date = currentDate
            )
            
            // 插入 Session，返回 sessionId
            currentSessionId = sessionRepository.insertSession(session)
            
            // Phase D: 保存 Journal（如果有笔记或情绪）
            if (mood.isNotEmpty() || note.isNotEmpty()) {
                val journal = JournalEntity(
                    sessionId = currentSessionId.toInt(),
                    mood = mood,
                    note = note,
                    date = currentDate
                )
                journalRepository.insertJournal(journal)
            }
            
            _showReflectionDialog.value = false
            resetTimer()
        }
    }
    
    /**
     * 跳过反思
     * Week 5-6: ✅ Phase B - 只保存 Session，不保存 Journal
     */
    fun skipReflection() {
        viewModelScope.launch {
            // Phase B: 保存 Session（不保存 Journal）
            val endTime = System.currentTimeMillis()
            val actualDuration = ((endTime - sessionStartTime) / 1000 / 60).toInt() // 分钟
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate = dateFormat.format(Date(sessionStartTime))
            
            val session = SessionEntity(
                category = "Academic", // 默认类别（用户跳过了选择）
                duration = actualDuration,
                startTime = sessionStartTime,
                endTime = endTime,
                date = currentDate
            )
            
            // 插入 Session
            currentSessionId = sessionRepository.insertSession(session)
            
            _showReflectionDialog.value = false
            resetTimer()
        }
    }
    
    /**
     * 清理资源
     */
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}





