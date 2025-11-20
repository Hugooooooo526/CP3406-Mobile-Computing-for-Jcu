package com.jcu.focusgarden.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcu.focusgarden.api.GeminiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.jcu.focusgarden.data.model.Recommendation
import com.jcu.focusgarden.data.model.WeeklySummary
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.utils.PDFGenerator
import com.jcu.focusgarden.utils.SummaryGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * AI Summary ViewModel
 * Implemented according to TD Document MVVM architecture
 * 
 * Week 3-4: Basic structure
 * Week 9: ✅ Implemented complete AI summary generation with local algorithm
 * Week 9 Enhancement: ✅ Added Gemini API integration and PDF export
 */
@HiltViewModel
class AISummaryViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    
    // Week 9: Summary State (using new data models)
    private val _weeklySummary = MutableStateFlow<WeeklySummary?>(null)
    val weeklySummary: StateFlow<WeeklySummary?> = _weeklySummary.asStateFlow()
    
    private val _recommendations = MutableStateFlow<List<Recommendation>>(emptyList())
    val recommendations: StateFlow<List<Recommendation>> = _recommendations.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Week 9 Enhancement: AI insights and PDF generation state
    private val _aiInsights = MutableStateFlow<String?>(null)
    val aiInsights: StateFlow<String?> = _aiInsights.asStateFlow()
    
    private val _pdfGenerationStatus = MutableStateFlow<String?>(null)
    val pdfGenerationStatus: StateFlow<String?> = _pdfGenerationStatus.asStateFlow()
    
    private val geminiService = GeminiService()
    
    init {
        // Week 9: Load real data from database
        loadWeeklySummary()
    }
    
    /**
     * Load weekly summary from database
     * Week 9: Implemented with real data and local AI algorithm
     */
    fun loadWeeklySummary() {
        viewModelScope.launch {
            _isLoading.value = true
            android.util.Log.d("AISummaryViewModel", "Loading weekly summary...")
            
            try {
                // Get all sessions from database - use first() to get current value
                val allSessions = sessionRepository.getAllSessions().first()
                android.util.Log.d("AISummaryViewModel", "Loaded ${allSessions.size} sessions")
                
                // Calculate current streak
                val allDates = sessionRepository.getAllDistinctDates()
                val currentStreak = calculateStreak(allDates)
                android.util.Log.d("AISummaryViewModel", "Current streak: $currentStreak days")
                
                // Generate weekly summary using SummaryGenerator
                val summary = SummaryGenerator.generateWeeklySummary(
                    allSessions = allSessions,
                    currentStreak = currentStreak
                )
                _weeklySummary.value = summary
                android.util.Log.d("AISummaryViewModel", "Summary generated: ${summary.totalFocusTime} minutes")
                
                // Generate recommendations
                val recs = SummaryGenerator.generateRecommendations(summary)
                _recommendations.value = recs
                android.util.Log.d("AISummaryViewModel", "Generated ${recs.size} recommendations")
                
            } catch (e: Exception) {
                android.util.Log.e("AISummaryViewModel", "Error loading summary: ${e.message}", e)
                // Handle error: show empty state
                _weeklySummary.value = WeeklySummary(0, 0, "N/A", 0, 0, 0, 0)
                _recommendations.value = listOf(
                    Recommendation(
                        message = "Start your focus journey today! Complete your first session. ",
                        priority = com.jcu.focusgarden.data.model.Priority.HIGH,
                        actionable = true
                    )
                )
            } finally {
                _isLoading.value = false
                android.util.Log.d("AISummaryViewModel", "Loading complete")
            }
        }
    }
    
    /**
     * Calculate current streak from dates
     * Same algorithm as DashboardViewModel
     */
    private fun calculateStreak(allDates: List<String>): Int {
        if (allDates.isEmpty()) return 0
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var streak = 0
        val calendar = Calendar.getInstance()
        var checkDate = dateFormat.format(calendar.time)
        
        while (allDates.contains(checkDate)) {
            streak++
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            checkDate = dateFormat.format(calendar.time)
        }
        
        return streak
    }
    
    /**
     * Generate AI-powered insights using Gemini API
     * Week 9 Enhancement
     * @return AI insights text or null if failed
     */
    private suspend fun generateAIInsights(): String? {
        val summary = _weeklySummary.value ?: return null
        
        android.util.Log.d("AISummaryViewModel", "Calling Gemini API for insights...")
        return geminiService.generateInsights(summary).getOrNull().also { insights ->
            if (insights != null) {
                android.util.Log.d("AISummaryViewModel", "Gemini API returned ${insights.length} chars")
            } else {
                android.util.Log.w("AISummaryViewModel", "Gemini API returned null")
            }
        }
    }
    
    /**
     * Generate PDF report with optional AI insights
     * Week 9 Enhancement
     */
    fun generatePDFReport(context: Context, includeAI: Boolean = true) {
        viewModelScope.launch {
            try {
                android.util.Log.d("AISummaryViewModel", "Starting PDF generation...")
                _pdfGenerationStatus.value = "Generating PDF report..."
                
                val summary = _weeklySummary.value ?: run {
                    android.util.Log.e("AISummaryViewModel", "No summary data available")
                    _pdfGenerationStatus.value = "No data available to generate report."
                    return@launch
                }
                val recs = _recommendations.value
                android.util.Log.d("AISummaryViewModel", "Summary: ${summary.totalFocusTime} min, ${recs.size} recommendations")
                
                // Generate AI insights if requested
                var aiInsights: String? = null
                if (includeAI) {
                    android.util.Log.d("AISummaryViewModel", "Generating AI insights...")
                    _pdfGenerationStatus.value = "Generating AI insights..."
                    aiInsights = generateAIInsights()
                    android.util.Log.d("AISummaryViewModel", "AI insights: ${aiInsights?.length ?: 0} chars")
                }
                
                // Generate PDF
                android.util.Log.d("AISummaryViewModel", "Creating PDF document...")
                val pdfPath = PDFGenerator.generateWeeklyReport(
                    context = context,
                    summary = summary,
                    recommendations = recs,
                    aiInsights = aiInsights
                )
                
                _pdfGenerationStatus.value = "PDF saved: $pdfPath"
                android.util.Log.i("AISummaryViewModel", "PDF generation successful: $pdfPath")
            } catch (e: Exception) {
                android.util.Log.e("AISummaryViewModel", "PDF generation failed", e)
                _pdfGenerationStatus.value = "PDF generation failed: ${e.message}"
            }
        }
    }
    
    /**
     * Clear PDF generation status
     */
    fun clearPDFStatus() {
        _pdfGenerationStatus.value = null
    }
    
    /**
     * Refresh summary data
     */
    fun refreshSummary() {
        loadWeeklySummary()
    }
}





