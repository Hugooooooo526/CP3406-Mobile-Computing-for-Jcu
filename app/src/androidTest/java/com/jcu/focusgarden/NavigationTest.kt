package com.jcu.focusgarden

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.jcu.focusgarden.ui.navigation.FocusGardenApp
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import org.junit.Rule
import org.junit.Test

/**
 * Integration tests for Navigation
 * Tests navigation flow between screens
 */
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navigation_bottomNavBar_displaysCorrectly() {
        composeTestRule.setContent {
            FocusGardenTheme {
                FocusGardenApp()
            }
        }

        // Verify all bottom navigation items exist
        composeTestRule.onNodeWithText("Timer").assertExists()
        composeTestRule.onNodeWithText("Dashboard").assertExists()
        composeTestRule.onNodeWithText("Heist").assertExists()
        composeTestRule.onNodeWithText("AI").assertExists()
    }

    @Test
    fun navigation_switchToDashboard_works() {
        composeTestRule.setContent {
            FocusGardenTheme {
                FocusGardenApp()
            }
        }

        // Click Dashboard tab
        composeTestRule.onNodeWithText("Dashboard").performClick()
        
        // Verify Dashboard screen content appears
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("This Week", substring = true).assertExists()
    }

    @Test
    fun navigation_switchToHeist_works() {
        composeTestRule.setContent {
            FocusGardenTheme {
                FocusGardenApp()
            }
        }

        // Click Heist tab
        composeTestRule.onNodeWithText("Heist").performClick()
        
        // Verify Heist screen content appears
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Heist Group Challenge", substring = true).assertExists()
    }

    @Test
    fun navigation_switchToAISummary_works() {
        composeTestRule.setContent {
            FocusGardenTheme {
                FocusGardenApp()
            }
        }

        // Click AI tab
        composeTestRule.onNodeWithText("AI").performClick()
        
        // Verify AI Summary screen content appears
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("AI Summary", substring = true).assertExists()
    }

    @Test
    fun navigation_returnToTimer_works() {
        composeTestRule.setContent {
            FocusGardenTheme {
                FocusGardenApp()
            }
        }

        // Navigate to Dashboard then back to Timer
        composeTestRule.onNodeWithText("Dashboard").performClick()
        composeTestRule.waitForIdle()
        
        composeTestRule.onNodeWithText("Timer").performClick()
        composeTestRule.waitForIdle()
        
        // Verify Timer screen is displayed
        composeTestRule.onNodeWithContentDescription("Start").assertExists()
    }
}
