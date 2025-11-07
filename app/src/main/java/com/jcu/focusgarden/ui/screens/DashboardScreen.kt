package com.jcu.focusgarden.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

/**
 * Dashboard 主屏幕
 * 显示用户的每日和每周进度、工作负载平衡以及快速操作
 * 
 * Week 5-6 Enhancement: 添加主题切换按钮
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onStartFocus: () -> Unit = {},
    onViewJournal: () -> Unit = {},
    onAISummary: () -> Unit = {},
    onToggleTheme: () -> Unit = {},
    onMusicToggle: () -> Unit = {},
    isMusicPlaying: Boolean = false
) {
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
                    // 主题切换按钮
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = Icons.Default.Brightness6,
                            contentDescription = "Toggle Theme",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
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
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // Card 1 - Today's Focus
            TodayFocusCard()
            
            // Card 2 - Weekly Focus Progress
            WeeklyProgressCard()
            
            // Card 3 - Workload Balance
            WorkloadBalanceCard()
            
            // Card 4 - Quick Actions
            QuickActionsCard(
                onStartFocus = onStartFocus,
                onViewJournal = onViewJournal,
                onAISummary = onAISummary,
                onMusicToggle = onMusicToggle,
                isMusicPlaying = isMusicPlaying
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
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

@Composable
private fun TodayFocusCard() {
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
                    text = "Focused 75 min",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Streak 指示器
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "🔥",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "3-Day Streak",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        // 简单的进度指示器
        LinearProgressIndicator(
            progress = 0.75f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

@Composable
private fun WeeklyProgressCard() {
    FocusCard {
        Text(
            text = stringResource(R.string.this_week),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // 模拟一周数据（周一到周日的专注分钟数）
        val weekData = listOf(60, 45, 90, 120, 75, 30, 20)
        
        WeeklyBarChart(weekData = weekData)
    }
}

@Composable
private fun WorkloadBalanceCard() {
    FocusCard {
        Text(
            text = stringResource(R.string.workload_balance),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // 60% 学术，40% 个人
        DonutChart(academicPercentage = 0.6f)
    }
}

@Composable
private fun QuickActionsCard(
    onStartFocus: () -> Unit,
    onViewJournal: () -> Unit,
    onAISummary: () -> Unit,
    onMusicToggle: () -> Unit = {},
    isMusicPlaying: Boolean = false
) {
    FocusCard {
        Text(
            text = stringResource(R.string.quick_actions),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // 第一行：Start Focus + View Journal
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(
                onClick = onStartFocus,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            
            ElevatedButton(
                onClick = onViewJournal,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Journal",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 第二行：AI Summary + Focus Music
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(
                onClick = onAISummary,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "🤖",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "AI",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            
            // 背景音乐按钮
            ElevatedButton(
                onClick = onMusicToggle,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = if (isMusicPlaying) 
                        MaterialTheme.colorScheme.secondary 
                    else 
                        MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (isMusicPlaying) Icons.Default.Stop else Icons.Default.MusicNote,
                    contentDescription = if (isMusicPlaying) "Stop Music" else "Play Music",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isMusicPlaying) "Stop" else "Music",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    FocusGardenTheme {
        DashboardScreen()
    }
}

