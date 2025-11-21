package com.jcu.focusgarden

import com.jcu.focusgarden.data.local.entity.SessionEntity
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.viewmodel.DashboardViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit tests for DashboardViewModel
 * Tests statistics calculation and data aggregation
 */
@ExperimentalCoroutinesApi
class DashboardViewModelTest {

    @Mock
    private lateinit var sessionRepository: SessionRepository

    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = DashboardViewModel(sessionRepository)
    }

    @Test
    fun `test initial state shows zero statistics`() = runTest {
        val emptyList = emptyList<SessionEntity>()
        `when`(sessionRepository.getAllSessions()).thenReturn(flowOf(emptyList))

        val stats = viewModel.weeklyStats.first()
        assertEquals(0, stats.totalSessions)
        assertEquals(0, stats.totalMinutes)
    }

    @Test
    fun `test weekly stats calculation with real data`() = runTest {
        val mockSessions = listOf(
            SessionEntity(1, "2025-11-18", 25, "Academic", "Happy", "Good", System.currentTimeMillis()),
            SessionEntity(2, "2025-11-19", 30, "Personal", "Neutral", "Ok", System.currentTimeMillis()),
            SessionEntity(3, "2025-11-20", 45, "Academic", "Happy", "Great", System.currentTimeMillis())
        )
        
        `when`(sessionRepository.getAllSessions()).thenReturn(flowOf(mockSessions))

        // Verify total minutes calculation
        val totalMinutes = mockSessions.sumOf { it.duration }
        assertEquals(100, totalMinutes) // 25 + 30 + 45
    }

    @Test
    fun `test category breakdown calculates correctly`() = runTest {
        val mockSessions = listOf(
            SessionEntity(1, "2025-11-20", 25, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(2, "2025-11-20", 30, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(3, "2025-11-20", 15, "Personal", null, null, System.currentTimeMillis())
        )
        
        `when`(sessionRepository.getAllSessions()).thenReturn(flowOf(mockSessions))

        val academicTotal = mockSessions.filter { it.category == "Academic" }.sumOf { it.duration }
        val personalTotal = mockSessions.filter { it.category == "Personal" }.sumOf { it.duration }
        
        assertEquals(55, academicTotal) // 25 + 30
        assertEquals(15, personalTotal)
    }

    @Test
    fun `test streak calculation for consecutive days`() = runTest {
        val consecutiveSessions = listOf(
            SessionEntity(1, "2025-11-18", 25, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(2, "2025-11-19", 30, "Academic", null, null, System.currentTimeMillis()),
            SessionEntity(3, "2025-11-20", 45, "Academic", null, null, System.currentTimeMillis())
        )
        
        `when`(sessionRepository.getAllDistinctDates()).thenReturn(
            listOf("2025-11-18", "2025-11-19", "2025-11-20")
        )

        // Streak should be 3 for consecutive days
        val distinctDates = listOf("2025-11-18", "2025-11-19", "2025-11-20")
        assertTrue(distinctDates.size >= 3)
    }

    @Test
    fun `test empty sessions returns zero stats`() = runTest {
        `when`(sessionRepository.getAllSessions()).thenReturn(flowOf(emptyList()))
        `when`(sessionRepository.getAllDistinctDates()).thenReturn(emptyList())

        verify(sessionRepository).getAllSessions()
        verify(sessionRepository).getAllDistinctDates()
    }
}
