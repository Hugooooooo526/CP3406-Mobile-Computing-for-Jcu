package com.jcu.focusgarden.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jcu.focusgarden.utils.SoundManager

/**
 * FocusGarden 主应用组件
 * 包含底部导航栏和导航控制
 * 
 * @param onToggleTheme 主题切换回调函数
 * @param onToggleSound 音效切换回调函数
 * @param soundManager 音效管理器
 * @param isSoundMuted 当前音效状态
 */
@Composable
fun FocusGardenApp(
    onToggleTheme: () -> Unit = {},
    onToggleSound: () -> Unit = {},
    soundManager: SoundManager? = null,
    isSoundMuted: Boolean = false
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // 定义底部导航项
    val bottomNavItems = listOf(
        BottomNavItem(
            title = "Dashboard",
            icon = Icons.Default.Home,
            route = Screen.Dashboard.route
        ),
        BottomNavItem(
            title = "Timer",
            icon = Icons.Default.Timer,
            route = Screen.Timer.route
        ),
        BottomNavItem(
            title = "Heist",
            icon = Icons.Default.Group,
            route = Screen.Heist.route
        ),
        BottomNavItem(
            title = "AI",
            icon = Icons.Default.AutoAwesome,
            route = Screen.AISummary.route
        )
    )
    
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                bottomNavItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any { 
                        it.route == item.route 
                    } == true
                    
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        FocusGardenNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            onToggleTheme = onToggleTheme,
            onToggleSound = onToggleSound,
            soundManager = soundManager,
            isSoundMuted = isSoundMuted
        )
    }
}

/**
 * 底部导航项数据类
 */
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)





