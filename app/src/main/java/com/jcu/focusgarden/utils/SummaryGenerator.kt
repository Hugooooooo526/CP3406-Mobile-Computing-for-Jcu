package com.jcu.focusgarden.utils

import com.jcu.focusgarden.data.local.entity.SessionEntity
import com.jcu.focusgarden.data.model.Priority
import com.jcu.focusgarden.data.model.Recommendation
import com.jcu.focusgarden.data.model.WeeklySummary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Summary Generator Utility
 * Local AI logic for generating weekly insights and recommendations
 * 
 * Week 9: AI Summary Module
 * No external APIs required - pure Kotlin logic
 */
object SummaryGenerator {
    
    /**
     * Generate weekly summary from session data
     * Analyzes the last 7 days of focus sessions
     */
    fun generateWeeklySummary(
        allSessions: List<SessionEntity>,
        currentStreak: Int
    ): WeeklySummary {
        // Get last 7 days date range
        val last7Days = getLastNDays(7)
        
        // Filter sessions for the past week
        val weeklySessions = allSessions.filter { session ->
            session.date in last7Days
        }
        
        // Calculate total focus time (in minutes)
        val totalTime = weeklySessions.sumOf { it.duration }
        
        // Calculate average daily focus
        val avgDaily = if (last7Days.isNotEmpty()) totalTime / 7 else 0
        
        // Find peak day (day with most focus time)
        val peakDay = if (weeklySessions.isNotEmpty()) {
            weeklySessions
                .groupBy { it.date }
                .mapValues { entry -> entry.value.sumOf { it.duration } }
                .maxByOrNull { it.value }
                ?.key
                ?.let { dateStr ->
                    // Convert date string to day name
                    getDayName(dateStr)
                } ?: "N/A"
        } else {
            "N/A"
        }
        
        // Calculate category breakdown
        val academicTime = weeklySessions
            .filter { it.category == "Academic" }
            .sumOf { it.duration }
        
        val personalTime = weeklySessions
            .filter { it.category == "Personal" }
            .sumOf { it.duration }
        
        return WeeklySummary(
            totalFocusTime = totalTime,
            averageDailyFocus = avgDaily,
            peakDay = peakDay,
            totalSessions = weeklySessions.size,
            academicTime = academicTime,
            personalTime = personalTime,
            currentStreak = currentStreak
        )
    }
    
    /**
     * Generate actionable recommendations based on summary
     * Rule-based algorithm without ML
     */
    fun generateRecommendations(summary: WeeklySummary): List<Recommendation> {
        val recommendations = mutableListOf<Recommendation>()
        
        // Rule 1: Low average focus time
        if (summary.averageDailyFocus < 60) {
            recommendations.add(
                Recommendation(
                    message = "Try to add one more 25-min Pomodoro session each day. Small, consistent steps lead to big improvements! 🎯",
                    priority = Priority.HIGH,
                    actionable = true
                )
            )
        } else if (summary.averageDailyFocus < 90) {
            recommendations.add(
                Recommendation(
                    message = "Good progress! Aim for 90+ minutes daily to reach your full potential. You're almost there! 💪",
                    priority = Priority.MEDIUM,
                    actionable = true
                )
            )
        }
        
        // Rule 2: Workload balance analysis
        val totalCategorizedTime = summary.academicTime + summary.personalTime
        if (totalCategorizedTime > 0) {
            val academicRatio = summary.academicTime.toFloat() / totalCategorizedTime
            
            when {
                academicRatio > 0.85f -> {
                    recommendations.add(
                        Recommendation(
                            message = "Great academic focus! Consider adding some personal development time for a balanced approach. 📚➡️🌱",
                            priority = Priority.MEDIUM,
                            actionable = true
                        )
                    )
                }
                academicRatio < 0.15f -> {
                    recommendations.add(
                        Recommendation(
                            message = "Excellent personal growth! Don't forget to allocate time for academic goals too. 🌱➡️📚",
                            priority = Priority.MEDIUM,
                            actionable = true
                        )
                    )
                }
                else -> {
                    // Good balance
                    recommendations.add(
                        Recommendation(
                            message = "Perfect work-life balance! You're managing both academic and personal development well. Keep it up! ⚖️",
                            priority = Priority.LOW,
                            actionable = false
                        )
                    )
                }
            }
        }
        
        // Rule 3: Streak encouragement
        when {
            summary.currentStreak >= 14 -> {
                recommendations.add(
                    Recommendation(
                        message = "Incredible! 14+ day streak! You've built a powerful habit. You're unstoppable! 🔥🎉",
                        priority = Priority.LOW,
                        actionable = false
                    )
                )
            }
            summary.currentStreak >= 7 -> {
                recommendations.add(
                    Recommendation(
                        message = "Amazing 7-day streak! You're building excellent consistency. Keep the momentum going! 🔥",
                        priority = Priority.LOW,
                        actionable = false
                    )
                )
            }
            summary.currentStreak >= 3 -> {
                recommendations.add(
                    Recommendation(
                        message = "Nice 3-day streak! You're on the right track. Can you make it to 7 days? 🔥",
                        priority = Priority.MEDIUM,
                        actionable = true
                    )
                )
            }
            else -> {
                recommendations.add(
                    Recommendation(
                        message = "Start building your streak! Focus for just 1 session today to begin your journey. 🌱",
                        priority = Priority.HIGH,
                        actionable = true
                    )
                )
            }
        }
        
        // Rule 4: Session count analysis
        if (summary.totalSessions == 0) {
            recommendations.add(
                Recommendation(
                    message = "No sessions this week yet. Start with just one 25-minute Pomodoro to break the ice! 🚀",
                    priority = Priority.HIGH,
                    actionable = true
                )
            )
        } else if (summary.totalSessions < 7) {
            recommendations.add(
                Recommendation(
                    message = "Try to complete at least 1 session per day. Consistency is more important than intensity! 📅",
                    priority = Priority.MEDIUM,
                    actionable = true
                )
            )
        }
        
        // Rule 5: Peak day insight
        if (summary.peakDay != "N/A" && summary.totalSessions >= 3) {
            recommendations.add(
                Recommendation(
                    message = "Your most productive day is ${summary.peakDay}! Try to replicate that focus on other days. 📊",
                    priority = Priority.LOW,
                    actionable = true
                )
            )
        }
        
        return recommendations
    }
    
    /**
     * Get list of last N days in yyyy-MM-dd format
     */
    private fun getLastNDays(n: Int): List<String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val days = mutableListOf<String>()
        
        for (i in 0 until n) {
            days.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }
        
        return days
    }
    
    /**
     * Convert date string (yyyy-MM-dd) to day name (e.g., "Monday")
     */
    private fun getDayName(dateStr: String): String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(dateStr) ?: return "N/A"
            val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            dayFormat.format(date)
        } catch (e: Exception) {
            "N/A"
        }
    }
}
