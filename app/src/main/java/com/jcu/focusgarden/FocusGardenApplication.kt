package com.jcu.focusgarden

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for FocusGarden
 * Enables Hilt dependency injection throughout the app
 * 
 * Week 9 Enhancement: Implemented Hilt for proper DI architecture
 */
@HiltAndroidApp
class FocusGardenApplication : Application()
