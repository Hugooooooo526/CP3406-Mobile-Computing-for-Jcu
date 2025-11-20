package com.jcu.focusgarden

import com.jcu.focusgarden.data.local.entity.SessionEntity
import com.jcu.focusgarden.data.model.Priority
import com.jcu.focusgarden.utils.SummaryGenerator
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for SummaryGenerator
 * Tests AI algorithm for weekly summary and recommendations
 */
class SummaryGeneratorTest {

    @Test
    fun `test weekly summary with no sessions`() {
        val summary = SummaryGenerator.generateWeeklySummary(
            allSessions = emptyList(),
            currentStreak = 0
        )

        assertEquals(0, summary.totalFocusTime)
        assertEquals(0, summary.averageDailyFocus)
        assertEquals("N/A", summary.peakDay)
        assertEquals(0, summary.totalSessions)
        assertEquals(0, summary.currentStreak)
    }

    @Test
    fun `test weekly summary calculates total focus time correctly`() {
        val sessions = listOf(
            SessionEntity(1, "2025-11-18", 25, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(2, "2025-11-19", 30, "Personal", null, null, System.currentTimeMillis()),
            SessionEntity(3, "2025-11-20", 45, "Academic", null, null, System.currentTimeMillis())
        )

        val summary = SummaryGenerator.generateWeeklySummary(
            allSessions = sessions,
            currentStreak = 3
        )

        assertEquals(100, summary.totalFocusTime) // 25 + 30 + 45
        assertEquals(3, summary.totalSessions)
        assertEquals(3, summary.currentStreak)
    }

    @Test
    fun `test weekly summary calculates category breakdown`() {
        val sessions = listOf(
            SessionEntity(1, "2025-11-20", 25, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(2, "2025-11-20", 30, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(3, "2025-11-20", 15, "Personal", null, null, System.currentTimeMillis())
        )

        val summary = SummaryGenerator.generateWeeklySummary(
            allSessions = sessions,
            currentStreak = 1
        )

        assertEquals(55, summary.academicTime) // 25 + 30
        assertEquals(15, summary.personalTime)
    }

    @Test
    fun `test recommendations for low daily average`() {
        val lowAverageSummary = com.jcu.focusgarden.data.model.WeeklySummary(
            totalFocusTime = 50,
            averageDailyFocus = 20, // Low average
            peakDay = "Monday",
            totalSessions = 5,
            academicTime = 30,
            personalTime = 20,
            currentStreak = 2
        )

        val recommendations = SummaryGenerator.generateRecommendations(lowAverageSummary)

        assertTrue(recommendations.isNotEmpty())
        assertTrue(recommendations.any { it.priority == Priority.HIGH })
        assertTrue(recommendations.any { it.message.contains("session") })
    }

    @Test
    fun `test recommendations for good performance`() {
        val goodSummary = com.jcu.focusgarden.data.model.WeeklySummary(
            totalFocusTime = 500,
            averageDailyFocus = 90, // Good average
            peakDay = "Monday",
            totalSessions = 20,
            academicTime = 300,
            personalTime = 200,
            currentStreak = 7
        )

        val recommendations = SummaryGenerator.generateRecommendations(goodSummary)

        assertTrue(recommendations.isNotEmpty())
        assertTrue(recommendations.any { it.priority == Priority.LOW })
    }

    @Test
    fun `test peak day calculation`() {
        val sessions = listOf(
            SessionEntity(1, "Monday", 25, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(2, "Monday", 30, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(3, "Tuesday", 15, "Personal", null, null, System.currentTimeMillis())
        )

        val summary = SummaryGenerator.generateWeeklySummary(
            allSessions = sessions,
            currentStreak = 2
        )

        // Monday has more total time (55 min) than Tuesday (15 min)
        assertEquals("Monday", summary.peakDay)
    }

    @Test
    fun `test recommendations include streak encouragement`() {
        val streakSummary = com.jcu.focusgarden.data.model.WeeklySummary(
            totalFocusTime = 200,
            averageDailyFocus = 50,
            peakDay = "Monday",
            totalSessions = 10,
            academicTime = 120,
            personalTime = 80,
            currentStreak = 5 // Good streak
        )

        val recommendations = SummaryGenerator.generateRecommendations(streakSummary)

        assertTrue(recommendations.any { it.message.contains("streak", ignoreCase = true) })
    }
}
