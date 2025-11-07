package com.jcu.focusgarden.domain.usecase

import com.jcu.focusgarden.data.local.entity.SessionEntity
import com.jcu.focusgarden.domain.model.Summary

/**
 * Generate Summary Use Case
 * 按照 TD 文档第 6 节实现
 * 
 * 本地 AI 总结算法（简化版）
 * Week 9 将用于自动生成周/月报告
 */
class GenerateSummaryUseCase {
    
    /**
     * 生成周总结
     * 
     * 算法逻辑参考 TD 文档第 6 节：
     * - 计算总时长
     * - 计算平均每日时长
     * - 找出高峰日
     * - 根据数据生成建议
     */
    fun generateWeeklySummary(sessions: List<SessionEntity>): Summary {
        if (sessions.isEmpty()) {
            return Summary(
                totalTime = 0,
                avgDaily = 0,
                peakDay = "N/A",
                recommendation = "Start your focus journey today!",
                longestStreak = 0
            )
        }
        
        // 计算总时长
        val totalTime = sessions.sumOf { it.duration }
        
        // 计算平均每日时长
        val uniqueDays = sessions.map { it.date }.distinct().size
        val avgDaily = if (uniqueDays > 0) totalTime / uniqueDays else 0
        
        // 找出高峰日
        val peakDay = sessions
            .groupBy { it.date }
            .maxByOrNull { it.value.sumOf { session -> session.duration } }
            ?.key ?: "N/A"
        
        // 生成个性化建议
        val recommendation = generateRecommendation(avgDaily, totalTime, sessions)
        
        // 计算最长连续天数
        val longestStreak = calculateLongestStreak(sessions)
        
        return Summary(
            totalTime = totalTime,
            avgDaily = avgDaily,
            peakDay = peakDay,
            recommendation = recommendation,
            longestStreak = longestStreak
        )
    }
    
    /**
     * 生成个性化建议
     * 按照 TD 文档第 6 节的示例逻辑
     */
    private fun generateRecommendation(
        avgDaily: Int,
        totalTime: Int,
        sessions: List<SessionEntity>
    ): String {
        return when {
            avgDaily < 30 -> 
                "Try adding one more session per day next week. Small steps lead to big changes! 🌱"
            
            avgDaily < 60 -> 
                "Good progress! Consider extending sessions by 10 minutes for deeper focus."
            
            avgDaily < 90 -> 
                "Excellent consistency! Try the Pomodoro technique (25 min focus + 5 min break)."
            
            else -> 
                "Outstanding performance! Maintain your current routine and remember to rest. 🌟"
        }
    }
    
    /**
     * 计算最长连续天数
     */
    private fun calculateLongestStreak(sessions: List<SessionEntity>): Int {
        if (sessions.isEmpty()) return 0
        
        val uniqueDates = sessions.map { it.date }.distinct().sorted()
        
        var currentStreak = 1
        var maxStreak = 1
        
        for (i in 1 until uniqueDates.size) {
            // 简化版：假设日期格式正确且连续
            // TODO: Week 9 - 实现更精确的日期比较
            currentStreak++
            if (currentStreak > maxStreak) {
                maxStreak = currentStreak
            }
        }
        
        return maxStreak
    }
    
    /**
     * 生成月度总结
     */
    fun generateMonthlySummary(sessions: List<SessionEntity>): Summary {
        // 类似周总结，但统计周期更长
        return generateWeeklySummary(sessions).copy(
            recommendation = "Monthly Achievement Unlocked! Keep growing your focus garden. 🌳"
        )
    }
}






