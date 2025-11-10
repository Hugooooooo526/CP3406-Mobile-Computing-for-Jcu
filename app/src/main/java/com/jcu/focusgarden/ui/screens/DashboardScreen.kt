package com.jcu.focusgarden.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jcu.focusgarden.R
import com.jcu.focusgarden.ui.components.DonutChart
import com.jcu.focusgarden.ui.components.FocusCard
import com.jcu.focusgarden.ui.components.WeeklyBarChart
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import com.jcu.focusgarden.viewmodel.DashboardViewModel

/**
 * Dashboard 主屏幕
 * 显示用户的每日和每周进度、工作负载平衡
 * 
 * Week 5-6 Enhancement: 
 * - 添加主题切换按钮 (TopAppBar)
 * - 添加音乐控制按钮 (TopAppBar)
 * Week 5-6 Enhancement:
 * - 添加主题切换按钮
 * - 添加音效开关按钮
 * Week 5-6 Phase E:
 * - ✅ 集成 DashboardViewModel
 * - ✅ 显示真实数据库数据
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onToggleTheme: () -> Unit = {},
    onMusicToggle: () -> Unit = {},
    isMusicPlaying: Boolean = false,
    onToggleSound: () -> Unit = {},
    isSoundMuted: Boolean = false,
    viewModel: DashboardViewModel? = null
) {
    // Week 5-6 Phase E: 观察 ViewModel 状态
    val todayFocusMinutes by viewModel?.todayFocusMinutes?.collectAsState() ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0) }
    val currentStreak by viewModel?.currentStreak?.collectAsState() ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0) }
    val weeklyData by viewModel?.weeklyData?.collectAsState() ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(listOf(0, 0, 0, 0, 0, 0, 0)) }
    val academicPercentage by viewModel?.academicPercentage?.collectAsState() ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0.5f) }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.dashboard_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // 音效开关按钮
                    IconButton(onClick = onToggleSound) {
                        Icon(
                            imageVector = if (isSoundMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isSoundMuted) "Unmute Sound" else "Mute Sound",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    // 主题切换按钮
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = Icons.Default.Brightness6,
                            contentDescription = "Toggle Theme",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    // 音乐控制按钮
                    IconButton(onClick = onMusicToggle) {
                        Icon(
                            imageVector = if (isMusicPlaying) Icons.Default.Stop else Icons.Default.MusicNote,
                            contentDescription = if (isMusicPlaying) "Stop Music" else "Play Music",
                            tint = if (isMusicPlaying) 
                                MaterialTheme.colorScheme.error 
                            else 
                                MaterialTheme.colorScheme.onPrimaryContainer
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Card 1 - Today's Focus (Week 5-6: 使用真实数据)
            TodayFocusCard(
                focusMinutes = todayFocusMinutes,
                streak = currentStreak
            )
            
            // Card 2 - Weekly Focus Progress (Week 5-6: 使用真实数据)
            WeeklyProgressCard(weekData = weeklyData)
            
            // Card 3 - Workload Balance (Week 5-6: 使用真实数据)
            WorkloadBalanceCard(academicPercentage = academicPercentage)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 激励文字
            Text(
                text = stringResource(R.string.motivation_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

/**
 * Week 5-6 Phase E: 今日专注卡片（使用真实数据）
 */
@Composable
private fun TodayFocusCard(
    focusMinutes: Int,
    streak: Int
) {
    FocusCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.today_focus),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Focused $focusMinutes min",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Streak 指示器（Week 5-6: 显示真实 Streak）
            if (streak > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "🔥",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$streak-Day Streak",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        // 简单的进度指示器（目标：每天 100 分钟）
        val targetMinutes = 100
        val progress = (focusMinutes.toFloat() / targetMinutes).coerceIn(0f, 1f)
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

/**
 * Week 5-6 Phase E: 本周进度卡片（使用真实数据）
 */
@Composable
private fun WeeklyProgressCard(weekData: List<Int>) {
    FocusCard {
        Text(
            text = stringResource(R.string.this_week),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        WeeklyBarChart(weekData = weekData)
    }
}

/**
 * Week 5-6 Phase E: 工作负载平衡卡片（使用真实数据）
 */
@Composable
private fun WorkloadBalanceCard(academicPercentage: Float) {
    FocusCard {
        Text(
            text = stringResource(R.string.workload_balance),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        DonutChart(academicPercentage = academicPercentage)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    FocusGardenTheme {
        DashboardScreen()
    }
}

