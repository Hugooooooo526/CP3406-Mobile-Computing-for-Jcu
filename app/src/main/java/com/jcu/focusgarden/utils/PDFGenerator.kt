package com.jcu.focusgarden.utils

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.jcu.focusgarden.data.model.Recommendation
import com.jcu.focusgarden.data.model.WeeklySummary
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * PDF Generator Utility
 * Week 9 Enhancement: Generate PDF reports from weekly summary
 * 
 * Uses Android's native PdfDocument API (no external dependencies)
 */
object PDFGenerator {
    
    /**
     * Generate PDF report from weekly summary
     * Returns the file path of the generated PDF
     */
    fun generateWeeklyReport(
        context: Context,
        summary: WeeklySummary,
        recommendations: List<Recommendation>,
        aiInsights: String? = null
    ): Result<String> {
        return try {
            // Create PDF document
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
            val page = pdfDocument.startPage(pageInfo)
            
            val canvas = page.canvas
            val paint = android.graphics.Paint()
            
            var yPosition = 50f
            val leftMargin = 50f
            val lineHeight = 30f
            
            // Title
            paint.textSize = 24f
            paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
            canvas.drawText("Weekly Focus Report", leftMargin, yPosition, paint)
            yPosition += lineHeight * 2
            
            // Date
            paint.textSize = 12f
            paint.typeface = android.graphics.Typeface.DEFAULT
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            canvas.drawText("Generated: ${dateFormat.format(Date())}", leftMargin, yPosition, paint)
            yPosition += lineHeight * 2
            
            // Summary Statistics
            paint.textSize = 16f
            paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
            canvas.drawText("Weekly Summary", leftMargin, yPosition, paint)
            yPosition += lineHeight
            
            paint.textSize = 12f
            paint.typeface = android.graphics.Typeface.DEFAULT
            canvas.drawText("• Total Focus Time: ${summary.totalFocusTime} minutes", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight
            canvas.drawText("• Average per Day: ${summary.averageDailyFocus} minutes", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight
            canvas.drawText("• Current Streak: ${summary.currentStreak} days", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight
            canvas.drawText("• Peak Day: ${summary.peakDay}", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight
            canvas.drawText("• Total Sessions: ${summary.totalSessions}", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight * 2
            
            // Category Breakdown
            paint.textSize = 16f
            paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
            canvas.drawText("Category Breakdown", leftMargin, yPosition, paint)
            yPosition += lineHeight
            
            paint.textSize = 12f
            paint.typeface = android.graphics.Typeface.DEFAULT
            canvas.drawText("• Academic Time: ${summary.academicTime} minutes", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight
            canvas.drawText("• Personal Time: ${summary.personalTime} minutes", leftMargin + 20, yPosition, paint)
            yPosition += lineHeight * 2
            
            // Recommendations
            if (recommendations.isNotEmpty()) {
                paint.textSize = 16f
                paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
                canvas.drawText("Recommendations", leftMargin, yPosition, paint)
                yPosition += lineHeight
                
                paint.textSize = 12f
                paint.typeface = android.graphics.Typeface.DEFAULT
                recommendations.take(5).forEach { rec ->
                    val prioritySymbol = when (rec.priority) {
                        com.jcu.focusgarden.data.model.Priority.HIGH -> "[!]"
                        com.jcu.focusgarden.data.model.Priority.MEDIUM -> "[*]"
                        com.jcu.focusgarden.data.model.Priority.LOW -> "[+]"
                    }
                    
                    // Wrap text if too long
                    val maxChars = 60
                    val lines = rec.message.chunked(maxChars)
                    lines.forEachIndexed { index, line ->
                        val prefix = if (index == 0) "$prioritySymbol " else "    "
                        canvas.drawText(prefix + line, leftMargin + 20, yPosition, paint)
                        yPosition += lineHeight
                    }
                }
                yPosition += lineHeight
            }
            
            // AI Insights (if provided)
            if (!aiInsights.isNullOrBlank()) {
                if (yPosition > 700) {
                    // Start new page if running out of space
                    pdfDocument.finishPage(page)
                    val page2 = pdfDocument.startPage(pageInfo)
                    yPosition = 50f
                }
                
                paint.textSize = 16f
                paint.typeface = android.graphics.Typeface.DEFAULT_BOLD
                canvas.drawText("AI-Powered Insights", leftMargin, yPosition, paint)
                yPosition += lineHeight
                
                paint.textSize = 12f
                paint.typeface = android.graphics.Typeface.DEFAULT
                
                // Wrap AI insights text
                val maxChars = 70
                val lines = aiInsights.chunked(maxChars)
                lines.forEach { line ->
                    canvas.drawText(line, leftMargin + 20, yPosition, paint)
                    yPosition += lineHeight
                    
                    // Check if need new page
                    if (yPosition > 750) {
                        pdfDocument.finishPage(page)
                        val newPage = pdfDocument.startPage(pageInfo)
                        yPosition = 50f
                    }
                }
            }
            
            pdfDocument.finishPage(page)
            
            // Save PDF to file
            val fileName = "FocusGarden_Report_${System.currentTimeMillis()}.pdf"
            val file = createPdfFile(context, fileName)
            
            FileOutputStream(file).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            
            pdfDocument.close()
            
            Result.success(file.absolutePath)
            
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Create PDF file in app's external files directory
     */
    private fun createPdfFile(context: Context, fileName: String): File {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            ?: context.filesDir
        
        if (!directory.exists()) {
            directory.mkdirs()
        }
        
        return File(directory, fileName)
    }
    
    /**
     * Get all generated PDF reports
     */
    fun getAllReports(context: Context): List<File> {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            ?: context.filesDir
        
        return directory.listFiles { file ->
            file.extension == "pdf" && file.name.startsWith("FocusGarden_Report")
        }?.toList() ?: emptyList()
    }
}
