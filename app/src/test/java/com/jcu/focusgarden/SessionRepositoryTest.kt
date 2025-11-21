package com.jcu.focusgarden

import com.jcu.focusgarden.data.local.dao.SessionDao
import com.jcu.focusgarden.data.local.entity.SessionEntity
import com.jcu.focusgarden.data.repository.SessionRepository
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
 * Unit tests for SessionRepository
 * Tests database operations and data retrieval
 */
@ExperimentalCoroutinesApi
class SessionRepositoryTest {

    @Mock
    private lateinit var sessionDao: SessionDao

    private lateinit var repository: SessionRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = SessionRepository(sessionDao)
    }

    @Test
    fun `test insert session calls dao correctly`() = runTest {
        val session = SessionEntity(
            id = 1,
            date = "2025-11-20",
            duration = 25,
            category = "Academic",
            emotion = "Happy",
            reflection = "Good session",
            completedAt = System.currentTimeMillis()
        )

        repository.insertSession(session)
        verify(sessionDao).insertSession(session)
    }

    @Test
    fun `test get all sessions returns flow`() = runTest {
        val mockSessions = listOf(
            SessionEntity(1, "2025-11-20", 25, "Academic", "Happy", "Test", System.currentTimeMillis()),
            SessionEntity(2, "2025-11-20", 30, "Personal", "Neutral", "Test", System.currentTimeMillis())
        )
        
        `when`(sessionDao.getAllSessions()).thenReturn(flowOf(mockSessions))
        
        val result = repository.getAllSessions().first()
        assertEquals(2, result.size)
        assertEquals("Academic", result[0].category)
    }

    @Test
    fun `test get sessions by date range`() = runTest {
        val mockSessions = listOf(
            SessionEntity(1, "2025-11-20", 25, "Academic", null, null, System.currentTimeMillis())
        )
        
        `when`(sessionDao.getSessionsByDateRange("2025-11-18", "2025-11-20"))
            .thenReturn(flowOf(mockSessions))
        
        val result = repository.getSessionsByDateRange("2025-11-18", "2025-11-20").first()
        assertEquals(1, result.size)
    }

    @Test
    fun `test delete all sessions`() = runTest {
        repository.deleteAllSessions()
        verify(sessionDao).deleteAllSessions()
    }
}
