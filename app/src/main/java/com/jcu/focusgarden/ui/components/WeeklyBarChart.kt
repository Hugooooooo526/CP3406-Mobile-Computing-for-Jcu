package com.jcu.focusgarden.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jcu.focusgarden.ui.theme.ProgressGreenEnd
import com.jcu.focusgarden.ui.theme.ProgressGreenStart

/**
 * 每周进度柱状图
 * 显示一周 7 天的专注时长
 */
@Composable
fun WeeklyBarChart(
    weekData: List<Int>, // 7 天的数据（分钟数）
    modifier: Modifier = Modifier
) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")
    val maxValue = weekData.maxOrNull()?.toFloat() ?: 1f
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 柱状图
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            weekData.forEachIndexed { index, value ->
                WeeklyBar(
                    value = value,
                    maxValue = maxValue,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                )
            }
        }
        
        // 日期标签
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            days.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun WeeklyBar(
    value: Int,
    maxValue: Float,
    modifier: Modifier = Modifier
) {
    val heightFraction = if (maxValue > 0) (value / maxValue).coerceIn(0f, 1f) else 0f
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val barWidth = size.width
        val barHeight = size.height * heightFraction
        
        val gradient = Brush.verticalGradient(
            colors = listOf(
                ProgressGreenStart,
                ProgressGreenEnd
            ),
            startY = size.height - barHeight,
            endY = size.height
        )
        
        drawRoundRect(
            brush = gradient,
            topLeft = Offset(0f, size.height - barHeight),
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
        )
    }
}

