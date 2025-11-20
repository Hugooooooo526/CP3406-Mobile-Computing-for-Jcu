package com.jcu.focusgarden.api

/**
 * Gemini API Configuration
 * Week 9 Enhancement: AI-powered PDF generation
 * 
 * SECURITY NOTE: In production, API keys should be stored in:
 * 1. local.properties (not committed to git)
 * 2. BuildConfig (injected at build time)
 * 3. Secure storage or environment variables
 * 
 * For testing/development purposes only
 */
object GeminiConfig {
    
    /**
     * IMPORTANT: Replace with your actual Gemini API key
     * Get your free API key from: https://ai.google.dev/
     * 
     * Example: "AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
     */
    const val API_KEY = "AIzaSyBARI6nswVs59CZKlEovJ-Og0dKejgucgk"
    
    /**
     * Model configuration
     * Using gemini-1.5-flash (fast and efficient)
     * Requires SDK version 0.9.0+
     */
    const val MODEL_NAME = "gemini-1.5-flash"
    
    /**
     * Check if API key is configured
     */
    fun isConfigured(): Boolean {
        return API_KEY != "YOUR_GEMINI_API_KEY_HERE" && API_KEY.isNotBlank()
    }
    
    /**
     * Get error message for unconfigured API
     */
    fun getConfigurationError(): String {
        return """
            Gemini API Key not configured!
            
            To use AI-powered PDF generation:
            1. Get a free API key from https://ai.google.dev/
            2. Open GeminiConfig.kt
            3. Replace API_KEY with your key
            4. Rebuild the app
            
            Note: For testing without API, the local summary will still work.
        """.trimIndent()
    }
}
