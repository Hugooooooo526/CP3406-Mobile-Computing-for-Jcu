package com.jcu.focusgarden

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jcu.focusgarden.data.local.FocusGardenDatabase
import com.jcu.focusgarden.data.local.dao.SessionDao
import com.jcu.focusgarden.data.local.entity.SessionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration tests for Room Database
 * Tests actual database operations on Android device/emulator
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var database: FocusGardenDatabase
    private lateinit var sessionDao: SessionDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            FocusGardenDatabase::class.java
        ).build()
        sessionDao = database.sessionDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertSession_andRetrieveIt() = runBlocking {
        val now = System.currentTimeMillis()
        val session = SessionEntity(
            id = 1,
            category = "Academic",
            duration = 25,
            startTime = now,
            endTime = now + 25 * 60 * 1000,
            date = "2025-11-20"
        )

        sessionDao.insertSession(session)
        
        val allSessions = sessionDao.getAllSessions().first()
        assertTrue(allSessions.contains(session))
        assertEquals(1, allSessions.size)
    }

    @Test
    fun insertMultipleSessions_retrievesAll() = runBlocking {
        val now = System.currentTimeMillis()
        val session1 = SessionEntity(1, "Academic", 25, now, now + 25 * 60 * 1000, "2025-11-18")
        val session2 = SessionEntity(2, "Personal", 30, now, now + 30 * 60 * 1000, "2025-11-19")
        val session3 = SessionEntity(3, "Academic", 45, now, now + 45 * 60 * 1000, "2025-11-20")

        sessionDao.insertSession(session1)
        sessionDao.insertSession(session2)
        sessionDao.insertSession(session3)

        val allSessions = sessionDao.getAllSessions().first()
        assertEquals(3, allSessions.size)
    }

    @Test
    fun getSessionsByDateRange_filtersCorrectly() = runBlocking {
        val now = System.currentTimeMillis()
        val session1 = SessionEntity(1, "Academic", 25, now, now + 25 * 60 * 1000, "2025-11-15")
        val session2 = SessionEntity(2, "Personal", 30, now, now + 30 * 60 * 1000, "2025-11-18")
        val session3 = SessionEntity(3, "Academic", 45, now, now + 45 * 60 * 1000, "2025-11-22")

        sessionDao.insertSession(session1)
        sessionDao.insertSession(session2)
        sessionDao.insertSession(session3)

        val filteredSessions = sessionDao.getSessionsByDateRange("2025-11-16", "2025-11-20").first()
        
        // Should only include session2 (2025-11-18)
        assertEquals(1, filteredSessions.size)
        assertEquals("2025-11-18", filteredSessions[0].date)
    }

    @Test
    fun getAllDistinctDates_returnsUniqueDates() = runBlocking {
        val now = System.currentTimeMillis()
        val session1 = SessionEntity(1, "Academic", 25, now, now + 25 * 60 * 1000, "2025-11-18")
        val session2 = SessionEntity(2, "Personal", 30, now, now + 30 * 60 * 1000, "2025-11-18")
        val session3 = SessionEntity(3, "Academic", 45, now, now + 45 * 60 * 1000, "2025-11-19")

        sessionDao.insertSession(session1)
        sessionDao.insertSession(session2)
        sessionDao.insertSession(session3)

        val distinctDates = sessionDao.getAllDistinctDates()
        
        // Should have 2 unique dates even though 3 sessions
        assertEquals(2, distinctDates.size)
        assertTrue(distinctDates.contains("2025-11-18"))
        assertTrue(distinctDates.contains("2025-11-19"))
    }

    @Test
    fun deleteSingleSession_removes() = runBlocking {
        val now = System.currentTimeMillis()
        val session1 = SessionEntity(1, "Academic", 25, now, now + 25 * 60 * 1000, "2025-11-18")
        val session2 = SessionEntity(2, "Personal", 30, now, now + 30 * 60 * 1000, "2025-11-19")

        sessionDao.insertSession(session1)
        sessionDao.insertSession(session2)

        sessionDao.deleteSession(session1)

        val allSessions = sessionDao.getAllSessions().first()
        assertEquals(1, allSessions.size)
        assertFalse(allSessions.contains(session1))
        assertTrue(allSessions.contains(session2))
    }
}
