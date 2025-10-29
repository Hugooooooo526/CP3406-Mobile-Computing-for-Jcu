package com.jcu.focusgarden.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.jcu.focusgarden.ui.theme.AcademicBlue
import com.jcu.focusgarden.ui.theme.PersonalOrange

/**
 * 环形图（Donut Chart）
 * 用于显示学术 vs 个人工作负载比例
 */
@Composable
fun DonutChart(
    academicPercentage: Float, // 0.0 - 1.0
    modifier: Modifier = Modifier
) {
    val personalPercentage = 1f - academicPercentage
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 环形图
        Canvas(
            modifier = Modifier
                .size(140.dp)
                .padding(16.dp)
        ) {
            val strokeWidth = 20.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2
            val topLeft = Offset(
                x = (size.width - radius * 2) / 2,
                y = (size.height - radius * 2) / 2
            )
            val arcSize = Size(radius * 2, radius * 2)
            
            // 绘制学术部分（蓝色）
            drawArc(
                color = AcademicBlue,
                startAngle = -90f,
                sweepAngle = 360f * academicPercentage,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            
            // 绘制个人部分（橙色）
            drawArc(
                color = PersonalOrange,
                startAngle = -90f + (360f * academicPercentage),
                sweepAngle = 360f * personalPercentage,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 图例
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChartLegend(
                color = AcademicBlue,
                label = "Academic ${(academicPercentage * 100).toInt()}%",
                modifier = Modifier.padding(end = 16.dp)
            )
            ChartLegend(
                color = PersonalOrange,
                label = "Personal ${(personalPercentage * 100).toInt()}%"
            )
        }
    }
}

@Composable
private fun ChartLegend(
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Canvas(modifier = Modifier.size(12.dp)) {
            drawCircle(color = color)
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

