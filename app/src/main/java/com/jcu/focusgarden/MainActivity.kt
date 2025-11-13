package com.jcu.focusgarden

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jcu.focusgarden.data.local.FocusGardenDatabase
import com.jcu.focusgarden.data.preferences.SoundPreferences
import com.jcu.focusgarden.data.preferences.ThemePreferences
import com.jcu.focusgarden.data.repository.JournalRepository
import com.jcu.focusgarden.data.repository.SessionRepository
import com.jcu.focusgarden.service.MusicService
import com.jcu.focusgarden.ui.navigation.FocusGardenApp
import com.jcu.focusgarden.ui.theme.FocusGardenTheme
import com.jcu.focusgarden.utils.SoundManager
import com.jcu.focusgarden.viewmodel.DashboardViewModel
import com.jcu.focusgarden.viewmodel.TimerViewModel
import kotlinx.coroutines.launch

/**
 * Main Activity
 * FocusGarden 应用的入口点
 * 
 * Week 3-4: 基础架构
 * Week 5-6 Enhancement:
 * - 添加深色/浅色主题切换功能
 * - 添加背景音乐播放功能
 * - 添加音效反馈系统
 * - 使用 DataStore 持久化偏好设置
 * - ✅ Week 5-6 MVP: 初始化 Database + Repository + ViewModel
 */
class MainActivity : ComponentActivity() {
    
    private lateinit var themePreferences: ThemePreferences
    private lateinit var soundPreferences: SoundPreferences
    private lateinit var soundManager: SoundManager
    
    // Week 5-6: MVP Development - Database & ViewModel
    private lateinit var database: FocusGardenDatabase
    private lateinit var sessionRepository: SessionRepository
    private lateinit var journalRepository: JournalRepository
    private lateinit var timerViewModel: TimerViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    // Music service related
    private var musicService: MusicService? = null
    private var isMusicBound = false
    private val isMusicPlaying = mutableStateOf(false)
    
    // Service connection
    private val musicConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.MusicBinder
            musicService = binder.getService()
            isMusicBound = true
            isMusicPlaying.value = musicService?.isPlaying() ?: false
        }
        
        override fun onServiceDisconnected(name: ComponentName?) {
            isMusicBound = false
            musicService = null
            isMusicPlaying.value = false
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 初始化偏好管理器
        themePreferences = ThemePreferences(this)
        soundPreferences = SoundPreferences(this)
        soundManager = SoundManager(this)
        
        // Week 5-6: 初始化数据库和 Repository
        database = FocusGardenDatabase.getDatabase(this)
        sessionRepository = SessionRepository(database.sessionDao())
        journalRepository = JournalRepository(database.journalDao())
        
        // 初始化 TimerViewModel
        timerViewModel = TimerViewModel(
            sessionRepository = sessionRepository,
            journalRepository = journalRepository
        )
        
        // Week 5-6: Initialize DashboardViewModel (Phase E)
        dashboardViewModel = DashboardViewModel(
            sessionRepository = sessionRepository,
            journalRepository = journalRepository
        )
        
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
            
            // 背景音乐切换函数
            val onMusicToggle: () -> Unit = {
                if (isMusicPlaying.value) {
                    // 停止音乐
                    stopMusicService()
                } else {
                    // 开始音乐
                    startMusicService()
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
                        onMusicToggle = onMusicToggle,
                        isMusicPlaying = isMusicPlaying.value,
                        onToggleSound = onToggleSound,
                        soundManager = soundManager,
                        isSoundMuted = isSoundMuted,
                        timerViewModel = timerViewModel,
                        dashboardViewModel = dashboardViewModel
                    )
                }
            }
        }
    }
    
    /**
     * Start music service
     */
    private fun startMusicService() {
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_PLAY
        }
        startService(intent)
        
        // Bind service to get status updates
        if (!isMusicBound) {
            bindService(
                Intent(this, MusicService::class.java),
                musicConnection,
                Context.BIND_AUTO_CREATE
            )
        } else {
            musicService?.playRandomMusic()
        }
        
        isMusicPlaying.value = true
    }
    
    /**
     * Stop music service
     */
    private fun stopMusicService() {
        val intent = Intent(this, MusicService::class.java).apply {
            action = MusicService.ACTION_STOP
        }
        startService(intent)
        
        if (isMusicBound) {
            unbindService(musicConnection)
            isMusicBound = false
        }
        
        isMusicPlaying.value = false
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Unbind music service
        if (isMusicBound) {
            unbindService(musicConnection)
            isMusicBound = false
        }
        
        // 释放音效资源
        soundManager.release()
    }
}
