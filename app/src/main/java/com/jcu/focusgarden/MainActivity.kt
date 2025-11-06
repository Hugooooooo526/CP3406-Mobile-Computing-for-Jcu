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
import com.jcu.focusgarden.data.preferences.SoundPreferences
import com.jcu.focusgarden.data.preferences.ThemePreferences
import com.jcu.focusgarden.ui.navigation.FocusGardenApp
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import com.jcu.focusgarden.utils.SoundManager
import kotlinx.coroutines.launch

/**
 * Main Activity
 * FocusGarden 应用的入口点
 * 
 * Week 5-6 Enhancement:
 * - 添加深色/浅色主题切换功能
 * - 添加音效反馈系统
 * - 使用 DataStore 持久化偏好设置
 */
class MainActivity : ComponentActivity() {
    
    private lateinit var themePreferences: ThemePreferences
    private lateinit var soundPreferences: SoundPreferences
    private lateinit var soundManager: SoundManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 初始化偏好管理器
        themePreferences = ThemePreferences(this)
        soundPreferences = SoundPreferences(this)
        soundManager = SoundManager(this)
        
        setContent {
            // 观察主题和音效状态
            val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)
            val isSoundMuted by soundPreferences.isSoundMuted.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()
            
            // 同步音效静音状态到 SoundManager
            androidx.compose.runtime.LaunchedEffect(isSoundMuted) {
                soundManager.setMuted(isSoundMuted)
            }
            
            // 主题切换函数
            val onToggleTheme: () -> Unit = {
                coroutineScope.launch {
                    themePreferences.toggleTheme()
                }
            }
            
            // 音效切换函数
            val onToggleSound: () -> Unit = {
                coroutineScope.launch {
                    soundPreferences.toggleSound()
                }
            }
            
            FocusGardenTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FocusGardenApp(
                        onToggleTheme = onToggleTheme,
                        onToggleSound = onToggleSound,
                        soundManager = soundManager,
                        isSoundMuted = isSoundMuted
                    )
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 释放音效资源
        soundManager.release()
    }
}

