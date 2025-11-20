package com.jcu.focusgarden.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
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
import com.jcu.focusgarden.viewmodel.TimerViewModel

/**
 * Timer Screen - Focus Session
 * 按照 TD 文档 4.3.2 规范实现
 * 提供 Pomodoro 风格的专注计时器
 * 
 * Week 3-4: 静态 UI
 * Week 5-6: ✅ 集成音效反馈 + ViewModel 状态管理
 * Phase F: ✅ 时长调节功能（Slider）
 * Enhancement 2025-11-20: ✅ 跳过功能（统计原定时长）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    soundManager: SoundManager? = null,
    viewModel: TimerViewModel? = null
) {
    // Week 5-6: 使用 ViewModel 状态（替代本地 remember）
    val remainingSeconds by viewModel?.remainingSeconds?.collectAsState() ?: remember { mutableStateOf(25 * 60) }
    val isRunning by viewModel?.isRunning?.collectAsState() ?: remember { mutableStateOf(false) }
    val showReflectionDialog by viewModel?.showReflectionDialog?.collectAsState() ?: remember { mutableStateOf(false) }
    
    // Phase F: 观察用户选择的时长
    val focusDuration by viewModel?.focusDuration?.collectAsState() ?: remember { mutableStateOf(25) }
    
    // 本地 UI 状态（不需要持久化）
    var ambientSoundEnabled by remember { mutableStateOf(false) }
    
    // Week 5-6: 监听计时器完成，播放音效
    LaunchedEffect(remainingSeconds) {
        if (remainingSeconds == 0 && isRunning) {
            soundManager?.playComplete()
        }
    }
    
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
                    totalSeconds = focusDuration * 60, // Phase F: 使用自定义时长
                    modifier = Modifier.size(280.dp)
                )
                
                // Phase F: 时长调节 Slider
                DurationSlider(
                    duration = focusDuration,
                    onDurationChange = { viewModel?.setFocusDuration(it) },
                    enabled = !isRunning, // 仅在未运行时可调节
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(horizontal = 16.dp)
                )
                
                // 控制按钮
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Reset Button
                    TextButton(
                        onClick = { 
                            // Week 5-6: 使用 ViewModel 方法
                            viewModel?.resetTimer()
                            soundManager?.playCancel() // 取消/重置音效
                        },
                        enabled = !isRunning
                    ) {
                        Text(
                            text = stringResource(R.string.reset),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    
                    // Start/Pause FAB
                    FloatingActionButton(
                        onClick = { 
                            // Week 5-6: 使用 ViewModel 方法
                            viewModel?.toggleTimer()
                            
                            // 播放音效
                            if (!isRunning) {
                                soundManager?.playStart() // 开始音效
                            } else {
                                soundManager?.playPause() // 暂停音效
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isRunning) "Pause" else "Start",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    // Skip Button (Enhancement 2025-11-20)
                    // TEST MODE: 始终显示以便快速测试 (点击 Start 后立即可用)
                    // TODO: 生产环境可改为 if (isRunning || remainingSeconds < focusDuration * 60)
                    FilledTonalButton(
                        onClick = { 
                            viewModel?.skipTimer()
                            soundManager?.playComplete() // 完成音效
                        },
                        enabled = isRunning || remainingSeconds < focusDuration * 60, // 只在运行或已开始时启用
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Skip",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Skip",
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
            onDismiss = { 
                // 关闭对话框，跳过保存
                viewModel?.skipReflection()
            },
            onSave = { category, mood, note ->
                // Week 5-6: ✅ Phase C - 保存到数据库（包含 category）
                viewModel?.saveReflection(
                    category = category,
                    mood = mood,
                    note = note
                )
            },
            onSkip = { 
                viewModel?.skipReflection()
            }
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
 * 
 * Week 5-6: ✅ Phase C - 添加 Category 选择
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReflectionDialog(
    onDismiss: () -> Unit,
    onSave: (category: String, mood: String, note: String) -> Unit,
    onSkip: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Academic") }
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
                // Week 5-6: Phase C - Category 选择
                Text(
                    text = "📚 Category",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Academic 选项
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCategory == "Academic",
                            onClick = { selectedCategory = "Academic" }
                        )
                        Text(
                            text = "Academic",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    
                    // Personal 选项
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCategory == "Personal",
                            onClick = { selectedCategory = "Personal" }
                        )
                        Text(
                            text = "Personal",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                // 心情选择
                Text(
                    text = "😊 How do you feel?",
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
                onClick = { onSave(selectedCategory, selectedMood, noteText) },
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

/**
 * Phase F: Duration Slider 组件
 * 允许用户调节专注时长（5-60分钟，步长5分钟）
 */
@Composable
private fun DurationSlider(
    duration: Int,
    onDurationChange: (Int) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 标题
        Text(
            text = "Focus Duration",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (enabled) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 当前时长显示
        Text(
            text = "$duration min",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = if (enabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            }
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Slider
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 最小值标签
            Text(
                text = "5",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Slider 控件
            Slider(
                value = duration.toFloat(),
                onValueChange = { onDurationChange(it.toInt()) },
                valueRange = 5f..60f,
                steps = 10, // 5-60分钟，步长5，共11个值，steps=10
                enabled = enabled,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledThumbColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            )
            
            // 最大值标签
            Text(
                text = "60",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TimerScreenPreview() {
    FocusGardenTheme {
        TimerScreen()
    }
}





