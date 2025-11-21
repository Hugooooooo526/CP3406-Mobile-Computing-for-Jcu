package com.jcu.focusgarden.data.model

/**
 * Weekly Summary Data Model
 * Contains aggregated focus session data for the past 7 days
 * 
 * Week 9: AI Summary Module
 * Used by AISummaryViewModel to display insights
 */
data class WeeklySummary(
    val totalFocusTime: Int,        // Total minutes focused this week
    val averageDailyFocus: Int,     // Average minutes per day
    val peakDay: String,            // Day with most focus time (e.g., "Monday")
    val totalSessions: Int,         // Number of completed sessions
    val academicTime: Int,          // Minutes spent on Academic category
    val personalTime: Int,          // Minutes spent on Personal category
    val currentStreak: Int          // Current consecutive days streak
)

/**
 * Recommendation Data Model
 * Contains AI-generated actionable suggestions
 * 
 * Week 9: Rule-based recommendation engine
 */
data class Recommendation(
    val message: String,            // Recommendation text
    val priority: Priority,         // Importance level
    val actionable: Boolean         // Whether user can act on it
)

/**
 * Priority Enum
 * Indicates recommendation urgency
 */
enum class Priority {
    HIGH,       // Critical improvement needed
    MEDIUM,     // Suggested adjustment
    LOW         // General encouragement
}

/**
 * Monthly Summary Data Model (Future enhancement)
 * Aggregated data for the past 30 days
 */
data class MonthlySummary(
    val totalFocusTime: Int,
    val averageDailyFocus: Int,
    val bestWeek: String,           // Week with highest focus time
    val totalSessions: Int,
    val academicTime: Int,
    val personalTime: Int,
    val longestStreak: Int
)
