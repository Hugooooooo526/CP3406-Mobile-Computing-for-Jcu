package com.jcu.focusgarden.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcu.focusgarden.R
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import com.jcu.focusgarden.utils.SoundManager

/**
 * Timer Screen - Focus Session
 * 按照 TD 文档 4.3.2 规范实现
 * 提供 Pomodoro 风格的专注计时器
 * 
 * Week 5-6 Enhancement: 集成音效反馈
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    soundManager: SoundManager? = null
) {
    // 静态 UI 状态（Week 3-4）
    var isPlaying by remember { mutableStateOf(false) }
    var ambientSoundEnabled by remember { mutableStateOf(false) }
    var showReflectionDialog by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableStateOf(25 * 60) } // 25分钟
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.timer_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // 大型圆形倒计时器
                CircularTimer(
                    remainingSeconds = remainingSeconds,
                    totalSeconds = 25 * 60,
                    modifier = Modifier.size(280.dp)
                )
                
                // 控制按钮
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Start/Pause FAB
                    FloatingActionButton(
                        onClick = { 
                            isPlaying = !isPlaying
                            
                            // 播放音效
                            if (isPlaying) {
                                soundManager?.playStart() // 开始音效
                            } else {
                                soundManager?.playPause() // 暂停音效
                            }
                            
                            // 模拟完成后显示反思对话框
                            if (remainingSeconds <= 0) {
                                soundManager?.playComplete() // 完成音效
                                showReflectionDialog = true
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Start",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    // Reset Button
                    TextButton(
                        onClick = { 
                            remainingSeconds = 25 * 60
                            isPlaying = false
                            soundManager?.playCancel() // 取消/重置音效
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.reset),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // 环境音效开关
                AmbientSoundToggle(
                    enabled = ambientSoundEnabled,
                    onToggle = { ambientSoundEnabled = it }
                )
            }
        }
    }
    
    // 反思对话框
    if (showReflectionDialog) {
        ReflectionDialog(
            onDismiss = { showReflectionDialog = false },
            onSave = { mood, note ->
                // TODO: 保存到数据库（Week 5-6）
                showReflectionDialog = false
            },
            onSkip = { showReflectionDialog = false }
        )
    }
}

/**
 * 圆形倒计时器组件
 * 使用 Canvas 绘制进度弧
 */
@Composable
private fun CircularTimer(
    remainingSeconds: Int,
    totalSeconds: Int,
    modifier: Modifier = Modifier
) {
    val progress = (remainingSeconds.toFloat() / totalSeconds).coerceIn(0f, 1f)
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timeText = String.format("%02d:%02d", minutes, seconds)
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // 绘制圆形进度条
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2
            val topLeft = Offset(
                x = (size.width - radius * 2) / 2,
                y = (size.height - radius * 2) / 2
            )
            val arcSize = Size(radius * 2, radius * 2)
            
            // 背景圆环（浅绿色）
            drawArc(
                color = Color(0xFFE8F5E9),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            
            // 进度圆环（绿色）
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        
        // 中心时间文本
        Text(
            text = timeText,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * 环境音效开关组件
 */
@Composable
private fun AmbientSoundToggle(
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ambient_sound),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Switch(
                checked = enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

/**
 * 反思对话框
 * 在专注会话结束后弹出
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReflectionDialog(
    onDismiss: () -> Unit,
    onSave: (mood: String, note: String) -> Unit,
    onSkip: () -> Unit
) {
    var selectedMood by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Text(text = "🌱", style = MaterialTheme.typography.headlineMedium)
        },
        title = {
            Text(
                text = "Reflect on Your Session",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 心情选择
                Text(
                    text = "How do you feel?",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("😀", "🙂", "😐", "🙁").forEach { emoji ->
                        FilterChip(
                            selected = selectedMood == emoji,
                            onClick = { selectedMood = emoji },
                            label = { 
                                Text(
                                    text = emoji,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        )
                    }
                }
                
                // 学习笔记输入
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text("What did you learn or improve?") },
                    placeholder = { Text("Optional notes...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(selectedMood, noteText) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onSkip) {
                Text(stringResource(R.string.skip))
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TimerScreenPreview() {
    FocusGardenTheme {
        TimerScreen()
    }
}





