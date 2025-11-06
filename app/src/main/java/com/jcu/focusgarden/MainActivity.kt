package com.jcu.focusgarden

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jcu.focusgarden.data.preferences.ThemePreferences
import com.jcu.focusgarden.ui.navigation.FocusGardenApp
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import kotlinx.coroutines.launch

/**
 * Main Activity
 * FocusGarden 应用的入口点
 * 
 * Week 5-6 Enhancement:
 * - 添加深色/浅色主题切换功能
 * - 使用 DataStore 持久化主题偏好
 */
class MainActivity : ComponentActivity() {
    
    private lateinit var themePreferences: ThemePreferences
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 初始化主题偏好管理器
        themePreferences = ThemePreferences(this)
        
        setContent {
            // 观察主题状态
            val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()
            
            // 主题切换函数
            val onToggleTheme: () -> Unit = {
                coroutineScope.launch {
                    themePreferences.toggleTheme()
                }
            }
            
            FocusGardenTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FocusGardenApp(onToggleTheme = onToggleTheme)
                }
            }
        }
    }
}

