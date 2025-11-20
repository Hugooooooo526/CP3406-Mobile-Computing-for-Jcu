package com.jcu.focusgarden.api

import com.google.ai.client.generativeai.GenerativeModel
import com.jcu.focusgarden.data.model.WeeklySummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Gemini API Service
 * Week 9 Enhancement: AI-powered insights generation
 * 
 * Uses Google Generative AI (Gemini) to generate
 * personalized insights and recommendations
 */
class GeminiService {
    
    private val model: GenerativeModel? = if (GeminiConfig.isConfigured()) {
        GenerativeModel(
            modelName = GeminiConfig.MODEL_NAME,
            apiKey = GeminiConfig.API_KEY
        )
    } else {
        null
    }
    
    private val TAG = "GeminiService"
    
    /**
     * Generate AI-powered insights from weekly summary
     * Returns enhanced analysis and personalized suggestions
     */
    suspend fun generateInsights(summary: WeeklySummary): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            android.util.Log.d(TAG, "Building prompt for Gemini API")
            
            // Check API configuration
            if (!GeminiConfig.isConfigured()) {
                val error = "Gemini API Key not configured"
                android.util.Log.e(TAG, error)
                return@withContext Result.failure(Exception(error))
            }
            
            // Build prompt
            val prompt = buildPrompt(summary)
            android.util.Log.d(TAG, "Prompt built: ${prompt.take(100)}...")
            android.util.Log.d(TAG, "Using API Key: ${GeminiConfig.API_KEY.take(10)}...")
            
            // Call Gemini API
            android.util.Log.d(TAG, "Calling Gemini API...")
            val response = model?.generateContent(prompt)
            android.util.Log.d(TAG, "API response received")
            
            val text = response?.text
            android.util.Log.d(TAG, "Response text: ${text?.take(100) ?: "null"}")
            
            if (text.isNullOrBlank()) {
                android.util.Log.w(TAG, "Empty response from Gemini API")
                Result.failure(Exception("Empty response from Gemini API"))
            } else {
                android.util.Log.i(TAG, "AI insights generated successfully: ${text.length} chars")
                Result.success(text.trim())
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Gemini API error: ${e.javaClass.simpleName}: ${e.message}", e)
            Result.failure(e)
        }
    }
    
    /**
     * Build prompt for Gemini API
     */
    private fun buildPrompt(summary: WeeklySummary): String {
        return """
            You are a productivity coach analyzing a student's weekly focus data. 
            Provide 2-3 sentences of insightful, actionable feedback.
            
            Weekly Data:
            - Total Focus Time: ${summary.totalFocusTime} minutes
            - Average per Day: ${summary.averageDailyFocus} minutes
            - Current Streak: ${summary.currentStreak} days
            - Peak Day: ${summary.peakDay}
            - Total Sessions: ${summary.totalSessions}
            - Academic Time: ${summary.academicTime} minutes
            - Personal Time: ${summary.personalTime} minutes
            
            Provide:
            1. One specific strength they're showing
            2. One actionable improvement suggestion
            3. One motivational insight
            
            Keep it concise, friendly, and encouraging. No bullet points - just flowing text.
        """.trimIndent()
    }
}
