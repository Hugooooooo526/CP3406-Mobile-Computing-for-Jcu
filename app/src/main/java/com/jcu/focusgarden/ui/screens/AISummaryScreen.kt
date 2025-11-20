package com.jcu.focusgarden.ui.screens

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jcu.focusgarden.data.model.Recommendation
import com.jcu.focusgarden.data.model.WeeklySummary
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import com.jcu.focusgarden.viewmodel.AISummaryViewModel

/**
 * AI Summary Screen - Weekly Insights
 * Implemented according to TD Document Section 4.3.4
 * Auto-generated weekly summary and personalized recommendations (no chat)
 * 
 * Week 9: ✅ Integrated with AISummaryViewModel for real data
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AISummaryScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: AISummaryViewModel? = null
) {
    // Week 9: Observe ViewModel state
    val weeklySummary by viewModel?.weeklySummary?.collectAsState() ?: remember { mutableStateOf(null) }
    val recommendations by viewModel?.recommendations?.collectAsState() ?: remember { mutableStateOf(emptyList()) }
    val isLoading by viewModel?.isLoading?.collectAsState() ?: remember { mutableStateOf(false) }
    
    // Week 9 Enhancement: PDF generation state
    val pdfStatus by viewModel?.pdfGenerationStatus?.collectAsState() ?: remember { mutableStateOf(null) }
    val context = LocalContext.current
    
    val scrollState = rememberScrollState()
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "AI Summary",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Week 9: Loading state or content
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                
                // Card 1 - Weekly Summary (with real data)
                WeeklySummaryCard(summary = weeklySummary)
                
                // Card 2 - Recommendations (with real data)
                NextWeekRecommendationsCard(recommendations = recommendations)
                
                // Week 9 Enhancement: PDF generation section
                // Note: Gemini API disabled due to API key permissions
                // PDF will generate with local recommendations only
                PDFGenerationSection(
                    onGeneratePDF = { viewModel?.generatePDFReport(context, includeAI = false) },
                    pdfStatus = pdfStatus,
                    onClearStatus = { viewModel?.clearPDFStatus() }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Weekly Summary Card
 * Week 9: Updated to use real data from WeeklySummary
 */
@Composable
private fun WeeklySummaryCard(summary: WeeklySummary?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 标题
            Text(
                text = "Weekly Summary",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Week 9: Display real data or empty state
            if (summary != null) {
                // 2x2 statistics grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatItem(
                        label = "Total Focus Time",
                        value = "${summary.totalFocusTime} min",
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        label = "Average per Day",
                        value = "${summary.averageDailyFocus} min",
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatItem(
                        label = "Current Streak",
                        value = "${summary.currentStreak} Days",
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        label = "Peak Day",
                        value = summary.peakDay,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Category breakdown
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatItem(
                        label = "Academic Time",
                        value = "${summary.academicTime} min",
                        modifier = Modifier.weight(1f)
                    )
                    StatItem(
                        label = "Personal Time",
                        value = "${summary.personalTime} min",
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                // Empty state
                Text(
                    text = "No data available yet. Complete your first focus session!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Week 9: Removed trend chart and mood (simplified for MVP)
        }
    }
}

/**
 * Stat Item Component
 */
@Composable
private fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// Week 9: Productivity trend chart removed for MVP simplicity

/**
 * Next Week Recommendations Card
 * Week 9: Updated to use real recommendations from AI
 */
@Composable
private fun NextWeekRecommendationsCard(recommendations: List<Recommendation>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 标题
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Next Week Recommendations",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            // Week 9: Display real recommendations or empty state
            if (recommendations.isNotEmpty()) {
                recommendations.forEach { recommendation ->
                    RecommendationItem(
                        recommendation = recommendation
                    )
                }
            } else {
                Text(
                    text = "Complete some focus sessions to get personalized recommendations!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Recommendation Item Component
 * Week 9: Updated to use Recommendation data model with priority
 */
@Composable
private fun RecommendationItem(
    recommendation: Recommendation
) {
    // Choose icon based on priority
    val icon = when (recommendation.priority) {
        com.jcu.focusgarden.data.model.Priority.HIGH -> Icons.Default.PriorityHigh
        com.jcu.focusgarden.data.model.Priority.MEDIUM -> Icons.Default.Lightbulb
        com.jcu.focusgarden.data.model.Priority.LOW -> Icons.Default.CheckCircle
    }
    
    val iconColor = when (recommendation.priority) {
        com.jcu.focusgarden.data.model.Priority.HIGH -> MaterialTheme.colorScheme.error
        com.jcu.focusgarden.data.model.Priority.MEDIUM -> MaterialTheme.colorScheme.primary
        com.jcu.focusgarden.data.model.Priority.LOW -> MaterialTheme.colorScheme.tertiary
    }
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = recommendation.message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * PDF Generation Section
 * Week 9 Enhancement: Generate and export PDF reports
 */
@Composable
private fun PDFGenerationSection(
    onGeneratePDF: () -> Unit,
    pdfStatus: String?,
    onClearStatus: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "📄 Export Report",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Text(
                text = "Generate a PDF report with weekly summary and recommendations",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Button(
                onClick = onGeneratePDF,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.FileDownload,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate PDF Report")
            }
            
            // Status message
            if (pdfStatus != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (pdfStatus.contains("saved"))
                            MaterialTheme.colorScheme.tertiaryContainer
                        else if (pdfStatus.contains("failed"))
                            MaterialTheme.colorScheme.errorContainer
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pdfStatus,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                        if (!pdfStatus.contains("Generating")) {
                            IconButton(onClick = onClearStatus) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AISummaryScreenPreview() {
    FocusGardenTheme {
        AISummaryScreen()
    }
}





