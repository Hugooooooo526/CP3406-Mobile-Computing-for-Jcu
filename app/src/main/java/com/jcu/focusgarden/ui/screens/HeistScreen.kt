package com.jcu.focusgarden.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import com.jcu.focusgarden.ui.theme.HeistYellow

/**
 * Heist Group Challenge Screen
 * Implemented according to TD Document Section 4.3.3
 * Supports group collaboration focus tracking (3-5 members)
 * 
 * Week 7-8: ✅ Simplified Implementation (Mock Data)
 * - Display group goals and Streak
 * - Show member progress list
 * - Mock data for 4 members
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeistScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onStartFocusWithTeam: () -> Unit = {}
) {
    // Week 7-8: Mock group data (simplified version)
    val groupName = "Study Squad"
    val groupGoal = "Complete 30 Pomodoros This Week"
    val groupStreak = 5 // 5-day streak
    val totalCompleted = 18 // 18 completed
    val totalTarget = 30 // target 30
    
    // Mock 4 members (name, today's minutes, weekly progress, initials)
    val members = listOf(
        MemberProgress("Alex Chen", 75, 5, 8, "AC"),      // 75min today, 5/8 completed
        MemberProgress("Sara Kim", 50, 4, 8, "SK"),       // 50min today, 4/8 completed  
        MemberProgress("John Davis", 100, 6, 8, "JD"),    // 100min today, 6/8 completed
        MemberProgress("Emma Wilson", 45, 3, 8, "EW")     // 45min today, 3/8 completed
    )
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Heist Group",
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
                actions = {
                    IconButton(onClick = { /* TODO: 显示菜单 */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
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
            
            // Week 7-8: Group goal card (display mock data)
            GroupGoalCard(
                groupName = groupName,
                goal = groupGoal,
                streak = groupStreak,
                completed = totalCompleted,
                target = totalTarget,
                onInviteMember = { /* Week 7-8: Mock feature */ }
            )
            
            // Member progress list
            MemberProgressList(
                members = members,
                onThumbsUp = { /* TODO */ }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Bottom section
            BottomSection(
                onStartFocusWithTeam = onStartFocusWithTeam
            )
        }
    }
}

/**
 * Group Goal Card
 * Week 7-8: Enhanced display of group statistics
 */
@Composable
private fun GroupGoalCard(
    groupName: String,
    goal: String,
    streak: Int,
    completed: Int,
    target: Int,
    onInviteMember: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = HeistYellow
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
            // Week 7-8: Group name
            Text(
                text = "🎯 $groupName",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Goal description
            Text(
                text = goal,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Week 7-8: Progress bar
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Team Progress",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$completed / $target",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                LinearProgressIndicator(
                    progress = completed.toFloat() / target,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            
            // Streak indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "🔥",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$streak-Day Group Streak",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Invite member button
            OutlinedButton(
                onClick = onInviteMember,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Invite Member",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

/**
 * Member Progress List
 */
@Composable
private fun MemberProgressList(
    members: List<MemberProgress>,
    onThumbsUp: (String) -> Unit
) {
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(members) { member ->
                MemberProgressItem(
                    member = member,
                    onThumbsUp = { onThumbsUp(member.name) }
                )
                
                if (member != members.last()) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}

/**
 * Individual Member Progress Item
 * Week 7-8: Display member's today minutes and weekly progress
 */
@Composable
private fun MemberProgressItem(
    member: MemberProgress,
    onThumbsUp: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar (initials)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = member.initials,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Week 7-8: Name, today's minutes and weekly progress
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Name and today's minutes
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${member.minutes} min today",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Weekly progress bar
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "This Week",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${member.completed}/${member.target}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                LinearProgressIndicator(
                    progress = member.progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Thumbs up button
        IconButton(
            onClick = onThumbsUp,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Thumbs up",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * Bottom Section
 */
@Composable
private fun BottomSection(
    onStartFocusWithTeam: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 开始按钮
        ElevatedButton(
            onClick = onStartFocusWithTeam,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Start Focus with Team",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Motivational text
        Text(
            text = "Small wins together make big growth 🌱",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Member Progress Data Class
 * Week 7-8: Added weekly completed count and target
 */
data class MemberProgress(
    val name: String,
    val minutes: Int,          // Today's minutes
    val completed: Int,        // Weekly completed Pomodoros
    val target: Int,           // Weekly target Pomodoros
    val initials: String
) {
    val progress: Float
        get() = if (target > 0) completed.toFloat() / target else 0f
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HeistScreenPreview() {
    FocusGardenTheme {
        HeistScreen()
    }
}





