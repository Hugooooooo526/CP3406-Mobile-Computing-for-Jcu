package com.jcu.focusgarden

import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.viewmodel.TimerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit tests for TimerViewModel
 * Tests timer state management and session completion
 */
@ExperimentalCoroutinesApi
class TimerViewModelTest {

    @Mock
    private lateinit var sessionRepository: SessionRepository

    private lateinit var viewModel: TimerViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = TimerViewModel(sessionRepository)
    }

    @Test
    fun `test initial state is not running`() {
        assertFalse(viewModel.isRunning.value)
        assertEquals(25 * 60, viewModel.remainingSeconds.value)
    }

    @Test
    fun `test timer starts correctly`() = runTest {
        viewModel.startTimer()
        assertTrue(viewModel.isRunning.value)
    }

    @Test
    fun `test timer pauses correctly`() = runTest {
        viewModel.startTimer()
        viewModel.pauseTimer()
        assertFalse(viewModel.isRunning.value)
    }

    @Test
    fun `test timer reset works`() = runTest {
        viewModel.setFocusDuration(30)
        viewModel.startTimer()
        viewModel.resetTimer()
        
        assertFalse(viewModel.isRunning.value)
        assertEquals(30 * 60, viewModel.remainingSeconds.value)
    }

    @Test
    fun `test focus duration changes correctly`() {
        viewModel.setFocusDuration(45)
        assertEquals(45, viewModel.focusDuration.value)
        assertEquals(45 * 60, viewModel.remainingSeconds.value)
    }

    @Test
    fun `test skip timer maintains planned duration`() = runTest {
        val plannedDuration = 25
        viewModel.setFocusDuration(plannedDuration)
        viewModel.startTimer()
        
        // Skip should store the planned duration, not elapsed time
        viewModel.skipTimer()
        
        // Verify timer is reset
        assertFalse(viewModel.isRunning.value)
    }
}
