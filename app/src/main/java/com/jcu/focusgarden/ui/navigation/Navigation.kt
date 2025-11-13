package com.jcu.focusgarden.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jcu.focusgarden.ui.screens.*
import com.jcu.focusgarden.utils.SoundManager
import com.jcu.focusgarden.viewmodel.TimerViewModel
import com.jcu.focusgarden.viewmodel.DashboardViewModel

/**
 * Navigation Routes
 * 按照 TD 文档定义的导航流程
 */
sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Timer : Screen("timer")
    object Heist : Screen("heist")
    object AISummary : Screen("ai_summary")
}

/**
 * 主导航组件
 * 管理应用内所有屏幕的导航
 * 
 * @param onToggleTheme 主题切换回调函数
 * @param onMusicToggle 背景音乐切换回调函数
 * @param isMusicPlaying 当前音乐播放状态
 * @param onToggleSound 音效切换回调函数
 * @param soundManager 音效管理器
 * @param isSoundMuted 当前音效状态
 * @param timerViewModel Timer 功能的 ViewModel (Week 5-6 MVP)
 * @param dashboardViewModel Dashboard 功能的 ViewModel (Week 5-6 Phase E)
 */
@Composable
fun FocusGardenNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Dashboard.route,
    onToggleTheme: () -> Unit = {},
    onMusicToggle: () -> Unit = {},
    isMusicPlaying: Boolean = false,
    onToggleSound: () -> Unit = {},
    soundManager: SoundManager? = null,
    isSoundMuted: Boolean = false,
    timerViewModel: TimerViewModel? = null,
    dashboardViewModel: DashboardViewModel? = null
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Dashboard Screen
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onToggleTheme = onToggleTheme,
                onMusicToggle = onMusicToggle,
                isMusicPlaying = isMusicPlaying,
                onToggleSound = onToggleSound,
                isSoundMuted = isSoundMuted,
                viewModel = dashboardViewModel
            )
        }
        
        // Timer Screen
        composable(Screen.Timer.route) {
            TimerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                soundManager = soundManager,
                viewModel = timerViewModel
            )
        }
        
        // Heist Group Screen
        composable(Screen.Heist.route) {
            HeistScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onStartFocusWithTeam = {
                    navController.navigate(Screen.Timer.route)
                }
            )
        }
        
        // AI Summary Screen
        composable(Screen.AISummary.route) {
            AISummaryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onGenerateMonthlyReport = {
                    // TODO: Week 9 - 实现月度报告生成
                }
            )
        }
    }
}





