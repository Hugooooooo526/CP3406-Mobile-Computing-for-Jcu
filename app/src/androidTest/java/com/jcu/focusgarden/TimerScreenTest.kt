package com.jcu.focusgarden

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.jcu.focusgarden.ui.screens.TimerScreen
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI Tests for TimerScreen
 * Tests user interactions and UI state changes
 */
class TimerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun timerScreen_displaysCorrectly() {
        composeTestRule.setContent {
            FocusGardenTheme {
                TimerScreen()
            }
        }

        // Verify timer display shows initial time
        composeTestRule.onNodeWithText("25:00").assertExists()
        
        // Verify start button exists
        composeTestRule.onNodeWithContentDescription("Start").assertExists()
    }

    @Test
    fun timerScreen_startButtonChangesToPause() {
        composeTestRule.setContent {
            FocusGardenTheme {
                TimerScreen()
            }
        }

        // Click start button
        composeTestRule.onNodeWithContentDescription("Start").performClick()
        
        // Verify pause button appears
        composeTestRule.onNodeWithContentDescription("Pause").assertExists()
    }

    @Test
    fun timerScreen_resetButtonWorks() {
        composeTestRule.setContent {
            FocusGardenTheme {
                TimerScreen()
            }
        }

        // Start and then reset
        composeTestRule.onNodeWithContentDescription("Start").performClick()
        composeTestRule.onNodeWithText("Reset").performClick()
        
        // Verify timer shows initial time again
        composeTestRule.onNodeWithText("25:00").assertExists()
    }

    @Test
    fun timerScreen_skipButtonAppearsWhenTimerStarts() {
        composeTestRule.setContent {
            FocusGardenTheme {
                TimerScreen()
            }
        }

        // Start timer
        composeTestRule.onNodeWithContentDescription("Start").performClick()
        
        // Verify skip button is enabled
        composeTestRule.onNodeWithText("Skip").assertIsEnabled()
    }

    @Test
    fun timerScreen_focusDurationSliderWorks() {
        composeTestRule.setContent {
            FocusGardenTheme {
                TimerScreen()
            }
        }

        // Verify initial slider value
        composeTestRule.onNodeWithText("Focus Duration").assertExists()
        composeTestRule.onNodeWithText("25 min").assertExists()
    }
}
